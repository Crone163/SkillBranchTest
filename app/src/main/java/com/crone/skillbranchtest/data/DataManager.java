package com.crone.skillbranchtest.data;

import android.content.Context;

import com.crone.skillbranchtest.MyApp;
import com.crone.skillbranchtest.models.DaoSession;
import com.crone.skillbranchtest.models.Persons;
import com.crone.skillbranchtest.models.Titles;
import com.crone.skillbranchtest.network.models.HousesModelRes;
import com.crone.skillbranchtest.network.models.PersonsModelRes;
import com.crone.skillbranchtest.network.rest.RestService;
import com.crone.skillbranchtest.network.rest.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by CRN_soft on 14.10.2016.
 */

public class DataManager {

    private RestService mRestService;
    private DaoSession mDaoSession;

    private DataManager() {

        mRestService = ServiceGenerator.createService(RestService.class);
        mDaoSession = MyApp.getDaoSession();
    }

    private static class DataMangerHolder {
        private final static DataManager INSTANCE = new DataManager();
    }

    public static DataManager getInstance() {
        return DataMangerHolder.INSTANCE;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }



    public Call<HousesModelRes> getHouse(String houseId) {
        return mRestService.getHouse(houseId);
    }

    public Call<PersonsModelRes> getPerson(String personId) {
        return mRestService.getPerson(personId);
    }



    public List<Persons> getHousePersonsFromDb(int homeId) {
        return new ArrayList<>();
    }

    public List<Titles> getHouseTitlesFromDb(int homeId) {
        return new ArrayList<>();
    }


}