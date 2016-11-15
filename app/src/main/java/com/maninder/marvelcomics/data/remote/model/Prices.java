package com.maninder.marvelcomics.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maninder on 14/11/16.
 */

public class Prices implements Parcelable {

    @SerializedName("type")
    public String type;
    @SerializedName("price")
    public double price;

    public Prices() {

    }

    @SuppressWarnings("unchecked")
    public Prices(Parcel in) {
        type = in.readString();
        price = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeDouble(price);
    }

    public static final Parcelable.Creator<Prices> CREATOR = new Parcelable.Creator<Prices>() {
        public Prices createFromParcel(Parcel in) {
            return new Prices(in);
        }

        public Prices[] newArray(int size) {
            return new Prices[size];
        }
    };
}
