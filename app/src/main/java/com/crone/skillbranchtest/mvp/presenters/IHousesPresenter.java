package com.crone.skillbranchtest.mvp.presenters;

import android.support.annotation.Nullable;

import com.crone.skillbranchtest.mvp.views.IHousesFragmentView;
import com.crone.skillbranchtest.mvp.views.IHousesView;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public interface IHousesPresenter {

    void takeView(IHousesView view);

    void dropView();

    void initView();


    void openPageStark();
    void openPageLann();
    void openPageTarg();
    void setChecked(int position);

    @Nullable
    IHousesView getView();
}
