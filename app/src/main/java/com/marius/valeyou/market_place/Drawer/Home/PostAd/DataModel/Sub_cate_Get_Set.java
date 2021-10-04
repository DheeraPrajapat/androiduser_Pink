package com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel;

public class Sub_cate_Get_Set {
    String id;
    String main_category_id;
    String language_id;
    String name;
    String label;
    String image;

    public Sub_cate_Get_Set(String id, String main_category_id, String language_id, String name, String label, String image) {
        this.id = id;
        this.main_category_id = main_category_id;
        this.language_id = language_id;
        this.name = name;
        this.label = label;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_category_id() {
        return main_category_id;
    }

    public void setMain_category_id(String main_category_id) {
        this.main_category_id = main_category_id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
