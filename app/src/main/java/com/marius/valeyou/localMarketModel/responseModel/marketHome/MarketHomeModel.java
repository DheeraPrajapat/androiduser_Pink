package com.marius.valeyou.localMarketModel.responseModel.marketHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**fdas
 * Created by Narad on 25-05-2021 at 04:16 PM.
 */
public class MarketHomeModel {

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
    private List<MarketPostModel> data = null;

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

    public List<MarketPostModel> getData() {
        return data;
    }

    public void setData(List<MarketPostModel> data) {
        this.data = data;
    }

}
