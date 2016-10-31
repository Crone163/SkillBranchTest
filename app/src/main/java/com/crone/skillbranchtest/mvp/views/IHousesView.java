package com.crone.skillbranchtest.mvp.views;

import com.crone.skillbranchtest.mvp.presenters.IHousesPresenter;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public interface IHousesView {


    void openPageStark();
    void openPageLann();
    void openPagerTarg();
    void setChecked(int position);

    void showMessage(String message);
    void showError(Throwable e);

    IHousesPresenter getPresenter();

}
