package com.marius.valeyou.data.beans.signup;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupData implements Serializable {

    /**
     * id : 70
     * firstName : Mr.
     * lastName : John
     * email : rokz.rk786@gmail.com
     * image : 6621f75d-e88d-4e35-aab2-80a3b3617d20.jpeg
     * phone : 8450834424
     * countryCode : +91
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
     * location : Carnaut place
     * latitude : 30.216589
     * longitude : 70.236598
     * authKey : e977b527e6541ca0dfaa86c4cda0a09839dbfbee926dfbd89a
     * age : 28
     * city : Delhi
     * state : Delhi
     */

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String phone;
    private String countryCode;
    private String description;
    private String location;
    private String latitude;
    private String longitude;
    private String authKey;
    private int age;
    private String city;
    private String state;
    private String dob;
    private int status;
    private int isEmailVerify;
    private String name;
    private String first_name;
    private String last_name;

    public int getIsEmailVerify() {
        return isEmailVerify;
    }

    public void setIsEmailVerify(int isEmailVerify) {
        this.isEmailVerify = isEmailVerify;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public int getAge() {
        return age;
    }

    public String getStrAge() {
        return String.valueOf(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCity() {
        if (state == null) {
            return city == null ? "" : city;
        } else if (state.equals("")) {
            return city == null ? "" : city;
        } else {
            return (city == null ? "" : city) + ", " + state;
        }
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(this.id);
//        dest.writeString(this.firstName);
//        dest.writeString(this.lastName);
//        dest.writeString(this.email);
//        dest.writeString(this.image);
//        dest.writeString(this.phone);
//        dest.writeString(this.countryCode);
//        dest.writeString(this.description);
//        dest.writeString(this.location);
//        dest.writeString(this.latitude);
//        dest.writeString(this.longitude);
//        dest.writeString(this.authKey);
//        dest.writeInt(this.age);
//        dest.writeInt(this.status);
//        dest.writeString(this.city);
//        dest.writeString(this.state);
//        dest.writeString(this.name);
//        dest.writeString(this.first_name);
//        dest.writeString(this.last_name);
//    }

    public SignupData() {
    }

    protected SignupData(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.image = in.readString();
        this.phone = in.readString();
        this.countryCode = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.authKey = in.readString();
        this.age = in.readInt();
        this.status = in.readInt();
        this.city = in.readString();
        this.state = in.readString();
        this.name = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
    }

    public static final Parcelable.Creator<SignupData> CREATOR = new Parcelable.Creator<SignupData>() {
        @Override
        public SignupData createFromParcel(Parcel source) {
            return new SignupData(source);
        }

        @Override
        public SignupData[] newArray(int size) {
            return new SignupData[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        if (name == null) {
            return first_name + " " + last_name;
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String capitalize(String s) {
        if (s != null) {
            StringBuffer capBuffer = new StringBuffer();
            Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(s);
            while (capMatcher.find()) {
                capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
            }

            return capMatcher.appendTail(capBuffer).toString();
        }
        return "";
    }

}
