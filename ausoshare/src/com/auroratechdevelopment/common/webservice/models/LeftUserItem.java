package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by happy pan on 2015/4/5.
 */
public class LeftUserItem implements Parcelable {
    public String Thumb;
    public String UserName;
    public String UserGender;
    public String UserAge;
    public String UserBirthday;
    public String UserDistance;
    public String UserCity;
    public double Lon;
    public double Lat;

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.Thumb);
        dest.writeString(this.UserName);
        dest.writeString(this.UserGender);
        dest.writeString(this.UserAge);
        dest.writeString(this.UserBirthday);
        dest.writeString(this.UserDistance);
        dest.writeString(this.UserCity);
        dest.writeDouble(this.Lon);
        dest.writeDouble(this.Lat);
    }

    public LeftUserItem(){}

    private LeftUserItem(Parcel in){
        this.Thumb  = in.readString();
        this.UserName = in.readString();
        this.UserGender = in.readString();
        this.UserAge = in.readString();
        this.UserBirthday  = in.readString();
        this.UserDistance = in.readString();
        this.UserCity = in.readString();
        this.Lon = in.readDouble();
        this.Lat = in.readDouble();
    }

    public static Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel parcel) {
            return new UserItem(parcel);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

}
