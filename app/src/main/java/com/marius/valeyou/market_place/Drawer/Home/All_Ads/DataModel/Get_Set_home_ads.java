package com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel;

import org.json.JSONArray;

public class Get_Set_home_ads {
    String id;
    String title;
    String price;
    String city;
    String image_url;
    JSONArray image_arr;

    public Get_Set_home_ads(String id, String title, String price, String city, String image_url, JSONArray image_arr) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.city = city;
        this.image_url = image_url;
        this.image_arr = image_arr;
    }

    public JSONArray getImage_arr() {
        return image_arr;
    }

    public void setImage_arr(JSONArray image_arr) {
        this.image_arr = image_arr;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
