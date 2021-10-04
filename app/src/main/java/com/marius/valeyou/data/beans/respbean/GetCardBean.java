package com.marius.valeyou.data.beans.respbean;

public class GetCardBean {

    /**
     * id : 3
     * userId : 70
     * name : Rahul Singh
     * cardNumber : 6126367896543234
     * expYear : 24
     * expMonth : 12
     * status : 0
     * createdAt : 2020-07-25T18:49:11.000Z
     * updatedAt : 2020-07-25T18:49:11.000Z
     */

    private int id;
    private int userId;
    private String name;
    private String cardNumber;
    private String expYear;
    private String expMonth;
    private int status;
    private String createdAt;
    private String updatedAt;
    private String cvv;

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
}
