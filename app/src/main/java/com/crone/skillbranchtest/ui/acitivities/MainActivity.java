package com.crone.skillbranchtest.ui.acitivities;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.crone.skillbranchtest.BuildConfig;
import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.storage.models.Houses;
import com.crone.skillbranchtest.mvp.presenters.HousesPresenter;
import com.crone.skillbranchtest.mvp.presenters.IHousesPresenter;
import com.crone.skillbranchtest.mvp.views.IHousesView;
import com.crone.skillbranchtest.ui.adapters.ViewPagerAdapter;

import com.crone.skillbranchtest.utils.MyConfig;
import com.crone.skillbranchtest.utils.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements IHousesView {

    HousesPresenter mPresenter = HousesPresenter.getInstance();

    private ViewPagerAdapter mAdapter;

    private CharSequence mTitles[];
    private int mNumbOfTabs = 3;

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tabs)
    SlidingTabLayout mTabs;
    @BindView(R.id.main_content)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter.takeView(this);
        mPresenter.initView();
        //Make custom toolbar which don't created drop shadow

        toolbar.setTitle(getString(R.string.main_title));
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(toolbar);

        mTitles = new CharSequence[]{getString(R.string.stark_name), getString(R.string.lann_name), getString(R.string.targ_name)};


        //Inflate Header Image on Navigation Drawer
        mNavigationView.inflateHeaderView(R.layout.navigation_header);


        //Allowed use cutom icon like a PNG image
        mNavigationView.setItemIconTintList(null);

        setupDrawer();


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumbOfTabs);

        // Assigning ViewPager View and setting the adapter
        if (mPager != null)
            mPager.setAdapter(mAdapter);

        // Assiging the Sliding Tab Layout View
        if (mTabs != null)
            mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.setChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    private void setupDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_stark:
                        mPresenter.openPageStark();
                        break;
                    case R.id.drawer_lann:
                        mPresenter.openPageLann();
                        break;
                    case R.id.drawer_targ:
                        mPresenter.openPageTarg();
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });


    }


    @Override
    public void openPageStark() {
        mPager.setCurrentItem(0, true);
    }

    @Override
    public void openPageLann() {
        mPager.setCurrentItem(1, true);
    }

    @Override
    public void openPagerTarg() {
        mPager.setCurrentItem(2, true);
    }

    @Override
    public void setChecked(int position) {
        if (mNavigationView != null) {
            switch (position) {
                case MyConfig.STARK_PAGER:
                    mNavigationView.getMenu().getItem(MyConfig.STARK_PAGER).setChecked(true);
                    break;
                case MyConfig.LANNISTER_PAGER:
                    mNavigationView.getMenu().getItem(MyConfig.LANNISTER_PAGER).setChecked(true);
                    break;
                case MyConfig.TARGARYEN_PAGER:
                    mNavigationView.getMenu().getItem(MyConfig.TARGARYEN_PAGER).setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error));
            // TODO: 31.10.2016 send error stacktrace to crashlytics
        }
    }

    @Override
    public IHousesPresenter getPresenter() {
        return mPresenter;
    }
}
