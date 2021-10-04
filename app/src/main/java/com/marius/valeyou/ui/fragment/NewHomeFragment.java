package com.marius.valeyou.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.share.Share;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.SuggestionsModel;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.reqbean.RequestBean;
import com.marius.valeyou.data.beans.reqbean.RequestSubCategory;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.databinding.DialogDeleteAccouontBinding;
import com.marius.valeyou.databinding.DialogLogoutBinding;
import com.marius.valeyou.databinding.FragmentHomeBinding;
import com.marius.valeyou.databinding.FragmentNewHomeBinding;
import com.marius.valeyou.databinding.HolderCitiesBinding;
import com.marius.valeyou.databinding.HolderFilterCategoryBinding;
import com.marius.valeyou.databinding.HolderMapItemsBinding;
import com.marius.valeyou.databinding.HolderMoreBinding;
import com.marius.valeyou.databinding.HolderNewCategoryLayoutBinding;
import com.marius.valeyou.databinding.HolderSetCategoryBinding;
import com.marius.valeyou.databinding.HolderStatesBinding;
import com.marius.valeyou.databinding.HolderSuggestionsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Splashscreen.SplashScreen;
import com.marius.valeyou.ui.activity.block.BlockActivity;
import com.marius.valeyou.ui.activity.identityverfication.IdentityVerficationActivityVM;
import com.marius.valeyou.ui.activity.identityverfication.IdentityVerificationActivity;
import com.marius.valeyou.ui.activity.languages.AppLanguageActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.login.LoginChooseActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivity;
import com.marius.valeyou.ui.fragment.home.HomeFragment;
import com.marius.valeyou.ui.fragment.home.HomeFragmentVM;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.ui.fragment.more.aboutus.AboutUsFragment;
import com.marius.valeyou.ui.fragment.more.changepassword.ChangePasswordFragment;
import com.marius.valeyou.ui.fragment.more.helpnsupport.HelpAndSupportFragment;
import com.marius.valeyou.ui.fragment.more.privacy_policy.PrivacyPolicyFragment;
import com.marius.valeyou.ui.fragment.more.profile.ProfileActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewHomeFragment extends AppFragment<FragmentNewHomeBinding, NewHomeFragmentVM> implements HomePageCategoryAdapter.ProductCallback {

    public static final String TAG = "NewHomeFragment";
    private List<CategoryListBean> categoryListBeans;
    private MainActivity mainActivity;

    private String rate = "";
    private List<RequestBean> selected = new ArrayList<>();
    private BottomSheetBehavior bottomSheetBehavior;
    List<SuggestionsModel> suggestionsModelList = new ArrayList<>();
    private boolean isAccount = false;

    HomePageCategoryAdapter adapter;
    boolean flag = false;
    String state_id = "";
    int stateId;
    String city_id = "";
    List<StatesModel> statesModelList;
    List<CitiesModel> citiesModelList;
    List<String> citiesList;
    ArrayList<String> statesList;

    public static Fragment newInstance() {
        return new NewHomeFragment();
    }

    @Override
    protected BindingFragment<NewHomeFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_new_home, NewHomeFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.setBean(viewModel.sharedPref.getUserData());
        binding.nav.setBean(viewModel.sharedPref.getUserData());

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getCount();
    }

    @Override
    protected void subscribeToEvents(NewHomeFragmentVM vm) {
        Log.d("Credential : ", vm.sharedPref.getUserData().getId() + "===" + vm.sharedPref.getUserData().getAuthKey());
        Log.d("TOKEN : ", vm.sharedPref.get(Constants.FCM_TOKEN, "xyz"));

        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);

        vm.getListOfCateegory(0, "");
        binding.setCheck(false);

        mainActivity.isRemote = true;
        mainActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (type.equalsIgnoreCase(requireContext().getResources().getString(R.string.remotejob))) {
                    binding.cvRemoteJob.performClick();
                } else if (type.equalsIgnoreCase(requireContext().getResources().getString(R.string.localjobs))) {
                    binding.cvLocalJob.performClick();
                }
            }
        };


        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()) {
                    case R.id.notificationIcon:
                        Intent intent = NotificationActivity.newIntent(getActivity());
                        startNewActivity(intent);
                        break;

                    case R.id.cv_profile:
                        binding.drawerView.openDrawer(GravityCompat.START);
                        break;

                    case R.id.rl_profile:
                        binding.drawerView.openDrawer(GravityCompat.START);
                        break;
                    case R.id.iv_profile:
                        binding.drawerView.openDrawer(GravityCompat.START);
                        break;

                    case R.id.cv_pic:
                        binding.drawerView.openDrawer(GravityCompat.START);
                        break;

                    case R.id.iv_close:
                        binding.drawerView.closeDrawers();
                        break;

                    case R.id.view_profile:
                        Intent intent1 = ProfileActivity.newIntent(getActivity());
                        startNewActivity(intent1);
                        break;

                    case R.id.view_img:
                        Intent intent3 = ProfileActivity.newIntent(getActivity());
                        startNewActivity(intent3);
                        break;

                    case R.id.imageViewfilter:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case R.id.iv_cancel:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;


                    case R.id.view_five:
                        binding.bottomLayout.ftRating.setType(5);
                        rate = "";
                        break;
                    case R.id.view_four:
                        binding.bottomLayout.ftRating.setType(4);
                        rate = "2";
                        break;
                    case R.id.view_three:
                        binding.bottomLayout.ftRating.setType(3);
                        rate = "3";
                        break;
                    case R.id.view_two:
                        binding.bottomLayout.ftRating.setType(2);
                        rate = "4";
                        break;
                    case R.id.view_one:
                        binding.bottomLayout.ftRating.setType(1);
                        rate = "5";
                        break;
                    case R.id.b_apply:
                        // Apply Filter
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        applyFilter();
                        break;

                    case R.id.et_state:
                        flag = true;
                        break;

                    case R.id.et_city:
                        break;

                    case R.id.cv_remote_job:
                        mainActivity.isRemote = true;
                        binding.setCheck(false);
                        vm.getListOfCateegory(0, "");
                        break;
                    case R.id.cv_local_job:
                        mainActivity.isRemote = false;
                        vm.getListOfCateegory(1, "");
                        binding.setCheck(true);
                        break;

                    case R.id.map:
                        mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance());
                        break;

                    case R.id.relReturn:
                        viewModel.sharedPref.put("loginType", "");
                        startNewActivity(LoginChooseActivity.newIntent(requireActivity(), "welcome"));
                        requireActivity().finish();
                        break;

                    case R.id.cv_my_account:
                        if (isAccount) {
                            isAccount = false;
                            binding.nav.llAccountsItems.setVisibility(View.GONE);

                        } else {
                            isAccount = true;
                            binding.nav.llAccountsItems.setVisibility(View.VISIBLE);

                        }

                        break;


                    case R.id.txt_change_password:
                        Intent changePasswordIntent = ChangePasswordFragment.newIntent(getActivity());
                        startNewActivity(changePasswordIntent);
                        break;


                    case R.id.txt_identity_verification:
                        binding.drawerView.closeDrawers();
                        Intent intent2 = IdentityVerificationActivity.newIntent(getActivity());
                        startNewActivity(intent2);
                        break;

                    case R.id.txt_deactivate_account:
                        binding.drawerView.closeDrawers();
                        dialogDeactivateAccount();
                        break;

                    case R.id.txt_delete_account:
                        binding.drawerView.closeDrawers();
                        dialogDeleteAccount();
                        break;


                }
            }
        });

        vm.notificationbadgeEventRequest.observe(this, new SingleRequestEvent.RequestObserver<NotificationBadgeModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<NotificationBadgeModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        mainActivity.badgeCount(resource.data.getCount());
                        if (resource.data.getNotification() > 0) {

                            binding.notificationCount.setVisibility(View.VISIBLE);
                            if (resource.data.getNotification() > 9) {
                                binding.notificationCount.setText("9+");
                            } else {
                                binding.notificationCount.setText(resource.data.getNotification() + "");
                            }

                        } else {
                            binding.notificationCount.setVisibility(View.GONE);
                        }
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
                        break;
                    case SUCCESS:

                        vm.sharedPref.deleteAll();
                        LoginManager.getInstance().logOut();
                        vm.success.setValue(resource.message);
                        Intent intent1 = LoginActivity.newIntent(getActivity());
                        startNewActivity(intent1, true, true);

                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }

                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);

                        break;
                }
            }
        });


        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        categoryListBeans = resource.data;
                        setRecyclerView(categoryListBeans);
                        ftAdapter.setList(categoryListBeans);
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


        binding.bottomLayout.ftState.etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                if (input.length() > 0) {
                    binding.bottomLayout.ftState.rvCities.setVisibility(View.VISIBLE);
                    vm.getCities(Integer.parseInt(state_id), input);
                } else {
                    binding.bottomLayout.ftState.rvCities.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        vm.citiesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<CitiesModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CitiesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:

                        citiesModelList = resource.data;
                        if (citiesModelList.size() > 0) {
                            cityadapter.setList(citiesModelList);
                        } else {
                            Toast.makeText(mainActivity, "No City Found", Toast.LENGTH_SHORT).show();
                        }

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


        binding.bottomLayout.ftState.etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                if (input.length() > 0) {
                    binding.bottomLayout.ftState.rvStates.setVisibility(View.VISIBLE);
                    vm.getStates(input);
                } else {
                    binding.bottomLayout.ftState.rvStates.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        vm.statesEventRequest.observe(this, new SingleRequestEvent.RequestObserver<List<StatesModel>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<StatesModel>> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        statesModelList = resource.data;
                        if (statesModelList.size() > 0) {
                            stateadapter.setList(statesModelList);
                        } else {
                            Toast.makeText(mainActivity, "No State Found", Toast.LENGTH_SHORT).show();
                        }

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


        binding.bottomLayout.ftCategory.rbRemoteJob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    vm.getListOfCateegory(0, "");
            }
        });


        binding.bottomLayout.ftCategory.rbLocalJob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    vm.getListOfCateegory(1, "");
            }
        });


        setBottomsheet();
        vm.getStates("");
        moveViewWithDrawer();
        setRecyclerViewMore();
        setFilterRecycler();
        setAdapterSuggestions();
        setStatesAdapter();
        setCitiesAdapter();

        binding.etSearch.addTextChangedListener(textWatcher);

        vm.suggestionsEvent.observe(this, new Observer<Resource<List<SuggestionsModel>>>() {
            @Override
            public void onChanged(Resource<List<SuggestionsModel>> listResource) {
                switch (listResource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        binding.typingText.setVisibility(View.GONE);
                        suggestionsModelList = listResource.data;
                        adapterSuggestions.setList(suggestionsModelList);

                        break;
                    case WARN:
                        vm.warn.setValue(listResource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(listResource.message);
                        break;
                }
            }
        });


        vm.successEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(resource.message);
                        viewModel.sharedPref.deleteAll();
                        Intent intent1 = LoginActivity.newIntent(getActivity());
                        startNewActivity(intent1, true, true);
                        getActivity().finishAffinity();
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        if (resource.message.equalsIgnoreCase(getResources().getString(R.string.unauthorised))) {
                            vm.sharedPref.deleteAll();
                            Intent in = LoginActivity.newIntent(getActivity());
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


    }


    private void setStatesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftState.rvStates.setLayoutManager(layoutManager);
        binding.bottomLayout.ftState.rvStates.setAdapter(stateadapter);
    }

    private void setCitiesAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftState.rvCities.setLayoutManager(layoutManager);
        binding.bottomLayout.ftState.rvCities.setAdapter(cityadapter);
    }

    SimpleRecyclerViewAdapter<StatesModel, HolderStatesBinding> stateadapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_states, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<StatesModel>() {
                @Override
                public void onItemClick(View v, StatesModel bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.ll_states:
                            binding.bottomLayout.ftState.etState.setText(bean.getName());
                            binding.bottomLayout.ftState.rvStates.setVisibility(View.GONE);
                            state_id = String.valueOf(bean.getId());
                            viewModel.getCities(bean.getId(), "");

                            break;
                    }
                }
            });

    SimpleRecyclerViewAdapter<CitiesModel, HolderCitiesBinding> cityadapter =
            new SimpleRecyclerViewAdapter(R.layout.holder_cities, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<CitiesModel>() {
                @Override
                public void onItemClick(View v, CitiesModel bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.ll_states:
                            city_id = String.valueOf(bean.getId());
                            binding.bottomLayout.ftState.etCity.setText(bean.getName());
                            binding.bottomLayout.ftState.rvCities.setVisibility(View.GONE);

                            break;
                    }
                }
            });


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String str = charSequence.toString();
            binding.typingText.setVisibility(View.VISIBLE);
            binding.typingText.setText("Searching for " + '"' + str + '"');
            if (str.length() > 0) {
                binding.rvSuggestions.setVisibility(View.VISIBLE);
                viewModel.searchSuggestions(str);
            } else {
                binding.typingText.setVisibility(View.GONE);
                suggestionsModelList.clear();
                binding.rvSuggestions.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void moveViewWithDrawer() {
        binding.nav.setName(viewModel.sharedPref.getUserData().getFirstName() + " " + viewModel.sharedPref.getUserData().getLastName());
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                binding.drawerView, binding.toolbar, R.string.acc_drawer_open, R.string.acc_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                binding.drawerView.setScrimColor(Color.TRANSPARENT);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawerView.addDrawerListener(mDrawerToggle);
    }

    private void setRecyclerView(List<CategoryListBean> list) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        HomePageCategoryAdapter adapter = new HomePageCategoryAdapter(getContext(), this::onItemClick, list);
        binding.rvCategories.setLayoutManager(layoutManager);
        binding.rvCategories.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View v, List<CategoryListBean> moreBeans, int pos) {

        List<SubCategoryBean> subCategoriesBean = moreBeans.get(pos).getSubCategories();

        if (moreBeans.get(pos).getType() == 0) {
            if (moreBeans.get(pos).getSubCategories().size() > 0) {

                showDialog(getActivity(), subCategoriesBean, pos);

            } else {

                RequestBean requestBean = new RequestBean();
                List<RequestSubCategory> subList = new ArrayList<>();
                requestBean.setCategory_id(String.valueOf(moreBeans.get(pos).getId()));
                requestBean.setSubcategory(subList);
                selected.add(requestBean);
                String json = new Gson().toJson(selected);

                mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance(String.valueOf(moreBeans.get(pos).getId()), "", "", json, "" + moreBeans.get(pos).getType()));

            }

        } else {

            RequestBean requestBean = new RequestBean();
            List<RequestSubCategory> subList = new ArrayList<>();
            requestBean.setCategory_id(String.valueOf(moreBeans.get(pos).getId()));
            requestBean.setSubcategory(subList);
            selected.add(requestBean);
            String json = new Gson().toJson(selected);
            mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance(String.valueOf(moreBeans.get(pos).getId()), "", "", json, "" + moreBeans.get(pos).getType()));

        }

    }

    private void setRecyclerViewMore() {
        binding.nav.rvMore.setLayoutManager(new LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false));
        SimpleRecyclerViewAdapter<MoreBean, HolderMoreBinding> adapter_more = new SimpleRecyclerViewAdapter<>(R.layout.holder_more, com.marius.valeyou.BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
            @Override
            public void onItemClick(View v, MoreBean shopBean) {
                binding.drawerView.closeDrawers();
                Intent intent;
                switch (shopBean.getId()) {

                    case 1:
                        Intent notificationIntent = NotificationActivity.newIntent(getActivity());
                        startNewActivity(notificationIntent);
                        break;

                    case 2:
                        Intent intent1 = PaymentActivity.newIntnt(getActivity(), "home");
                        startNewActivity(intent1);
                        break;


                    case 3:
                        Intent langInt = AppLanguageActivity.newIntent(getActivity());
                        startNewActivity(langInt);

                        break;


                    case 4:
                        Intent blockIntent = BlockActivity.newIntent(getActivity());
                        startNewActivity(blockIntent);

                        break;

                    case 5:
                        mainActivity.addSubFragment(TAG, PrivacyPolicyFragment.newInstance());
                        break;

                    case 6:
                        mainActivity.addSubFragment(TAG, HelpAndSupportFragment.newInstance());
                        break;
                    case 7:
                        mainActivity.addSubFragment(TAG, AboutUsFragment.newInstance());
                        break;
                    case 8:
                        dialogLogout();
                        break;
                    case 9:
                        viewModel.sharedPref.put("loginType", "");
                        startNewActivity(LoginChooseActivity.newIntent(requireActivity(), "welcome"));
                        requireActivity().finish();
                        break;

                }
            }
        });
        binding.nav.rvMore.setAdapter(adapter_more);
        adapter_more.setList(getMoreData());
    }

    private List<MoreBean> getMoreData() {
        List<MoreBean> menuBeanList = new ArrayList<>();
//        menuBeanList.add(new MoreBean(9, getResources().getString(R.string.return_to_main_menu), R.drawable.ic_return_service_mobile));
        menuBeanList.add(new MoreBean(1, getResources().getString(R.string.notifications), R.drawable.ic_notification_icon));
        //menuBeanList.add(new MoreBean(2, getResources().getString(R.string.payments), R.drawable.pay));
        //menuBeanList.add(new MoreBean(3, getResources().getString(R.string.select_language), R.drawable.ic_language));
        menuBeanList.add(new MoreBean(4, getResources().getString(R.string.blocked_contacts), R.drawable.ic_block));
        menuBeanList.add(new MoreBean(5, getResources().getString(R.string.terms), R.drawable.ic_privacy_policy_icon));
        menuBeanList.add(new MoreBean(6, getResources().getString(R.string.help_support), R.drawable.ic_help_icon));
        menuBeanList.add(new MoreBean(7, getResources().getString(R.string.about_us), R.drawable.ic_about_us_icon));
        menuBeanList.add(new MoreBean(8, getResources().getString(R.string.logout_account), R.drawable.ic_logout_icon));
        return menuBeanList;
    }


    private BaseCustomDialog<DialogLogoutBinding> dialogSuccess;

    private void dialogLogout() {
        dialogSuccess = new BaseCustomDialog<>(getActivity(), R.layout.dialog_logout, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
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


    private void setBottomsheet() {
        viewModel.getListOfCateegory(0, "");
        binding.bottomLayout.ftRating.setType(5);
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


        setFilterCatRv();


    }


    private void setFilterCatRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.ftCategory.rvCatFilter.setLayoutManager(layoutManager);
        binding.bottomLayout.ftCategory.rvCatFilter.setAdapter(ftAdapter);
        //ftAdapter.setList(getFtCategory());
    }

    private List<MoreBean> filterCat;

    private void setFilterRecycler() {
        binding.bottomLayout.setType(2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.bottomLayout.rvFilterCat.setLayoutManager(layoutManager);
        binding.bottomLayout.rvFilterCat.setAdapter(adapterCat);
        filterCat = getListData();
        adapterCat.setList(filterCat);
    }

    private List<MoreBean> getListData() {
        List<MoreBean> beanList = new ArrayList<>();
        // beanList.add(new MoreBean(1, "Categories", 1));
        beanList.add(new MoreBean(2, getResources().getString(R.string.rating), 1));
        beanList.add(new MoreBean(3, getResources().getString(R.string.distance), 0));
        beanList.add(new MoreBean(4, getResources().getString(R.string.state), 0));
        beanList.add(new MoreBean(5, getResources().getString(R.string.no_of_jobs), 0));
        return beanList;
    }

    SimpleRecyclerViewAdapter<MoreBean, HolderFilterCategoryBinding> adapterCat =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_filter_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
                @Override
                public void onItemClick(View v, MoreBean moreBean) {
                    for (int j = 0; j < filterCat.size(); j++) {
                        if (filterCat.get(j).getId() == moreBean.getId()) {
                            filterCat.get(j).setImage(1);
                        } else {
                            filterCat.get(j).setImage(0);
                        }
                    }
                    adapterCat.setList(filterCat);
                    binding.bottomLayout.setType(moreBean.getId());
                }
            });

    SimpleRecyclerViewAdapter<CategoryListBean, HolderSetCategoryBinding> ftAdapter =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_set_category, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<CategoryListBean>() {
                @Override
                public void onItemClick(View v, CategoryListBean moreBean) {
                    for (int j = 0; j < categoryListBeans.size(); j++) {
                        if (categoryListBeans.get(j).getId() == moreBean.getId()) {
                            if (moreBean.isCheck()) {
                                categoryListBeans.get(j).setCheck(false);
                            } else {
                                categoryListBeans.get(j).setCheck(true);
                            }
                        }
                    }
                    ftAdapter.setList(categoryListBeans);
                }
            });


    private void setAdapterSuggestions() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvSuggestions.setLayoutManager(layoutManager);
        binding.rvSuggestions.setAdapter(adapterSuggestions);
    }

    SimpleRecyclerViewAdapter<SuggestionsModel, HolderFilterCategoryBinding> adapterSuggestions =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_suggestions, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<SuggestionsModel>() {
                @Override
                public void onItemClick(View v, SuggestionsModel moreBean) {

                    if (moreBean.getSearch_type() == 0) {

                        mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance("", "", String.valueOf(moreBean.getId()), "", ""));

                    } else {

                        mainActivity.addSubFragment(HomeFragment.TAG, HomeFragment.newInstance(String.valueOf(moreBean.getId()), "", "", "", ""));

                    }

                    binding.rvSuggestions.setVisibility(View.GONE);

                }
            });

    String cat_ids = "";
    String state = "";

    private void applyFilter() {
        cat_ids = "";
        String distance = String.valueOf(binding.bottomLayout.ftDistance.distanceValue.getProgress() * 10);
        String no_of_job = String.valueOf(binding.bottomLayout.ftJob.vsJob.getProgress() * 10);

       /* for (int j = 0; j < categoryListBeans.size(); j++) {
            if (categoryListBeans.get(j).isCheck()) {
                cat_ids = cat_ids + categoryListBeans.get(j).getId() + ",";
            }
        }
        if (!cat_ids.equalsIgnoreCase("") && cat_ids.length() > 1) {
            cat_ids = cat_ids.substring(0, cat_ids.length() - 1);
        }*/
        if (no_of_job.equalsIgnoreCase("0")) {
            no_of_job = "";
        }

        if (distance.equalsIgnoreCase("0")) {
            distance = "";
        }

        mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance("", "", "", distance, no_of_job, state, rate, state_id, city_id));

    }

    public void showDialog(Activity activity, List<SubCategoryBean> subCategoriesBean, int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sub_cat_custom_layout);

        RecyclerView recycler_view = dialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        SubCatAdapter subCatAdapter = new SubCatAdapter(activity, subCategoriesBean);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(subCatAdapter);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button done = dialog.findViewById(R.id.done);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub_cat_ids = "";
                String cat_id = "";
                for (int i = 0; i < subCategoriesBean.size(); i++) {
                    if (subCategoriesBean.get(i).isCheck()) {
                        if (sub_cat_ids.isEmpty()) {
                            sub_cat_ids = String.valueOf(subCategoriesBean.get(i).getId());
                        } else {
                            sub_cat_ids = sub_cat_ids + "," + subCategoriesBean.get(i).getId();
                        }
                    }
                }
                cat_id = String.valueOf(subCategoriesBean.get(0).getCategoryId());


                //create json
                RequestBean requestBean = new RequestBean();
                List<RequestSubCategory> subcategory = new ArrayList<>();
                requestBean.setCategory_id(String.valueOf(categoryListBeans.get(position).getId()));
                for (int i = 0; i < categoryListBeans.get(position).getSubCategories().size(); i++) {
                    if (categoryListBeans.get(position).getSubCategories().get(i).isCheck()) {
                        subcategory.add(new RequestSubCategory(String.valueOf(categoryListBeans.get(position).getSubCategories().get(i).getId()), categoryListBeans.get(position).getSubCategories().get(i).getPrice()));
                    }
                    requestBean.setSubcategory(subcategory);
                }

                selected.add(requestBean);

                if (subcategory.size() > 0) {
                    String json = new Gson().toJson(selected);
                    mainActivity.addSubFragmentWithoutCache(HomeFragment.TAG, HomeFragment.newInstance(cat_id, sub_cat_ids, "", json, "0"));

                    dialog.cancel();

                } else {

                    viewModel.info.setValue(getResources().getString(R.string.plsease_select_atleast_one_category));

                }

                dialog.cancel();


            }
        });

        dialog.show();
    }

    private BaseCustomDialog<DialogDeleteAccouontBinding> dialogDelete;

    private void dialogDeleteAccount() {
        dialogDelete = new BaseCustomDialog<>(getActivity(), R.layout.dialog_delete_accouont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:

                            dialogDelete.dismiss();
                            viewModel.deleteOrDeactivate(viewModel.sharedPref.getUserData().getAuthKey(), "1");
                            break;
                        case R.id.btn_cancel:
                            dialogDelete.dismiss();
                            break;

                        case R.id.iv_cancel:
                            dialogDelete.dismiss();
                            break;
                    }
                }
            }
        });
        dialogDelete.getWindow().setBackgroundDrawableResource(R.color.white_trans);
        dialogDelete.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogDelete.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogDelete.show();
    }

    private BaseCustomDialog<DialogDeactivateAccopuontBinding> dialogDeactiviateAccount;

    private void dialogDeactivateAccount() {
        dialogDeactiviateAccount = new BaseCustomDialog<>(getActivity(), R.layout.dialog_deactivate_accopuont, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogDeactiviateAccount.dismiss();
                            viewModel.deleteOrDeactivate(viewModel.sharedPref.getUserData().getAuthKey(), "0");
                            break;
                        case R.id.btn_cancel:
                            dialogDeactiviateAccount.dismiss();
                            break;

                        case R.id.iv_cancel:
                            dialogDeactiviateAccount.dismiss();
                            break;
                    }


                }
            }
        });
        dialogDeactiviateAccount.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogDeactiviateAccount.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogDeactiviateAccount.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogDeactiviateAccount.show();
    }
}
