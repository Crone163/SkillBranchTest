package com.crone.skillbranchtest.adapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.acitivities.DetailActivity;
import com.crone.skillbranchtest.data.DataManager;
import com.crone.skillbranchtest.models.Houses;
import com.crone.skillbranchtest.models.HousesDao;
import com.crone.skillbranchtest.models.ItemsData;
import com.crone.skillbranchtest.models.Persons;
import com.crone.skillbranchtest.models.PersonsDao;
import com.crone.skillbranchtest.models.Titles;
import com.crone.skillbranchtest.models.TitlesDao;
import com.crone.skillbranchtest.utils.MyConfig;
import com.crone.skillbranchtest.utils.SetIntentParams;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.MyViewHolder> {
    private ArrayList<ItemsData> mDataset;
    private int mIconRes;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView;
        public TextView mTextDesc;
        public LinearLayout mCardView;
        public IMyViewHolderClicks mListener;
        public ImageView mIcon;

        public MyViewHolder(View v, IMyViewHolderClicks listener, int icon) {
            super(v);
            mListener = listener;
            mCardView = (LinearLayout) v.findViewById(R.id.card);
            mTextDesc = (TextView) v.findViewById(R.id.desc);
            mCardView.setOnClickListener(this);
            mIcon = (ImageView) v.findViewById(R.id.icon);
            mIcon.setBackgroundResource(icon);
            mTextView = (TextView) v.findViewById(R.id.titile);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == mCardView.getId()) {
                mListener.onClickCard(getAdapterPosition());
            }
        }

        interface IMyViewHolderClicks {
            void onClickCard(int position);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterItems(ArrayList<ItemsData> myDataset, int iconRes) {
        mDataset = myDataset;
        mIconRes = iconRes;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public AdapterItems.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                        int viewType) {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v, new MyViewHolder.IMyViewHolderClicks() {
            @Override
            public void onClickCard(int position) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                SetIntentParams.setParams(intent,mDataset.get(position).id);
                v.getContext().startActivity(intent);
            }
        }, mIconRes);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).name);
        holder.mTextDesc.setText(mDataset.get(position).titles);

    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        } else {
            return mDataset.size();
        }

    }




}
