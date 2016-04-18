package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by happy pan on 2015/11/12.
 */
public class CurrentIncomeItem implements Parcelable {
    public String currentIncome;
    public Top10Item[] top10;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currentIncome);

        if(top10 == null){
            dest.writeInt(0);
        }else{
            dest.writeInt(top10.length);
        }
        if(top10 != null){
            dest.writeParcelableArray(top10,0);
        }

    }

    public CurrentIncomeItem() {

    }

    private CurrentIncomeItem(Parcel in) {
        this.currentIncome = in.readString();
        int length1 = in.readInt();
        if(length1 > 0){
            Parcelable[] parcelableArray =
                    in.readParcelableArray(BulkDiscountItem.class.getClassLoader());
            if (parcelableArray != null) {
                top10 = Arrays.copyOf(parcelableArray, parcelableArray.length, Top10Item[].class);
            }
        }
    }

    public static Creator<CurrentIncomeItem> CREATOR = new Creator<CurrentIncomeItem>() {
        @Override
        public CurrentIncomeItem createFromParcel(Parcel source) {
            return new CurrentIncomeItem(source);
        }

        @Override
        public CurrentIncomeItem[] newArray(int size) {
            return new CurrentIncomeItem[0];
        }
    };
}