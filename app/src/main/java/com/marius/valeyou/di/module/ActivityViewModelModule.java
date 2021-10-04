package com.marius.valeyou.di.module;

import androidx.lifecycle.ViewModel;

import com.marius.valeyou.PostDetailActivityVM;
import com.marius.valeyou.di.mapkey.ViewModelKey;
import com.marius.valeyou.localMarketModel.ui.activity.allCategory.AllCategoryActivityVM;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivityVM;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivityVM;
import com.marius.valeyou.localMarketModel.ui.activity.other_user_profile.OtherUserProfileVM;
import com.marius.valeyou.localMarketModel.ui.activity.searched.SearchedActivityVM;
import com.marius.valeyou.localMarketModel.ui.activity.selectWorkAreaMap.SelectWorkAreaMapVM;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivityVM;
import com.marius.valeyou.ui.RatingActivity;
import com.marius.valeyou.ui.RatingActivityVM;
import com.marius.valeyou.ui.activity.LocationActivityVM;
import com.marius.valeyou.ui.activity.PayPerHourActivityVM;
import com.marius.valeyou.ui.activity.block.BlockActivity;
import com.marius.valeyou.ui.activity.block.BlockActivityVM;
import com.marius.valeyou.ui.activity.categorytitle.CategoryTitleActivityVM;
import com.marius.valeyou.ui.activity.forgot.ForgotPasswordActivityVM;
import com.marius.valeyou.ui.activity.forgot.recoverpass.RecoverPasswordActivityVM;
import com.marius.valeyou.ui.activity.identityverfication.IdentityVerficationActivityVM;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivity;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivityVM;
import com.marius.valeyou.ui.activity.login.LoginActivityVM;
import com.marius.valeyou.ui.activity.main.MainActivityVM;
import com.marius.valeyou.ui.activity.notification.NotificationActivityVM;
import com.marius.valeyou.ui.activity.post_detail_sub.SubMapVM;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivityVM;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivityVM;
import com.marius.valeyou.ui.activity.signup.SignupActivityVM;
import com.marius.valeyou.ui.activity.tourpage.TourActivityVM;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivityVM;
import com.marius.valeyou.ui.fragment.NewHomeFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivityVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype.WhenTypeActivityVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.where.WhereActivityVM;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivityVM;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragmentVM;
import com.marius.valeyou.ui.fragment.more.helpnsupport.faq.FAQActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.addidentity.AddIdentityActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.addportfolio.AddPortfolioActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.editprofile.EditProfileActivityVM;
import com.marius.valeyou.ui.fragment.more.profile.selectlanguage.SelectLanguageActivityVM;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivityVM;
import com.marius.valeyou.ui.fragment.myjob.detail.additionalcost.AdditionalCostActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.additionalcost.AdditionalCostActivityVM;
import com.marius.valeyou.ui.fragment.myjob.detail.attachments.AttachmentsActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.attachments.AttachmentsActivityVM;
import com.marius.valeyou.ui.fragment.myjob.detail.uploaded_work.UploadedWorkActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.uploaded_work.UploadedWorkActivityVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivityVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.PaymentCardDetailsVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess.PaymentSuccessActivityVM;
import com.marius.valeyou.ui.fragment.myjob.reschedule.RescheduleActivity;
import com.marius.valeyou.ui.fragment.myjob.reschedule.RescheduleActivityVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ActivityViewModelModule {
    // NOTE: customize here
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeActivityVM.class)
    abstract ViewModel welcomeActivityVM(WelcomeActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityVM.class)
    abstract ViewModel LoginActivityVM(LoginActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SignupActivityVM.class)
    abstract ViewModel SignupActivityVM(SignupActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordActivityVM.class)
    abstract ViewModel ForgotPasswordActivityVM(ForgotPasswordActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityVM.class)
    abstract ViewModel MainActivityVM(MainActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(TourActivityVM.class)
    abstract ViewModel TourActivityVM(TourActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RecoverPasswordActivityVM.class)
    abstract ViewModel RecoverPasswordActivityVM(RecoverPasswordActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationActivityVM.class)
    abstract ViewModel NotificationActivityVM(NotificationActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileActivityVM.class)
    abstract ViewModel ProfileActivityVM(ProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(OtherUserProfileVM.class)
    abstract ViewModel OtherUserProfileVM(OtherUserProfileVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileActivityVM.class)
    abstract ViewModel EditProfileActivityVM(EditProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(WhereActivityVM.class)
    abstract ViewModel WhereActivityVM(WhereActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(WhenActivityVM.class)
    abstract ViewModel WhenActivityVM(WhenActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PaymentActivityVM.class)
    abstract ViewModel PaymentActivityVM(PaymentActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PaymentCardDetailsVM.class)
    abstract ViewModel PaymentCardDetailsVM(PaymentCardDetailsVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PaymentSuccessActivityVM.class)
    abstract ViewModel PaymentSuccessActivityVM(PaymentSuccessActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChatActivityVM.class)
    abstract ViewModel ChatActivityVM(ChatActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordFragmentVM.class)
    abstract ViewModel ChangePasswordFragmentVM(ChangePasswordFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(FAQActivityVM.class)
    abstract ViewModel FAQActivityVM(FAQActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SelectLanguageActivityVM.class)
    abstract ViewModel SelectLanguageActivityVM(SelectLanguageActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AddPortfolioActivityVM.class)
    abstract ViewModel AddPortfolioActivityVM(AddPortfolioActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SelectCategoryActivityVM.class)
    abstract ViewModel SelectCategoryActivityVM(SelectCategoryActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProviderProfileActivityVM.class)
    abstract ViewModel ProviderProfileActivityVM(ProviderProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(WhenTypeActivityVM.class)
    abstract ViewModel WhenTypeActivityVM(WhenTypeActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryTitleActivityVM.class)
    abstract ViewModel CategoryTitleActivityVM(CategoryTitleActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PayPerHourActivityVM.class)
    abstract ViewModel PayPerHourActivityVM(PayPerHourActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AddIdentityActivityVM.class)
    abstract ViewModel AddIdentityActivityVM(AddIdentityActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RatingActivityVM.class)
    abstract ViewModel RatingActivityVM(RatingActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(LocationActivityVM.class)
    abstract ViewModel LocationActivityVM(LocationActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(IdentityVerficationActivityVM.class)
    abstract ViewModel IdentityVerficationActivityVM(IdentityVerficationActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(BlockActivityVM.class)
    abstract ViewModel BlockActivityVM(BlockActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AppLanguageActivityVM.class)
    abstract ViewModel AppLanguageActivityVM(AppLanguageActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(JobDetailActivityVM.class)
    abstract ViewModel JobDetailActivityVM(JobDetailActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RescheduleActivityVM.class)
    abstract ViewModel RescheduleActivityVM(RescheduleActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(AdditionalCostActivityVM.class)
    abstract ViewModel AdditionalCostActivityVM(AdditionalCostActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(UploadedWorkActivityVM.class)
    abstract ViewModel UploadedWorkActivityVM(UploadedWorkActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AttachmentsActivityVM.class)
    abstract ViewModel AttachmentsActivityVM(AttachmentsActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MainLocalMarketActivityVM.class)
    abstract ViewModel MainLocalMarketActivityVM(MainLocalMarketActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(SelectWorkAreaMapVM.class)
    abstract ViewModel SelectWorkAreaMapVM(SelectWorkAreaMapVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AllCategoryActivityVM.class)
    abstract ViewModel AllCategoryActivityVM(AllCategoryActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MessagingActivityVM.class)
    abstract ViewModel MessagingActivityVM(MessagingActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CreateShopProfileActivityVM.class)
    abstract ViewModel CreateShopProfileActivityVM(CreateShopProfileActivityVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SearchedActivityVM.class)
    abstract ViewModel SearchedActivityVM(SearchedActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(PostDetailActivityVM.class)
    abstract ViewModel PostDetailActivityVM(PostDetailActivityVM vm);


    @Binds
    @IntoMap
    @ViewModelKey(SubMapVM.class)
    abstract ViewModel SubMapVM(SubMapVM vm);

}
