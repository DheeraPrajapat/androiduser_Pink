package com.marius.valeyou.data.beans.base;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaginatedList<T> {
    @SerializedName("total")
    private long total;
    @SerializedName("per_page")
    private long perPage;
    @SerializedName("current_page")
    private long currentPage;
    @SerializedName("last_page")
    private long lastPage;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    @SerializedName("from")
    private Long from;
    @SerializedName("to")
    private Long to;
    @SerializedName("data")
    private ArrayList<T> data = new ArrayList<T>();

    public long getTotal() {
        return total;
    }

    public long getPerPage() {
        return perPage;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public long getLastPage() {
        return lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public ArrayList<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PaginatedList{" +
                "total=" + total +
                ", perPage=" + perPage +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                ", nextPageUrl='" + nextPageUrl + '\'' +
                ", prevPageUrl='" + prevPageUrl + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", data=" + data +
                '}';
    }
}