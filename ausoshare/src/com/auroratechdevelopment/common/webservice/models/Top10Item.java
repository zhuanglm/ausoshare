package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/11/12.
 */
public class Top10Item implements Parcelable {
    public String name;
    public String totalEarning;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.totalEarning);

    }

    public Top10Item() {

    }

    private Top10Item(Parcel in) {
        this.name = in.readString();
        this.totalEarning = in.readString();
    }

    public static Creator<Top10Item> CREATOR = new Creator<Top10Item>() {
        @Override
        public Top10Item createFromParcel(Parcel source) {
            return new Top10Item(source);
        }

        @Override
        public Top10Item[] newArray(int size) {
            return new Top10Item[0];
        }
    };
}