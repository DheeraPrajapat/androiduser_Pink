package com.marius.valeyou.data.beans;

public class CitiesModel {

    /**
     * id : 1100015
     * stateId : 11
     * name : Alta Floresta d`Oeste
     * createdAt : 2020-09-22T17:14:11.000Z
     * updatedAt : 2020-09-22T17:14:11.000Z
     */

    private int id;
    private int stateId;
    private String name;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
