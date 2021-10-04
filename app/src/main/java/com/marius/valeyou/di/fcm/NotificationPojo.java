package com.marius.valeyou.di.fcm;

import java.io.Serializable;

/**
 * Created by jatin on 6/13/2017.
 */

public class NotificationPojo {

    /**
     * data : {"firstName":"Sanjeev","lastName":"Sharma","image":"90c38d2c-7b66-4dd1-ba44-b81a7c5a7aa8.jpg","countryCode":"+91","latitude":"31.3851592","location":"40 north alerton st","id":88,"email":"sanjeev1991.ss@gmail.com","longitude":"75.3857309"}
     * title : Valeyou
     * message : Sanjeev Sharma added you favorit list
     */

    private DataBean data;
    private String title;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * firstName : Sanjeev
         * lastName : Sharma
         * image : 90c38d2c-7b66-4dd1-ba44-b81a7c5a7aa8.jpg
         * countryCode : +91
         * latitude : 31.3851592
         * location : 40 north alerton st
         * id : 88
         * email : sanjeev1991.ss@gmail.com
         * longitude : 75.3857309
         */

        private String firstName;
        private String lastName;
        private String image;
        private String countryCode;
        private String latitude;
        private String location;
        private int id;
        private String email;
        private String longitude;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }

}
