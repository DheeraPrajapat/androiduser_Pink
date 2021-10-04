package com.marius.valeyou.data.beans.base;

import android.os.Parcel;
import android.os.Parcelable;

public class MoreBean implements Parcelable{
    private int id;
    private String name;
    private String company_name;
    private boolean isSelected;
    private int image;
    private int type;

    public MoreBean(  String company_name,boolean isSelected ) {
        this.company_name = company_name;
        this.isSelected = isSelected;

    } public MoreBean(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public MoreBean(int id, String name, int image, int type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
    }

    protected MoreBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readInt();
        type = in.readInt();
    }

    public static final Creator<MoreBean> CREATOR = new Creator<MoreBean>() {
        @Override
        public MoreBean createFromParcel(Parcel in) {
            return new MoreBean(in);
        }

        @Override
        public MoreBean[] newArray(int size) {
            return new MoreBean[size];
        }
    };

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeInt(type);
    }
}
