package com.marius.valeyou.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arvind Poonia on 12/7/2018.
 */
public class UserBean implements Parcelable {

    private String user_id;
    private String username;
    private String email;
    private String user_long_id;
    private String first_name;
    private String last_name;
    private String profile_picture;
    private int gender;
    private String cc;
    private String phone;
    private String token;
    private int social_source;
    private String business_token;


    public int getSocial_source() {
        return social_source;
    }

    public void setSocial_source(int social_source) {
        this.social_source = social_source;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_long_id() {
        return user_long_id;
    }

    public void setUser_long_id(String user_long_id) {
        this.user_long_id = user_long_id;
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

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBusiness_token() {
        return business_token;
    }

    public void setBusiness_token(String business_token) {
        this.business_token = business_token;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.user_long_id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.profile_picture);
        dest.writeInt(this.gender);
        dest.writeString(this.cc);
        dest.writeString(this.phone);
        dest.writeString(this.token);
        dest.writeInt(this.social_source);
        dest.writeString(this.business_token);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.user_id = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.user_long_id = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.profile_picture = in.readString();
        this.gender = in.readInt();
        this.cc = in.readString();
        this.phone = in.readString();
        this.token = in.readString();
        this.social_source = in.readInt();
        this.business_token = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}