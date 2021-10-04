package com.marius.valeyou.data.beans;

public class StatesModel {

    /**
     * id : 11
     * name : Rondônia
     * abbr : RO
     * createdAt : 2020-09-22T17:17:20.000Z
     * updatedAt : 2020-09-22T17:17:20.000Z
     */

    private int id;
    private String name;
    private String abbr;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
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
}
