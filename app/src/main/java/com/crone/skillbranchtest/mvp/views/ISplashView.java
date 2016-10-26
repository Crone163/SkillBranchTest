package com.crone.skillbranchtest.mvp.views;

import android.content.Context;

import com.crone.skillbranchtest.mvp.presenters.ISplashPresenter;

/**
 * Created by CRN_soft on 26.10.2016.
 */

public interface ISplashView {

    void showMessage(String message);
    void showNetworkIssue();
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    ISplashPresenter getPresenter();

    Context getContext();

    void completeWriteData();
}
