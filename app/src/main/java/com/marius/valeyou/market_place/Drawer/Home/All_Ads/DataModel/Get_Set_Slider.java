package com.marius.valeyou.market_place.Drawer.Home.All_Ads.DataModel;

public class Get_Set_Slider {

    String id;
    String image;
    String type;

    public Get_Set_Slider(String id, String image, String type) {
        this.id = id;
        this.image = image;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
