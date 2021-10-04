package com.marius.valeyou.localMarketModel.ui.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.facebook.login.LoginManager;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityMainLocalMarketBinding;
import com.marius.valeyou.databinding.DialogBuyRentBinding;
import com.marius.valeyou.databinding.DialogLogoutBinding;
import com.marius.valeyou.databinding.HolderDrawerItemBinding;
import com.marius.valeyou.databinding.HolderExitDialogBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.ui.fragment.commonList.CommonListFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.favourite.FavouriteFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.home.HomeFragmentMarket;
import com.marius.valeyou.localMarketModel.ui.fragment.home.SearchMarkerFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.message.RecentChatListFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.myShop.MyShopFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.orders.MyOrdersFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.profile.ProfileFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.settingHelp.SettingFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.misc.BackStackManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainLocalMarketActivity extends AppActivity<ActivityMainLocalMarketBinding, MainLocalMarketActivityVM> {
    public static final String TAG = "MainLocalMarketActivity";
    private MoreBean mBean;
    private boolean isSubFragment = false;
    public ToolClickListener clickListener;
    public MarketCategoryModel categoryListBeans;
    public MarketPostModel marketPostModel;
    int RequestCode = 88;
    public boolean isList = false;
    public String latLngFilter = "";
    public String lati = "";
    public String longi = "";
    public boolean fromMyProduct = false;
    public String click_tag_id = "";
    public ImageView imgToolRight;
    SignupData signupData = new SignupData();

    public static Intent newIntent(Activity activity, String comeFrom) {
        Intent intent = new Intent(activity, MainLocalMarketActivity.class);
        intent.putExtra("comeFrom", comeFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BindingActivity<MainLocalMarketActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_main_local_market, MainLocalMarketActivityVM.class);
    }

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 6 * 1000; //Delay for 15 seconds.  One second = 1000 milliseconds.


// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel != null)
            binding.nav.setBean(viewModel.sharedPref.getUserData());
        viewModel.getCount();
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                viewModel.getCount();
                handler.postDelayed(runnable, delay);
            }
        }, delay);
    }

    @Override
    protected void subscribeToEvents(final MainLocalMarketActivityVM vm) {
        imgToolRight = findViewById(R.id.imgToolRight);
        sharedPref.put("loginType", "market");


        binding.nav.setBean(viewModel.sharedPref.getUserData());
        signupData = viewModel.sharedPref.getUserData();
        if (signupData.getFirstName() != null) {
            String name = AppUtils.capitalize(signupData.getFirstName() + " " + signupData.getLastName());
            binding.nav.tvName.setText(name);
        }
        binding.nav.llNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubFragment(ProfileFragment.TAG, ProfileFragment.newInstance());
                binding.drawerView.closeDrawers();
            }
        });

//        testDecodeJWT(viewModel.sharedPref.getUserData().getAuthKey());
        viewHeaderDefault();

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                if (isSubFragment) {
                    onBackPressed();
                    binding.header.imgToolRight.setVisibility(View.GONE);

                } else {
                    binding.drawerView.openDrawer(GravityCompat.START);
                }
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.txtRight:
                        if (clickListener == null) {
                            return;
                        }
                        clickListener.OnToolClick("rightText");
                        break;
//                    case R.id.imgToolRight:
//                        addSubFragment(CommonListFragment.TAG,
//                                CommonListFragment.newInstance(R.string.notifications, false, R.string.title, false, R.drawable.app_logo, 0, R.layout.holder_notification));

//                        break;

                    case R.id.imgLocation:
                        addSubFragment(SearchMarkerFragment.TAG, SearchMarkerFragment.newInstance());

