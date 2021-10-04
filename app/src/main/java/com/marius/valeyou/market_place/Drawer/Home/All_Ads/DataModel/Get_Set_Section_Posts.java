package com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel;

import org.json.JSONArray;

public class Get_Set_Section_Posts {
    String id;
    String section_name;
    String section_order;
    JSONArray section_posts_arr;

    public Get_Set_Section_Posts(String id, String section_name, String section_order, JSONArray section_posts_arr) {
        this.id = id;
        this.section_name = section_name;
        this.section_order = section_order;
        this.section_posts_arr = section_posts_arr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_order() {
        return section_order;
    }

    public void setSection_order(String section_order) {
        this.section_order = section_order;
    }

    public JSONArray getSection_posts_arr() {
        return section_posts_arr;
    }

    public void setSection_posts_arr(JSONArray section_posts_arr) {
        this.section_posts_arr = section_posts_arr;
    }
}
