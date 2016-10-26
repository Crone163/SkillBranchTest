package com.crone.skillbranchtest.ui.acitivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crone.skillbranchtest.BuildConfig;
import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.mvp.presenters.ISplashPresenter;
import com.crone.skillbranchtest.mvp.presenters.SplashPresenter;
import com.crone.skillbranchtest.mvp.views.ISplashView;
import com.crone.skillbranchtest.ui.custom_views.dotprogress.DotProgressBar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements ISplashView {

    SplashPresenter mPresenter = SplashPresenter.getInstance();
    private final static long APP_START_TIME = System.currentTimeMillis();
    private static final long SLEEP_THREAD = 3000;

    @BindView(R.id.splash_coordinator)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.dot_progress_bar)
    DotProgressBar mDotProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mPresenter.takeView(this);
        mPresenter.initView();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }


    //region ================= ISplashView =================

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkIssue() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.network_issue), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.network_issue_button_text), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.initView();
                    }
                }).setActionTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error_enable_debug));
        }
    }

    @Override
    public void showLoad() {
        if (mDotProgressBar != null)
            mDotProgressBar.startAnimation();

    }

    @Override
    public void hideLoad() {
        if (mDotProgressBar != null)
            mDotProgressBar.stopAnimation();
    }

    @Override
    public ISplashPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * Берём время между открытием активити и выполнением колбека о завершение парсинга, записи бд
     * и вычитаем разницу недостающего времени для условия SplashActivity wait >= 3000ms
     */
    @Override
    public void completeWriteData() {
        if (System.currentTimeMillis() - APP_START_TIME < SLEEP_THREAD) {
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //delay
                        Thread.sleep(SLEEP_THREAD - (System.currentTimeMillis() - APP_START_TIME));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    launchMainActivity();
                }
            });
            myThread.start();
        } else {
            launchMainActivity();
        }
    }
    //endregion

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(SplashActivity.this);
    }

}
