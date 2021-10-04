package com.marius.valeyou.data.beans.respbean;

public class BidsBean {

    /**
     * id : 4
     * orderId : 178
     * price : 200
     * created : 1595684430
     * status : 0
     * providerFirstName : Tornado
     * providerLastName : Infotech
     * providerId : 165
     * providerImage : 74e58ec9-3b24-4cd8-aa0e-a282b8799623.png
     * providerDescription : great work
     * avgrating : 0
     * distance : 101.53
     */

    private int id;
    private int orderId;
    private int price;
    private int created;
    private int status;
    private String providerFirstName;
    private String providerLastName;
    private int providerId;
    private String providerImage;
    private String providerDescription;
    private int avgrating;
    private double distance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProviderFirstName() {
        return providerFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        this.providerFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return providerLastName;
    }

    public void setProviderLastName(String providerLastName) {
        this.providerLastName = providerLastName;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderImage() {
        return providerImage;
    }

    public void setProviderImage(String providerImage) {
        this.providerImage = providerImage;
    }

    public String getProviderDescription() {
        return providerDescription;
    }

    public void setProviderDescription(String providerDescription) {
        this.providerDescription = providerDescription;
    }

    public int getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(int avgrating) {
        this.avgrating = avgrating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
