package com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel;

import org.json.JSONArray;

public class Get_Set_Fav_Ads {

    String post_id;
    String price;
    String created;
    String title;
    // About the user
    String user_id;
    String user_name;
    String user_email;
    String user_pic;
    JSONArray img_array;
    String sub_category_id;
    String main_category_id;
    String sub_category_name;
    String main_category_name;



    // Images Data Model
    String img_id;
    String img_url;

    public Get_Set_Fav_Ads (String post_id, String price, String created, String title, String user_id
            , String user_name, String user_email, String user_pic, JSONArray img_array, String sub_category_id, String main_category_id,
                               String sub_category_name,  String main_category_name) {
        this.post_id = post_id;
        this.price = price;
        this.created = created;
        this.title = title;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_id = user_id;
        this.user_pic = user_pic;
        this.img_array = img_array;
        this.sub_category_id = sub_category_id;
        this.main_category_id = main_category_id;
        this.sub_category_name = sub_category_name;
        this.main_category_name = main_category_name;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getMain_category_name() {
        return main_category_name;
    }

    public void setMain_category_name(String main_category_name) {
        this.main_category_name = main_category_name;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getMain_category_id() {
        return main_category_id;
    }

    public void setMain_category_id(String main_category_id) {
        this.main_category_id = main_category_id;
    }

    public JSONArray getImg_array() {
        return img_array;
    }

    public void setImg_array(JSONArray img_array) {
        this.img_array = img_array;
    }
//    public Get_Set_Display_Ads(String img_id, String img_url) {
//        this.img_id = img_id;
//        this.img_url = img_url;
//    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getImg_id() {
//        return img_id;
//    }
//
//    public void setImg_id(String img_id) {
//        this.img_id = img_id;
//    }

//    public String getImg_url() {
//        return img_url;
//    }
//
//    public void setImg_url(String img_url) {
//        this.img_url = img_url;
//    }

}
