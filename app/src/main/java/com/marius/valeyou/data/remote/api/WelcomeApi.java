package com.marius.valeyou.data.remote.api;

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
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WelcomeApi {


    @FormUrlEncoded
    @POST("user_signup")
    Single<ApiResponse<SignupData>> signupApi(@Header("security_key") String security_key, @Header("accept-language") String acceptLanguage, @FieldMap() Map<String, String> data,
                                              @Field("device_type ") int device_type, @Field("ngo") int ngo);

    @FormUrlEncoded
    @POST("user_login")
    Single<ApiResponse<SignupData>> loginApi(@Header("security_key") String security_key, @Header("accept-language") String acceptLanguage, @FieldMap() Map<String, String> data);

    @PUT("user_logout")
    Single<ApiResponse> logOut(@Header("security_key") String security_key, @Header("accept-language") String acceptLanguage, @Header("user_id") int user_id, @Header("auth_key") String auth_key);

    @FormUrlEncoded
    @PUT("user_change_password")
    Single<ApiResponse> userChangePassword(@Header("security_key") String security_key, @Header("accept-language") String acceptLanguage, @Header("auth_key") String auth_key,
                                           @Field("old_password") String old_password, @Field("new_password") String new_password);

    @GET("user_provider_nearme")
    Single<ApiResponse<List<ProviderNearMe>>> getJobProvider(@Header("security_key") String security_key, @Header("auth_key") String auth_key,
                                                             @Header("latitude") String latitude, @Header("longitude") String longitude,
                                                             @Query("limit") String limit, @Query("page") String page,
                                                             @Query("search") String search);

    @GET("user_get_categories")
    Single<ApiResponse<List<CategoryListBean>>> getCategory(@Header("security_key") String security_key,
                                                            @Header("accept-language") String acceptLanguage,
                                                            @Header("auth_key") String auth_key,
                                                            @Header("type") int type,
                                                            @Query("search") String search);

    @GET("user_get_categories")
    Single<ApiResponse<List<CategoryListBean>>> getRProviderCategories(@Header("security_key") String security_key,
                                                                       @Header("accept-language") String acceptLanguage,
                                                                       @Header("auth_key") String auth_key,
                                                                       @Query("providerId") int providerId);

    @GET("user_get_sub_categories")
    Single<ApiResponse<List<SubCategoryBean>>> getSubCategory(@Header("security_key") String security_key,
                                                              @Header("accept-language") String acceptLanguage,
                                                              @Header("auth_key") String auth_key, @Header("category_id") int category_id);

    @GET("all_content")
    Single<ApiResponse<PrivacyResponseBean>> getAllContent(@Header("security_key") String security_key,
                                                           @Header("accept-language") String acceptLanguage,
                                                           @Header("auth_key") String auth_key, @Header("type") int type);

    @GET("user_get_profile")
    Single<ApiResponse<GetProfileBean>> getProfileData(@Header("security_key") String security_key,
                                                       @Header("accept-language") String acceptLanguage,
                                                       @Header("auth_key") String auth_key, @Header("user_id") int user_id);

    @GET("user_faqs")
    Single<ApiResponse<List<FaqBean>>> getFaq(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth_key, @Header("user_id") int user_id);

    @FormUrlEncoded
    @POST("user_forgot_password")
    Single<SimpleApiResponse> forgetPassword(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Field("email") String email);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "provider_delete_language", hasBody = true)
    Single<SimpleApiResponse> deleteLanguage(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Header("auth_key") String auth_key, @Field("lang_id") int firstname);

    @GET("user_account_setting")
    Single<SimpleApiResponse> accountSettings(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth_key, @Header("type") String type);

    @FormUrlEncoded
    @POST("user_add_to_fav_list")
    Single<SimpleApiResponse> addToFav(@Header("security_key") String security_key,
                                       @Header("accept-language") String acceptLanguage,
                                       @Header("auth_key") String auth_key,
                                       @Field("provider_id") int provider_id, @Field("status") int status);

    @GET("user_get_favourite_list")
    Single<ApiResponse<List<FavoriteListBean>>> getFavoriteList(@Header("security_key") String security_key,
                                                                @Header("accept-language") String acceptLanguage,
                                                                @Header("auth_key") String auth_key);

    @GET("user_get_provider_detail")
    Single<ApiResponse<ProviderDetails>> getProviderDetails(@Header("security_key") String security_key,
                                                            @Header("accept-language") String acceptLanguage,
                                                            @Header("auth_key") String auth, @Header("provider_id") String privider_id);

    @Multipart
    @POST("user_add_post")
    Single<ApiResponse<AddJobBean>> createJob(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth,
                                              @Part("provider_id") RequestBody provider_id,
                                              @Part("title") RequestBody title,
                                              @Part("description") RequestBody description,
                                              @Part("startTime") RequestBody estimationTime,
                                              @Part("endTime") RequestBody estimationPrice,
                                              @Part("location") RequestBody location,
                                              @Part("latitude") RequestBody latitude,
                                              @Part("longitude") RequestBody longitude,
                                              @Part("state") RequestBody state,
                                              @Part("zipCode") RequestBody zip_code,
                                              @Part("city") RequestBody city,
                                              @Part("street") RequestBody street,
                                              @Part("appartment") RequestBody appartment,
                                              @Part("date") RequestBody date,
                                              @Part("time") RequestBody time,
                                              @Part("selected_data") RequestBody selected_data,
                                              @Part("type") RequestBody type,
                                              @Part("startPrice") RequestBody startPrice,
                                              @Part("endPrice") RequestBody endPrice,
                                              @Part("jobType") RequestBody jobType,
                                              @Part("number") RequestBody number,
                                              @Part MultipartBody.Part image);

    @Multipart
    @PUT("user_edit_profile")
    Single<ApiResponse<SignupData>> updateProfile(@Header("security_key") String security_key,
                                                  @Header("accept-language") String acceptLanguage,
                                                  @Header("auth_key") String auth,
                                                  @PartMap Map<String, RequestBody> data,
                                                  @Part MultipartBody.Part image);

    @GET("user_job_list")
    Single<ApiResponse<List<GetAllJobBean>>> getAllJobList(@Header("security_key") String security_key,
                                                           @Header("accept-language") String acceptLanguage,
                                                           @Header("auth_key") String auth, @QueryMap Map<String, String> data);

    @GET("user_post_details")
    Single<ApiResponse<JobDetailsBean>> getJobDetails(@Header("security_key") String security_key,
                                                      @Header("accept-language") String acceptLanguage,
                                                      @Header("auth_key") String auth, @Header("post_id") String post_id);

    @FormUrlEncoded
    @POST("user_accept_reject_bid")
    Single<SimpleApiResponse> acceptRejectBid(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth, @FieldMap() Map<String, Integer> data);

    @FormUrlEncoded
    @POST("user_add_card")
    Single<SimpleApiResponse> userAddCard(@Header("security_key") String security_key,
                                          @Header("accept-language") String acceptLanguage,
                                          @Header("auth_key") String auth, @Field("name") String name,
                                          @Field("card_number") long card_number, @Field("expiry_year") int expiry_year,
                                          @Field("expiry_month") int expiry_month);

    @GET("user_get_card")
    Single<ApiResponse<List<GetCardBean>>> getUserCard(@Header("security_key") String security_key,
                                                       @Header("accept-language") String acceptLanguage,
                                                       @Header("auth_key") String auth);

    @GET("user_get_notifications")
    Single<ApiResponse<List<GetNotificationList>>> getNotificationList(@Header("security_key") String security_key, @Header("auth_key") String auth,
                                                                       @Header("accept-language") String acceptlanguage);

    @GET("user_read_notification")
    Single<SimpleApiResponse> readNotification(@Header("security_key") String security_key,
                                               @Header("accept-language") String acceptLanguage,
                                               @Header("auth_key") String auth,
                                               @Header("type") String type, @Header("notification_id") String notification_id);

    @FormUrlEncoded
    @POST("user_social_login")
    Single<ApiResponse<SignupData>> socialLogin(@Header("security_key") String security_key,
                                                @Header("accept-language") String acceptLanguage,
                                                @Field("social_id") String social_id,
                                                @Field("social_type") String social_type, @Field("device_type ") int device_type,
                                                @FieldMap() Map<String, String> data);

    @GET("user_filter_search")
    Single<ApiResponse<List<ProviderNearMe>>> addFilter(@Header("security_key") String security_key,
                                                        @Header("accept-language") String acceptLanguage,
                                                        @Header("auth_key") String auth,

                                                        @Header("category_id") String category_id, @Header("id") String id,

                                                        @Header("rating") String rating,
                                                        @Header("distance") String distance, @Header("state") String state, @Header("total_jobs") String total_jobs,
                                                        @Header("search") String search, @Header("sub_cat") String sub_cat, @Header("state_id") String state_id, @Header("city_id") String city_id);


    @Multipart
    @POST("user_add_edit_identity")
    Single<SimpleApiResponse> addIdentity(@Header("security_key") String security_key,
                                          @Header("accept-language") String acceptLanguage,
                                          @Header("auth_key") String auth,
                                          @Part("type") RequestBody type,
                                          @Part("api_type") RequestBody api_type,
                                          @Part MultipartBody.Part frontImage,
                                          @Part MultipartBody.Part backImage,
                                          @Part MultipartBody.Part selfieImage);

    @Multipart
    @POST("user_add_edit_identity")
    Single<SimpleApiResponse> editIdentity(@Header("security_key") String security_key,
                                           @Header("accept-language") String acceptLanguage,
                                           @Header("auth_key") String auth,
                                           @Part("identity_id") RequestBody identity_id,
                                           @Part("type") RequestBody type,
                                           @Part("api_type") RequestBody api_type,
                                           @Part MultipartBody.Part frontImage,
                                           @Part MultipartBody.Part backImage,
                                           @Part MultipartBody.Part selfieImage);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user_delete_identity", hasBody = true)
    Single<SimpleApiResponse> deleteIdentity(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Header("auth_key") String auth_key,
                                             @Field("identity_id") int identity_id);


    @FormUrlEncoded
    @POST("user_send_verify_email")
    Single<SimpleApiResponse> sendOTP(@Header("security_key") String security_key,
                                      @Header("accept-language") String acceptLanguage,
                                      @Field("email") String email);

    @FormUrlEncoded
    @POST("user_check_verify_email_otp")
    Single<SimpleApiResponse> verifyEmail(@Header("security_key") String security_key,
                                          @Header("accept-language") String acceptLanguage,
                                          @Field("email") String email,
                                          @Field("otp") String otp);

    @GET("get_comission")
    Single<ApiResponse<ComissionModel>> getComission(@Header("security_key") String security_key, @Header("accept-language") String acceptLanguage);

    @POST("user_stripe_payment")
    @FormUrlEncoded
    Single<SimpleApiResponse> stripePaymentApi(@Header("security_key") String security_key,
                                               @Header("accept-language") String acceptLanguage,
                                               @Header("auth_key") String auth_key,
                                               @Field("orderId") int orderId,
                                               @Field("cardId") String cardId,
                                               @Field("amount") String amount,
                                               @Field("serviceFees") String serviceFees,
                                               @Field("cvv") String cvv);

    @POST("user_rate_provider")
    @FormUrlEncoded
    Single<SimpleApiResponse> rateProvider(@Header("security_key") String security_key,
                                           @Header("accept-language") String acceptLanguage,
                                           @Header("auth_key") String auth_key,
                                           @Field("userTo") int userTo,
                                           @Field("orderId") int orderId,
                                           @Field("ratings") String ratings,
                                           @Field("description") String description);


    @POST("complete_job")
    @FormUrlEncoded
    Single<SimpleApiResponse> completeJob(@Header("security_key") String security_key,
                                          @Header("accept-language") String acceptLanguage,
                                          @Header("auth_key") String auth_key,
                                          @Field("job_id") String job_id,
                                          @Field("status") String status);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user_delete_card", hasBody = true)
    Single<SimpleApiResponse> deleteCard(@Header("security_key") String security_key,
                                         @Header("accept-language") String acceptLanguage,
                                         @Header("auth_key") String auth_key,
                                         @Field("card_id") String card_id);


    @GET("user_get_notification_count")
    Single<ApiResponse<NotificationBadgeModel>> getCount(@Header("security_key") String security_key,
                                                         @Header("accept-language") String acceptLanguage,
                                                         @Header("auth_key") String auth_key);

    @GET("user_get_notification_count")
    Single<ApiResponse<NotificationBadgeModel>> getCountMarket(@Header("security_key") String security_key,
                                                               @Header("accept-language") String acceptLanguage,
                                                               @Header("auth_key") String auth_key, @Query("app_type") String app_type);


    @GET("get_state")
    Single<ApiResponse<List<StatesModel>>> getAllStates(@Header("security_key") String security_key,
                                                        @Header("accept-language") String acceptLanguage,
                                                        @Header("auth_key") String auth_key,
                                                        @Query("search") String search);


    @GET("get_cities")
    Single<ApiResponse<List<CitiesModel>>> getCitites(@Header("security_key") String security_key,
                                                      @Header("accept-language") String acceptLanguage,
                                                      @Header("auth_key") String auth_key,
                                                      @Query("id") int id,
                                                      @Query("search") String search);


    @GET("user_sugessions")
    Single<ApiResponse<List<SuggestionsModel>>> getSuggestions(@Header("security_key") String security_key,
                                                               @Header("accept-language") String acceptLanguage,
                                                               @Header("auth_key") String auth_key,
                                                               @Header("search") String search);

    @GET("user_get_provider_availibility")
    Single<ApiResponse<List<BookingModel>>> getBookings(@Header("security_key") String security_key,
                                                        @Header("accept-language") String acceptLanguage,
                                                        @Header("auth_key") String auth_key,
                                                        @Query("date") String date,
                                                        @Query("provider_id") String provider_id);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "delete_job", hasBody = true)
    Single<SimpleApiResponse> deleteJob(@Header("security_key") String security_key,
                                        @Header("accept-language") String acceptLanguage,
                                        @Header("auth_key") String auth_key,
                                        @Field("order_id") String order_id);


    @POST("block_provider")
    @FormUrlEncoded
    Single<SimpleApiResponse> blockUnblockProvider(@Header("security_key") String security_key,
                                                   @Header("accept-language") String acceptLanguage,
                                                   @Header("auth_key") String auth_key,
                                                   @Field("user2Id") String user2Id,
                                                   @Field("api_type") String api_type);


    @GET("block_list")
    Single<ApiResponse<List<BlockModel>>> getBlockList(@Header("security_key") String security_key,
                                                       @Header("accept-language") String acceptLanguage,
                                                       @Header("auth_key") String auth_key);


    @POST("change_status")
    @FormUrlEncoded
    Single<SimpleApiResponse> changeStatus(@Header("security_key") String security_key,
                                           @Header("accept-language") String acceptLanguage,
                                           @Header("auth_key") String auth_key,
                                           @Field("order_id") String order_id,
                                           @Field("type") String type,
                                           @Field("status") String status,
                                           @Field("reason") String reason);


    @POST("confirm_reschedule_job")
    @FormUrlEncoded
    Single<SimpleApiResponse> confirmReshedule(@Header("security_key") String security_key,
                                               @Header("accept-language") String acceptLanguage,
                                               @Header("auth_key") String auth_key,
                                               @Field("order_id") String order_id,
                                               @Field("type") String type);

    @POST("hire_new_provider")
    @FormUrlEncoded
    Single<SimpleApiResponse> hireAnotherProvider(@Header("security_key") String security_key,
                                                  @Header("accept-language") String acceptLanguage,
                                                  @Header("auth_key") String auth_key,
                                                  @Field("order_id") String order_id);

    @POST("reschedule_job")
    @FormUrlEncoded
    Single<SimpleApiResponse> rescheduleJob(@Header("security_key") String security_key,
                                            @Header("accept-language") String acceptLanguage,
                                            @Header("auth_key") String auth_key,
                                            @Field("order_id") String order_id,
                                            @Field("reScheduleDate") String reScheduleDate,
                                            @Field("type") String type);


    @POST("user_update_job")
    @FormUrlEncoded
    Single<SimpleApiResponse> updateEndPrice(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Header("auth_key") String auth_key,
                                             @Field("orderId") String order_id,
                                             @Field("endPrice") String endPrice);


    @GET("market_category")
    Single<MarketCategoryModel> getMarketCategory(@Header("security_key") String security_key,
                                                  @Header("accept-language") String acceptLanguage,
                                                  @Header("auth_key") String auth_key);

    @GET("market_get_search_history")
    Single<SearchedModel> getSearchedModel(@Header("security_key") String security_key,
                                           @Header("accept-language") String acceptLanguage,
                                           @Header("auth_key") String auth_key);

    @GET("market_home")
    Single<MarketHomeModel> getMarketHomeList(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth_key,
                                              @Query("latitude") String latitude,
                                              @Query("longitude") String longitude,
                                              @Query("search") String search,
                                              @Query("category") String category,
                                              @Query("private_saller") String private_saller,
                                              @Query("commercial_seller") String commercial_seller,
                                              @Query("only_sale") String only_sale,
                                              @Query("only_rent") String only_rent,
                                              @Query("radious") String radious,
                                              @Query("radious2") String radious2,
                                              @Query("price_min") String price_min,
                                              @Query("price_max") String price_max,
                                              @Query("product_type") String product_type

    );

    @Multipart
    @POST("market_add_post")
    Single<SimpleApiResponse> marketAddPost(@Header("security_key") String security_key,
                                            @Header("accept-language") String acceptLanguage,
                                            @Header("auth_key") String auth,
                                            @Part("title") RequestBody title,
                                            @Part("description") RequestBody description,
                                            @Part("category") RequestBody category,
                                            @Part("product_type") RequestBody product_type,
                                            @Part("post_type") RequestBody post_type,
                                            @Part("shipping") RequestBody shipping,
                                            @Part("price") RequestBody price,
                                            @Part("fixed_price") RequestBody fixed_price,
                                            @Part("tag") RequestBody tag,
                                            @Part("owner_type") RequestBody owner_type,
                                            @Part("location") RequestBody location,
                                            @Part("latitude") RequestBody latitude,
                                            @Part("longitude") RequestBody longitude,
                                            @Part("search_keyword") RequestBody search_keyword,
                                            @Part("phone") RequestBody phone,
                                            @Part("country_code") RequestBody country_code,
                                            @Part("shop_id") RequestBody shop_id,
                                            @Part MultipartBody.Part[] image);


    @Multipart
    @PUT("market_edit_post")
    Single<SimpleApiResponse> marketEditPost(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Header("auth_key") String auth,
                                             @Part("post_id") RequestBody postId,
                                             @Part("title") RequestBody title,
                                             @Part("description") RequestBody description,
                                             @Part("category") RequestBody category,
                                             @Part("product_type") RequestBody product_type,
                                             @Part("post_type") RequestBody post_type,
                                             @Part("shipping") RequestBody shipping,
                                             @Part("price") RequestBody price,
                                             @Part("fixed_price") RequestBody fixed_price,
                                             @Part("tag") RequestBody tag,
                                             @Part("owner_type") RequestBody owner_type,
                                             @Part("location") RequestBody location,
                                             @Part("latitude") RequestBody latitude,
                                             @Part("longitude") RequestBody longitude,
                                             @Part("search_keyword") RequestBody search_keyword,
                                             @Part("phone") RequestBody phone,
                                             @Part("shop_id") RequestBody shop_id,
                                             @Part("country_code") RequestBody country_code,
                                             @Part MultipartBody.Part[] image);


    @GET("market_get_fav_post")
    Single<FavouriteModel> getFavourite(@Header("security_key") String security_key,
                                        @Header("accept-language") String acceptLanguage,
                                        @Header("auth_key") String auth_key);

    @FormUrlEncoded
    @POST("market_add_fav_post")
    Single<SimpleApiResponse> addToFavourite(@Header("security_key") String security_key,
                                             @Header("accept-language") String acceptLanguage,
                                             @Header("auth_key") String auth_key,
                                             @Field("post_id") String postId,
                                             @Field("type") String type);//type(1=fav,0=unfav)

    @GET("market_get_user_details")
    Single<UserDetailsModel> getUserDetails(@Header("security_key") String security_key,
                                            @Header("accept-language") String acceptLanguage,
                                            @Header("auth_key") String auth_key,
                                            @Query("user_id") String userId,
                                            @Query("type") String type);

    @GET("market_get_my_post")
    Single<MarketHomeModel> getMyPost(@Header("security_key") String security_key,
                                      @Header("accept-language") String acceptLanguage,
                                      @Header("auth_key") String auth_key);

    @FormUrlEncoded
    @POST("market_mark_as_sold")
    Single<SimpleApiResponse> markAsSold(@Header("security_key") String security_key,
                                         @Header("accept-language") String acceptLanguage,
                                         @Header("auth_key") String auth_key,
                                         @Field("post_id") String postId,
                                         @Field("sold") String sold);//send (Yes, No)


    @HTTP(method = "DELETE", path = "market_delete_post", hasBody = true)
    Single<SimpleApiResponse> deletePost(@Header("security_key") String security_key,
                                         @Header("accept-language") String acceptLanguage,
                                         @Header("auth_key") String auth_key,
                                         @Query("post_id") String postId);


    @HTTP(method = "DELETE", path = "market_delete_post_image", hasBody = true)
    Single<SimpleApiResponse> deletePostImage(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth_key,
                                              @Query("img_id") String postImageId);


    @Multipart
    @POST("market_create_profile")
    Single<SimpleApiResponse> shopCreateProfile(@Header("security_key") String security_key,
                                                @Header("accept-language") String acceptLanguage,
                                                @Header("auth_key") String auth,
                                                @Part("company_name") RequestBody company_name,
                                                @Part("register_number") RequestBody register_number,
                                                @Part("category") RequestBody category,
                                                @Part("shipping") RequestBody shipping,
                                                @Part("business_hours") RequestBody business_hours,
                                                @Part("phone") RequestBody phone,
                                                @Part("country_code") RequestBody country_code,
                                                @Part("address") RequestBody address,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude,
                                                @Part MultipartBody.Part[] image);


    @Multipart
    @PUT("market_edit_shop_profile")
    Single<SimpleApiResponse> shopUpdateProfile(@Header("security_key") String security_key,
                                                @Header("accept-language") String acceptLanguage,
                                                @Header("auth_key") String auth,
                                                @Part("shop_id") RequestBody shop_id,
                                                @Part("company_name") RequestBody company_name,
                                                @Part("register_number") RequestBody register_number,
                                                @Part("category") RequestBody category,
                                                @Part("shipping") RequestBody shipping,
                                                @Part("business_hours") RequestBody business_hours,
                                                @Part("phone") RequestBody phone,
                                                @Part("country_code") RequestBody country_code,
                                                @Part("address") RequestBody address,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude,
                                                @Part MultipartBody.Part[] image);


    @GET("market_get_shop_profile")
    Single<MarketShopProfile> getMarketShopProfile(@Header("security_key") String security_key,
                                                   @Header("accept-language") String acceptLanguage,
                                                   @Header("auth_key") String auth_key);

    @GET("market_get_my_post")
    Single<MarketHomeModel> getFilterProducts(@Header("security_key") String security_key,
                                                   @Header("accept-language") String acceptLanguage,
                                                   @Header("auth_key") String auth_key,
                                                   @Query("shop_name") String shop_name,
                                                   @Query("owner_type") String owner_type);


    @HTTP(method = "DELETE", path = "market_delete_shop_image", hasBody = true)
    Single<SimpleApiResponse> deleteShopImage(@Header("security_key") String security_key,
                                              @Header("accept-language") String acceptLanguage,
                                              @Header("auth_key") String auth_key,
                                              @Query("img_id") String postImageId);

}
