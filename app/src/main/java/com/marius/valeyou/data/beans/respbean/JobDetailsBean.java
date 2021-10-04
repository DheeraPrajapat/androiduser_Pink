package com.marius.valeyou.data.beans.respbean;

import java.util.List;

public class JobDetailsBean {

    /**
     * id : 1
     * date : 1610104815
     * providerId : 295
     * userId : 124
     * title : Local Job
     * startTime : 1610104815
     * endTime : 1610133615
     * startPrice : 0
     * endPrice : 330
     * city : São Bernardo do Campo
     * state : São Paulo
     * cityId : 35
     * stateId : 3548708
     * description : tedting jobs
     * location : São Bernardo do Campo, State of São Paulo, Brazil
     * status : 8
     * time : 1610104815
     * type : 2
     * jobType : 1
     * confirmationCode : 101088
     * reScheduleDate : 0
     * isPay : 0
     * isPayment : 0
     * isRate : 0
     * bidPrice : 350
     * bid_status : 0
     * orderCategories : [{"id":1,"categoryId":48,"category":{"id":48,"name":"Eventos","image":"Events.png"},"subCategory":[]}]
     * provider : {"id":295,"firstName":"Nicky","lastName":"Sharma","image":"19f5ccf5-ec9c-46f5-940d-a66ccde9c617.jpg","phone":"97816 30062"}
     * order_activities : [{"id":16,"user_id":295,"order_id":1,"message":"Arriving Provider","code":3,"status":3,"created_at":1610108515,"updated_at":1610108515},{"id":17,"user_id":295,"order_id":1,"message":"Arrived Provider","code":4,"status":4,"created_at":1610113690,"updated_at":1610113690},{"id":18,"user_id":295,"order_id":1,"message":"Code Verified","code":6,"status":6,"created_at":1610115669,"updated_at":1610115669},{"id":19,"user_id":295,"order_id":1,"message":"Start Job","code":7,"status":7,"created_at":1610118235,"updated_at":1610118235},{"id":20,"user_id":295,"order_id":1,"message":"Complete Job","code":8,"status":8,"created_at":1610119842,"updated_at":1610119842}]
     * additional_works : [{"id":7,"title":"test","price":234,"paid":0},{"id":8,"title":"Switch","price":33,"paid":0}]
     * no_access_charges : []
     * orderImages : [{"id":85,"orderId":1,"images":"edc5169d-703b-47ef-93fb-3a9405b0e938.jpg","createdAt":"2021-01-08T11:21:40.000Z","updatedAt":"2021-01-08T11:21:40.000Z"}]
     * user_rating : {}
     * provider_rating : {}
     * bids : [{"id":110,"orderId":1,"price":350,"createdAt":1610107918,"status":1,"providerFirstName":"Nicky","providerLastName":"Sharma","providerId":295,"providerImage":"19f5ccf5-ec9c-46f5-940d-a66ccde9c617.jpg","providerDescription":"i am provider","avgrating":4.2,"distance":0}]
     */

    private int id;
    private String date;
    private int providerId;
    private int userId;
    private String title;
    private String startTime;
    private String endTime;
    private String startPrice;
    private String endPrice;
    private String city;
    private String state;
    private int cityId;
    private int stateId;
    private String description;
    private String location;
    private int status;
    private String time;
    private int type;
    private int jobType;
    private int confirmationCode;
    private String reScheduleDate;
    private int isPay;
    private int isPayment;
    private int isRate;
    private int bidPrice;
    private int bid_status;
    private double total_amount;
    private int isSchedule;
    private int total_additional_amount;

    public int getTotal_additional_amount() {
        return total_additional_amount;
    }

    public void setTotal_additional_amount(int total_additional_amount) {
        this.total_additional_amount = total_additional_amount;
    }

    public int getIsSchedule() {
        return isSchedule;
    }

    public void setIsSchedule(int isSchedule) {
        this.isSchedule = isSchedule;
    }

    /**
     * id : 295
     * firstName : Nicky
     * lastName : Sharma
     * image : 19f5ccf5-ec9c-46f5-940d-a66ccde9c617.jpg
     * phone : 97816 30062
     */

    private ProviderBean provider;
    private List<ProviderWorkImagesBean> provider_work_images;

    public List<ProviderWorkImagesBean> getProvider_work_images() {
        return provider_work_images;
    }

    public void setProvider_work_images(List<ProviderWorkImagesBean> provider_work_images) {
        this.provider_work_images = provider_work_images;
    }

    /**
     * id : 1
     * categoryId : 48
     * category : {"id":48,"name":"Eventos","image":"Events.png"}
     * subCategory : []
     */

    private List<OrderCategoriesBean> orderCategories;
    /**
     * id : 16
     * user_id : 295
     * order_id : 1
     * message : Arriving Provider
     * code : 3
     * status : 3
     * created_at : 1610108515
     * updated_at : 1610108515
     */

