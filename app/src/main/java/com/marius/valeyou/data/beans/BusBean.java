package com.marius.valeyou.data.beans;

import androidx.annotation.NonNull;

public class BusBean {
    @NonNull
    private BusType busType;
    private Object data;
    private String message;

    public BusBean(@NonNull BusType busType) {
        this.busType = busType;
    }

    public BusBean(@NonNull BusType busType, Object data) {
        this.busType = busType;
        this.data = data;
    }

    public BusBean(@NonNull BusType busType, Object data, String message) {
        this.busType = busType;
        this.data = data;
        this.message = message;
    }

    @NonNull
    public  BusType getBusType() {
        return busType;
    }

    public void setBusType(@NonNull BusType busType) {
        this.busType = busType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum BusType {
        MEET_COUNT,CHAT
    }
}
