package com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel;

import android.net.Uri;

public class Gallary_Pics_Get_Set {
    Uri Image_Uri;
    String img_id;
    String post_id;

    public Gallary_Pics_Get_Set(Uri image_Uri, String img_id, String post_id) {
        Image_Uri = image_Uri;
        this.img_id = img_id;
        this.post_id = post_id;
    }


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public Uri getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(Uri image_Uri) {
        Image_Uri = image_Uri;
    }
}
