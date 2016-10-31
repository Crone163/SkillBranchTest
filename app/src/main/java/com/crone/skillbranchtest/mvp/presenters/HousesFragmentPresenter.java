package com.crone.skillbranchtest.mvp.presenters;

import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.mvp.models.HousesModel;
import com.crone.skillbranchtest.mvp.views.IHousesFragmentView;

import java.util.ArrayList;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public class HousesFragmentPresenter implements IHousesFragmentPresenter {

    private static HousesFragmentPresenter ourInstance = new HousesFragmentPresenter();
    private IHousesFragmentView mView;
    private HousesModel mHousesModel;

    private HousesFragmentPresenter() {
        mHousesModel = new HousesModel();
    }

    public static HousesFragmentPresenter getInstance() {
        return ourInstance;
    }


    @Override
    public void takeView(IHousesFragmentView view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void initView() {
        getView().initData();
    }


    @Override
    public void onItemClick(ArrayList<ItemsData> data, int positionItem) {
       getView().onItemClick((int)data.get(positionItem).id);
    }

    @Override
    public void sendInfoById(int houseId) {
        mHousesModel.findInfoById(houseId);
    }


    @Override
    public int getIconByPosition(int position) {
        return mHousesModel.getIcon(position);
    }


    @Override
    public ArrayList<ItemsData> getDataByPosition(int position) {
        return mHousesModel.getDataByPosition(position);
    }


    @Override
    public IHousesFragmentView getView() {
        return mView;
    }
}
