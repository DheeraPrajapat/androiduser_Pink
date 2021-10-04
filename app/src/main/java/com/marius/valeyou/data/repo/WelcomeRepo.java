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
import com.marius.valeyou.localMarketModel.responseModel.FavouriteModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.SearchedModel;
import com.marius.valeyou.localMarketModel.responseModel.UserDetailsModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface WelcomeRepo {
    Single<ApiResponse<SignupData>> signupApi(Map<String, String> map, int device_type, int ngo);

    Single<ApiResponse<SignupData>> loginApi(Map<String, String> map);

    Single<SimpleApiResponse> sendOTP(String email);

    Single<SimpleApiResponse> verifyEmail(String email, String otp);

    Single<ApiResponse> logOut(int user_id, String auth_key);

    Single<ApiResponse> userChangePassword(String auth_key, String old_password, String new_password);

    Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(String auth_key, String latitude, String longitude, String limit,
                                                             String page, String search);

    Single<ApiResponse<List<CategoryListBean>>> getCategory(String auth_key, int type, String search);

    Single<ApiResponse<List<CategoryListBean>>> getProviderCategories(String auth_key, int providerId);

    Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(String auth_key, int category_id);

    Single<ApiResponse<PrivacyResponseBean>> getAllContent(String auth_key, int type);

    Single<ApiResponse<GetProfileBean>> getProfileData(String auth_key, int user_id);

    Single<ApiResponse<List<FaqBean>>> getFaq(String auth_key, int user_id);

    Single<SimpleApiResponse> forgetPassword(String email);

    Single<SimpleApiResponse> deleteLanguage(String auth_key, int language_id);

    Single<SimpleApiResponse> accountSettings(String auth_key, String type);

    Single<SimpleApiResponse> addToFav(String auth_key, int provider_id, int status);

    Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(String auth_key);

    Single<ApiResponse<ProviderDetails>> getProviderDetails(String authkey, String provider_id);

    Single<ApiResponse<AddJobBean>> createJob(String auth,
                                              RequestBody provider_id,
                                              RequestBody title,
                                              RequestBody description,
                                              RequestBody estimationTime,
                                              RequestBody estimationPrice,
                                              RequestBody location,
                                              RequestBody latitude,
                                              RequestBody longitude,
                                              RequestBody state,
                                              RequestBody zip_code,
                                              RequestBody city,
                                              RequestBody street,
                                              RequestBody appartment,
                                              RequestBody date,
                                              RequestBody time,
                                              RequestBody selected_data,
                                              RequestBody type,
                                              RequestBody startPrice,
                                              RequestBody endPrice,
                                              MultipartBody.Part image,
                                              RequestBody jobType,
                                              RequestBody number);

    Single<ApiResponse<SignupData>> updateProfile(String auth, Map<String, RequestBody> data, MultipartBody.Part image);

    Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(String auth, Map<String, String> data);

    Single<ApiResponse<JobDetailsBean>> getJobDetails(String auth, String post_id);

    Single<SimpleApiResponse> acceptRejectBid(String auth, Map<String, Integer> data);

    Single<SimpleApiResponse> userAddCard(String auth, String name, long card_number, int expiry_year, int expiry_month);

    Single<ApiResponse<List<GetCardBean>>> getUserCard(String auth);

    Single<ApiResponse<List<GetNotificationList>>> getNotificationList(String auth, String acceptLanguage);

    Single<SimpleApiResponse> readNotification(String auth, String type, String notification_id);

    Single<ApiResponse<SignupData>> socialLogin(String social_id, String social_type, int device_type, Map<String, String> data);

    Single<ApiResponse<List<ProviderNearMe>>> addFilter(String auth, String category_id, String id, String rating, String distance, String state, String total_jobs, String search, String sub_cat, String state_id, String city_id);

    Single<SimpleApiResponse> addIdentity(String auth_key, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImage);

    Single<SimpleApiResponse> editIdentity(String auth_key, RequestBody identity_id, RequestBody type, RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImag);

    Single<SimpleApiResponse> deleteIdentity(String auth_key, int identity_id);

    Single<ApiResponse<ComissionModel>> getComission();

    Single<SimpleApiResponse> stripePaymentApi(String auth_key, int orderId, String cardId, String amount, String servicefee, String cvv);

    Single<SimpleApiResponse> rateProvder(String auth_key, int userTo, int orderId, String rating, String description);

    Single<SimpleApiResponse> completeJob(String auth_key, String job_id, String status);

    Single<SimpleApiResponse> deleteCard(String auth_key, String card_id);

    Single<ApiResponse<NotificationBadgeModel>> getCount(String auth_key);

    Single<ApiResponse<NotificationBadgeModel>> getCountMarket(String auth_key);

    Single<ApiResponse<List<StatesModel>>> getStates(String auth_key, String search);

    Single<ApiResponse<List<CitiesModel>>> getCitites(String auth_key, int id, String search);

    Single<ApiResponse<List<SuggestionsModel>>> getSuggestions(String auth_key, String search);

    Single<ApiResponse<List<BookingModel>>> getBookings(String auth_key, String date, String provider_id);

    Single<SimpleApiResponse> deleteJob(String auth_key, String order_id);

    Single<SimpleApiResponse> blockUnblockProvider(String auth_key, String user2Id, String api_type);

    Single<ApiResponse<List<BlockModel>>> blockList(String auth_key);

    Single<SimpleApiResponse> changeStatus(String auth_key, String order_id, String type, String status, String reason);


    Single<SimpleApiResponse> confirmReschedule(String auth_key, String order_id, String type);

    Single<SimpleApiResponse> hireAnotherProvider(String auth_key, String order_id);


    Single<SimpleApiResponse> rescheduleJob(String auth_key, String order_id, String reScheduleDate, String type);

    Single<SimpleApiResponse> updateEndPrice(String auth_key, String orderId, String endPrice);

    Single<MarketCategoryModel> getMarketCategory(String auth_key);

    Single<SearchedModel> getSearchedModel(String auth_key);

    Single<MarketHomeModel> getMarketHomeList(String auth_key, String latitude, String longitude, String search, String category,String private_saller,
                                              String commercial_seller, String only_sale,String only_rent, String radius, String radius2, String price_min, String price_max,String product_type);

    Single<SimpleApiResponse> marketPost(boolean isEdit,
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
                                         MultipartBody.Part[] image);

    Single<FavouriteModel> getFavourite(String auth_key);

    Single<SimpleApiResponse> addToFavourite(String auth_key, String postId, String type);

    Single<UserDetailsModel> getUserDetails(String auth_key, String userId, String type);

    Single<MarketHomeModel> getMyPost(String auth_key);

    Single<SimpleApiResponse> markAsSold(String auth_key, String postId, String sold);

    Single<SimpleApiResponse> deletePost(String auth_key, String postId);

    Single<SimpleApiResponse> deletePostImage(String auth_key, String postImageId);

    Single<SimpleApiResponse> shopCreateProfile(boolean isEdit, String auth,
                                                RequestBody shop_id,
                                                RequestBody company_name,
                                                RequestBody register_number,
                                                RequestBody category,
                                                RequestBody shipping,
                                                RequestBody business_hours,
                                                RequestBody phone,
                                                RequestBody countryCodeBody,
                                                RequestBody address,
                                                RequestBody latitude,
                                                RequestBody longitude,
                                                MultipartBody.Part[] image);

    Single<MarketShopProfile> getMarketShopProfile(String auth_key);


  Single<MarketHomeModel> getFilterProducts(String auth_key,String shop_name,String owner_type);

    Single<SimpleApiResponse> deleteShopImage(String auth_key, String shopImageId);

}
