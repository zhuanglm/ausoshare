package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/11/12.
 */
public class ForwardedADItem implements Parcelable {
    public String adID;
    public String thumb;
    public String description;
    public String amount;
    public String click;
    public String reviewURL;
    public String active;
    public String shareURL;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.adID);
        dest.writeString(this.thumb);
        dest.writeString(this.description);
        dest.writeString(this.amount);
        dest.writeString(this.click);
        dest.writeString(this.reviewURL);
        dest.writeString(this.active);
        dest.writeString(this.shareURL);
    }

    public ForwardedADItem() {

    }

    private ForwardedADItem(Parcel in) {
        this.adID = in.readString();
        this.thumb = in.readString();
        this.description = in.readString();
        this.amount = in.readString();
        this.click = in.readString();
        this.reviewURL = in.readString();
        this.active = in.readString();
        this.shareURL = in.readString();
    }

    public static Creator<ForwardedADItem> CREATOR = new Creator<ForwardedADItem>() {
        @Override
        public ForwardedADItem createFromParcel(Parcel source) {
            return new ForwardedADItem(source);
        }

        @Override
        public ForwardedADItem[] newArray(int size) {
            return new ForwardedADItem[0];
        }
    };
}