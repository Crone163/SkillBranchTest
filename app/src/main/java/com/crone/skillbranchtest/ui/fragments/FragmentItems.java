package com.crone.skillbranchtest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.ui.adapters.AdapterItems;
import com.crone.skillbranchtest.data.storage.models.ItemsData;
import com.crone.skillbranchtest.utils.MyConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentItems extends Fragment {

    private static final String ARGUMENT_POSITION = "arg_page_position";

    private AdapterItems mAdapter;
    private int mPosition;


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
        mPosition = getArguments().getInt(ARGUMENT_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);

        mAdapter = new AdapterItems(getDataByPosition(mPosition), getIconByPosition(mPosition));
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

    /**
     * Getting icon for tabs
     * @param position - tabs number
     * @return int resource
     */
    private int getIconByPosition(int position){
        switch (position) {
            case 0:
                return R.drawable.stark_icon;
            case 1:
                return R.drawable.lanister_icon;
            case 2:
                return R.drawable.targ_icon;
            default:
                return R.mipmap.ic_launcher;
        }
    }


    /**
     * Getting data for RecycleView
     * @param position - tabs number
     * @return ArrayList with items
     */
    private ArrayList<ItemsData> getDataByPosition(int position){
        Map<String, ArrayList<ItemsData>> data = EventBus.getDefault().getStickyEvent(HashMap.class);
        switch (position) {
            case 0:
                return data.get(MyConfig.STARK_ARG);
            case 1:
                return data.get(MyConfig.LANNISTER_ARG);
            case 2:
                return data.get(MyConfig.TARGARYEN_ARG);
            default:
                return null;
        }
    }


}


