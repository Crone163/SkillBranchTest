package com.crone.skillbranchtest.mvp.presenters;

import android.support.annotation.Nullable;

import com.crone.skillbranchtest.mvp.views.IHousesView;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public class HousesPresenter implements IHousesPresenter {

    private static HousesPresenter ourInstance = new HousesPresenter();
    private IHousesView mView;

    public static HousesPresenter getInstance() {
        return ourInstance;
    }


    @Override
    public void takeView(IHousesView view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void initView() {

    }


    @Override
    public void openPageStark() {
        mView.openPageStark();
    }

    @Override
    public void openPageLann() {
        mView.openPageLann();
    }

    @Override
    public void openPageTarg() {
        mView.openPagerTarg();
    }

    @Override
    public void setChecked(int position) {
        if(getView()!=null){
            getView().setChecked(position);
        }

    }

    @Nullable
    @Override
    public IHousesView getView() {
        return mView;
    }
}
