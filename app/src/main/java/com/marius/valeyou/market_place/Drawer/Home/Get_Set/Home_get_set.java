package com.marius.valeyou.market_place.Drawer.Home.Get_Set;

public class Home_get_set {
    String cate_id;
    String cate_name;
    String cate_label;
    String cate_lang_id;
    String main_cat_id;
    String sub_cat_id;
    String image;

    public Home_get_set(String cate_id, String cate_name, String cate_label, String cate_lang_id, String main_cat_id,  String sub_cat_id, String image) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.cate_label = cate_label;
        this.cate_lang_id = cate_lang_id;
        this.main_cat_id = main_cat_id;
        this.sub_cat_id = sub_cat_id;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(String main_cat_id) {
        this.main_cat_id = main_cat_id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
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

    public String getCate_lang_id() {
        return cate_lang_id;
    }

    public void setCate_lang_id(String cate_lang_id) {
        this.cate_lang_id = cate_lang_id;
    }
}
