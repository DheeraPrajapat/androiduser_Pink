package com.marius.valeyou.di.module;

import androidx.lifecycle.ViewModel;

import com.marius.valeyou.di.mapkey.ViewModelKey;
import com.marius.valeyou.localMarketModel.ui.fragment.commonList.CommonListFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.favourite.FavouriteFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.home.HomeFragmentMarketVM;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.DetailMapVM;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.message.RecentChatListFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopDetailFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.orders.MyOrdersFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.privacyTermsAbout.PrivacyTermsAboutFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.profile.ProfileFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.settingHelp.SettingFragmentVM;
import com.marius.valeyou.localMarketModel.ui.fragment.userPost.UserPostFragmentVM;
import com.marius.valeyou.ui.activity.tourpage.tourfragment.TourFragmentVM;
import com.marius.valeyou.ui.fragment.NewHomeFragmentVM;
import com.marius.valeyou.ui.fragment.home.HomeFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.StandardPickFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragmentVM;
import com.marius.valeyou.ui.fragment.message.ChatListFragmentVM;
import com.marius.valeyou.ui.fragment.more.MoreFragmentVM;
import com.marius.valeyou.ui.fragment.favourite.MyMFragmentVM;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragmentVM;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragmentVM;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragmentVM;
import com.marius.valeyou.ui.fragment.myjob.upcoming.UpComingJobFragmentVM;
import com.marius.valeyou.ui.fragment.products.ProductFragmentVM;
import com.marius.valeyou.ui.fragment.products.junkremove.JunkRemovalFragmentVM;
import com.marius.valeyou.ui.fragment.restaurant.RestaurantFragmentVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentViewModelModule {
    // NOTE: customize here

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentVM.class)
    abstract ViewModel HomeFragmentVM(HomeFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyMFragmentVM.class)
    abstract ViewModel MyMFragmentVM(MyMFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProductFragmentVM.class)
    abstract ViewModel ProductFragmentVM(ProductFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantFragmentVM.class)
    abstract ViewModel RestaurantFragmentVM(RestaurantFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MoreFragmentVM.class)
    abstract ViewModel MoreFragmentVM(MoreFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(TourFragmentVM.class)
    abstract ViewModel TourFragmentVM(TourFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ChatListFragmentVM.class)
    abstract ViewModel ChatListFragmentVM(ChatListFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyJobFragmentVM.class)
    abstract ViewModel MyJobFragmentVM(MyJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(UpComingJobFragmentVM.class)
    abstract ViewModel UpComingJobFragmentVM(UpComingJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(AboutUsFragmentVM.class)
    abstract ViewModel AboutUsFragmentVM(AboutUsFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PrivacyPolicyFragmentVM.class)
    abstract ViewModel PrivacyPolicyFragmentVM(PrivacyPolicyFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(HelpAndSupportFragmentVM.class)
    abstract ViewModel HelpAndSupportFragmentVM(HelpAndSupportFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JunkRemovalFragmentVM.class)
    abstract ViewModel JunkRemovalFragmentVM(JunkRemovalFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(StandardPickFragmentVM.class)
    abstract ViewModel StandardPickFragmentVM(StandardPickFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JunkRemovalJobFragmentVM.class)
    abstract ViewModel JunkRemovalJobFragmentVM(JunkRemovalJobFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(JobDetailsFragmentVM.class)
    abstract ViewModel JobDetailsFragmentVM(JobDetailsFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(InvoiceFragmentVM.class)
    abstract ViewModel InvoiceFragmentVM(InvoiceFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(NewHomeFragmentVM.class)
    abstract ViewModel NewHomeFragmentVM(NewHomeFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentMarketVM.class)
    abstract ViewModel HomeFragmentMarketVM(HomeFragmentMarketVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CreateAdFragmentVM.class)
    abstract ViewModel CreateAdFragmentVM(CreateAdFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(CommonListFragmentVM.class)
    abstract ViewModel CommonListFragmentVM(CommonListFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(SettingFragmentVM.class)
    abstract ViewModel SettingFragmentVM(SettingFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentVM.class)
    abstract ViewModel ProfileFragmentVM(ProfileFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(HomeListDetailFragmentVM.class)
    abstract ViewModel HomeListDetailFragmentVM(HomeListDetailFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(DetailMapVM.class)
    abstract ViewModel DetailMapVM(DetailMapVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyOrdersFragmentVM.class)
    abstract ViewModel MyOrdersFragmentVM(MyOrdersFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteFragmentVM.class)
    abstract ViewModel FavouriteFragmentVM(FavouriteFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(UserPostFragmentVM.class)
    abstract ViewModel UserPostFragmentVM(UserPostFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(PrivacyTermsAboutFragmentVM.class)
    abstract ViewModel PrivacyTermsAboutFragmentVM(PrivacyTermsAboutFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(RecentChatListFragmentVM.class)
    abstract ViewModel RecentChatListFragmentVM(RecentChatListFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyShopFragmentVM.class)
    abstract ViewModel MyShopFragmentVM(MyShopFragmentVM vm);

    @Binds
    @IntoMap
    @ViewModelKey(MyShopDetailFragmentVM.class)
    abstract ViewModel MyShopDetailFragmentVM(MyShopDetailFragmentVM vm);

}
