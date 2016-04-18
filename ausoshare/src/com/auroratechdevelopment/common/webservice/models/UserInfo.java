package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/11/12.
 */
public class UserInfo implements Parcelable {
    public String city;
    public String province;
    public String country;
    public String lon;
    public String lat;
    public int startNumber;
    public int counts;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.province);
        dest.writeString(this.country);
        dest.writeString(this.lon);
        dest.writeString(this.lat);
        dest.writeInt(this.startNumber);
        dest.writeInt(this.counts);
    }

    public UserInfo() {

    }

    private UserInfo(Parcel in) {
        this.city = in.readString();
        this.province = in.readString();
        this.country = in.readString();
        this.lon = in.readString();
        this.lat = in.readString();
        this.startNumber = in.readInt();
        this.counts = in.readInt();
    }

    public static Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[0];
        }
    };
}