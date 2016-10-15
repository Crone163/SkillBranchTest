package com.crone.skillbranchtest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.adapters.AdapterItems;
import com.crone.skillbranchtest.models.ItemsData;

import java.util.ArrayList;


public class FragmentItems extends Fragment {

    private static final String ARGUMENT_DATA = "arg_page_position";
    private static final String ARGUMENT_ICON = "arg_icon";
    private AdapterItems mAdapter;
    private ArrayList<ItemsData> mData;
    private int mIcon;

    public FragmentItems newInstance(int resource,ArrayList<ItemsData> position) {
        FragmentItems fragment = new FragmentItems();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARGUMENT_DATA, position);
        arguments.putInt(ARGUMENT_ICON, resource);
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = getArguments().getParcelableArrayList(ARGUMENT_DATA);
        mIcon = getArguments().getInt(ARGUMENT_ICON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        mAdapter = new AdapterItems(mData,mIcon);
        rv.setAdapter(mAdapter);
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
        //When fragment is displayed that allowed to set data
       //EventBus.getDefault().post(new MessageEvent(mPosition));
    }




}