//                        Intent areaIntent = SelectWorkAreaMapActivity.newIntent(MainLocalMarketActivity.this);
//                        areaIntent.putExtra(Constants.LOCATION, sharedPref.get(Constants.SELECT_LOCATION, ""));
//                        areaIntent.putExtra(Constants.LATITUDE, sharedPref.get(Constants.SELECT_LATITUDE, "0"));
//                        areaIntent.putExtra(Constants.LONGITUDE, sharedPref.get(Constants.SELECT_LONGITUDE, "0"));
//                        areaIntent.putExtra(Constants.RADIUS, sharedPref.get(Constants.SELECT_RADIUS, "0"));
//
//                        startActivityForResult(areaIntent, RequestCode);

                        break;
                    case R.id.imgMessage:
                        addSubFragment(RecentChatListFragment.TAG, RecentChatListFragment.newInstance());

                        break;
                }
            }
        });


        vm.notificationEventRequest.observe(this, new SingleRequestEvent.RequestObserver<NotificationBadgeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<NotificationBadgeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        badgeCount(resource.data.getCount());
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });


        vm.logout.observe(this, new SingleRequestEvent.RequestObserver<SignupData>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SignupData> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:

                        dismissProgressDialog();

                        vm.sharedPref.deleteAll();
                        LoginManager.getInstance().logOut();
                        vm.success.setValue(resource.message);
                        Intent intent1 = LoginActivity.newIntent(MainLocalMarketActivity.this);
                        startNewActivity(intent1, true, true);

                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(MainLocalMarketActivity.this);
                            startNewActivity(in, true, true);

                        }

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);

                        break;
                }
            }
        });


        BackStackManager.getInstance(this).setFragmentChangeListioner((tag, isSubFrag) -> {
            isSubFragment = isSubFrag;
            if (isSubFragment)
                showBackButton();
            else
                hideBackButton();
        });

        setRecyclerView();
        moveViewWithDrawer();

        changeFragment(HomeFragmentMarket.TAG);

        binding.header.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clickListener.OnToolClick(s.toString());
            }
        });

        binding.header.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i(TAG, "Enter pressed_actionId: " + actionId + " == " + EditorInfo.IME_ACTION_DONE);
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i(TAG, "Enter pressed");
                    clickListener.OnToolClick(getStr(R.string.done));
                }
                return false;
            }
        });


    }

    public void badgeCount(int count) {
         if (count > 0) {
            binding.header.rlNotificationCount.setVisibility(View.VISIBLE);
            if (count > 9) {
                binding.header.tvCount.setText("9+");
            } else {
                binding.header.tvCount.setText(count + "");
            }

        } else {

            binding.header.rlNotificationCount.setVisibility(View.GONE);

        }
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainLocalMarketActivity.this);
        binding.nav.rvDrawerItems.setLayoutManager(layoutManager);
        binding.nav.rvDrawerItems.setAdapter(drawerAdapter);
        drawerAdapter.setList(getData());
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderDrawerItemBinding> drawerAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_drawer_item, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    mBean = bean;
                    binding.drawerView.closeDrawers();
                }
            });


    private List<MoreBean> getData() {
        List<MoreBean> data = new ArrayList<>();
       // data.add(new MoreBean(7, getStr(R.string.return_to_main_menu), R.drawable.ic_return_service_mobile));
        data.add(new MoreBean(1, getStr(R.string.profile), R.drawable.ic_profile));
//        data.add(new MoreBean(2, getStr(R.string.sell_products), R.drawable.ic_sell_product));
//        data.add(new MoreBean(3, getStr(R.string.buy_rent_product), R.drawable.ic_buy_rent_product));
        data.add(new MoreBean(10, getResources().getString(R.string.payments), R.drawable.pay));
        data.add(new MoreBean(11, getStr(R.string.my_shop), R.drawable.ic_my_order));
        data.add(new MoreBean(4, getStr(R.string.my_products), R.drawable.ic_my_order));
        data.add(new MoreBean(5, getStr(R.string.settings_help), R.drawable.ic_settings_help));
        //  data.add(new MoreBean(9, getStr(R.string.messsage), R.drawable.ic_message_icon));
        data.add(new MoreBean(6, getStr(R.string.favourites), R.drawable.ic_fav));
        data.add(new MoreBean(8, getStr(R.string.logout), R.drawable.ic_logout));
        return data;

    }

    private String getStr(int strName) {
        return MainLocalMarketActivity.this.getResources().getString(strName);
    }

    private void moveViewWithDrawer() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(MainLocalMarketActivity.this, binding.drawerView, binding.toolbar, R.string.acc_drawer_open, R.string.acc_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Intent intent;
                binding.header.imgToolRight.setVisibility(View.GONE);
                switch (mBean != null ? mBean.getId() : 0) {
                    case 1:
                        addSubFragment(ProfileFragment.TAG, ProfileFragment.newInstance());
                        break;

                    case 2:
                        addSubFragment(CreateAdFragment.TAG, CreateAdFragment.newInstance());
                        break;

                    case 3:
                        dialogRentBuy();
                        break;

                    case 4:

                        addSubFragment(MyOrdersFragment.TAG, MyOrdersFragment.newInstance());
                        break;

                    case 5:
                        addSubFragment(SettingFragment.TAG, SettingFragment.newInstance());
                        break;

                    case 6:
                        addSubFragment(CommonListFragment.TAG, FavouriteFragment.newInstance());
                        break;

                    case 7:
                        sharedPref.put("loginType", "");
                        startNewActivity(LoginChooseActivity.newIntent(MainLocalMarketActivity.this, "welcome"));
                        finish(true);
                        break;

                    case 8:
                        dialogLogout();
                        break;

                    case 9:
                        addSubFragment(RecentChatListFragment.TAG, RecentChatListFragment.newInstance());
                        break;

                    case 10:
                        break;

                    case 11:
                        addSubFragment(MyShopFragment.TAG, MyShopFragment.newInstance());
                        break;


                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mBean = null;
            }
        };
        binding.drawerView.addDrawerListener(mDrawerToggle);
    }


    public void changeFragment(@Nullable String tab) {
        switch (tab) {
            case HomeFragmentMarket.TAG:
                BackStackManager.getInstance(this).addReplaceFragments(R.id.containerMarket, HomeFragmentMarket.TAG, HomeFragmentMarket.newInstance(), tab != null);
                break;
        }
    }

    public void setHeader(int header) {
        binding.header.txtTitle.setText(getStr(header));
    }

    public void setHeaderString(String header) {
        binding.header.txtTitle.setText(header);
    }

    public void setRightText(int rightTxt) {
        binding.header.txtRight.setText(getStr(rightTxt));
    }


    public void addSubFragment(@NonNull String tab, Fragment fragment) {
        BackStackManager.getInstance(this).addReplaceSubFragments(R.id.containerMarket, tab, fragment, true);
    }

    public void addSubFragmentWithoutCache(@NonNull String tab, Fragment fragment) {
        BackStackManager.getInstance(this).pushSubFragmentsWithoutCache(R.id.containerMarket, tab, fragment, true);
    }

    public void hideBackButton() {
        binding.header.imgBack.setImageResource(R.drawable.ic_menu_options_white);
//        binding.header.imgBack.animate().alpha(0);
    }

    public void showBackButton() {
        binding.header.imgBack.setImageResource(R.drawable.ic_back_white);
//        binding.header.imgBack.animate().alpha(1);
    }

    public void viewHeaderItems(boolean isSearch, boolean isRightTxt, boolean isRightImg) {
        binding.header.setSearch(isSearch);
        binding.header.setRightText(isRightTxt);
        binding.header.setRightImage(isRightImg);
        binding.header.imgLocation.setVisibility(View.GONE);
        binding.header.imgMessage.setVisibility(View.GONE);
        //    binding.header.rlNotificationCount.setVisibility(View.GONE);
    }

    public void viewHeaderDefault() {
        binding.header.setSearch(true);
        binding.header.setRightText(false);
        binding.header.setRightImage(true);
        binding.header.imgLocation.setVisibility(View.VISIBLE);
        binding.header.imgMessage.setVisibility(View.VISIBLE);
        //    binding.header.rlNotificationCount.setVisibility(View.VISIBLE);
        clearEdtSearch();
    }

    public void clearEdtSearch() {
        binding.header.edtSearch.setText("");
    }

    @Override
    public void onBackPressed() {
        if (BackStackManager.getInstance(this).manager.getBackStackEntryCount() == 0) {
            if (isList) {
                clickListener.OnToolClick(MainLocalMarketActivity.this.getResources().getString(R.string.market_place));
            } else {
                sharedPref.put("loginType", "");
                startNewActivity(LoginChooseActivity.newIntent(MainLocalMarketActivity.this, "welcome"));
                finish(true);
//                setExitDialog();
            }
        } else {
            onBackPressed(true);
        }
        BackStackManager.getInstance(this).removeBackStack();
        if (BackStackManager.getInstance(this).manager.getBackStackEntryCount() == 0) {
            viewHeaderDefault();

        }
    }

    /*@Override
    public void onBackPressed() {
        if (backStepFragment()) {
            if (BackStackManager.getInstance(this).getCurrentTab().equalsIgnoreCase(HomeFragmentMarket.TAG)) {

                setExitDialog();

            } else {
                changeFragment(HomeFragmentMarket.TAG);

            }
        }
    }*/


    private BaseCustomDialog<HolderExitDialogBinding> exitDialog;

    private void setExitDialog() {
        exitDialog = new BaseCustomDialog<>(MainLocalMarketActivity.this, R.layout.holder_exit_dialog, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            exitDialog.dismiss();
                            finish(true);
                            break;
                        case R.id.btn_cancel:
                            exitDialog.dismiss();
                            break;
                    }
                }
            }
        });

        exitDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        exitDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        exitDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        exitDialog.show();

    }

    public boolean backStepFragment() {
        return BackStackManager.getInstance(this).removeFragment(R.id.containerMarket, true);
    }

    public void showMarker() {
        int fragCount = BackStackManager.getInstance(MainLocalMarketActivity.this).manager.getBackStackEntryCount();
        if (fragCount > 0) {
            for (int i = 0; i < fragCount; i++) {
                onBackPressed();
            }
        }
//        clickListener.OnToolClick(filterName);
//        clickListener.OnToolClick(getStr(R.string.market_place));

    }


    private BaseCustomDialog<DialogBuyRentBinding> dialogBuyRent;

    private void dialogRentBuy() {
        dialogBuyRent = new BaseCustomDialog<>(MainLocalMarketActivity.this, R.layout.dialog_buy_rent, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogBuyRent.dismiss();
                            int fragCount = BackStackManager.getInstance(MainLocalMarketActivity.this).manager.getBackStackEntryCount();
                            if (fragCount > 0) {
                                for (int i = 0; i < fragCount; i++) {
                                    onBackPressed();
                                }
                            }
                            clickListener.OnToolClick(getStr(R.string.buy_rent_product));

                            break;
                        case R.id.b_cancel:
                            addSubFragment(CreateAdFragment.TAG, CreateAdFragment.newInstance());
                            dialogBuyRent.dismiss();
                            break;
                    }
                }
            }
        });
        dialogBuyRent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogBuyRent.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogBuyRent.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogBuyRent.show();
    }


    private BaseCustomDialog<DialogLogoutBinding> dialogSuccess;

    private void dialogLogout() {
        dialogSuccess = new BaseCustomDialog<>(MainLocalMarketActivity.this, R.layout.dialog_logout, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
//                            SharedPrefrence.logout_user(MainLocalMarketActivity.this);
                            viewModel.logout();
                            break;
                        case R.id.b_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
