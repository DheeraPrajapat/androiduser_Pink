package com.marius.valeyou.util.location;

import android.location.Location;

import com.google.android.gms.common.api.ResolvableApiException;

public class LocCallback {
    private Type type;
    private ResolvableApiException apiException;
    private String message;
    private Location location;
    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public LocCallback(Type type, ResolvableApiException apiException) {
        this.type = type;
        this.apiException = apiException;
    }

    public LocCallback(Type type, Location location) {
        this.type = type;
        this.location = location;
    }

    public LocCallback(Type type) {
        this.type = type;
    }

    public LocCallback(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public ResolvableApiException getApiException() {
        return apiException;
    }

    public String getMessage() {
        return message!=null?message:"";
    }

    public Location getLocation() {
        return location;
    }

    public enum Type {
        OPEN_GPS, FOUND, STARTED, STOPPED, ERROR,PROMPT_CANCEL
    }
}
