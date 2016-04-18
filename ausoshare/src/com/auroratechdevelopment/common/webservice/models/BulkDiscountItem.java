package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edwardliu on 1/23/2015.
 */
public class BulkDiscountItem implements Parcelable {
    public String Quantity;
    public String UnitPrice;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Quantity);
        dest.writeString(this.UnitPrice);
    }

    public BulkDiscountItem() {
    }

    private BulkDiscountItem(Parcel in) {
        this.Quantity = in.readString();
        this.UnitPrice = in.readString();

    }

    public static Creator<BulkDiscountItem> CREATOR = new Creator<BulkDiscountItem>() {
        public BulkDiscountItem createFromParcel(Parcel source) {
            return new BulkDiscountItem(source);
        }

        public BulkDiscountItem[] newArray(int size) {
            return new BulkDiscountItem[size];
        }
    };
}
