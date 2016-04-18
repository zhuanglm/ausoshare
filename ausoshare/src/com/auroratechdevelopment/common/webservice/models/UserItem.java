package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 2015-04-15.
 */
public class UserItem implements Parcelable {

    public String Barcode;
    public String BarcodeUrl;
    public String HistoryUrl;
    public String DetailUrl;
    public int Rewards;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Barcode);
        parcel.writeString(BarcodeUrl);
        parcel.writeString(HistoryUrl);
        parcel.writeString(DetailUrl);
        parcel.writeInt(Rewards);
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        public UserItem createFromParcel(Parcel pc) {
            return new UserItem(pc);
        }
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    public UserItem(Parcel pc) {
        Barcode = pc.readString();
        BarcodeUrl = pc.readString();
        HistoryUrl = pc.readString();
        DetailUrl = pc.readString();
        Rewards = pc.readInt();
    }
}
