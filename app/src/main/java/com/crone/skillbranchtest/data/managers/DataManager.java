package com.crone.skillbranchtest.data.managers;

import com.crone.skillbranchtest.MyApp;
import com.crone.skillbranchtest.data.storage.models.DaoSession;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.data.network.responces.HousesModelRes;
import com.crone.skillbranchtest.data.network.responces.PersonsModelRes;
import com.crone.skillbranchtest.data.network.RestService;
import com.crone.skillbranchtest.data.network.ServiceGenerator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by CRN_soft on 14.10.2016.
 */

public class DataManager {

    private static DataManager INSTANCE = null;

    private RestService mRestService;
    private DaoSession mDaoSession;
    private PreferencesManager mPreferencesManager;

    private DataManager() {
        mPreferencesManager = PreferencesManager.getInstance();
        mRestService = ServiceGenerator.createService(RestService.class);
        mDaoSession = MyApp.getDaoSession();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    //region =================== GreenDAO ORM =======================
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    //endregion

    //region ================== Rest Service ========================
    public Call<HousesModelRes> getHouse(String houseId) {
        return mRestService.getHouse(houseId);
    }

    public Call<PersonsModelRes> getPerson(String personId) {
        return mRestService.getPerson(personId);
    }
    //endregion


    //region ======================= SharedPreferences ===============
    public void saveStateInPreferences(boolean save) {
        mPreferencesManager.firstComplete(save);
    }

    public boolean getStateFromPreferences() {
        return mPreferencesManager.alreadySaved();
    }
    //endregion



    //region ====================== EventBus ==========================
    public void setDataToEventBus(Map<String, ArrayList<ItemsData>> data){
        EventBus.getDefault().postSticky(data);
    }
    //endregion




}