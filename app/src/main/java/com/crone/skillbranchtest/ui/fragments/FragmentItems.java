package com.crone.skillbranchtest.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.mvp.presenters.HousesFragmentPresenter;
import com.crone.skillbranchtest.mvp.presenters.IHousesFragmentPresenter;
import com.crone.skillbranchtest.mvp.views.IHousesFragmentView;
import com.crone.skillbranchtest.ui.acitivities.DetailActivity;
import com.crone.skillbranchtest.ui.adapters.AdapterItems;
import com.crone.skillbranchtest.utils.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentItems extends Fragment implements IHousesFragmentView {

    HousesFragmentPresenter mPresenter = HousesFragmentPresenter.getInstance();

    private static final String ARGUMENT_POSITION = "arg_page_position";

    private int mTabsPosition;
    private ArrayList<ItemsData> mItemsData;
    private int mIcon;

    @BindView(R.id.recycle_view)
    RecyclerView rv;

    public FragmentItems newInstance(int position) {
        FragmentItems fragment = new FragmentItems();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_POSITION, position);
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabsPosition = getArguments().getInt(ARGUMENT_POSITION);
        mPresenter.takeView(this);
        mPresenter.initView();
    }

    @Override
    public void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.bind(this,view);

        rv.setHasFixedSize(true);

        AdapterItems mAdapter = new AdapterItems(mItemsData, mIcon);
        rv.setAdapter(mAdapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        mPresenter.onItemClick(mItemsData,position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    //region ===================== IHousesFragmentView =================
    @Override
    public IHousesFragmentPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initData() {
        mItemsData = mPresenter.getDataByPosition(mTabsPosition);
        mIcon = mPresenter.getIconByPosition(mTabsPosition);
    }

    @Override
    public void onItemClick(int houseId) {
        mPresenter.sendInfoById(houseId);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        getContext().startActivity(intent);
    }
    //endregion
}


