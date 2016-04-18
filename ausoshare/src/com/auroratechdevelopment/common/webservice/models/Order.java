package com.auroratechdevelopment.common.webservice.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2015-10-28.
 */
public class Order implements Parcelable {

    public long orderId;
    public List<OrderItem> items;

    public String note;
    public String billingCountry;
    public String billingLastName;
    public String billingFirstName;
    public String billingCompany;
    public String billingAddress1;
    public String billingAddress2;
    public String billingCity;
    public String billingState;
    public String billingPostCode;
    public String billingEmail;
    public String billingPhone;
    public String shippingFirstName;
    public String shippingLastName;
    public String shippingCompany;
    public String shippingCountry;
    public String shippingAddress1;
    public String shippingAddress2;
    public String shippingCity;
    public String shippingState;
    public String shippingPostCode;
    public String orderShipping;
    public String orderDiscount;
    public String orderCartDiscount;
    public String orderTax;
    public String orderShippingTax;
    public String orderTotal;
    public String orderCurrency;
    public String isPriceIncludeTax;
    public String shippingMethod;
    public String paymentMethod;
    public String status;
    public String orderDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.orderId);
        dest.writeTypedList(items);
        dest.writeString(this.note);
        dest.writeString(this.billingCountry);
        dest.writeString(this.billingLastName);
        dest.writeString(this.billingFirstName);
        dest.writeString(this.billingCompany);
        dest.writeString(this.billingAddress1);
        dest.writeString(this.billingAddress2);
        dest.writeString(this.billingCity);
        dest.writeString(this.billingState);
        dest.writeString(this.billingPostCode);
        dest.writeString(this.billingEmail);
        dest.writeString(this.billingPhone);
        dest.writeString(this.shippingFirstName);
        dest.writeString(this.shippingLastName);
        dest.writeString(this.shippingCompany);
        dest.writeString(this.shippingCountry);
        dest.writeString(this.shippingAddress1);
        dest.writeString(this.shippingAddress2);
        dest.writeString(this.shippingCity);
        dest.writeString(this.shippingState);
        dest.writeString(this.shippingPostCode);
        dest.writeString(this.orderShipping);
        dest.writeString(this.orderDiscount);
        dest.writeString(this.orderCartDiscount);
        dest.writeString(this.orderTax);
        dest.writeString(this.orderShippingTax);
        dest.writeString(this.orderTotal);
        dest.writeString(this.orderCurrency);
        dest.writeString(this.isPriceIncludeTax);
        dest.writeString(this.shippingMethod);
        dest.writeString(this.paymentMethod);
        dest.writeString(this.status);
        dest.writeString(this.orderDate);
    }

    public Order() {
    }

    private Order(Parcel in) {
        this.orderId = in.readLong();
        if(items == null) {
            items  = new ArrayList();
        }
        in.readTypedList(items, OrderItem.CREATOR);
        this.note = in.readString();
        this.billingCountry = in.readString();
        this.billingLastName = in.readString();
        this.billingFirstName = in.readString();
        this.billingCompany = in.readString();
        this.billingAddress1 = in.readString();
        this.billingAddress2 = in.readString();
        this.billingCity = in.readString();
        this.billingState = in.readString();
        this.billingPostCode = in.readString();
        this.billingEmail = in.readString();
        this.billingPhone = in.readString();
        this.shippingFirstName = in.readString();
        this.shippingLastName = in.readString();
        this.shippingCompany = in.readString();
        this.shippingCountry = in.readString();
        this.shippingAddress1 = in.readString();
        this.shippingAddress2 = in.readString();
        this.shippingCity = in.readString();
        this.shippingState = in.readString();
        this.shippingPostCode = in.readString();
        this.orderShipping = in.readString();
        this.orderDiscount = in.readString();
        this.orderCartDiscount = in.readString();
        this.orderTax = in.readString();
        this.orderShippingTax = in.readString();
        this.orderTotal = in.readString();
        this.orderCurrency = in.readString();
        this.isPriceIncludeTax = in.readString();
        this.shippingMethod = in.readString();
        this.paymentMethod = in.readString();
        this.status = in.readString();
        this.orderDate = in.readString();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
