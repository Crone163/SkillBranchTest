package com.crone.skillbranchtest.mvp.presenters;

import android.support.annotation.Nullable;

import com.crone.skillbranchtest.mvp.models.DetailModel;
import com.crone.skillbranchtest.mvp.views.IDetailView;

/**
 * Created by CRN_soft on 01.11.2016.
 */

public class DetailPresenter implements IDetailPresenter {

    private static DetailPresenter ourInstance = new DetailPresenter();
    private IDetailView mView;
    private DetailModel mDetailModel;

    private DetailPresenter() {
        mDetailModel = new DetailModel();
    }

    public static DetailPresenter getInstance() {
        return ourInstance;
    }

    @Override
    public void takeView(IDetailView view) {
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
    public void setTitle(String title) {
        if (getView() != null) {
            getView().setTitle(title);
        }
    }

    @Override
    public void setWords(String words) {
        if (getView() != null) {
            getView().setWords(words);
        }
    }

    @Override
    public void setAliases(String aliases) {
        if (getView() != null) {
            getView().setAliases(aliases);
        }
    }

    @Override
    public void setBorn(String born) {
        if (getView() != null) {
            getView().setBorn(born);
        }
    }

    @Override
    public void setToolBarText(String name) {
        if (getView() != null) {
            getView().setToolBarText(name);
        }
    }

    @Override
    public void showDead(String season) {
        if (getView() != null) {
            if (season != null && season.length() > 0)
                getView().showDead(season);
        }
    }

    @Override
    public void showFather(String name, int fatherId) {
        if (getView() != null) {
            if (fatherId != -1) {
                getView().showFather(name);
                getView().clickFather(fatherId);
            }
        }
    }

    @Override
    public void showMother(String name, int motherId) {
        if (getView() != null) {
            if (motherId != -1) {
                getView().showMother(name);
                getView().clickMother(motherId);
            }
        }
    }

    @Override
    public void setExpandImage(int housesId) {
        if (getView() != null) {
            getView().setExpandImage(housesId);
        }
    }

    @Override
    public void clickMother(int motherId) {
        if (getModel() != null) {
            getModel().findInfoById(motherId);
        }
    }

    @Override
    public void clickFather(int fatherId) {
        if (getModel() != null) {
            getModel().findInfoById(fatherId);
        }
    }

    @Override
    public void sendInfoById(int houseId) {
        if (getModel() != null)
            getModel().findInfoById(houseId);
    }

    @Nullable
    @Override
    public IDetailView getView() {
        return mView;
    }

    @Nullable
    @Override
    public DetailModel getModel() {
        return mDetailModel;
    }
}
