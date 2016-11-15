package com.maninder.marvelcomics.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maninder on 14/11/16.
 */

/**
 * This Class extend Parcelable because in this way we can convert in JSON;
 */
public class Comics implements Parcelable {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("pageCount")
    public int pageCount;
    @SerializedName("prices")
    public Prices prices;
    @SerializedName("creators")
    public String author;
    public String role;
    @SerializedName("thumbnail")
    public Image thumbnail;
    //Is a list
    @SerializedName("images")
    public Image images;

    public Comics() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private Comics(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        prices = (Prices) in.readParcelable(Prices.class.getClassLoader());
        author = in.readString();
        role = in.readString();
        thumbnail = (Image) in.readParcelable(Image.class.getClassLoader());
        images = (Image) in.readParcelable(Image.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(pageCount);
        dest.writeParcelable(prices, flags);
        dest.writeString(author);
        dest.writeString(role);
        dest.writeParcelable(thumbnail, flags);
        dest.writeParcelable(images, flags);

    }

    public static final Parcelable.Creator<Comics> CREATOR
            = new Parcelable.Creator<Comics>() {
        public Comics createFromParcel(Parcel in) {
            return new Comics(in);
        }

        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };

//    @Override
//    public String toString() {
//        return "Comics: id= " + id
//                + " Title= " + title
//                + " PageCount= " + pageCount
//                + " prices= " + prices;
//    }

}
