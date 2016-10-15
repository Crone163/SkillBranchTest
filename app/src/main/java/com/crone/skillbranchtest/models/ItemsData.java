package com.crone.skillbranchtest.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ItemsData implements Parcelable {

    public long id;

    public long idHouse;

    public String name;

    public String titles;

    public ItemsData(Parcel in) {
        id = in.readLong();
        idHouse = in.readLong();
        name = in.readString();
        titles = in.readString();
    }

    public static final Creator<ItemsData> CREATOR = new Creator<ItemsData>() {
        @Override
        public ItemsData createFromParcel(Parcel in) {
            return new ItemsData(in);
        }

        @Override
        public ItemsData[] newArray(int size) {
            return new ItemsData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(idHouse);
        parcel.writeString(name);
        parcel.writeString(titles);
    }
}
