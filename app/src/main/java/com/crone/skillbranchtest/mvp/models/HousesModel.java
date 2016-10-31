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

public class HousesModel {

    private DataManager mDataManager;

    public HousesModel() {
        mDataManager = DataManager.getInstance();
    }

    /**
     * Getting data for RecycleView
     *
     * @param position - tabs number
     * @return ArrayList with items
     */
    public ArrayList<ItemsData> getDataByPosition(int position) {
        Map<String, ArrayList<ItemsData>> data = EventBus.getDefault().getStickyEvent(HashMap.class);
        switch (position) {
            case MyConfig.STARK_PAGER:
                return data.get(MyConfig.STARK_ARG);
            case MyConfig.LANNISTER_PAGER:
                return data.get(MyConfig.LANNISTER_ARG);
            case MyConfig.TARGARYEN_PAGER:
                return data.get(MyConfig.TARGARYEN_ARG);
            default:
                return null;
        }
    }

    /**
     * Getting icon for tabs
     *
     * @param tabsNumber - tabs number
     * @return int resource
     */
    public int getIcon(int tabsNumber) {
        switch (tabsNumber) {
            case MyConfig.STARK_PAGER:
                return R.drawable.stark_icon;
            case MyConfig.LANNISTER_PAGER:
                return R.drawable.lanister_icon;
            case MyConfig.TARGARYEN_PAGER:
                return R.drawable.targ_icon;
            default:
                return R.mipmap.ic_launcher;
        }
    }

    public void findInfoById(int houseId) {
        mDataManager.findInfoById(houseId);
    }
}
