package com.crone.skillbranchtest.utils;

import android.content.Intent;

import com.crone.skillbranchtest.data.managers.DataManager;
import com.crone.skillbranchtest.data.storage.models.HousesDao;
import com.crone.skillbranchtest.data.storage.models.Persons;
import com.crone.skillbranchtest.data.storage.models.PersonsDao;
import com.crone.skillbranchtest.data.storage.models.Titles;
import com.crone.skillbranchtest.data.storage.models.TitlesDao;

import java.util.List;

public class SetIntentParams {

    public static void setParams(Intent intent, long id) {

        PersonsDao mPersonDao = DataManager.getInstance().getDaoSession().getPersonsDao();
        HousesDao mHouseDao = DataManager.getInstance().getDaoSession().getHousesDao();
        TitlesDao mTitlesDao = DataManager.getInstance().getDaoSession().getTitlesDao();

        //Getting Titile + Aliases
        List<Titles> listTitles = mTitlesDao.queryBuilder()
                .where(TitlesDao.Properties.TitlePersonRemoteId.eq(id))
                .list();

        String alies = "";
        for (int d = 0; d < listTitles.size(); d++) {
            if (listTitles.get(d).getIsTitle()) {
                intent.putExtra("title", listTitles.get(d).getCharacteristic());
            } else {
                alies += listTitles.get(d).getCharacteristic() + "\n";
            }
        }
        intent.putExtra("alies", alies);

        List<Persons> personsList = mPersonDao.queryBuilder()
                .where(PersonsDao.Properties.PersonRemoteId.eq(id))
                .list();

        //Getting HouseID
        intent.putExtra("house_id",personsList.get(0).getPersonHouseRemoteId());
        //Getting Name
        intent.putExtra("name", personsList.get(0).getName());
        //Getting Born
        intent.putExtra("born", personsList.get(0).getBorn());
        //Getting Died
        intent.putExtra("died", personsList.get(0).getDied());
        //Getting Mother
        if (personsList.get(0).getMother() != null) {
            List<Persons> parentList = mPersonDao.queryBuilder()
                    .where(PersonsDao.Properties.PersonRemoteId.eq(personsList.get(0).getMother()))
                    .list();

            intent.putExtra("mother_name", parentList.get(0).getName());
            intent.putExtra("mother", personsList.get(0).getMother());
        }
        //Getting Father
        if (personsList.get(0).getFather() != null) {
            List<Persons> parentList = mPersonDao.queryBuilder()
                    .where(PersonsDao.Properties.PersonRemoteId.eq(personsList.get(0).getFather()))
                    .list();

            intent.putExtra("father_name", parentList.get(0).getName());
            intent.putExtra("father", personsList.get(0).getFather());
        }
        //Getting Words
        for (int i = 0; i < mHouseDao.count(); i++) {
            if (mHouseDao.queryBuilder().list().get(i).getRemoteId().equals(personsList.get(0).getPersonHouseRemoteId())) {
                intent.putExtra("words", mHouseDao.queryBuilder().list().get(i).getWords());
            }
        }

    }

}
