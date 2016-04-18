package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 2015-09-10.
 */
public class RelatedItem implements Parcelable {
    public long ID;
    public String Guid;
    public String PostTitle;
    public String ThumbUrl;
    public String RegularPrice;
    public String SalePrice;
    public String Description;
    public String ShortDescription;
    public String[] Category;
    public String BulkDIscount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.ID);
        dest.writeString(this.Guid);
        dest.writeString(this.PostTitle);
        dest.writeString(this.ThumbUrl);
        dest.writeString(this.RegularPrice);
        dest.writeString(this.SalePrice);
        dest.writeString(this.Description);
        dest.writeString(this.ShortDescription);
        dest.writeStringArray(this.Category);
        dest.writeString(this.BulkDIscount);
    }

    public RelatedItem() {
    }

    private RelatedItem(Parcel in) {
        this.ID = in.readLong();
        this.Guid = in.readString();
        this.PostTitle = in.readString();
        this.ThumbUrl = in.readString();
        this.RegularPrice = in.readString();
        this.SalePrice = in.readString();
        this.Description = in.readString();
        this.ShortDescription = in.readString();
        this.Category = in.createStringArray();
        this.BulkDIscount = in.readString();
    }

    public static final Parcelable.Creator<RelatedItem> CREATOR = new Parcelable.Creator<RelatedItem>() {
        public RelatedItem createFromParcel(Parcel source) {
            return new RelatedItem(source);
        }

        public RelatedItem[] newArray(int size) {
            return new RelatedItem[size];
        }
    };
}
