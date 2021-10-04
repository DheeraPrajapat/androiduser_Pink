package com.marius.valeyou.data.beans.respbean;

public class GetNotificationList {

    /**
     * id : 178
     * userId : 165
     * type : 1
     * user2Id : 70
     * isSeen : 0
     * message : Tornado Bid on Your Post
     * createdAt : 1595684430
     * providerImage : 74e58ec9-3b24-4cd8-aa0e-a282b8799623.png
     * firstName : Tornado
     * lastName : Infotech
     */

    private int id;
    private int userId;
    private int type;
    private int user2Id;
    private int isSeen;
    private String message;
    private int createdAt;
    private String providerImage;
    private String firstName;
    private String lastName;
    private int isPayment;
    private Order order;

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    public int getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(int isSeen) {
        this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getProviderImage() {
        return providerImage;
    }

    public void setProviderImage(String providerImage) {
        this.providerImage = providerImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public class Order{

        /**
         * id : 272
         * status : 3
         * jobType : 0
         */

        private int id;
        private int status;
        private int jobType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getJobType() {
            return jobType;
        }

        public void setJobType(int jobType) {
            this.jobType = jobType;
        }
    }
}
