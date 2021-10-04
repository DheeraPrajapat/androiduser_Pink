package com.marius.valeyou.market_place.Drawer.Home.Country_list;

public class Country_get_Set {
    String id;
    String name;
    String code;
    String active;
    String default_country;

    public Country_get_Set(String id, String name, String code, String active, String default_country) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.active = active;
        this.default_country = default_country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDefault_country() {
        return default_country;
    }

    public void setDefault_country(String default_country) {
        this.default_country = default_country;
    }
}
