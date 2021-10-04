package com.marius.valeyou.localMarketModel.responseModel;

import android.os.Parcel;
import android.os.Parcelable;

public class PostImagesModel implements Parcelable {
    String id;
    String post_id;
    String image;

    protected PostImagesModel(Parcel in) {
        id = in.readString();
        post_id = in.readString();
        image = in.readString();
    }

    public static final Creator<PostImagesModel> CREATOR = new Creator<PostImagesModel>() {
        @Override
        public PostImagesModel createFromParcel(Parcel in) {
            return new PostImagesModel(in);
        }

        @Override
        public PostImagesModel[] newArray(int size) {
            return new PostImagesModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(post_id);
        dest.writeString(image);
    }
}
