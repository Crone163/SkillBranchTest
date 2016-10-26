package com.crone.skillbranchtest.mvp.presenters;

import com.crone.skillbranchtest.mvp.views.ISplashView;

/**
 * Created by CRN_soft on 26.10.2016.
 */

public interface ISplashPresenter {
    void takeView(ISplashView view);
    void dropView();
    void initView();
    ISplashView getView();
}
