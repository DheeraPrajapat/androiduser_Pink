package com.marius.valeyou.data.beans.respbean;

import android.os.Parcel;
import android.os.Parcelable;

public class SubCategoryBean implements Parcelable {

    /**
     * id : 2
     * categoryId : 19
     * name : Bathroom Cleaning
     * description : Bathroom Cleaning
     * image : bathroom.jpg
     */

    private int id;
    private int categoryId;
    private String name;
    private String description;
    private String image;
    private boolean check;
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        dest.writeInt(this.id);
        dest.writeInt(this.categoryId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.image);
    }

    public SubCategoryBean() {
    }

    protected SubCategoryBean(Parcel in) {
        this.id = in.readInt();
        this.categoryId = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<SubCategoryBean> CREATOR = new Parcelable.Creator<SubCategoryBean>() {
        @Override
        public SubCategoryBean createFromParcel(Parcel source) {
            return new SubCategoryBean(source);
        }

        @Override
        public SubCategoryBean[] newArray(int size) {
            return new SubCategoryBean[size];
        }
    };

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
