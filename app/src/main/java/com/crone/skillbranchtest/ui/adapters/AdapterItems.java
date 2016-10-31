package com.crone.skillbranchtest.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.crone.skillbranchtest.R;
import com.crone.skillbranchtest.data.storage.models.ItemsData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.MyViewHolder> {

    private ArrayList<ItemsData> mDataset;
    private int mIconRes;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titile)
        TextView mTextView;
        @BindView(R.id.desc)
        TextView mTextDesc;
        @BindView(R.id.icon)
        ImageView mIcon;


        private MyViewHolder(View v, int icon) {
            super(v);
            ButterKnife.bind(this,v);
            mIcon.setBackgroundResource(icon);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterItems(ArrayList<ItemsData> myDataset, int iconRes) {
        mDataset = myDataset;
        mIconRes = iconRes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterItems.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v,mIconRes);
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
