package com.crone.skillbranchtest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.fragments.FragmentItems;
import com.crone.skillbranchtest.models.ItemsData;
import com.crone.skillbranchtest.utils.MyConfig;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {
   private CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
   private int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    private ArrayList<ItemsData> mItems1;
    private ArrayList<ItemsData> mItems2;
    private ArrayList<ItemsData>  mItems3;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, ArrayList<ItemsData> items1, ArrayList<ItemsData> items2, ArrayList<ItemsData> items3) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        mItems1 = items1;
        mItems2 = items2;
        mItems3 = items3;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentItems().newInstance(R.drawable.stark_icon,mItems1);
            case 1:
                return new FragmentItems().newInstance(R.drawable.lanister_icon,mItems2);
            case 2:
                return new FragmentItems().newInstance(R.drawable.targ_icon,mItems3);
            default:
                // This should never happen. Always account for each position above
                return null;

        }

    }




    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}