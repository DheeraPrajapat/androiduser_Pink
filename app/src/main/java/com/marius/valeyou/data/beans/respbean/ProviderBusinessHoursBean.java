package com.marius.valeyou.data.beans.respbean;

public class ProviderBusinessHoursBean {
    /**
     * id : 1
     * providerId : 122
     * day : Monday
     * fromTime : 10am
     * toTime : 7px
     * createdAt : 2020-06-17T14:27:13.000Z
     * updatedAt : 2020-06-17T14:27:13.000Z
     */

    private int id;
    private int providerId;
    private String day;
    private String fromTime;
    private String toTime;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
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
