package com.crone.skillbranchtest.mvp.views;

/**
 * Created by CRN_soft on 31.10.2016.
 */

public interface IDetailView {

    void setTitle(String title);
    void setWords(String words);
    void setAliases(String aliases);
    void setBorn(String born);
    void setToolBarText(String name);
    void showDead(String season);
    void showFather(String name);
    void showMother(String name);
    void setExpandImage(int housesId);
    void clickMother(int motherId);
    void clickFather(int fatherId);

    void showMessage(String message);
    void showError(Throwable e);

    //IDetailPresenter getPresenter();

}
