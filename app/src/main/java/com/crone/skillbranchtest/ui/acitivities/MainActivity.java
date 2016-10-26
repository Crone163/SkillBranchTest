package com.crone.skillbranchtest.ui.acitivities;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.ui.adapters.ViewPagerAdapter;

import com.crone.skillbranchtest.utils.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private SlidingTabLayout mTabs;
    private CharSequence mTitles[];
    private int mNumbOfTabs = 3;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private MenuItem mItem;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make custom toolbar which don't created drop shadow
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.main_title));
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(toolbar);

        mTitles = new CharSequence[]{getString(R.string.stark_name), getString(R.string.lann_name), getString(R.string.targ_name)};

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Inflate Header Image on Navigation Drawer
        mNavigationView.inflateHeaderView(R.layout.navigation_header);

        if (savedInstanceState == null) {
            //Checked first item
            mItem = mNavigationView.getMenu().findItem(R.id.drawer_stark);
            mItem.setChecked(true);
        }

        //Allowed use cutom icon like a PNG image
        mNavigationView.setItemIconTintList(null);

        setupDrawer();



        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, mNumbOfTabs);

        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        if (mPager != null)
            mPager.setAdapter(mAdapter);

        //


        // Assiging the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
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
                if (mItem != null) mItem.setChecked(false);
                switch (item.getItemId()) {
                    case R.id.drawer_stark:
                        mPager.setCurrentItem(0, true);
                        break;

                    case R.id.drawer_lann:
                        mPager.setCurrentItem(1, true);
                        break;

                    case R.id.drawer_targ:
                        mPager.setCurrentItem(2, true);
                        break;

                }
                //setChecker(item);
                mItem = item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }


        });


    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onStart() {
        super.onStart();
    }


}
