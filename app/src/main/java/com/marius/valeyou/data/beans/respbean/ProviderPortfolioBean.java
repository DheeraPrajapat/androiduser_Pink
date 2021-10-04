package com.marius.valeyou.data.beans.respbean;

public class ProviderPortfolioBean {
    /**
     * id : 1
     * providerId : 122
     * title : test
     * description : test data
     * status : 0
     * image : 7ea1ee9b-524b-43a4-9143-ae7422816bd7.jpg
     * createdAt : 2020-06-18T13:08:35.000Z
     * updatedAt : 2020-06-18T13:08:35.000Z
     */

    private int id;
    private int providerId;
    private String title;
    private String description;
    private int status;
    private String image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