    private List<OrderActivitiesBean> order_activities;
    /**
     * id : 7
     * title : test
     * price : 234
     * paid : 0
     */

    private List<AdditionalWorksBean> additional_works;
    private List<NoAccessChargesBean> no_access_charges;

    private UserRatingBean user_rating;
    private ProviderRatingBean provider_rating;

    public UserRatingBean getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(UserRatingBean user_rating) {
        this.user_rating = user_rating;
    }

    public ProviderRatingBean getProvider_rating() {
        return provider_rating;
    }

    public void setProvider_rating(ProviderRatingBean provider_rating) {
        this.provider_rating = provider_rating;
    }

    /**
     * id : 85
     * orderId : 1
     * images : edc5169d-703b-47ef-93fb-3a9405b0e938.jpg
     * createdAt : 2021-01-08T11:21:40.000Z
     * updatedAt : 2021-01-08T11:21:40.000Z
     */

    private List<OrderImagesBean> orderImages;

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * id : 110
     * orderId : 1
     * price : 350
     * createdAt : 1610107918
     * status : 1
     * providerFirstName : Nicky
     * providerLastName : Sharma
     * providerId : 295
     * providerImage : 19f5ccf5-ec9c-46f5-940d-a66ccde9c617.jpg
     * providerDescription : i am provider
     * avgrating : 4.2
     * distance : 0
     */



    private List<BidsBean> bids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(int confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getReScheduleDate() {
        return reScheduleDate;
    }

    public void setReScheduleDate(String reScheduleDate) {
        this.reScheduleDate = reScheduleDate;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public int getIsRate() {
        return isRate;
    }

    public void setIsRate(int isRate) {
        this.isRate = isRate;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getBid_status() {
        return bid_status;
    }

    public void setBid_status(int bid_status) {
        this.bid_status = bid_status;
    }

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public List<OrderCategoriesBean> getOrderCategories() {
        return orderCategories;
    }

    public void setOrderCategories(List<OrderCategoriesBean> orderCategories) {
        this.orderCategories = orderCategories;
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

    public List<OrderImagesBean> getOrderImages() {
        return orderImages;
    }

    public void setOrderImages(List<OrderImagesBean> orderImages) {
        this.orderImages = orderImages;
    }

    public List<BidsBean> getBids() {
        return bids;
    }

    public void setBids(List<BidsBean> bids) {
        this.bids = bids;
    }

    public static class ProviderBean {
        private int id;
        private String firstName;
        private String lastName;
        private String image;
        private String phone;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class ProviderWorkImagesBean{

        /**
         * id : 5
         * order_id : 14
         * provider_id : 243
         * image : 545446e6-71dc-48c9-ac22-11093f2b55fd.jpg
         * type : 0
         * status : 1
         * created_at : 1611569222
         * updated_at : 1611569222
         */

        private int id;
        private int order_id;
        private int provider_id;
        private String image;
        private int type;
        private int status;
        private int created_at;
        private int updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getProvider_id() {
            return provider_id;
        }

        public void setProvider_id(int provider_id) {
            this.provider_id = provider_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

    public static class OrderCategoriesBean {
        private int id;
        private int categoryId;
        /**
         * id : 48
         * name : Eventos
         * image : Events.png
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

    public static class BidsBean {
        private int id;
        private int orderId;
        private int price;
        private int createdAt;
        private int status;
        private String providerFirstName;
        private String providerLastName;
        private int providerId;
        private String providerImage;
        private String providerDescription;
        private String avgrating;
        private String distance;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(int createdAt) {
            this.createdAt = createdAt;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getProviderFirstName() {
            return providerFirstName;
        }

        public void setProviderFirstName(String providerFirstName) {
            this.providerFirstName = providerFirstName;
        }

        public String getProviderLastName() {
            return providerLastName;
        }

        public void setProviderLastName(String providerLastName) {
            this.providerLastName = providerLastName;
        }

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public String getProviderImage() {
            return providerImage;
        }

        public void setProviderImage(String providerImage) {
            this.providerImage = providerImage;
        }

        public String getProviderDescription() {
            return providerDescription;
        }

        public void setProviderDescription(String providerDescription) {
            this.providerDescription = providerDescription;
        }

        public String getAvgrating() {
            return avgrating;
        }

        public void setAvgrating(String avgrating) {
            this.avgrating = avgrating;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        private String id;
        private int userby;
        private int userTo;
        private int orderId;
        private String ratings;
        private String description;
        private int type;
        private int createdAt;
        private String updatedAt;
        private int userBy;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        private String id;
        private int userby;
        private int userTo;
        private int orderId;
        private String ratings;
        private String description;
        private int type;
        private int createdAt;
        private String updatedAt;
        private int userBy;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
}
