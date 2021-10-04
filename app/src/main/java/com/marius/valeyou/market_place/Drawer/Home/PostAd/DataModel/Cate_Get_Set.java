package com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel;

import org.json.JSONArray;

public class Cate_Get_Set {
    String Main_cate_id;
    String cate_name;
    String cate_label;
    String language;
    String image;
    JSONArray sub_cate;
    public Cate_Get_Set(String main_cate_id, String cate_name, String cate_label, String language, String image, JSONArray sub_cate) {
        Main_cate_id = main_cate_id;
        this.cate_name = cate_name;
        this.cate_label = cate_label;
        this.language = language;
        this.image = image;
        this.sub_cate = sub_cate;
    }

    public JSONArray getSub_cate() {
        return sub_cate;
    }

    public void setSub_cate(JSONArray sub_cate) {
        this.sub_cate = sub_cate;
    }

    public String getMain_cate_id() {
        return Main_cate_id;
    }

    public void setMain_cate_id(String main_cate_id) {
        Main_cate_id = main_cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_label() {
        return cate_label;
    }

    public void setCate_label(String cate_label) {
        this.cate_label = cate_label;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
