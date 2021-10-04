package com.marius.valeyou.ui.activity.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.messaging.FirebaseMessaging;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityMainBinding;
import com.marius.valeyou.databinding.HolderExitDialogBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.fcm.Config;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.market_place.Splashscreen.SplashScreen;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.NewHomeFragment;
import com.marius.valeyou.ui.fragment.home.HomeFragment;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.ui.fragment.message.ChatListFragment;
import com.marius.valeyou.ui.fragment.more.MoreFragment;
import com.marius.valeyou.ui.fragment.favourite.MyMFragment;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.InvoiceFragment;
import com.marius.valeyou.ui.fragment.products.ProductFragment;
import com.marius.valeyou.ui.fragment.restaurant.RestaurantFragment;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;
import com.marius.valeyou.util.misc.BackStackManager;
import com.marius.valeyou.util.misc.RxBus;

import javax.inject.Inject;

public class MainActivity extends AppActivity<ActivityMainBinding, MainActivityVM> {
    public static final String TAG = "MainActivity";

    BottomSheetBehavior bottomSheetBehavior;
    @Inject
    SharedPref sharedPref;
    private boolean exit = false;
    @Inject
    LiveLocationDetecter liveLocationDetecter;
    @Inject
    RxBus rxBus;
    int jobId;
    public ToolClickListener clickListener;
    public boolean isRemote = false;


    public static Intent newIntent(Activity activity, String comeFrom) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("comeFrom", comeFrom);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent newIntent(Activity activity, String comeFrom, int id) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("comeFrom", comeFrom);
        intent.putExtra("job_id", id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BindingActivity<MainActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_main, MainActivityVM.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottomsheet();
        Intent intent = getIntent();
//        if (intent != null) {
//            String comeFrom = intent.getStringExtra("comeFrom");
//
//            if (comeFrom.equalsIgnoreCase("jobs")) {
//
//                changeFragment(MyJobFragment.TAG);
//
//            }
//            else if (comeFrom.equalsIgnoreCase("login")) {
//
//                addSubFragmentWithoutCache(TAG, NewHomeFragment.newInstance());
//
//            } else if (comeFrom.equalsIgnoreCase("jobs_detail")) {
//
//                jobId = intent.getIntExtra("job_id", 0);
//                changeFragment(MyJobFragment.TAG);
//
//            } else {
//
//                changeFragment(NewHomeFragment.TAG);
//
//            }
//        } else {
            changeFragment(NewHomeFragment.TAG);
//        }
    }

