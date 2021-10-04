package com.marius.valeyou.data.beans.chat;

public class MessagesModel {

    /**
     * id : 1
     * userId : 67
     * user2Id : 122
     * message : hii
     * constantId : 2
     * msg_type : 0
     * type : 0
     * createdAt : 1522078598
     * user2_name : Nisha
     * user2_image : user.png
     * user_name : chandan
     * user_image : 860943ae-1f19-470b-8da6-3ee74f3da0b9.png
     */

    private int id;
    private int userId;
    private int user2Id;
    private String message;
    private int constantId;

    private int type;
    private int createdAt;
    private String user2_name;
    private String user2_image;
    private String user_name;
    private String user_image;
    private int fav;
    private int i_block;
    private int he_block;
    private int msgType;
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getI_block() {
        return i_block;
    }

    public void setI_block(int i_block) {
        this.i_block = i_block;
    }

    public int getHe_block() {
        return he_block;
    }

    public void setHe_block(int he_block) {
        this.he_block = he_block;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getConstantId() {
        return constantId;
    }

    public void setConstantId(int constantId) {
        this.constantId = constantId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser2_name() {
        return user2_name;
    }

    public void setUser2_name(String user2_name) {
        this.user2_name = user2_name;
    }

    public String getUser2_image() {
        return user2_image;
    }

    public void setUser2_image(String user2_image) {
        this.user2_image = user2_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

}
