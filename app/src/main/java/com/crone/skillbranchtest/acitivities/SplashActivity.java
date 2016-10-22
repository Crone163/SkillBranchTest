package com.crone.skillbranchtest.acitivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.DataManager;
import com.crone.skillbranchtest.models.Houses;
import com.crone.skillbranchtest.models.HousesDao;
import com.crone.skillbranchtest.models.ItemsData;
import com.crone.skillbranchtest.models.Persons;
import com.crone.skillbranchtest.models.PersonsDao;
import com.crone.skillbranchtest.models.Titles;
import com.crone.skillbranchtest.models.TitlesDao;
import com.crone.skillbranchtest.network.models.HousesModelRes;
import com.crone.skillbranchtest.network.models.PersonsModelRes;
import com.crone.skillbranchtest.utils.MyConfig;
import com.crone.skillbranchtest.utils.NetworkStatusChecker;


import org.greenrobot.eventbus.EventBus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {


    private CoordinatorLayout mCoordinatorLayout;

    private HousesDao mHouseDao;
    private TitlesDao mTitlesDao;
    private PersonsDao mPersonDao;
    private DataManager mDataManager;
    private List<Persons> persons = new ArrayList<>();
    private List<Houses> houses = new ArrayList<>();
    private List<Titles> titles = new ArrayList<>();

    private ArrayList<ItemsData> starkItems = new ArrayList<>();
    private ArrayList<ItemsData> lannItems = new ArrayList<>();
    private ArrayList<ItemsData> targItems = new ArrayList<>();

    private int checkSessions;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);



        checkSessions = 0;
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.splash_coordinator);
        mDataManager = DataManager.getInstance();


        mSharedPreferences = getPreferences(MODE_PRIVATE);


        mHouseDao = mDataManager.getDaoSession().getHousesDao();
        mTitlesDao = mDataManager.getDaoSession().getTitlesDao();
        mPersonDao = mDataManager.getDaoSession().getPersonsDao();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //If data in DB open activity without parse
        if(mSharedPreferences.getBoolean(MyConfig.ALREADY_SAVED,false)){
            startWriteData();
            return;
        }
        if (!NetworkStatusChecker.isNetworkAvailable(this) ) {
            showSnackbar("Network Issue.");
            return;
        }
        processHouse(MyConfig.STARK_ID);
        processHouse(MyConfig.LANNISTER_ID);
        processHouse(MyConfig.TARGARYEN_ID);
    }

    private void showSnackbar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }


    private void processHouse(final int id) {
        if (NetworkStatusChecker.isNetworkAvailable(this)) {

            Call<HousesModelRes> call = mDataManager.getHouse(String.valueOf(id));
            checkSessions++;
            call.enqueue(new Callback<HousesModelRes>() {
                @Override
                public void onResponse(Call<HousesModelRes> call, Response<HousesModelRes> response) {
                    if (response.code() == 200) {
                        houses.add(new Houses((long) id, response.body()));
                        List<String> housePersonUrls = response.body().getPersonsUrls();
                        for (String urlPerson : housePersonUrls) {
                            processPersonUrl(id, urlPerson);
                        }
                    } else if (response.code() == 404) {
                        showSnackbar("House not found");
                    } else {
                        showSnackbar("Error! House not found ID: " + String.valueOf(id));
                    }
                    launchMainActivity();
                }

                @Override
                public void onFailure(Call<HousesModelRes> call, Throwable t) {
                    showSnackbar("Error! Can't get Info about House");
                }
            });
        }
    }

    private void processPersonUrl(final int houseId, String url) {
        String[] parts = url.split("/");
        final String personId = parts[parts.length - 1];
        Call<PersonsModelRes> call = mDataManager.getPerson(personId);
        checkSessions++;
        call.enqueue(new Callback<PersonsModelRes>() {
            @Override
            public void onResponse(Call<PersonsModelRes> call, Response<PersonsModelRes> response) {
                if (response.code() == 200) {
                    persons.add(new Persons((long) houseId, Long.valueOf(personId), response.body()));
                    for (String title : response.body().getTitles()) {
                        titles.add(new Titles(Long.valueOf(personId), true, title));
                    }
                    for (String alias : response.body().getAliases()) {
                        titles.add(new Titles(Long.valueOf(personId), false, alias));
                    }
                    //Parse Mother and Father in DB
                    if (response.body().getMother() != null) {
                        processPersonUrl(houseId, MyConfig.CHARACTERS_URL + response.body().getMother());
                    }
                    if (response.body().getFather() != null) {
                        processPersonUrl(houseId, MyConfig.CHARACTERS_URL + response.body().getFather());
                    }
                } else if (response.code() == 404) {
                    showSnackbar("Person not found");
                } else {
                    showSnackbar("Error! Person not found ID: " + String.valueOf(houseId));
                }
                launchMainActivity();
            }

            @Override
            public void onFailure(Call<PersonsModelRes> call, Throwable t) {
                showSnackbar("Error! Can't get Info about Person");
            }
        });
    }


    void launchMainActivity() {

        checkSessions--;
        if (checkSessions != 0) {
            return;
        }


        mHouseDao.insertOrReplaceInTx(houses);
        mPersonDao.insertOrReplaceInTx(persons);
        mTitlesDao.insertOrReplaceInTx(titles);
        //Saved
        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putBoolean(MyConfig.ALREADY_SAVED, true);
        ed.apply();

        startWriteData();


    }


    void startWriteData(){
        //Start new Thread where happens collection of specific information
        final Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //delay
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < mPersonDao.count(); i++) {

                    ItemsData data = new ItemsData();
                    data.id = mPersonDao.queryBuilder().list().get(i).getPersonRemoteId();
                    data.idHouse = mPersonDao.queryBuilder().list().get(i).getPersonHouseRemoteId();
                    data.name = mPersonDao.queryBuilder().list().get(i).getName();
                    List<Titles> t = mPersonDao.queryBuilder().list().get(i).getCharacteristics();
                    data.titles = "";
                    for (int n = 0; n < t.size(); n++) {
                        if (n != t.size() - 1) {
                            data.titles += t.get(n).getCharacteristic() + ", ";
                        } else {
                            data.titles += t.get(n).getCharacteristic();
                        }
                    }

                    if (data.idHouse == MyConfig.STARK_ID) {
                        starkItems.add(data);
                    } else if (data.idHouse == MyConfig.LANNISTER_ID) {
                        lannItems.add(data);
                    } else {
                        targItems.add(data);
                    }


                }
                Map<String, ArrayList<ItemsData>> data = new HashMap<>();
                data.put(MyConfig.STARK_ARG,starkItems);
                data.put(MyConfig.LANNISTER_ARG,lannItems);
                data.put(MyConfig.TARGARYEN_ARG,targItems);
                EventBus.getDefault().postSticky(data);

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);


                finish();

            }
        });
        myThread.start();
    }


}
