package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class ProductItem implements Parcelable {

    public long ID;
    public String PostDate;
    public String PostContent;
    public String PostTitle;
    public String Guid;
    public String PostStatus;
    public String Thumb;
    public String RegularPrice;
    public String SalePrice;
    public String Description;
    public String ShortDescription;
    public boolean IsFavourite;
    public String selectedQuantity;
    public String[] Category;
    public BulkDiscountItem[] BulkDiscount;

    public ProductItem(){

    }

    protected ProductItem(Parcel in) {
        ID = in.readLong();
        PostDate = in.readString();
        PostContent = in.readString();
        PostTitle = in.readString();
        Guid = in.readString();
        PostStatus = in.readString();
        Thumb = in.readString();
        RegularPrice = in.readString();
        SalePrice = in.readString();
        Description = in.readString();
        ShortDescription = in.readString();
        IsFavourite = in.readByte() != 0x00;
        selectedQuantity = in.readString();

        int length = in.readInt();
        if(length > 0){
            Category = new String[length];
            in.readStringArray(Category);
        }

        int length1 = in.readInt();
        if(length1 > 0){
            Parcelable[] parcelableArray =
                    in.readParcelableArray(BulkDiscountItem.class.getClassLoader());
            if (parcelableArray != null) {
                BulkDiscount = Arrays.copyOf(parcelableArray, parcelableArray.length, BulkDiscountItem[].class);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ID);
        dest.writeString(PostDate);
        dest.writeString(PostContent);
        dest.writeString(PostTitle);
        dest.writeString(Guid);
        dest.writeString(PostStatus);
        dest.writeString(Thumb);
        dest.writeString(RegularPrice);
        dest.writeString(SalePrice);
        dest.writeString(Description);
        dest.writeString(ShortDescription);
        dest.writeByte((byte) (IsFavourite ? 1 : 0));
        dest.writeString(selectedQuantity == null ? "" : selectedQuantity);

        if(Category == null){
            dest.writeInt(0);
        }else {
            dest.writeInt(Category.length);
        }
        if(Category != null){
            dest.writeStringArray(Category);
        }


        if(BulkDiscount == null){
            dest.writeInt(0);
        }else{
            dest.writeInt(BulkDiscount.length);
        }
        if(BulkDiscount != null){
            dest.writeParcelableArray(BulkDiscount,0);
        }

    }

    public static final Parcelable.Creator<ProductItem> CREATOR = new Parcelable.Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };
}