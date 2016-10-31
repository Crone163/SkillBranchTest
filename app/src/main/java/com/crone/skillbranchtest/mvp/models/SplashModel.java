package com.crone.skillbranchtest.mvp.models;

import com.crone.skillbranchtest.data.managers.DataManager;
import com.crone.skillbranchtest.data.storage.models.Houses;
import com.crone.skillbranchtest.data.storage.models.HousesDao;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.data.storage.models.Persons;
import com.crone.skillbranchtest.data.storage.models.PersonsDao;
import com.crone.skillbranchtest.data.storage.models.Titles;
import com.crone.skillbranchtest.data.storage.models.TitlesDao;
import com.crone.skillbranchtest.data.network.responces.HousesModelRes;
import com.crone.skillbranchtest.data.network.responces.PersonsModelRes;
import com.crone.skillbranchtest.utils.MyConfig;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CRN_soft on 26.10.2016.
 */

public class SplashModel {

    private DataManager mDataManager;
    private ModelCallback mCallback;

    private HousesDao mHouseDao;
    private TitlesDao mTitlesDao;
    private PersonsDao mPersonDao;

    private List<Persons> persons = new ArrayList<>();
    private List<Houses> houses = new ArrayList<>();
    private List<Titles> titles = new ArrayList<>();

    private int checkSessions;

    public SplashModel() {
        mDataManager = DataManager.getInstance();
        mHouseDao = mDataManager.getDaoSession().getHousesDao();
        mTitlesDao = mDataManager.getDaoSession().getTitlesDao();
        mPersonDao = mDataManager.getDaoSession().getPersonsDao();
    }

    public boolean dataAlreadySet() {
        return mDataManager.getStateFromPreferences();
    }

    public void loadData(ModelCallback callback) {
        mCallback = callback;
        if (!dataAlreadySet()) {
            processHouse(MyConfig.STARK_ID);
            processHouse(MyConfig.LANNISTER_ID);
            processHouse(MyConfig.TARGARYEN_ID);
        } else {
            startWriteData();
        }
    }

    private void processHouse(final int id) {
        checkSessions++;
        Call<HousesModelRes> call = mDataManager.getHouse(String.valueOf(id));
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
                    mCallback.onError("House not found");

                } else {
                    mCallback.onError("Error! House not found ID: " + String.valueOf(id));
                }
                saveInDB();
            }

            @Override
            public void onFailure(Call<HousesModelRes> call, Throwable t) {
                mCallback.onError("Error! Can't get Info about House");
            }
        });

    }

    private void processPersonUrl(final int houseId, String url) {
        checkSessions++;
        String[] parts = url.split("/");
        final String personId = parts[parts.length - 1];
        Call<PersonsModelRes> call = mDataManager.getPerson(personId);
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
                    mCallback.onError("Person not found");
                } else {
                    mCallback.onError("Error! Person not found ID: " + String.valueOf(houseId));
                }

                saveInDB();
            }

            @Override
            public void onFailure(Call<PersonsModelRes> call, Throwable t) {
                mCallback.onError("Error! Can't get Info about Person");
            }
        });
    }

    private void saveInDB() {
        // когда пришел последний ответ, то можно начинать запись в бд
        checkSessions--;
        if (checkSessions != 0) {
            return;
        }

        mHouseDao.insertOrReplaceInTx(houses);
        mPersonDao.insertOrReplaceInTx(persons);
        mTitlesDao.insertOrReplaceInTx(titles);
        mDataManager.saveStateInPreferences(true);
        startWriteData();
    }

    /**
     * Чтобы не лагал ProgressDot, запускаем операцию в отдельном потоке.
     */
    private void startWriteData() {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ItemsData> starkItems = new ArrayList<>();
                ArrayList<ItemsData> lannItems = new ArrayList<>();
                ArrayList<ItemsData> targItems = new ArrayList<>();
                for (int i = 0; i < mPersonDao.count(); i++) {
                    ItemsData data = new ItemsData();
                    //берём только нужные данные(айди,айди дома, имя, тайтл и алиасес)
                    data.id = mPersonDao.queryBuilder().list().get(i).getPersonRemoteId();
                    data.idHouse = mPersonDao.queryBuilder().list().get(i).getPersonHouseRemoteId();
                    data.name = mPersonDao.queryBuilder().list().get(i).getName();
                    //берем тайтл и алиасес
                    List<Titles> t = mPersonDao.queryBuilder().list().get(i).getCharacteristics();
                    data.titles = "";
                    //раставляем запятые
                    for (int n = 0; n < t.size(); n++) {
                        if (n != t.size() - 1) {
                            data.titles += t.get(n).getCharacteristic() + ", ";
                        } else {
                            data.titles += t.get(n).getCharacteristic();
                        }
                    }
                    //определяем к какому дому относится наш персонаж
                    switch ((int) data.idHouse) {
                        case MyConfig.STARK_ID:
                            starkItems.add(data);
                            break;
                        case MyConfig.LANNISTER_ID:
                            lannItems.add(data);
                            break;
                        case MyConfig.TARGARYEN_ID:
                            targItems.add(data);
                            break;
                    }

                }
                //передаем мапу с данными
                Map<String, ArrayList<ItemsData>> data = new HashMap<>();
                data.put(MyConfig.STARK_ARG, starkItems);
                data.put(MyConfig.LANNISTER_ARG, lannItems);
                data.put(MyConfig.TARGARYEN_ARG, targItems);
                //Упаковываем распарсенные данные в EventBus
                mDataManager.setDataToEventBus(data);
                //говорим что закончили работу
                mCallback.onFinish();

            }
        });
        myThread.start();
    }
}
