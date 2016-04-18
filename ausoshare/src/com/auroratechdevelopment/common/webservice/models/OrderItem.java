package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 2015-10-28.
 */
public class OrderItem implements Parcelable {
    public long productId;
    public int quantity;
    public String unitPrice;
    public String subOrderTax;
    public String subTotal;
    public String productName;
    public String Thumb;

    public OrderItem(long productId, int quantity, String unitPrice, String subOrderTax, String thumb) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subOrderTax = subOrderTax;
        this.Thumb = Thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.productId);
        dest.writeInt(this.quantity);
        dest.writeString(this.unitPrice);
        dest.writeString(this.subOrderTax);
        dest.writeString(this.subTotal);
        dest.writeString(this.productName);
        dest.writeString(this.Thumb);
    }

    private OrderItem(Parcel in) {
        this.productId = in.readLong();
        this.quantity = in.readInt();
        this.unitPrice = in.readString();
        this.subOrderTax = in.readString();
        this.subTotal = in.readString();
        this.productName = in.readString();
        this.Thumb = in.readString();
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
