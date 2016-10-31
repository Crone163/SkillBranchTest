package com.crone.skillbranchtest.mvp.views;

import com.crone.skillbranchtest.mvp.presenters.IHousesFragmentPresenter;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public interface IHousesFragmentView {

    IHousesFragmentPresenter getPresenter();

    void initData();

    void onItemClick(int houseId);
}
