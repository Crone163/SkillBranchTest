package com.crone.skillbranchtest.mvp.models;

/**
 * Created by CRN_soft on 26.10.2016.
 */

public interface ModelCallback {

    void onFinish();

    void onError(String message);
}
