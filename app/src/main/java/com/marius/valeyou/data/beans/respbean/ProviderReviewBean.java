package com.marius.valeyou.data.beans.respbean;

public class ProviderReviewBean {

    /**
     * id : 1
     * userby : 67
     * userTo : 119
     * ratings : 3
     * description : good job
     * type : 1
     * createdAt : 1585216676
     * updatedAt : null
     * userBy : 67
     * user : {"id":67,"firstName":"Chandan","lastName":"thakur","image":"860943ae-1f19-470b-8da6-3ee74f3da0b9.png"}
     */

    private int id;
    private int userby;
    private int userTo;
    private String ratings;
    private String description;
    private int type;
    private int createdAt;
    private Object updatedAt;
    private int userBy;
    private UserBean user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserby() {
        return userby;
    }

    public void setUserby(int userby) {
        this.userby = userby;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserBy() {
        return userBy;
    }

    public void setUserBy(int userBy) {
        this.userBy = userBy;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 67
         * firstName : Chandan
         * lastName : thakur
         * image : 860943ae-1f19-470b-8da6-3ee74f3da0b9.png
         */

        private int id;
        private String firstName;
        private String lastName;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
