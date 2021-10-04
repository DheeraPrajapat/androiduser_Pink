package com.marius.valeyou.data.beans.reqbean;

import android.os.Parcel;
import android.os.Parcelable;

public class SocialDataBean implements Parcelable {
    private String social_id;
    private String first_name;
    private String last_name;
    private String email;
    private String social_type;

    public SocialDataBean(String social_id, String first_name, String last_name, String email,String social_type) {
        this.social_id = social_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.social_type = social_type;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setSocial_type(String social_type) {
        this.social_type = social_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.social_id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.email);
        dest.writeString(this.social_type);
    }

    public SocialDataBean() {
    }

    protected SocialDataBean(Parcel in) {
        this.social_id = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.email = in.readString();
        this.social_type = in.readString();
    }

    public static final Parcelable.Creator<SocialDataBean> CREATOR = new Parcelable.Creator<SocialDataBean>() {
        @Override
        public SocialDataBean createFromParcel(Parcel source) {
            return new SocialDataBean(source);
        }

        @Override
        public SocialDataBean[] newArray(int size) {
            return new SocialDataBean[size];
        }
    };

}
