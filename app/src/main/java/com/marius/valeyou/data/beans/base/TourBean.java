package com.marius.valeyou.data.beans.base;

import android.os.Parcel;
import android.os.Parcelable;

public class TourBean implements Parcelable {

    private int id;
    private String title;
    private String description;
    private int image;

    public TourBean(int id, String title, String description, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeInt(this.image);
        dest.writeString(this.title);
    }

    protected TourBean(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.image = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<TourBean> CREATOR = new Creator<TourBean>() {
        @Override
        public TourBean createFromParcel(Parcel source) {
            return new TourBean(source);
        }

        @Override
        public TourBean[] newArray(int size) {
            return new TourBean[size];
        }
    };

}
