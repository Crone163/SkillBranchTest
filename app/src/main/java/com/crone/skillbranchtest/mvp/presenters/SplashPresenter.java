package com.crone.skillbranchtest.mvp.presenters;

import com.crone.skillbranchtest.mvp.models.SplashModel;
import com.crone.skillbranchtest.mvp.models.ModelCallback;
import com.crone.skillbranchtest.mvp.views.ISplashView;
import com.crone.skillbranchtest.utils.NetworkStatusChecker;

/**
 * Created by CRN_soft on 26.10.2016.
 */

public class SplashPresenter implements ISplashPresenter, ModelCallback {

    private static SplashPresenter ourInstance = new SplashPresenter();

    private SplashModel mSplashModel;
    private ISplashView mSplashView;

    private SplashPresenter() {
        mSplashModel = new SplashModel();
    }

    public static SplashPresenter getInstance() {
        return ourInstance;
    }

    //region ================= ISplashPresenter ===================
    @Override
    public void takeView(ISplashView view) {
        mSplashView = view;
    }

    @Override
    public void dropView() {
        mSplashView = null;
    }

    @Override
    public void initView() {
        if (getView() != null) {
            if(mSplashModel.dataAlreadySet()){
                getView().showLoad();
                mSplashModel.loadData(this);
            } else {
                if (!NetworkStatusChecker.isNetworkAvailable(getView().getContext())) {
                    getView().showNetworkIssue();
                    getView().hideLoad();
                } else {
                    getView().showLoad();
                    mSplashModel.loadData(this);
                }
            }
        }
    }

    @Override
    public ISplashView getView() {
        return mSplashView;
    }
    //endregion

    //region ================ ModelCallback ====================
    @Override
    public void onFinish() {
        getView().completeWriteData();
    }

    @Override
    public void onError(String message) {
        getView().showMessage(message);
    }
    //endregion

}
