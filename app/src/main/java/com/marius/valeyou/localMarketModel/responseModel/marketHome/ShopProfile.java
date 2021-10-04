package com.marius.valeyou.localMarketModel.responseModel.marketHome;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marius.valeyou.localMarketModel.responseModel.BusinessTimeModel;

public class ShopProfile implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("company_name")
    @Expose
    private String companyName = "";
    @SerializedName("register_number")
    @Expose
    private String registerNumber = "";
    @SerializedName("shipping")
    @Expose
    private String shipping = "";
    @SerializedName("address")
    @Expose
    private String address = "";
    @SerializedName("latitude")
    @Expose
    private String latitude = "";
    @SerializedName("longitude")
    @Expose
    private String longitude = "";
    @SerializedName("category")
    @Expose
    private String category = "";
    @SerializedName("business_hours")
    @Expose
    private List<BusinessTimeModel> businessHours = null;
    @SerializedName("country_code")
    @Expose
    private String country_code = "";

    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("market_shop_images")
    @Expose
    private List<PostImage> marketShopImages = null;

    private boolean isCompanySelected;

    public boolean isCompanySelected() {
        return isCompanySelected;
    }

    public void setCompanySelected(boolean companySelected) {
        isCompanySelected = companySelected;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BusinessTimeModel> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(List<BusinessTimeModel> businessHours) {
        this.businessHours = businessHours;
    }

    public String getCountry_code() {
        if(country_code!=null){
            return country_code;
        }
        return "";
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<PostImage> getMarketShopImages() {
        return marketShopImages;
    }

    public void setMarketShopImages(List<PostImage> marketShopImages) {
        this.marketShopImages = marketShopImages;
    }

    public String capitalize(String s) {
        if (s != null) {
            StringBuffer capBuffer = new StringBuffer();
            Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(s);
            while (capMatcher.find()) {
                capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
            }

            return capMatcher.appendTail(capBuffer).toString();
        }
        return "";
    }
}