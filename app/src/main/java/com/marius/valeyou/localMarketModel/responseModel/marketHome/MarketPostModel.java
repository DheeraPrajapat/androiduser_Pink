package com.marius.valeyou.localMarketModel.responseModel.marketHome;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marius.valeyou.data.beans.signup.SignupData;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Narad on 31-05-2021 at 12:11 PM.
 */
public class MarketPostModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("product_type")
    @Expose
    private String product_type;


    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("fixed_price")
    @Expose
    private String fixedPrice;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("owner_type")
    @Expose
    private String ownerType;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("search_keyword")
    @Expose
    private String searchKeyword;
    @SerializedName("updated_at")
    @Expose
    private Integer updatedAt;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("fav")
    @Expose
    private Integer fav;
    @SerializedName("post_images")
    @Expose
    private List<PostImage> postImages = null;
    @SerializedName("user")
    @Expose
    private SignupData user;
    @SerializedName("shop_profile")
    @Expose
    private ShopProfile shopProfile;

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

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPrice() {
        return price;
    }

    public String getStrPrice() {
        return price.toString();
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(String fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getSoldStatus() {
        if (sold.equalsIgnoreCase("No")) {
            return "Mark as sold";
        } else {
            return "Sold";
        }
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getSearchKeyword() {
        return searchKeyword == null ? "" : searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Integer getFav() {
        return fav;
    }

    public void setFav(Integer fav) {
        this.fav = fav;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImage> postImages) {
        this.postImages = postImages;
    }

    public SignupData getUser() {
        return user;
    }

    public void setUser(SignupData user) {
        this.user = user;
    }

    public ShopProfile getShopProfile() {
        return shopProfile;
    }

    public void setShopProfile(ShopProfile shopProfile) {
        this.shopProfile = shopProfile;
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
