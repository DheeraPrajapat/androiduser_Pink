package com.marius.valeyou.localMarketModel.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedModel {

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
        private List<Datum> data = null;

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

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }


    public static class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("keyword")
        @Expose
        private String keyword;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("top_rate")
        @Expose
        private Integer topRate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getTopRate() {
            return topRate;
        }

        public void setTopRate(Integer topRate) {
            this.topRate = topRate;
        }

    }
}
