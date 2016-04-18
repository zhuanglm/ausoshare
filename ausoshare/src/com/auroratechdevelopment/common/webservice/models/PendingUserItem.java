package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 2015-04-15.
 */
public class PendingUserItem implements Parcelable {
    public long ID;
    public String UserName;
    public String Email;
    public String RegisteredDate;
    public String DisplayName;
    public String LastName;
    public String FirstName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.ID);
        parcel.writeString(this.UserName);
        parcel.writeString(this.Email);
        parcel.writeString(this.RegisteredDate);
        parcel.writeString(this.DisplayName);
        parcel.writeString(this.FirstName);
        parcel.writeString(this.LastName);
    }

    public PendingUserItem(){

    }

    public PendingUserItem(Parcel pc) {
        this.ID = pc.readLong();
        this.UserName = pc.readString();
        this.Email = pc.readString();
        this.RegisteredDate = pc.readString();
        this.DisplayName = pc.readString();
        this.FirstName = pc.readString();
        this.LastName = pc.readString();
    }

    public static final Parcelable.Creator<PendingUserItem> CREATOR = new Parcelable.Creator<PendingUserItem>() {
        public PendingUserItem createFromParcel(Parcel pc) {
            return new PendingUserItem(pc);
        }
        public PendingUserItem[] newArray(int size) {
            return new PendingUserItem[size];
        }
    };


}
