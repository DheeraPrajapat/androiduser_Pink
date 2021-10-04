package com.marius.valeyou.data.beans.respbean;

import java.util.List;

public class ProviderDetails {

    /**
     * id : 122
     * name : Nisha Rani
     * phone : 123456
     * image : user.png
     * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
     * address : V.P.O Panjoa Kalan teh amb
     * email : s@yopmail.com
     * like_status : 0
     * average_rating : 4.5
     * total_ratings : 2
     * totalprovidedservices : 0
     * firstName : Nisha
     * lastName : Rani
     * countryCode : +91
     * providerCategories : [{"id":5,"categoryId":18,"providerId":122,"category":{"id":18,"name":"Saloon","image":"saloon.png"},"subCategories":[{"id":9,"categoryId":18,"providerId":122,"subCategory":{"id":4,"name":"Spa","image":"kindpng_1721146.png"}},{"id":10,"categoryId":18,"providerId":122,"subCategory":{"id":2,"name":"Bathroom Cleaning","image":"bathroom.jpg"}}]}]
     * providerLanguage : [{"id":2,"providerId":122,"name":"Hindi","type":"Good","status":0,"createdAt":"2020-06-17T14:29:48.000Z","updatedAt":"2020-06-17T14:29:48.000Z"}]
     * providerPortfolio : [{"id":1,"providerId":122,"title":"test","description":"test data","status":0,"image":"7ea1ee9b-524b-43a4-9143-ae7422816bd7.jpg","createdAt":"2020-06-18T13:08:35.000Z","updatedAt":"2020-06-18T13:08:35.000Z"}]
     * providerCertificate : [{"id":1,"providerId":122,"image":"87528cca-40cc-44e7-a2e8-46ca527478a3.jpg","title":"add certificate","description":"my certificate addeed","status":0,"createdAt":"2020-06-18T13:13:33.000Z","updatedAt":"2020-06-18T13:13:33.000Z"}]
     * providerBusinessHours : [{"id":1,"providerId":122,"day":"Monday","fromTime":"10am","toTime":"7px","createdAt":"2020-06-17T14:27:13.000Z","updatedAt":"2020-06-17T14:27:13.000Z"},{"id":2,"providerId":122,"day":"Tuesday","fromTime":"10am","toTime":"7px","createdAt":"2020-06-17T14:27:13.000Z","updatedAt":"2020-06-17T14:27:13.000Z"}]
     */

    private int id;
    private String name;
    private String phone;
    private String image;
    private String description;
    private String address;
    private String email;
    private int like_status;
    private double average_rating;
    private int total_ratings;
    private int totalprovidedservices;
    private String firstName;
    private String lastName;
    private String countryCode;
    private String category_name;
    private String distance;
    private String tagId;
    private List<ProviderCategoriesBean> providerCategories;
    private List<ProviderLanguageBean> providerLanguage;
    private List<ProviderPortfolioBean> providerPortfolio;
    private List<ProviderCertificateBean> providerCertificate;
    private List<ProviderBusinessHoursBean> providerBusinessHours;
    private List<ProviderReviewBean> ProviderReview;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    public int getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(int total_ratings) {
        this.total_ratings = total_ratings;
    }

    public int getTotalprovidedservices() {
        return totalprovidedservices;
    }

    public void setTotalprovidedservices(int totalprovidedservices) {
        this.totalprovidedservices = totalprovidedservices;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<ProviderCategoriesBean> getProviderCategories() {
        return providerCategories;
    }

    public void setProviderCategories(List<ProviderCategoriesBean> providerCategories) {
        this.providerCategories = providerCategories;
    }

    public List<ProviderLanguageBean> getProviderLanguage() {
        return providerLanguage;
    }

    public void setProviderLanguage(List<ProviderLanguageBean> providerLanguage) {
        this.providerLanguage = providerLanguage;
    }

    public List<ProviderPortfolioBean> getProviderPortfolio() {
        return providerPortfolio;
    }

    public void setProviderPortfolio(List<ProviderPortfolioBean> providerPortfolio) {
        this.providerPortfolio = providerPortfolio;
    }

    public List<ProviderCertificateBean> getProviderCertificate() {
        return providerCertificate;
    }

    public void setProviderCertificate(List<ProviderCertificateBean> providerCertificate) {
        this.providerCertificate = providerCertificate;
    }

    public List<ProviderBusinessHoursBean> getProviderBusinessHours() {
        return providerBusinessHours;
    }

    public void setProviderBusinessHours(List<ProviderBusinessHoursBean> providerBusinessHours) {
        this.providerBusinessHours = providerBusinessHours;
    }

    public List<ProviderReviewBean> getProviderReview() {
        return ProviderReview;
    }

    public void setProviderReview(List<ProviderReviewBean> providerReview) {
        ProviderReview = providerReview;
    }

    public static class ProviderCategoriesBean {
        /**
         * id : 5
         * categoryId : 18
         * providerId : 122
         * category : {"id":18,"name":"Saloon","image":"saloon.png"}
         * subCategories : [{"id":9,"categoryId":18,"providerId":122,"subCategory":{"id":4,"name":"Spa","image":"kindpng_1721146.png"}},{"id":10,"categoryId":18,"providerId":122,"subCategory":{"id":2,"name":"Bathroom Cleaning","image":"bathroom.jpg"}}]
         */

        private int id;
        private int categoryId;
        private int providerId;
        private CategoryBean category;
        private List<SubCategoriesBean> subCategories;

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

        public int getProviderId() {
            return providerId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public List<SubCategoriesBean> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(List<SubCategoriesBean> subCategories) {
            this.subCategories = subCategories;
        }

        public static class CategoryBean {
            /**
             * id : 18
             * name : Saloon
             * image : saloon.png
             */

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

        public static class SubCategoriesBean {
            /**
             * id : 9
             * categoryId : 18
             * providerId : 122
             * subCategory : {"id":4,"name":"Spa","image":"kindpng_1721146.png"}
             */

            private int id;
            private int categoryId;
            private int providerId;
            private SubCategoryBean subCategory;

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

            public int getProviderId() {
                return providerId;
            }

            public void setProviderId(int providerId) {
                this.providerId = providerId;
            }

            public SubCategoryBean getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(SubCategoryBean subCategory) {
                this.subCategory = subCategory;
            }

            public static class SubCategoryBean {
                /**
                 * id : 4
                 * name : Spa
                 * image : kindpng_1721146.png
                 */

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
    }

}
