package com.marius.valeyou.data.beans.respbean;

import java.util.List;

public class GetAllJobBean {


    /**
     * id : 3
     * userId : 124
     * providerId : 295
     * title : Outor locla job
     * description : testing
     * location : São José do Rio Preto, State of São Paulo, Brazil
     * date : 1610342683
     * status : 8
     * type : 2
     * jobType : 0
     * city : São José do Rio Preto
     * state : São Paulo
     * cityId : 35
     * stateId : 3549805
     * startTime : 1610342683
     * endTime : 1610364283
     * startPrice : 0
     * endPrice : 650
     * confirmationCode : 121482
     * orderCategories : [{"id":3,"categoryId":76,"category":{"id":76,"name":"Outros","image":"Others - more.png"},"subCategory":[]}]
     * orderImages : [{"id":87,"orderId":3,"images":"24e5bd94-3081-403c-96c2-40091c785b11.jpg","createdAt":"2021-01-11T05:25:38.000Z","updatedAt":"2021-01-11T05:25:38.000Z"}]
     * order_activities : [{"id":25,"user_id":295,"order_id":3,"message":"Start Job","code":7,"status":7,"created_at":1610433729,"updated_at":1610433729},{"id":26,"user_id":295,"order_id":3,"message":"Complete Job","code":8,"status":8,"created_at":1610435334,"updated_at":1610435334}]
     * additional_works : []
     * no_access_charges : []
     * paymentStatus : 1
     */

    private int id;
    private int userId;
    private int providerId;
    private String title;
    private String description;
    private String location;
    private String date;
    private int status;
    private int type;
    private int jobType;
    private String city;
    private String state;
    private int cityId;
    private int stateId;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String endPrice;
    private int confirmationCode;
    private int paymentStatus;
    private int isPay;

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    /**
     * id : 3
     * categoryId : 76
     * category : {"id":76,"name":"Outros","image":"Others - more.png"}
     * subCategory : []
     */

    private List<OrderCategoriesBean> orderCategories;
    /**
     * id : 87
     * orderId : 3
     * images : 24e5bd94-3081-403c-96c2-40091c785b11.jpg
     * createdAt : 2021-01-11T05:25:38.000Z
     * updatedAt : 2021-01-11T05:25:38.000Z
     */

    private List<OrderImagesBean> orderImages;
    /**
     * id : 25
     * user_id : 295
     * order_id : 3
     * message : Start Job
     * code : 7
     * status : 7
     * created_at : 1610433729
     * updated_at : 1610433729
     */

    private List<OrderActivitiesBean> order_activities;
    private List<AdditionalWorksBean> additional_works;
    private List<NoAccessChargesBean> no_access_charges;

    private JobDetailsBean.UserRatingBean user_rating;
    private JobDetailsBean.ProviderRatingBean provider_rating;

    private ReasonBean reason;

    public ReasonBean getReason() {
        return reason;
    }

    public void setReason(ReasonBean reason) {
        this.reason = reason;
    }

    public JobDetailsBean.UserRatingBean getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(JobDetailsBean.UserRatingBean user_rating) {
        this.user_rating = user_rating;
    }

    public JobDetailsBean.ProviderRatingBean getProvider_rating() {
        return provider_rating;
    }

    public void setProvider_rating(JobDetailsBean.ProviderRatingBean provider_rating) {
        this.provider_rating = provider_rating;
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

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public int getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(int confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderCategoriesBean> getOrderCategories() {
        return orderCategories;
    }

    public void setOrderCategories(List<OrderCategoriesBean> orderCategories) {
        this.orderCategories = orderCategories;
    }

    public List<OrderImagesBean> getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(List<OrderImagesBean> orderImages) {
        this.orderImages = orderImages;
    }

    public List<OrderActivitiesBean> getOrder_activities() {
        return order_activities;
    }

    public void setOrder_activities(List<OrderActivitiesBean> order_activities) {
        this.order_activities = order_activities;
    }

    public List<AdditionalWorksBean> getAdditional_works() {
        return additional_works;
    }

    public void setAdditional_works(List<AdditionalWorksBean> additional_works) {
        this.additional_works = additional_works;
    }

    public List<NoAccessChargesBean> getNo_access_charges() {
        return no_access_charges;
    }

    public void setNo_access_charges(List<NoAccessChargesBean> no_access_charges) {
        this.no_access_charges = no_access_charges;
    }

    public static class OrderCategoriesBean {
        private int id;
        private int categoryId;
        /**
         * id : 76
         * name : Outros
         * image : Others - more.png
         */

        private CategoryBean category;
        private List<?> subCategory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public List<?> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<?> subCategory) {
            this.subCategory = subCategory;
        }

        public static class CategoryBean {
            private int id;
            private String name;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }

    public static class OrderImagesBean {
        private int id;
        private int orderId;
        private String images;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
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

    public static class OrderActivitiesBean {
        private int id;
        private int user_id;
        private int order_id;
        private String message;
        private int code;
        private int status;
        private int created_at;
        private int updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }
    }

    public static class NoAccessChargesBean{

        /**
         * id : 3
         * description : 230
         * price : 0
         * paid : 0
         */

        private int id;
        private String description;
        private double price;
        private int paid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getPaid() {
            return paid;
        }

        public void setPaid(int paid) {
            this.paid = paid;
        }
    }

    public static class AdditionalWorksBean {
        private int id;
        private String title;
        private int price;
        private int paid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPaid() {
            return paid;
        }

        public void setPaid(int paid) {
            this.paid = paid;
        }
    }

    public static class UserRatingBean{

        /**
         * id : 48
         * userby : 124
         * userTo : 295
         * orderId : 1
         * ratings : 5
         * description : good job
         * type : 1
         * createdAt : 1610460941
         * updatedAt : 2021-01-12T14:15:40.000Z
         * userBy : 124
         */

        private int id;
        private int userby;
        private int userTo;
        private int orderId;
        private int ratings;
        private String description;
        private int type;
        private int createdAt;
        private String updatedAt;
        private int userBy;

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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
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

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getUserBy() {
            return userBy;
        }

        public void setUserBy(int userBy) {
            this.userBy = userBy;
        }
    }

    public static class ProviderRatingBean{

        /**
         * id : 49
         * userby : 295
         * userTo : 124
         * orderId : 1
         * ratings : 4
         * description : Thanks , i like your services
         * type : 2
         * createdAt : 1610461061
         * updatedAt : 2021-01-12T14:17:41.000Z
         * userBy : 295
         */

        private int id;
        private int userby;
        private int userTo;
        private int orderId;
        private int ratings;
        private String description;
        private int type;
        private int createdAt;
        private String updatedAt;
        private int userBy;

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

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getRatings() {
            return ratings;
        }

        public void setRatings(int ratings) {
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

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getUserBy() {
            return userBy;
        }

        public void setUserBy(int userBy) {
            this.userBy = userBy;
        }
    }

    public static class ReasonBean{

        /**
         * id : 2
         * reason : i am not able to come to do this job bcause of lots of reasn.
         * type : 1
         * order_id : 26
         * user_id : 243
         * user_data : {"id":243,"firstName":"Dummy","lastName":"Provider","image":"c281e331-c4e8-426a-accf-dac7326f8444.jpg"}
         */

        private int id;
        private String reason;
        private int type;
        private int order_id;
        private int user_id;
        /**
         * id : 243
         * firstName : Dummy
         * lastName : Provider
         * image : c281e331-c4e8-426a-accf-dac7326f8444.jpg
         */

        private UserDataBean user_data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public UserDataBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserDataBean user_data) {
            this.user_data = user_data;
        }

        public static class UserDataBean {
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

}
