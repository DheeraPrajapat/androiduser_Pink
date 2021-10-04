package com.marius.valeyou.di.module.androidcomponent;

import com.marius.valeyou.localMarketModel.ui.fragment.commonList.CommonListFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.favourite.FavouriteFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.home.HomeFragmentMarket;
import com.marius.valeyou.localMarketModel.ui.fragment.home.SearchMarkerFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.DetailMapFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.message.RecentChatListFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.orders.MyOrdersFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.privacyTermsAbout.PrivacyTermsAboutFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.profile.ProfileFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.settingHelp.SettingFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.userPost.UserPostFragment;
import com.marius.valeyou.ui.activity.tourpage.tourfragment.TourFragment;
import com.marius.valeyou.ui.fragment.NewHomeFragment;
import com.marius.valeyou.ui.fragment.home.HomeFragment;
import com.marius.valeyou.ui.fragment.loadtype.StandardPickFragment;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.ui.fragment.message.ChatListFragment;
import com.marius.valeyou.ui.fragment.more.MoreFragment;
import com.marius.valeyou.ui.fragment.favourite.MyMFragment;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.ui.fragment.myjob.upcoming.UpComingJobFragment;
import com.marius.valeyou.ui.fragment.products.ProductFragment;
import com.marius.valeyou.ui.fragment.products.junkremove.JunkRemovalFragment;
import com.marius.valeyou.ui.fragment.restaurant.RestaurantFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    // NOTE: user  here

    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @ContributesAndroidInjector
    abstract MyMFragment myMFragment();

    @ContributesAndroidInjector
    abstract ProductFragment productFragment();

    @ContributesAndroidInjector
    abstract RestaurantFragment restaurantFragment();

    @ContributesAndroidInjector
    abstract MoreFragment moreFragment();

    @ContributesAndroidInjector
    abstract TourFragment tourFragment();

    @ContributesAndroidInjector
    abstract ChatListFragment chatListFragment();

    @ContributesAndroidInjector
    abstract MyJobFragment myJobFragment();

    @ContributesAndroidInjector
    abstract UpComingJobFragment upComingJobFragment();

    @ContributesAndroidInjector
    abstract AboutUsFragment aboutUsFragment();

    @ContributesAndroidInjector
    abstract PrivacyPolicyFragment privacyPolicyFragment();

    @ContributesAndroidInjector
    abstract HelpAndSupportFragment helpAndSupportFragment();

    @ContributesAndroidInjector
    abstract JunkRemovalFragment junkRemovalFragment();

    @ContributesAndroidInjector
    abstract StandardPickFragment standardPickFragment();

    @ContributesAndroidInjector
    abstract JunkRemovalJobFragment junkRemovalJobFragment();

    @ContributesAndroidInjector
    abstract JobDetailsFragment jobDetailsFragment();

    @ContributesAndroidInjector
    abstract InvoiceFragment invoiceFragment();

    @ContributesAndroidInjector
    abstract NewHomeFragment newHomeFragment();

    @ContributesAndroidInjector
    abstract HomeFragmentMarket homeFragmentMarket();


    @ContributesAndroidInjector
    abstract CreateAdFragment createAdFragment();

    @ContributesAndroidInjector
    abstract CommonListFragment commonListFragment();

    @ContributesAndroidInjector
    abstract SettingFragment settingFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();

    @ContributesAndroidInjector
    abstract HomeListDetailFragment homeListDetailFragment();

    @ContributesAndroidInjector
    abstract DetailMapFragment detailMapFragment();

    @ContributesAndroidInjector
    abstract MyOrdersFragment myOrdersFragment();

    @ContributesAndroidInjector
    abstract FavouriteFragment favouriteFragment();

    @ContributesAndroidInjector
    abstract UserPostFragment userPostFragment();

    @ContributesAndroidInjector
    abstract PrivacyTermsAboutFragment privacyTermsAboutFragment();

    @ContributesAndroidInjector
    abstract RecentChatListFragment RecentChatListFragment();

    @ContributesAndroidInjector
    abstract SearchMarkerFragment searchMarkerFragment();

    @ContributesAndroidInjector
    abstract MyShopFragment myShopFragment();


    @ContributesAndroidInjector
    abstract MyShopDetailFragment myShopDetailFragment();

}
