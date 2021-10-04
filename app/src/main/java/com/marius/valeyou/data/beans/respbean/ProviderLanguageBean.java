package com.marius.valeyou.data.beans.respbean;

public class ProviderLanguageBean {
    /**
     * id : 2
     * providerId : 122
     * name : Hindi
     * type : Good
     * status : 0
     * createdAt : 2020-06-17T14:29:48.000Z
     * updatedAt : 2020-06-17T14:29:48.000Z
     */

    private int id;
    private int providerId;
    private String name;
    private String type;
    private int status;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
