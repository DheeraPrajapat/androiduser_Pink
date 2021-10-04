package com.marius.valeyou.data.repo;

import com.marius.valeyou.data.beans.BookingModel;
import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.SuggestionsModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.block.BlockModel;
import com.marius.valeyou.data.beans.comission.ComissionModel;
import com.marius.valeyou.data.beans.respbean.AddJobBean;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.FaqBean;
import com.marius.valeyou.data.beans.respbean.FavoriteListBean;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.api.WelcomeApi;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.localMarketModel.responseModel.FavouriteModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.SearchedModel;
import com.marius.valeyou.localMarketModel.responseModel.UserDetailsModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.util.Constants;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class WelcomeRepoImpl implements WelcomeRepo {

    private final SharedPref sharedPref;
    private final WelcomeApi welcomeApi;
    private final NetworkErrorHandler networkErrorHandler;

    public WelcomeRepoImpl(SharedPref sharedPref, WelcomeApi welcomeApi, NetworkErrorHandler networkErrorHandler) {
        this.sharedPref = sharedPref;
        this.welcomeApi = welcomeApi;
        this.networkErrorHandler = networkErrorHandler;
    }

    @Override
    public Single<ApiResponse<SignupData>> signupApi(Map<String, String> data, int device_type, int ngo) {
        return welcomeApi.signupApi(Constants.SECURITY_KEY, sharedPref.get("language", "en"), data, device_type, ngo).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<SignupData>> loginApi(Map<String, String> map) {
        return welcomeApi.loginApi(Constants.SECURITY_KEY, sharedPref.get("language", "en"), map).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<SimpleApiResponse> sendOTP(String email) {
        return welcomeApi.sendOTP(Constants.SECURITY_KEY, sharedPref.get("language", "en"), email);
    }

    @Override
    public Single<SimpleApiResponse> verifyEmail(String email, String otp) {
        return welcomeApi.verifyEmail(Constants.SECURITY_KEY, sharedPref.get("language", "en"), email, otp);
    }

    @Override
    public Single<ApiResponse> logOut(int user_id, String auth_key) {
        return welcomeApi.logOut(Constants.SECURITY_KEY, sharedPref.get("language", "en"), user_id, auth_key);
    }

    @Override
    public Single<ApiResponse> userChangePassword(String auth_key, String old_password, String new_password) {
        return welcomeApi.userChangePassword(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, old_password, new_password);
    }

    @Override
    public Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(String auth_key, String latitude, String longitude, String limit, String page, String search) {
        return welcomeApi.getJobProvider(Constants.SECURITY_KEY, auth_key, latitude, longitude, limit, page, search);
    }

    @Override
    public Single<ApiResponse<List<CategoryListBean>>> getCategory(String auth_key, int type, String search) {
        return welcomeApi.getCategory(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, type, search);
    }

    @Override
    public Single<ApiResponse<List<CategoryListBean>>> getProviderCategories(String auth_key, int providerId) {
        return welcomeApi.getRProviderCategories(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, providerId);
    }


    @Override
    public Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(String auth_key, int category_id) {
        return welcomeApi.getSubCategory(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, category_id);
    }

    @Override
    public Single<ApiResponse<PrivacyResponseBean>> getAllContent(String auth_key, int type) {
        return welcomeApi.getAllContent(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, type);
    }

    @Override
    public Single<ApiResponse<GetProfileBean>> getProfileData(String auth_key, int user_id) {
        return welcomeApi.getProfileData(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, user_id);
    }

    @Override
    public Single<ApiResponse<List<FaqBean>>> getFaq(String auth_key, int user_id) {
        return welcomeApi.getFaq(Constants.SECURITY_KEY, auth_key, sharedPref.get("language", "en"), user_id);
    }

    @Override
    public Single<SimpleApiResponse> forgetPassword(String email) {
        return welcomeApi.forgetPassword(Constants.SECURITY_KEY, sharedPref.get("language", "en"), email);
    }

    @Override
    public Single<SimpleApiResponse> deleteLanguage(String auth_key, int language_id) {
        return welcomeApi.deleteLanguage(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, language_id);
    }

    @Override
    public Single<SimpleApiResponse> accountSettings(String auth_key, String type) {
        return welcomeApi.accountSettings(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, type);
    }

    @Override
    public Single<SimpleApiResponse> addToFav(String auth_key, int provider_id, int status) {
        return welcomeApi.addToFav(Constants.SECURITY_KEY, auth_key, sharedPref.get("language", "en"), provider_id, status);
    }

    @Override
    public Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(String auth_key) {
        return welcomeApi.getFavoriteList(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<ApiResponse<ProviderDetails>> getProviderDetails(String authkey, String provider_id) {
        return welcomeApi.getProviderDetails(Constants.SECURITY_KEY, sharedPref.get("language", "en"), authkey, provider_id);
    }

    @Override
    public Single<ApiResponse<AddJobBean>> createJob(String auth, RequestBody provider_id, RequestBody title,
                                                     RequestBody description, RequestBody estimationTime,
                                                     RequestBody estimationPrice, RequestBody location,
                                                     RequestBody latitude, RequestBody longitude, RequestBody state,
                                                     RequestBody zip_code, RequestBody city, RequestBody street,
                                                     RequestBody appartment, RequestBody date, RequestBody time,
                                                     RequestBody selected_data, RequestBody type, RequestBody startPrice,
                                                     RequestBody endPrice, MultipartBody.Part image, RequestBody jobType, RequestBody number) {
        return welcomeApi.createJob(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, provider_id, title, description, estimationTime, estimationPrice,
                location, latitude, longitude, state, zip_code, city, street, appartment, date, time, selected_data, type, startPrice, endPrice, jobType, number, image);
    }

    @Override
    public Single<ApiResponse<SignupData>> updateProfile(String auth, Map<String, RequestBody> data, MultipartBody.Part image) {
        return welcomeApi.updateProfile(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, data, image).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(String auth, Map<String, String> data) {
        return welcomeApi.getAllJobList(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, data);
    }

    @Override
    public Single<ApiResponse<JobDetailsBean>> getJobDetails(String auth, String post_id) {
        return welcomeApi.getJobDetails(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, post_id);
    }

    @Override
    public Single<SimpleApiResponse> acceptRejectBid(String auth, Map<String, Integer> data) {
        return welcomeApi.acceptRejectBid(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, data);
    }

    @Override
    public Single<SimpleApiResponse> userAddCard(String auth, String name, long card_number, int expiry_year, int expiry_month) {
        return welcomeApi.userAddCard(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, name, card_number, expiry_year, expiry_month);
    }

    @Override
    public Single<ApiResponse<List<GetCardBean>>> getUserCard(String auth) {
        return welcomeApi.getUserCard(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth);
    }

    @Override
    public Single<ApiResponse<List<GetNotificationList>>> getNotificationList(String auth, String acceptLanguage) {
        return welcomeApi.getNotificationList(Constants.SECURITY_KEY, auth, acceptLanguage);
    }

    @Override
    public Single<SimpleApiResponse> readNotification(String auth, String type, String notification_id) {
        return welcomeApi.readNotification(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, type, notification_id);
    }

    @Override
    public Single<ApiResponse<SignupData>> socialLogin(String social_id, String social_type, int device_type, Map<String, String> data) {
        return welcomeApi.socialLogin(Constants.SECURITY_KEY, sharedPref.get("language", "en"), social_id, social_type, device_type, data).doOnSuccess(userBeanApiResponse -> {
            if (userBeanApiResponse.getData() != null) {
                sharedPref.putUserData(userBeanApiResponse.getData());
            }
        });
    }

    @Override
    public Single<ApiResponse<List<ProviderNearMe>>> addFilter(String auth, String category_id, String id, String rating, String distance, String state, String total_jobs, String search, String sub_cat, String state_id, String city_id) {
        return welcomeApi.addFilter(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth, category_id, id, rating, distance, state, total_jobs, search, sub_cat, state_id, city_id);
    }

    @Override
    public Single<SimpleApiResponse> addIdentity(String auth_key, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImage) {
        return welcomeApi.addIdentity(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, type, api_type, frontImage, backImage, selfieImage);
    }

    @Override
    public Single<SimpleApiResponse> editIdentity(String auth_key, RequestBody identity_id, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImage) {
        return welcomeApi.editIdentity(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, identity_id, type, api_type, frontImage, backImage, selfieImage);
    }

    @Override
    public Single<SimpleApiResponse> deleteIdentity(String auth_key, int identity_id) {
        return welcomeApi.deleteIdentity(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, identity_id);
    }

    @Override
    public Single<ApiResponse<ComissionModel>> getComission() {
        return welcomeApi.getComission(Constants.SECURITY_KEY, sharedPref.get("language", "en"));
    }

    @Override
    public Single<SimpleApiResponse> stripePaymentApi(String auth_key, int orderId, String cardId, String amount, String servicefee, String cvv) {
        return welcomeApi.stripePaymentApi(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, orderId, cardId, amount, servicefee, cvv);
    }

    @Override
    public Single<SimpleApiResponse> rateProvder(String auth_key, int userTo, int orderId, String rating, String description) {
        return welcomeApi.rateProvider(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, userTo, orderId, rating, description);
    }

    @Override
    public Single<SimpleApiResponse> completeJob(String auth_key, String job_id, String status) {
        return welcomeApi.completeJob(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, job_id, status);
    }

    @Override
    public Single<SimpleApiResponse> deleteCard(String auth_key, String card_id) {
        return welcomeApi.deleteCard(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, card_id);
    }

    @Override
    public Single<ApiResponse<NotificationBadgeModel>> getCount(String auth_key) {
        return welcomeApi.getCount(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<ApiResponse<NotificationBadgeModel>> getCountMarket(String auth_key) {
        return welcomeApi.getCountMarket(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key,"1");
    }

    @Override
    public Single<ApiResponse<List<StatesModel>>> getStates(String auth_key, String search) {
        return welcomeApi.getAllStates(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, search);
    }

    @Override
    public Single<ApiResponse<List<CitiesModel>>> getCitites(String auth_key, int id, String search) {
        return welcomeApi.getCitites(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, id, search);
    }

    @Override
    public Single<ApiResponse<List<SuggestionsModel>>> getSuggestions(String auth_key, String search) {
        return welcomeApi.getSuggestions(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, search);
    }

    @Override
    public Single<ApiResponse<List<BookingModel>>> getBookings(String auth_key, String date, String provider_id) {
        return welcomeApi.getBookings(Constants.SECURITY_KEY, auth_key, sharedPref.get("language", "en"), date, provider_id);
    }

    @Override
    public Single<SimpleApiResponse> deleteJob(String auth_key, String order_id) {
        return welcomeApi.deleteJob(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, order_id);
    }

    @Override
    public Single<SimpleApiResponse> blockUnblockProvider(String auth_key, String user2Id, String api_type) {
        return welcomeApi.blockUnblockProvider(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, user2Id, api_type);
    }

    @Override
    public Single<ApiResponse<List<BlockModel>>> blockList(String auth_key) {
        return welcomeApi.getBlockList(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<SimpleApiResponse> changeStatus(String auth_key, String order_id, String type, String status, String reason) {
        return welcomeApi.changeStatus(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, order_id, type, status, reason);
    }

    @Override
    public Single<SimpleApiResponse> confirmReschedule(String auth_key, String order_id, String type) {
        return welcomeApi.confirmReshedule(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, order_id, type);
    }

    @Override
    public Single<SimpleApiResponse> hireAnotherProvider(String auth_key, String order_id) {

        return welcomeApi.hireAnotherProvider(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, order_id);

    }

    @Override
    public Single<SimpleApiResponse> rescheduleJob(String auth_key, String order_id, String reScheduleDate, String type) {
        return welcomeApi.rescheduleJob(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, order_id, reScheduleDate, type);
    }

    @Override
    public Single<SimpleApiResponse> updateEndPrice(String auth_key, String orderId, String endPrice) {
        return welcomeApi.updateEndPrice(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, orderId, endPrice);
    }

    @Override
    public Single<MarketCategoryModel> getMarketCategory(String auth_key) {
        return welcomeApi.getMarketCategory(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<SearchedModel> getSearchedModel(String auth_key) {
        return welcomeApi.getSearchedModel(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<MarketHomeModel> getMarketHomeList(String auth_key, String latitude, String longitude, String search,
                                                     String category,String private_saller, String commercial_seller, String only_sale,String only_rent,String radius, String radius2,String price_min,String price_max,String product_type) {
        return welcomeApi.getMarketHomeList(Constants.SECURITY_KEY, sharedPref.get("language", "en"),
                auth_key, latitude, longitude, search, category,private_saller , commercial_seller, only_sale, only_rent, radius, radius2,price_min,price_max,product_type
                );
    }


    @Override
    public Single<SimpleApiResponse> marketPost(boolean isEdit,
                                                String auth,
                                                RequestBody postId,
                                                RequestBody title,
                                                RequestBody description,
                                                RequestBody category,
                                                RequestBody product_type,
                                                RequestBody post_type,
                                                RequestBody shipping,
                                                RequestBody price,
                                                RequestBody fixed_price,
                                                RequestBody tag,
                                                RequestBody owner_type,
                                                RequestBody location,
                                                RequestBody latitude,
                                                RequestBody longitude,
                                                RequestBody search_keyword,
                                                RequestBody phone,
                                                RequestBody is_phone_show,
                                                RequestBody country_code,
                                                RequestBody shop_id,
                                                MultipartBody.Part[] image) {
        if (isEdit) {
            return welcomeApi.marketEditPost(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth,
                    postId, title, description, category,product_type, post_type, shipping, price, fixed_price, tag, owner_type,
                    location, latitude, longitude, search_keyword, phone,country_code,shop_id, image);

        } else {
            return welcomeApi.marketAddPost(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth,
                    title, description, category,product_type, post_type, shipping, price, fixed_price, tag, owner_type,
                    location, latitude, longitude, search_keyword, phone,country_code,shop_id, image);
        }
    }


    @Override
    public Single<FavouriteModel> getFavourite(String auth_key) {
        return welcomeApi.getFavourite(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }


    @Override
    public Single<SimpleApiResponse> addToFavourite(String auth_key, String postId, String type) {
        return welcomeApi.addToFavourite(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, postId, type);
    }


    @Override
    public Single<UserDetailsModel> getUserDetails(String auth_key, String userId, String type) {
        return welcomeApi.getUserDetails(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, userId, type);
    }

    @Override
    public Single<MarketHomeModel> getMyPost(String auth_key) {
        return welcomeApi.getMyPost(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<SimpleApiResponse> markAsSold(String auth_key, String postId, String type) {
        return welcomeApi.markAsSold(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, postId, type);
    }

    @Override
    public Single<SimpleApiResponse> deletePost(String auth_key, String postId) {
        return welcomeApi.deletePost(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, postId);
    }

    @Override
    public Single<SimpleApiResponse> deletePostImage(String auth_key, String postImageId) {
        return welcomeApi.deletePostImage(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, postImageId);
    }

    @Override
    public Single<SimpleApiResponse> shopCreateProfile(boolean isEdit, String auth,
                                                       RequestBody shop_id,
                                                       RequestBody company_name,
                                                       RequestBody register_number,
                                                       RequestBody category,
                                                       RequestBody shipping,
                                                       RequestBody business_hours,
                                                       RequestBody phone,
                                                       RequestBody country_code,
                                                       RequestBody address,
                                                       RequestBody latitude,
                                                       RequestBody longitude,
                                                       MultipartBody.Part[] image) {
        if (isEdit) {
            return welcomeApi.shopUpdateProfile(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth,
                    shop_id, company_name, register_number, category, shipping, business_hours, phone,country_code, address, latitude, longitude, image);

        } else {
            return welcomeApi.shopCreateProfile(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth,
                    company_name, register_number, category, shipping, business_hours, phone,country_code, address, latitude, longitude, image);
        }
    }

    @Override
    public Single<MarketShopProfile> getMarketShopProfile(String auth_key) {
        return welcomeApi.getMarketShopProfile(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key);
    }

    @Override
    public Single<MarketHomeModel> getFilterProducts(String auth_key,String shop_name,String owner_type) {
        return welcomeApi.getFilterProducts(Constants.SECURITY_KEY,
                sharedPref.get("language", "en"),
                auth_key,shop_name,owner_type);
    }

    @Override
    public Single<SimpleApiResponse> deleteShopImage(String auth_key, String shopImageId) {
        return welcomeApi.deleteShopImage(Constants.SECURITY_KEY, sharedPref.get("language", "en"), auth_key, shopImageId);
    }



}