    @Override
    protected void subscribeToEvents(final MainActivityVM vm) {

        sharedPref.put("loginType", "service");

        binding.header.imgBack.setVisibility(View.GONE);
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {
                case R.id.ll_menu:
                    changeFragment(MyJobFragment.TAG);
                    break;
                case R.id.ll_fav:
                    changeFragment(MyMFragment.TAG);
                    break;
                case R.id.cv_home:

                    changeFragment(NewHomeFragment.TAG);

                    break;
                case R.id.ll_settings:
                    // for marketPlace
                    //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    //deactivate market place
                    Intent intent = SelectCategoryActivity.newIntent(MainActivity.this, 0, "home");
                    startNewActivity(intent);

                    break;
                case R.id.ll_message:
                    addSubFragmentWithoutCache(TAG, ChatListFragment.newInstance());
                    break;

                case R.id.iv_cancel:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;

                case R.id.ll_marketPlace:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Intent intent2 = new Intent(MainActivity.this, SplashScreen.class);
                    intent2.putExtra("email", sharedPref.getUserData().getEmail());
                    intent2.putExtra("password", sharedPref.get("password", "abc"));
                    startNewActivity(intent2);
                    break;

                case R.id.ll_post:
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Intent intent1 = SelectCategoryActivity.newIntent(MainActivity.this, 0, "home");
                    startNewActivity(intent1);
                    break;
            }
        });

        vm.obrNavClick.observe(this, menuItem -> {
            //  binding.header.ivProfile.setImageResource(R.drawable.ic_base_profile);
            switch (menuItem.getItemId()) {
                case R.id.nav_1:
                    changeFragment(HomeFragment.TAG);
                    break;
                case R.id.nav_2:
                    changeFragment(MyMFragment.TAG);
                    break;
                case R.id.nav_3:
                    changeFragment(ProductFragment.TAG);
                    break;
                case R.id.nav_4:
                    changeFragment(RestaurantFragment.TAG);
                    break;
                case R.id.nav_5:
                    changeFragment(MoreFragment.TAG);
                    break;

            }
        });

        BackStackManager.getInstance(this).setFragmentChangeListioner((tag, isSubFragment) -> {
            if (isSubFragment)
                showBackButton();
            else
                hideBackButton();
        });


    }

    private boolean check = false;

    public void changeFragment(@Nullable String tab) {
        switch (tab) {
            case NewHomeFragment.TAG:
                check = true;
                setHeader("");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
                BackStackManager.getInstance(this).pushFragments(R.id.container, NewHomeFragment.TAG, NewHomeFragment.newInstance(), tab != null);
                break;
            case MyMFragment.TAG:
                check = false;
                setHeader(getResources().getString(R.string.favourites));
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyMFragment.TAG, MyMFragment.newInstance(), true);
                break;
            case MyJobFragment.TAG:
                check = false;
                setHeader(getResources().getString(R.string.my_jobs));
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, MyJobFragment.TAG, MyJobFragment.newInstance(), true);
                break;
            case RestaurantFragment.TAG:
                check = false;
                setHeader("Restaurant");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, RestaurantFragment.TAG, RestaurantFragment.newInstance(), true);
                break;
            case MoreFragment.TAG:
                check = false;
                setHeader("");
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                Intent intent = NotificationActivity.newIntent(MainActivity.this);
                startNewActivity(intent);
                //BackStackManager.getInstance(this).pushFragments(R.id.container, MoreFragment.TAG, MoreFragment.newInstance(), true);
                break;
            case ChatListFragment.TAG:
                check = false;
                setHeader(getResources().getString(R.string.messsage));
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, ChatListFragment.TAG, ChatListFragment.newInstance(), true);
                break;

            case JobDetailsFragment.TAG:
                check = false;
                binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
                BackStackManager.getInstance(this).pushFragments(R.id.container, JobDetailsFragment.TAG, JobDetailsFragment.newInstance(jobId), true);
                break;
        }
    }


    public void addSubFragment(@NonNull String tab, Fragment fragment) {
        BackStackManager.getInstance(this).pushSubFragments(R.id.container, tab, fragment, true);
    }

    public void addSubFragmentWithoutCache(@NonNull String tab, Fragment fragment) {
        BackStackManager.getInstance(this).pushSubFragmentsWithoutCache(R.id.container, tab, fragment, true);
    }


    public void hideBackButton() {
        binding.header.imgBack.animate().alpha(0);
    }

    public void showBackButton() {
        binding.header.imgBack.animate().alpha(1);
    }

    @Override
    public void onBackPressed() {
        finish();

//        if (backStepFragment()) {
//            if (BackStackManager.getInstance(this).getCurrentTab() == "NewHomeFragment") {
//                if (clickListener != null && !isRemote) {
//                    clickListener.OnToolClick(MainActivity.this.getResources().getString(R.string.remotejob));
//                } else {
//                    sharedPref.put("loginType", "");
//                    startNewActivity(LoginChooseActivity.newIntent(MainActivity.this, "welcome"));
//                    finish();
////                    setExitDialog();
//                }
//
//            } else {
//
//                changeFragment(NewHomeFragment.TAG);
//
//            }
//        }
    }

    private BaseCustomDialog<HolderExitDialogBinding> exitDialog;

    private void setExitDialog() {
        exitDialog = new BaseCustomDialog<>(MainActivity.this, R.layout.holder_exit_dialog, new BaseCustomDialog.Listener() {
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
        return BackStackManager.getInstance(this).removeFragment(R.id.container, true);
    }

    /*
     * show or hide bottom navigation bar
     **/
    public void hideBottomNav(boolean bool) {

    }

    /*
     * set header text
     **/
    public void setHeader(String header) {
        binding.header.tvTitle.setText(header);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        liveLocationDetecter.onActivityResult(requestCode, resultCode);
    }

    @SuppressLint("SetTextI18n")
    public void badgeCount(int count) {
        if (count > 0) {
            binding.bottomNav.tvBadge.setVisibility(View.VISIBLE);

            if (count > 9) {
                binding.bottomNav.tvBadge.setText("9+");
            } else {
                binding.bottomNav.tvBadge.setText("0" + count);
            }

        } else {

            binding.bottomNav.tvBadge.setVisibility(View.GONE);

        }
    }

    //bottomsheet
    private void setBottomsheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.llOne);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                float s = (1f - v / 2f);
            }
        });
    }
}
