package com.example.countnum;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * 账单类
 * @author 吴若恒
 * @date 2019/5/14
 * @包括月份，单价，数量**/
public class Bill implements Parcelable {
    private String monthDate;
    private String unitPrice;
    private String num;

    private String userName;

    private String id;

    public Bill() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bill(String id,String monthDate, String unitPrice, String num, String userName) {
        this.monthDate = monthDate;
        this.unitPrice = unitPrice;
        this.num = num;
        this.userName = userName;
        this.id = id;
    }

    protected Bill(Parcel in) {
        monthDate = in.readString();
        unitPrice = in.readString();
        num = in.readString();
    }

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(monthDate);
        dest.writeString(unitPrice);
        dest.writeString(num);
        dest.writeString(userName);
        dest.writeString(id);
    }
}
