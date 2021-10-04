package com.marius.valeyou.data.beans.respbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GetProfileBean implements Parcelable {

    /**
     * id : 70
     * firstName : R.
     * lastName : Singh
     * email : rokz.rk786@gmail.com
     * image :
     * phone : 9876543210
     * countryCode : +91
     * description :
     * location :
     * latitude : 30.6101511
     * longitude : 76.841284
     * authKey : a75128255bef818835e236b2ca0182a39994517dfc80ff212c
     * age : 0
     * dob :
     * city :
     * state :
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
    private String dob;
    private String city;
    private String state;
    private List<IdentityBean> identity;

    public List<IdentityBean> getIdentity() {
        return identity;
    }

    public void setIdentity(List<IdentityBean> identity) {
        this.identity = identity;
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

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.image);
        dest.writeString(this.phone);
        dest.writeString(this.countryCode);
        dest.writeString(this.description);
        dest.writeString(this.location);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.authKey);
        dest.writeInt(this.age);
        dest.writeString(this.city);
        dest.writeString(this.state);
    }

    public GetProfileBean() {
    }

    protected GetProfileBean(Parcel in) {
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
        this.city = in.readString();
        this.state = in.readString();
    }

    public static final Parcelable.Creator<GetProfileBean> CREATOR = new Parcelable.Creator<GetProfileBean>() {
        @Override
        public GetProfileBean createFromParcel(Parcel source) {
            return new GetProfileBean(source);
        }

        @Override
        public GetProfileBean[] newArray(int size) {
            return new GetProfileBean[size];
        }
    };

    public static class IdentityBean implements Parcelable {

        /**
         * id : 26
         * userId : 88
         * frontImage : c8dc9da1-04bf-4007-ba05-532813005a9e.jpg
         * backImage : b079b1bc-3b7d-4090-98ed-e0da69eb7aa6.jpg
         * type : 0
         * status : 0
         * createdAt : 2020-08-06T08:18:19.000Z
         * updatedAt : 2020-08-06T08:18:19.000Z
         */

        private int id;
        private int userId;
        private String frontImage;
        private String backImage;
        private String selfie;
        private int type;
        private int status;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFrontImage() {
            return frontImage;
        }

        public void setFrontImage(String frontImage) {
            this.frontImage = frontImage;
        }

        public String getBackImage() { return backImage; }

        public void setBackImage(String backImage) {
            this.backImage = backImage;
        }

        public String getSelfie() {
            return selfie;
        }

        public void setSelfie(String selfie) {
            this.selfie = selfie;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.userId);
            dest.writeString(this.frontImage);
            dest.writeString(this.backImage);
            dest.writeString(this.selfie);
            dest.writeInt(this.type);
            dest.writeInt(this.status);
            dest.writeString(this.createdAt);
            dest.writeString(this.updatedAt);
        }

        public IdentityBean() {
        }

        protected IdentityBean(Parcel in) {
            this.id = in.readInt();
            this.userId = in.readInt();
            this.frontImage = in.readString();
            this.backImage = in.readString();
            this.selfie = in.readString();
            this.type = in.readInt();
            this.status = in.readInt();
            this.createdAt = in.readString();
            this.updatedAt = in.readString();
        }

        public static final Creator<IdentityBean> CREATOR = new Creator<IdentityBean>() {
            @Override
            public IdentityBean createFromParcel(Parcel source) {
                return new IdentityBean(source);
            }

            @Override
            public IdentityBean[] newArray(int size) {
                return new IdentityBean[size];
            }
        };
    }
}
