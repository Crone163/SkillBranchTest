package com.crone.skillbranchtest.mvp.models;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.managers.DataManager;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.utils.MyConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public class DetailModel {

    private DataManager mDataManager;

    public DetailModel(){
        mDataManager = DataManager.getInstance();
    }

    public void findInfoById(int houseId){
        mDataManager.findInfoById(houseId);
    }
}
