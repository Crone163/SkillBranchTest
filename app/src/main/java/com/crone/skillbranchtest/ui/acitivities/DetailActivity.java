package com.crone.skillbranchtest.ui.acitivities;

import android.content.Intent;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crone.skillbranchtest.BuildConfig;
import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.storage.models.CharactersInfo;
import com.crone.skillbranchtest.mvp.presenters.DetailPresenter;
import com.crone.skillbranchtest.mvp.views.IDetailView;
import com.crone.skillbranchtest.utils.MyConfig;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements IDetailView {

    DetailPresenter mPresenter = DetailPresenter.getInstance();

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout mCollapsToolbar;
    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;
    @BindView(R.id.expanded_image)
    ImageView mExpandedImage;
    @BindView(R.id.words_text)
    TextView mWords;
    @BindView(R.id.aliases_text)
    TextView mAliases;
    @BindView(R.id.born_text)
    TextView mBorn;
    @BindView(R.id.titles_text)
    TextView mTitle;
    @BindView(R.id.father)
    TextView mFatherText;
    @BindView(R.id.mother)
    TextView mMotherText;
    @BindView(R.id.father_check)
    Button mFatherCheck;
    @BindView(R.id.mother_check)
    Button mMotherCheck;

    private boolean startOnce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mPresenter.takeView(this);
        mPresenter.initView();

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Make statusbar Transparent when Expanded Appbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG).show();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CharactersInfo info) {
        //для экономии памяти, стикиевент выполняется один раз, чтобы не хранить предыдущие данные в stickyevent
        if (!startOnce) {
            mPresenter.setExpandImage(info.houseId.intValue());
            mPresenter.setToolBarText(info.name);
            mPresenter.setAliases(info.aliases);
            mPresenter.setWords(info.words);
            mPresenter.setBorn(info.born);
            mPresenter.setTitle(info.title);
            mPresenter.showDead(info.died);
            mPresenter.showFather(info.fatherName, info.fatherId);
            mPresenter.showMother(info.motherName, info.motherId);
            startOnce = true;
        }
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setWords(String words) {
        mWords.setText(words);
    }

    @Override
    public void setAliases(String aliases) {
        mAliases.setText(aliases);
    }

    @Override
    public void setBorn(String born) {
        mBorn.setText(born);
    }

    @Override
    public void setToolBarText(String name) {
        mCollapsToolbar.setTitle(name);
        mToolbar.setTitle(name);
    }

    @Override
    public void showDead(String season) {
        showSnackbar("He was Dead: " + season);

    }

    @Override
    public void showFather(String name) {
        mFatherText.setVisibility(View.VISIBLE);
        mFatherCheck.setVisibility(View.VISIBLE);
        mFatherCheck.setText(name);
    }

    @Override
    public void showMother(String name) {
        mMotherText.setVisibility(View.VISIBLE);
        mMotherCheck.setText(name);
        mMotherCheck.setVisibility(View.VISIBLE);
    }

    @Override
    public void setExpandImage(int housesId) {
        switch (housesId) {
            case MyConfig.STARK_ID:
                Picasso.with(this).load(R.drawable.starks).fit().into(mExpandedImage);
                break;
            case MyConfig.LANNISTER_ID:
                Picasso.with(this).load(R.drawable.lannister).fit().into(mExpandedImage);
                break;
            case MyConfig.TARGARYEN_ID:
                Picasso.with(this).load(R.drawable.targarien).fit().into(mExpandedImage);
                break;
        }
    }

    @Override
    public void clickMother(final int motherId) {
        mMotherCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendInfoById(motherId);
                Intent intentReOpen = new Intent(DetailActivity.this, DetailActivity.class);
                startActivity(intentReOpen);
            }
        });

    }

    @Override
    public void clickFather(final int fatherId) {
        mFatherCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendInfoById(fatherId);
                Intent intentReOpen = new Intent(DetailActivity.this, DetailActivity.class);
                startActivity(intentReOpen);
            }
        });

    }

    @Override
    public void showMessage(String message) {
        showSnackbar(message);
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


}


