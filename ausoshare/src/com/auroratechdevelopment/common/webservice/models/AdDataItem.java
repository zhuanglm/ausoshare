package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/11/12.
 */
public class AdDataItem implements Parcelable {
    public String adID;
    public String thumb;
    public String description;
    public String totalAdFunds;
    public String completedPercentage;
    public String perClick;
    public String reviewURL;
    public String shareURL;
    public String description_long;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adID);
        dest.writeString(this.thumb);
        dest.writeString(this.description);
        dest.writeString(this.totalAdFunds);
        dest.writeString(this.completedPercentage);
        dest.writeString(this.perClick);
        dest.writeString(this.reviewURL);
        dest.writeString(this.shareURL);
        dest.writeString(this.description_long);
    }

    public AdDataItem() {

    }

    private AdDataItem(Parcel in) {
        this.adID = in.readString();
        this.thumb = in.readString();
        this.description = in.readString();
        this.totalAdFunds = in.readString();
        this.completedPercentage = in.readString();
        this.perClick = in.readString();
        this.reviewURL = in.readString();
        this.shareURL = in.readString();
        this.description_long = in.readString();
    }

    public static Creator<AdDataItem> CREATOR = new Creator<AdDataItem>() {
        @Override
        public AdDataItem createFromParcel(Parcel source) {
            return new AdDataItem(source);
        }

        @Override
        public AdDataItem[] newArray(int size) {
            return new AdDataItem[0];
        }
    };
}