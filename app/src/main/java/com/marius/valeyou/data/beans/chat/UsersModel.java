package com.marius.valeyou.data.beans.chat;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class UsersModel implements Parcelable {

    /**
     * id : 4
     * lastMsgId : 141
     * chat : {"id":141,"userId":88,"user2Id":127,"message":"Hye","createdAt":1582196916,"unread_message":1,"user":{"id":88,"name":"Sanjeev Sharma","image":"90c38d2c-7b66-4dd1-ba44-b81a7c5a7aa8.jpg"}}
     */

    private int id;
    private int lastMsgId;
    private ChatBean chat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastMsgId() {
        return lastMsgId;
    }

    public void setLastMsgId(int lastMsgId) {
        this.lastMsgId = lastMsgId;
    }

    public ChatBean getChat() {
        return chat;
    }

    public void setChat(ChatBean chat) {
        this.chat = chat;
    }

    public static class ChatBean implements Parcelable {
        /**
         * id : 141
         * userId : 88
         * user2Id : 127
         * message : Hye
         * createdAt : 1582196916
         * unread_message : 1
         * user : {"id":88,"name":"Sanjeev Sharma","image":"90c38d2c-7b66-4dd1-ba44-b81a7c5a7aa8.jpg"}
         */

        private int id;
        private int userId;
        private int user2Id;
        private String message;
        private int createdAt;
        private int unread_message;
        private int msgType;
        private int app_type;
        private UserBean user;
        private ShopDataBean shop_data;

        public int getApp_type() {
            return app_type;
        }

        public void setApp_type(int app_type) {
            this.app_type = app_type;
        }

        public ShopDataBean getShop_data() {
            return shop_data;
        }

        public void setShop_data(ShopDataBean shop_data) {
            this.shop_data = shop_data;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public static Creator<ChatBean> getCREATOR() {
            return CREATOR;
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

        public int getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(int createdAt) {
            this.createdAt = createdAt;
        }

        public int getUnread_message() {
            return unread_message;
        }

        public void setUnread_message(int unread_message) {
            this.unread_message = unread_message;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean implements Parcelable {
            /**
             * id : 88
             * name : Sanjeev Sharma
             * image : 90c38d2c-7b66-4dd1-ba44-b81a7c5a7aa8.jpg
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.name);
                dest.writeString(this.image);
            }

            public UserBean() {
            }

            protected UserBean(Parcel in) {
                this.id = in.readInt();
                this.name = in.readString();
                this.image = in.readString();
            }

            public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
                @Override
                public UserBean createFromParcel(Parcel source) {
                    return new UserBean(source);
                }

                @Override
                public UserBean[] newArray(int size) {
                    return new UserBean[size];
                }
            };
        }

        public static class ShopDataBean implements Parcelable{
            private int id;
            private String user_id;
            private String post_type;
            private String sold;
            private String title;
            private String product_type;
            private String phone;
            private String country_code;
            private String category;
            private String description;
            private String shipping;
            private String location;
            private String latitude;
            private String longitude;
            private String owner_type;
            private String price;
            private String fixed_price;
            private String tag_id;
            private String tag;
            private String fav;
            private String status;
            private String shop_id;
            private String shop_name;
           private List<PostImagesModel> post_images;


            public List<PostImagesModel> getPost_images() {
                return post_images;
            }

            public void setPost_images(List<PostImagesModel> post_images) {
                this.post_images = post_images;
            }

            public class PostImagesModel implements Parcelable {
                String id;
                String post_id;
                String image;

                protected PostImagesModel(Parcel in) {
                    id = in.readString();
                    post_id = in.readString();
                    image = in.readString();
                }

                public final Creator<PostImagesModel> CREATOR = new Creator< PostImagesModel>() {
                    @Override
                    public  PostImagesModel createFromParcel(Parcel in) {
                        return new  PostImagesModel(in);
                    }

                    @Override
                    public  PostImagesModel[] newArray(int size) {
                        return new  PostImagesModel[size];
                    }
                };

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPost_id() {
                    return post_id;
                }

                public void setPost_id(String post_id) {
                    this.post_id = post_id;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(id);
                    dest.writeString(post_id);
                    dest.writeString(image);
                }
            }




            protected ShopDataBean(Parcel in) {
                id = in.readInt();
                user_id = in.readString();
                post_type = in.readString();
                sold = in.readString();
                title = in.readString();
                product_type = in.readString();
                phone = in.readString();
                country_code = in.readString();
                category = in.readString();
                description = in.readString();
                shipping = in.readString();
                location = in.readString();
                latitude = in.readString();
                longitude = in.readString();
                owner_type = in.readString();
                price = in.readString();
                fixed_price = in.readString();
                tag_id = in.readString();
                tag = in.readString();
                fav = in.readString();
                status = in.readString();
                shop_id = in.readString();
                shop_name = in.readString();
            }

            public static final Creator<ShopDataBean> CREATOR = new Creator<ShopDataBean>() {
                @Override
                public ShopDataBean createFromParcel(Parcel in) {
                    return new ShopDataBean(in);
                }

                @Override
                public ShopDataBean[] newArray(int size) {
                    return new ShopDataBean[size];
                }
            };

            public String getFav() {
                return fav;
            }

            public void setFav(String fav) {
                this.fav = fav;
            }

            public String getPost_type() {
                return post_type;
            }

            public void setPost_type(String post_type) {
                this.post_type = post_type;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
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

            public String getOwner_type() {
                return owner_type;
            }

            public void setOwner_type(String owner_type) {
                this.owner_type = owner_type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getFixed_price() {
                return fixed_price;
            }

            public void setFixed_price(String fixed_price) {
                this.fixed_price = fixed_price;
            }

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }



            public String getShipping() {
                return shipping;
            }

            public void setShipping(String shipping) {
                this.shipping = shipping;
            }


            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }



            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeString(user_id);
                dest.writeString(post_type);
                dest.writeString(sold);
                dest.writeString(title);
                dest.writeString(product_type);
                dest.writeString(phone);
                dest.writeString(country_code);
                dest.writeString(category);
                dest.writeString(description);
                dest.writeString(shipping);
                dest.writeString(location);
                dest.writeString(latitude);
                dest.writeString(longitude);
                dest.writeString(owner_type);
                dest.writeString(price);
                dest.writeString(fixed_price);
                dest.writeString(tag_id);
                dest.writeString(tag);
                dest.writeString(fav);
                dest.writeString(status);
                dest.writeString(shop_id);
                dest.writeString(shop_name);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.userId);
            dest.writeInt(this.user2Id);
            dest.writeString(this.message);
            dest.writeInt(this.createdAt);
            dest.writeInt(this.unread_message);
            dest.writeParcelable(this.user, flags);
        }

        public ChatBean() {
        }

        protected ChatBean(Parcel in) {
            this.id = in.readInt();
            this.userId = in.readInt();
            this.user2Id = in.readInt();
            this.message = in.readString();
            this.createdAt = in.readInt();
            this.unread_message = in.readInt();
            this.user = in.readParcelable(UserBean.class.getClassLoader());
        }

        public static final Creator<ChatBean> CREATOR = new Creator<ChatBean>() {
            @Override
            public ChatBean createFromParcel(Parcel source) {
                return new ChatBean(source);
            }

            @Override
            public ChatBean[] newArray(int size) {
                return new ChatBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.lastMsgId);
        dest.writeParcelable(this.chat, flags);
    }

    public UsersModel() {
    }

    protected UsersModel(Parcel in) {
        this.id = in.readInt();
        this.lastMsgId = in.readInt();
        this.chat = in.readParcelable(ChatBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UsersModel> CREATOR = new Parcelable.Creator<UsersModel>() {
        @Override
        public UsersModel createFromParcel(Parcel source) {
            return new UsersModel(source);
        }

        @Override
        public UsersModel[] newArray(int size) {
            return new UsersModel[size];
        }
    };
}
