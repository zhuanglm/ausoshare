package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edwardliu on 1/19/2015.
 */
public class CategoryItem implements Parcelable {

    public long Id;
    public String Name;
    public String Thumb;


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeLong(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.Thumb);
    }

    public CategoryItem(){

    }

    private CategoryItem(Parcel in){
        this.Id = in.readLong();
        this.Name = in.readString();
        this.Thumb = in.readString();
    }

    public static final Parcelable.Creator<CategoryItem> CREATOR = new Parcelable.Creator<CategoryItem>(){
        public CategoryItem createFromParcel(Parcel source){
            return new CategoryItem(source);
        }

        public CategoryItem[] newArray(int size){
            return new CategoryItem[size];
        }
    };
}
