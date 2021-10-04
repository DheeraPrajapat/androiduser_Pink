package com.marius.valeyou.market_place.Drawer.Home.City_Listt;

public class City_Get_Set {
    String id;
    String city_name;
    String country_id;
    String default_val;
    String country_name;


    public City_Get_Set(String id, String city_name, String country_id, String default_val,String country_name) {
        this.id = id;
        this.city_name = city_name;
        this.country_id = country_id;
        this.default_val = default_val;
        this.country_name = country_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getDefault_val() {
        return default_val;
    }

    public void setDefault_val(String default_val) {
        this.default_val = default_val;
    }
}
