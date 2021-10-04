package com.marius.valeyou.di.module.androidcomponent;

import com.marius.valeyou.PostDetailActivity;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.ui.activity.allCategory.AllCategoryActivity;
import com.marius.valeyou.localMarketModel.ui.activity.changePassword.ChangePasswordActivity;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.editProfile.EditProfile2Activity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.activity.other_user_profile.OtherUserProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.searched.SearchedActivity;
import com.marius.valeyou.localMarketModel.ui.activity.selectWorkAreaMap.SelectWorkAreaMapActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivity;
import com.marius.valeyou.ui.RatingActivity;
import com.marius.valeyou.ui.activity.LocationActivity;
import com.marius.valeyou.ui.activity.PayPerHourActivity;
import com.marius.valeyou.ui.activity.block.BlockActivity;
import com.marius.valeyou.ui.activity.categorytitle.CategoryTitleActivity;
import com.marius.valeyou.ui.activity.forgot.ForgotPasswordActivity;
import com.marius.valeyou.ui.activity.forgot.recoverpass.RecoverPasswordActivity;
import com.marius.valeyou.ui.activity.identityverfication.IdentityVerificationActivity;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.post_detail_sub.SubMapActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.activity.signup.SignupActivity;
import com.marius.valeyou.ui.activity.tourpage.TourActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype.WhenTypeActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.where.WhereActivity;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.faq.FAQActivity;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.addidentity.AddIdentityActivity;
import com.marius.valeyou.ui.fragment.more.profile.addportfolio.AddPortfolioActivity;
import com.marius.valeyou.ui.fragment.more.profile.editprofile.EditProfileActivity;
import com.marius.valeyou.ui.fragment.more.profile.selectlanguage.SelectLanguageActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.additionalcost.AdditionalCostActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.attachments.AttachmentsActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.uploaded_work.UploadedWorkActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.PaymentCardDetails;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess.PaymentSuccessActivity;
import com.marius.valeyou.ui.fragment.myjob.reschedule.RescheduleActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    // NOTE: UserApp  here

    @ContributesAndroidInjector
    abstract WelcomeActivity welcomeActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector
    abstract SignupActivity signupActivity();

    @ContributesAndroidInjector
    abstract ForgotPasswordActivity forgotPasswordActivity();

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract TourActivity tourActivity();

    @ContributesAndroidInjector
    abstract RecoverPasswordActivity recoverPasswordActivity();

    @ContributesAndroidInjector
    abstract NotificationActivity notificationActivity();

    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

 @ContributesAndroidInjector
    abstract OtherUserProfileActivity otherUserProfileActivity();

    @ContributesAndroidInjector
    abstract EditProfileActivity editProfileActivity();

    @ContributesAndroidInjector
    abstract WhereActivity whereActivity();

    @ContributesAndroidInjector
    abstract WhenActivity whenActivity();

    @ContributesAndroidInjector
    abstract PaymentActivity paymentActivity();

    @ContributesAndroidInjector
    abstract PaymentCardDetails paymentCardDetails();

    @ContributesAndroidInjector
    abstract PaymentSuccessActivity paymentSuccessActivity();

    @ContributesAndroidInjector
    abstract ChatActivity chatActivity();

    @ContributesAndroidInjector
    abstract ChangePasswordFragment changePasswordFragment();

    @ContributesAndroidInjector
    abstract FAQActivity faqActivity();

    @ContributesAndroidInjector
    abstract SelectLanguageActivity selectLanguageActivity();

    @ContributesAndroidInjector
    abstract AddPortfolioActivity addPortfolioActivity();

    @ContributesAndroidInjector
    abstract SelectCategoryActivity selectCategoryActivity();

    @ContributesAndroidInjector
    abstract ProviderProfileActivity providerProfileActivity();

    @ContributesAndroidInjector
    abstract WhenTypeActivity whenTypeActivity();

    @ContributesAndroidInjector
    abstract CategoryTitleActivity categoryTitleActivity();

    @ContributesAndroidInjector
    abstract PayPerHourActivity payPerHourActivity();

    @ContributesAndroidInjector
    abstract AddIdentityActivity addIdentityActivity();

    @ContributesAndroidInjector
    abstract RatingActivity ratingActivity();

    @ContributesAndroidInjector
    abstract LocationActivity locationActivity();

    @ContributesAndroidInjector
    abstract IdentityVerificationActivity identityVerificationActivity();

    @ContributesAndroidInjector
    abstract BlockActivity blockActivity();

    @ContributesAndroidInjector
    abstract AppLanguageActivity appLanguageActivity();

    @ContributesAndroidInjector
    abstract JobDetailActivity jobDetailActivity();

    @ContributesAndroidInjector
    abstract RescheduleActivity rescheduleActivity();


    @ContributesAndroidInjector
    abstract AdditionalCostActivity additionalCostActivity();

    @ContributesAndroidInjector
    abstract UploadedWorkActivity uploadedWorkActivity();

    @ContributesAndroidInjector
    abstract AttachmentsActivity attachmentsActivity();

    @ContributesAndroidInjector
    abstract MainLocalMarketActivity mainLocalMarketActivity();

    @ContributesAndroidInjector
    abstract SelectWorkAreaMapActivity selectWorkAreaMapActivity();

    @ContributesAndroidInjector
    abstract AllCategoryActivity allCategoryActivity();

    @ContributesAndroidInjector
    abstract EditProfile2Activity editProfile2Activity();

    @ContributesAndroidInjector
    abstract ChangePasswordActivity changePasswordActivity();

    @ContributesAndroidInjector
    abstract MessagingActivity messagingActivity();

    @ContributesAndroidInjector
    abstract CreateShopProfileActivity createShopProfileActivity();

    @ContributesAndroidInjector
    abstract LoginChooseActivity loginChooseActivity();

    @ContributesAndroidInjector
    abstract SearchedActivity searchedActivity();

    @ContributesAndroidInjector
    abstract PostDetailActivity postDetailActivity();

    @ContributesAndroidInjector
    abstract SubMapActivity subMapActivity();

}
