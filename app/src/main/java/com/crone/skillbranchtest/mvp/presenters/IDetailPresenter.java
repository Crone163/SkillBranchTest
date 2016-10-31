package com.crone.skillbranchtest.mvp.presenters;

import android.support.annotation.Nullable;

import com.crone.skillbranchtest.mvp.models.DetailModel;
import com.crone.skillbranchtest.mvp.views.IDetailView;

/**
 * Created by CRN_soft on 01.11.2016.
 */

public interface IDetailPresenter {

    void takeView(IDetailView view);

    void dropView();

    void initView();

    void setTitle(String title);

    void setWords(String words);

    void setAliases(String aliases);

    void setBorn(String born);

    void setToolBarText(String name);

    void showDead(String season);

    void showFather(String name, int fatherId);

    void showMother(String name, int motherId);

    void setExpandImage(int housesId);

    void clickMother(int motherId);

    void clickFather(int fatherId);

    void sendInfoById(int houseId);

    @Nullable
    IDetailView getView();

    @Nullable
    DetailModel getModel();
}
