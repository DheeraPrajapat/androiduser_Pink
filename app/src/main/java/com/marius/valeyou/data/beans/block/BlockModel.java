package com.marius.valeyou.data.beans.block;

public class BlockModel {

    /**
     * id : 1
     * userId : 124
     * user2Id : 243
     * type : 0
     * createdAt : 2020-12-10T09:00:02.000Z
     * updatedAt : 2020-12-10T09:00:02.000Z
     * provider : {"id":243,"first_name":"Sanjeev","last_name":"Sharma","image":"864e5606-8f26-4f2a-a472-b5e26873acf8.jpg"}
     */

    private int id;
    private int userId;
    private int user2Id;
    private int type;
    private String createdAt;
    private String updatedAt;
    private ProviderBean provider;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public static class ProviderBean {
        /**
         * id : 243
         * first_name : Sanjeev
         * last_name : Sharma
         * image : 864e5606-8f26-4f2a-a472-b5e26873acf8.jpg
         */

        private int id;
        private String first_name;
        private String last_name;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
