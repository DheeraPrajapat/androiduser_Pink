package com.marius.valeyou.localMarketModel.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;

import java.util.ArrayList;
import java.util.List;

public class MarketShopProfile {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ShopProfile> data=new ArrayList<>();

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ShopProfile> getData() {
        return data;
    }

    public void setData(List<ShopProfile> data) {
        this.data = data;
    }

}
