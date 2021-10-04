package com.marius.valeyou.data.beans.respbean;

public class FavoriteListBean {

    /**
     * id : 7
     * userId : 70
     * providerId : 118
     * status : 1
     * providerfirstName : david
     * providerlastName : joy
     * providerImage : Valeyou_logo.png
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
     * avg_rating : 0
     */

    private int id;
    private int userId;
    private int providerId;
    private int status;
    private String providerfirstName;
    private String providerlastName;
    private String providerImage;
    private String description;
    private double avg_rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProviderfirstName() {
        return providerfirstName;
    }

    public void setProviderfirstName(String providerfirstName) {
        this.providerfirstName = providerfirstName;
    }

    public String getProviderlastName() {
        return providerlastName;
    }

    public void setProviderlastName(String providerlastName) {
        this.providerlastName = providerlastName;
    }

    public String getProviderImage() {
        return providerImage;
    }

    public void setProviderImage(String providerImage) {
        this.providerImage = providerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }
}
