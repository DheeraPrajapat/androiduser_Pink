package com.marius.valeyou.localMarketModel.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;

import java.util.List;

/**
 * Created by Narad on 31-05-2021 at 12:16 PM.
 */
public class FavouriteModel {

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
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("post_id")
        @Expose
        private Integer postId;
        @SerializedName("login_type")
        @Expose
        private Integer loginType;
        @SerializedName("created_at")
        @Expose
        private Integer createdAt;
        @SerializedName("updated_at")
        @Expose
        private Integer updatedAt;
        @SerializedName("market_post")
        @Expose
        private MarketPostModel marketPost;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getPostId() {
            return postId;
        }

        public void setPostId(Integer postId) {
            this.postId = postId;
        }

        public Integer getLoginType() {
            return loginType;
        }

        public void setLoginType(Integer loginType) {
            this.loginType = loginType;
        }

        public Integer getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Integer createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Integer updatedAt) {
            this.updatedAt = updatedAt;
        }

        public MarketPostModel getMarketPost() {
            return marketPost;
        }

        public void setMarketPost(MarketPostModel marketPost) {
            this.marketPost = marketPost;
        }

    }

}
