package com.crone.skillbranchtest.mvp.presenters;

import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.mvp.views.IHousesFragmentView;

import java.util.ArrayList;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public interface IHousesFragmentPresenter {

    void takeView(IHousesFragmentView view);

    void dropView();

    void initView();

    int getIconByPosition(int position);

    ArrayList<ItemsData> getDataByPosition(int position);

    void onItemClick(ArrayList<ItemsData> data, int positionItem);

    void sendInfoById(int houseId);

    IHousesFragmentView getView();
}
