package com.crone.skillbranchtest.data.managers;

import com.crone.skillbranchtest.MyApp;
import com.crone.skillbranchtest.data.storage.models.CharactersInfo;
import com.crone.skillbranchtest.data.storage.models.DaoSession;
import com.crone.skillbranchtest.data.storage.models.HousesDao;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.data.network.responces.HousesModelRes;
import com.crone.skillbranchtest.data.network.responces.PersonsModelRes;

import com.crone.skillbranchtest.data.network.RestService;
import com.crone.skillbranchtest.data.network.ServiceGenerator;

import com.crone.skillbranchtest.data.network.RestService;
import com.crone.skillbranchtest.data.network.ServiceGenerator;
import com.crone.skillbranchtest.data.storage.models.Persons;
import com.crone.skillbranchtest.data.storage.models.PersonsDao;
import com.crone.skillbranchtest.data.storage.models.Titles;
import com.crone.skillbranchtest.data.storage.models.TitlesDao;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.util.Log;

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


    //region ====================== Transfer Data to EventBus ==========================
    public void setDataToEventBus(Map<String, ArrayList<ItemsData>> data) {
        EventBus.getDefault().postSticky(data);
    }

    /**
     * This function will be used in FragmentItems and DetailActivity
     * @param id - houseId
     */
    public void findInfoById(long id) {

        CharactersInfo charactersInfo = new CharactersInfo();

        PersonsDao mPersonDao = mDaoSession.getPersonsDao();
        HousesDao mHouseDao = mDaoSession.getHousesDao();
        TitlesDao mTitlesDao = mDaoSession.getTitlesDao();

        //Getting Titile + Aliases
        List<Titles> listTitles = mTitlesDao.queryBuilder()
                .where(TitlesDao.Properties.TitlePersonRemoteId.eq(id))
                .list();

        String aliases = "";
        for (int d = 0; d < listTitles.size(); d++) {
            if (listTitles.get(d).getIsTitle()) {
                charactersInfo.title = listTitles.get(d).getCharacteristic();
            } else {
                aliases += listTitles.get(d).getCharacteristic() + "\n";
            }
        }
        charactersInfo.aliases = aliases;

        List<Persons> personsList = mPersonDao.queryBuilder()
                .where(PersonsDao.Properties.PersonRemoteId.eq(id))
                .list();

        //Getting HouseID
        charactersInfo.houseId = personsList.get(0).getPersonHouseRemoteId();
        //Getting Name
        charactersInfo.name = personsList.get(0).getName();
        //Getting Born
        charactersInfo.born = personsList.get(0).getBorn();
        //Getting Died
        charactersInfo.died = personsList.get(0).getDied();
        //Getting Mother
        if (personsList.get(0).getMother() != null) {
            List<Persons> parentList = mPersonDao.queryBuilder()
                    .where(PersonsDao.Properties.PersonRemoteId.eq(personsList.get(0).getMother()))
                    .list();

            charactersInfo.motherName = parentList.get(0).getName();
            charactersInfo.motherId = personsList.get(0).getMother().intValue();
        }
        //Getting Father
        if (personsList.get(0).getFather() != null) {
            List<Persons> parentList = mPersonDao.queryBuilder()
                    .where(PersonsDao.Properties.PersonRemoteId.eq(personsList.get(0).getFather()))
                    .list();

            charactersInfo.fatherName = parentList.get(0).getName();
            charactersInfo.fatherId = personsList.get(0).getFather().intValue();
        }
        //Getting Words
        for (int i = 0; i < mHouseDao.count(); i++) {
            if (mHouseDao.queryBuilder().list().get(i).getRemoteId().equals(personsList.get(0).getPersonHouseRemoteId())) {
                charactersInfo.words = mHouseDao.queryBuilder().list().get(i).getWords();
            }
        }
        EventBus.getDefault().postSticky(charactersInfo);
    }
    //endregion

}