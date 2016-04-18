package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/11/12.
 */
public class userInfoItem implements Parcelable {
    public String nickname;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);

    }

    public userInfoItem() {

    }

    private userInfoItem(Parcel in) {
        this.nickname = in.readString();

    }

    public static Creator<userInfoItem> CREATOR = new Creator<userInfoItem>() {
        @Override
        public userInfoItem createFromParcel(Parcel source) {
            return new userInfoItem(source);
        }

        @Override
        public userInfoItem[] newArray(int size) {
            return new userInfoItem[0];
        }
    };
}