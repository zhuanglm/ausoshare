package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Edward Liu on 2015/11/12.
 */
public class OnGoingAdItem implements Parcelable{
    public AdDataItem[] AdData;

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        if(AdData == null){
            dest.writeInt(0);
        }else{
            dest.writeInt(AdData.length);
        }
        if(AdData != null){
            dest.writeParcelableArray(AdData,0);
        }
    }

    public OnGoingAdItem(){

    }

    private OnGoingAdItem(Parcel in){
        int length1 = in.readInt();
        if(length1 > 0){
            Parcelable[] parcelableArray =
                    in.readParcelableArray(BulkDiscountItem.class.getClassLoader());
            if (parcelableArray != null) {
                AdData = Arrays.copyOf(parcelableArray, parcelableArray.length, AdDataItem[].class);
            }
        }
    }

    public static Creator<OnGoingAdItem> CREATOR = new Creator<OnGoingAdItem>() {
        @Override
        public OnGoingAdItem createFromParcel(Parcel source) {
            return new OnGoingAdItem(source);
        }

        @Override
        public OnGoingAdItem[] newArray(int size) {
            return new OnGoingAdItem[0];
        }
    };

}
