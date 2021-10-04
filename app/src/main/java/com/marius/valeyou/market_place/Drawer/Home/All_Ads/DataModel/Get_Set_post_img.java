package com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel;

public class Get_Set_post_img {
    String id;
    String post_id;
    String img_url;

    public Get_Set_post_img(String id, String post_id, String img_url) {
        this.id = id;
        this.post_id = post_id;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
