package com.marius.valeyou.data.beans.respbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CategoryListBean implements Parcelable {

    /**
     * id : 30
     * name : Business
     * image : business.png
     * description : Business
     * subCategories : [{"id":27,"categoryId":30,"name":"Virtual Assistant ","image":"NicePng_virtual-assistant-png_4257861.png"},{"id":28,"categoryId":30,"name":"Data Entry ","image":"typing.png"}]
     */

    private int id;
    private String name;
    private String image;
    private String description;
    private int type;
    private List<SubCategoryBean> subCategories;
    private boolean check;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubCategoryBean> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryBean> subCategories) {
        this.subCategories = subCategories;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.description);
        dest.writeInt(this.type);
        dest.writeTypedList(this.subCategories);
        dest.writeByte(this.check ? (byte) 1 : (byte) 0);
    }

    public CategoryListBean() {
    }

    protected CategoryListBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.description = in.readString();
        this.type = in.readInt();
        this.subCategories = in.createTypedArrayList(SubCategoryBean.CREATOR);
        this.check = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CategoryListBean> CREATOR = new Parcelable.Creator<CategoryListBean>() {
        @Override
        public CategoryListBean createFromParcel(Parcel source) {
            return new CategoryListBean(source);
        }

        @Override
        public CategoryListBean[] newArray(int size) {
            return new CategoryListBean[size];
        }
    };
}
