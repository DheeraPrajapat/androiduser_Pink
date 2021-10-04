package com.marius.valeyou.localMarketModel.ui.fragment.createAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.ApiUtils;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogCreateAdBinding;
import com.marius.valeyou.databinding.FragmentCreateAdBinding;
import com.marius.valeyou.databinding.HolderImageCloseBinding;
import com.marius.valeyou.databinding.HolderTxtBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.ShopProfile;
import com.marius.valeyou.localMarketModel.ui.SelectGalleryImageAdapter;
import com.marius.valeyou.localMarketModel.ui.activity.allCategory.AllCategoryActivity;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.activity.createShopProfile.CreateShopProfileActivity;
import com.marius.valeyou.localMarketModel.ui.activity.searched.SearchedActivity;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Select_Pic_Adp;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateAdFragment extends AppFragment<FragmentCreateAdBinding, CreateAdFragmentVM> {
    public static final String TAG = "CreateAdFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    private Uri resultUri = null;
    private List<MoreBean> imageList = new ArrayList<>();
    String postType = "";//Sale,Rent, Searched
    String productType = "";//new,old
    String shipping = "";//Yes,No
    String ownerType = "";//Private,Commercial
    int CategoryRequestCode = 12;
    int CreateShopRequestCode = 15;
    int SearcedRequestCode = 16;
    String lat = "";
    String lng = "";
    String is_phone_show = "";
    boolean isEdit = false;
    String postId = "";
    String removeImgId = "";
    List<String> imgIdList = new ArrayList<>();
    List<PostImage> imgLinkList = new ArrayList<>();
    boolean showDialog = false;
    List<CompanyListModel> companyNameList = new ArrayList<>();
    CompanyListModel companyListModel;
    CompanyAdapter companyAdapter;
    public String shop_id = "";
    public String address = "";
    public String phone = "";

    public static Fragment newInstance() {

        CreateAdFragment fragment = new CreateAdFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_CREATE, true);
        fragment.setArguments(args);
        return fragment;

    }

    public static Fragment newInstance(boolean isUpdate) {
        CreateAdFragment fragment = new CreateAdFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_UPDATE, isUpdate);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected BindingFragment<CreateAdFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_create_ad, CreateAdFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(final CreateAdFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);
        marketActivity.setHeader(R.string.create_ad);
        marketActivity.setRightText(R.string.preview);



//        lat = sharedPref.get("lati", "");
//        lng = sharedPref.get("longi", "");
//        address = sharedPref.get("address", "");
//        phone = sharedPref.get("phone", "");
//        binding.etAddress.setText(address);
//        binding.edtPhone.setText(phone);

        shop_id = sharedPref.get("shop_id", "");
        ownerType = sharedPref.get("owner_type", "");

        binding.setBean(marketActivity.marketPostModel);
        if (marketActivity.marketPostModel.getCategory() != null) {
            if(!ownerType.equalsIgnoreCase(getActivity().getResources().getString(R.string.private_text))){
                binding.etAddress.setEnabled(false);
                binding.txtCategory.setEnabled(false);
                binding.edtPhone.setEnabled(false);
                binding.edtPrePhone.setEnabled(false);

            }


            lat=marketActivity.marketPostModel.getLatitude();
            lng=marketActivity.marketPostModel.getLongitude();

        }
        if (this.getArguments() != null) {
            if (this.getArguments().getBoolean(Constants.IS_UPDATE, false)) {
                binding.btnPlaceAd.setText(requireContext().getResources().getString(R.string.update_ad));
                marketActivity.setHeader(R.string.update_ad);
                binding.setBean(marketActivity.marketPostModel);

                if (marketActivity.marketPostModel.getLatitude() != null
                        && marketActivity.marketPostModel.getLongitude() != null) {
                    lat = marketActivity.marketPostModel.getLatitude();
                    lng = marketActivity.marketPostModel.getLongitude();
                }
                ownerType=marketActivity.marketPostModel.getOwnerType();
                isEdit = true;
                postId = String.valueOf(marketActivity.marketPostModel.getId());
                binding.rvImage.setVisibility(View.VISIBLE);

                imgLinkList.clear();

                imgLinkList.addAll(marketActivity.marketPostModel.getPostImages());

                setLinkRecyclerView();

            } else if (this.getArguments().getBoolean(Constants.IS_CREATE, false)) {
                binding.setBean(marketActivity.marketPostModel);

            }
        }

        imgIdList.clear();

        binding.rgPostType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbSell) {
                    binding.rbSearched.setChecked(false);
                    binding.rbRent.setChecked(false);
                    binding.rbRent.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbSearched.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbSell.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    binding.txtSearched.setVisibility(View.GONE);
                    postType = "Sale";
                } else if (checkedId == R.id.rbRent) {
                    binding.rbSearched.setChecked(false);
                    binding.rbSell.setChecked(false);
                    binding.rbSell.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbSearched.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbRent.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    binding.txtSearched.setVisibility(View.GONE);
                    postType = "Rent";
                } else {
                    binding.rbSell.setChecked(false);
                    binding.rbRent.setChecked(false);
                    binding.rbSell.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbRent.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbSearched.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    binding.txtSearched.setVisibility(View.VISIBLE);
                    postType = "Searched";
                }
            }
        });

        binding.rgProductType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbNew) {
                    binding.rbOld.setChecked(false);
                    binding.rbOld.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbNew.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    productType = "New";
                } else {
                    binding.rbNew.setChecked(false);
                    binding.rbNew.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbOld.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    productType = "Used";
                }
            }
        });

        binding.rgShipping.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbNo) {
                    binding.rbYes.setChecked(false);
                    binding.rbYes.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbNo.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    shipping = "No";
                } else {
                    binding.rbNo.setChecked(false);
                    binding.rbNo.setTextColor(requireContext().getResources().getColor(R.color.black));
                    binding.rbYes.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    shipping = "Yes";
                }
            }
        });


        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.txtSearched:
                    Intent intentSearched = SearchedActivity.newIntent(requireActivity());
                    startActivityForResult(intentSearched, SearcedRequestCode);

                    break;

                case R.id.txtCategory:

                    Intent intent = AllCategoryActivity.newIntent(requireActivity(), marketActivity.categoryListBeans);
                    startActivityForResult(intent, CategoryRequestCode);

                    break;

                case R.id.btnPlaceAd:
                    if (checkValidation()) {
                        if (isNetworkAvailable(getActivity())) {
                            PostJob();
                        } else {
                            noNetworkToast(getActivity());
                        }
                        //  setCreateDialog();
                    }
                    break;


                case R.id.txtFixedPrice:
                    showMoreDialog(view);
                    break;


            }
        });

        vm.addPostEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        marketActivity.onBackPressed();
                        if (isEdit) {
                            marketActivity.onBackPressed();
                        }
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

        vm.deletePostImageEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (imgIdList.size() > 1) {
                            imgIdList.remove(removeImgId);
                            removeImgId = imgIdList.get(0);
                            viewModel.deletePostImage(removeImgId);
                            imageLinkAdapter.setList(marketActivity.marketPostModel.getPostImages());
                        } else {
                            PostJob();
                        }
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

        vm.marketShopProfileEvent.observe(this, new SingleRequestEvent.RequestObserver<MarketShopProfile>() {
            @Override
            public void onRequestReceived(@NonNull Resource<MarketShopProfile> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        companyNameList.clear();
                        dismissProgressDialog();
                        if (resource.data.getData() != null) {
                            sharedPref.put(Constants.SHOP_NAME, resource.data.getData().get(0).getCompanyName() == null ? "" : resource.data.getData().get(0).getCompanyName());
                            sharedPref.put(Constants.SHOP_ADDRESS, resource.data.getData().get(0).getAddress() == null ? "" : resource.data.getData().get(0).getAddress());

                            for (int i = 0; i < resource.data.getData().size(); i++) {
                                companyListModel = new CompanyListModel();
                                companyListModel.setComapny_name(resource.data.getData().get(i).getCompanyName());
                                companyListModel.setShop_id(String.valueOf(resource.data.getData().get(i).getId()));
                                companyListModel.setCompany_selected(false);
                                companyNameList.add(companyListModel);

                            }


                        } else {
                            sharedPref.put(Constants.SHOP_NAME, "");
                            sharedPref.put(Constants.SHOP_ADDRESS, "");
                        }

                        if (showDialog) {
                            showDialog = false;


                            setCreateDialog();
                        }

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


        binding.etAddress.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                binding.etAddress.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails placeDetails) {

                        lat = String.valueOf(placeDetails.geometry.location.lat);
                        lng = String.valueOf(placeDetails.geometry.location.lng);

                        Log.d("CreateAdFragment", "Location: " + placeDetails.formatted_address
                                + ", lat: " + lat + ", lng: " + lng);

                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        });

        setRecyclerView();

    }


    private void setLinkRecyclerView() {
        binding.rvImage.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.rvImage.setAdapter(imageLinkAdapter);
        imageLinkAdapter.setList(imgLinkList);
    }


    SimpleRecyclerViewAdapter<PostImage, HolderImageCloseBinding> imageLinkAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_image_close, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<PostImage>() {
                @Override
                public void onItemClick(View v, PostImage bean) {
                    if (v.getId() == R.id.imgCancel) {
                        imgIdList.add(String.valueOf(bean.getId()));
                        imgLinkList.remove(bean);
                        imageLinkAdapter.setList(imgLinkList);
                        removeImgId = imgIdList.get(0);
                    }
                }
            });


    private void setRecyclerView() {
        binding.rvAddImage.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        binding.rvAddImage.setAdapter(imageAdapter);
        imageList.clear();
        imageList.add(new MoreBean(0, "", 1));
        imageAdapter.setList(imageList);
    }

    SelectGalleryImageAdapter imageAdapter = new SelectGalleryImageAdapter(
            new SelectGalleryImageAdapter.SimpleCallback() {
                @Override
                public void onItemClick(View v, MoreBean bean) {
                    switch (v != null ? v.getId() : 0) {
                        case R.id.imgCancel:
                            imageList.remove(bean);
                            imageAdapter.setList(imageList);
                            break;

                        case R.id.imgCamera:
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                                    .start(requireActivity());
                            break;
                    }
                }
            });


    private BaseCustomDialog<DialogCreateAdBinding> dialogCreateAd;

    @SuppressLint("SetTextI18n")
    private void setCreateDialog() {
        ownerType = "";
        dialogCreateAd = new BaseCustomDialog<>(requireContext(), R.layout.dialog_create_ad, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            if (ownerType.isEmpty()) {
                                viewModel.error.setValue("Please select ownerType.");
                                dialogCreateAd.dismiss();
                                return;
                            }
                            if (isEdit && imgIdList.size() > 0) {
                                viewModel.deletePostImage(removeImgId);
                            } else {
                                if (isNetworkAvailable(getActivity())) {
                                    PostJob();
                                } else {
                                    noNetworkToast(getActivity());
                                }

                            }
                            dialogCreateAd.dismiss();
                            break;
                        case R.id.iv_cancel:
                            dialogCreateAd.dismiss();
                            break;

                        case R.id.ivCreateCompany:
                            ShopProfile profile = new ShopProfile();
                            profile.setPhone(binding.edtPhone.getText().toString());
                            profile.setAddress(binding.etAddress.getText().toString());
                            profile.setLatitude(lat);
                            profile.setLongitude(lng);
                            profile.setCountry_code("+01");
                            Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans, profile);
                            startActivityForResult(intent, CreateShopRequestCode);
                            dialogCreateAd.dismiss();
                            break;
                    }
                }
            }
        });

        dialogCreateAd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogCreateAd.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogCreateAd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogCreateAd.show();

//        dialogCreateAd.getBinding().txtShopName.setText(sharedPref.get(Constants.SHOP_NAME, "Shop name")
//                + "\n" + sharedPref.get(Constants.SHOP_ADDRESS, "Address"));

        if (companyNameList.size() > 0) {
//             companyAdapter = new CompanyAdapter(CreateAdFragment.this, companyNameList,
//                    new CompanyAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int item) {
//                        }
//
//
//                    });

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            dialogCreateAd.getBinding().rvCompanies.setLayoutManager(layoutManager);
            dialogCreateAd.getBinding().rvCompanies.setAdapter(companyAdapter);

        }


        dialogCreateAd.getBinding().rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPrivate) {
                    dialogCreateAd.getBinding().rbCommercial.setChecked(false);
                    dialogCreateAd.getBinding().rbCommercial.setTextColor(requireContext().getResources().getColor(R.color.black));
                    dialogCreateAd.getBinding().rbPrivate.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    ownerType = "Private";
                    dialogCreateAd.getBinding().rlCompanies.setVisibility(View.GONE);
                    shop_id = "";
                }

                else {
                    dialogCreateAd.getBinding().rbPrivate.setChecked(false);
                    dialogCreateAd.getBinding().rbPrivate.setTextColor(requireContext().getResources().getColor(R.color.black));
                    dialogCreateAd.getBinding().rbCommercial.setTextColor(requireContext().getResources().getColor(R.color.colorPrimary));
                    ownerType = "Commercial";
                    dialogCreateAd.getBinding().rlCompanies.setVisibility(View.VISIBLE);
                    if (companyNameList.size() > 0) {
                        shop_id = companyNameList.get(0).getShop_id();
                    }
                    if (sharedPref.get(Constants.SHOP_NAME, "").equalsIgnoreCase("")) {
                        ShopProfile profile = new ShopProfile();
                        profile.setPhone(binding.edtPhone.getText().toString());
                        profile.setAddress(binding.etAddress.getText().toString());
                        profile.setLatitude(lat);
                        profile.setLongitude(lng);
                        profile.setCountry_code("+91");
                        Intent intent = CreateShopProfileActivity.newIntent(requireActivity(), marketActivity.categoryListBeans, profile);
                        startActivityForResult(intent, CreateShopRequestCode);
                        dialogCreateAd.dismiss();
                    }
                }
            }
        });

        if (isEdit) {
            if (marketActivity.marketPostModel.getOwnerType().equalsIgnoreCase("Private")) {
                dialogCreateAd.getBinding().rbPrivate.setChecked(true);
            } else {
                dialogCreateAd.getBinding().rbCommercial.setChecked(true);
            }
        }

    }

    //Check Internet Connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void noNetworkToast(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.e_internet), Toast.LENGTH_SHORT).show();
    }

    PopupWindow popupWindow;

    private void showMoreDialog(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_fixed_price, null);
        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(10);
        popupWindow.showAsDropDown(view, 0, 0);
        RecyclerView recyclerView = popupView.findViewById(R.id.rv_language);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(languageAdapter);
        languageAdapter.setList(getFixedList());
        binding.txtFixedPrice.setText(getFixedList().get(0).getName());

    }

    SimpleRecyclerViewAdapter<MoreBean, HolderTxtBinding> languageAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_txt, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
        @Override
        public void onItemClick(View v, MoreBean bean) {
            if (v.getId() == R.id.ll_items) {
                binding.txtFixedPrice.setText(bean.getName());
                popupWindow.dismiss();
            }
        }
    });

    private List<MoreBean> getFixedList() {
        List<MoreBean> fixedList = new ArrayList<>();
        fixedList.add(new MoreBean(0, "Fixed", 0));
        fixedList.add(new MoreBean(1, "Negotiable", 0));
        return fixedList;
    }

    private boolean checkValidation() {
        if (binding.edtTitle.getText().toString().isEmpty()) {
//            viewModel.error.setValue(getResources().getString(R.string.please_add_job_title));
            binding.edtTitle.setError("");
            return false;
        }

        if (binding.txtCategory.getText().toString().isEmpty()) {
            binding.txtCategory.setError("");
            return false;
        }

        if (binding.edtPrice.getText().toString().isEmpty()) {
            binding.edtPrice.setError("");
            return false;
        }

        if (binding.txtFixedPrice.getText().toString().isEmpty()) {
            binding.txtFixedPrice.setError("");
            return false;
        }

        if (!postType.equalsIgnoreCase("Searched")) {
            binding.txtSearched.setText("");
        }

        if (postType.isEmpty()) {
            viewModel.error.setValue("Please select post type");
            return false;
        }

        if (postType.equalsIgnoreCase("Searched") && binding.txtSearched.getText().toString().isEmpty()) {
            binding.txtSearched.setError("");
            return false;
        }

        if (shipping.isEmpty()) {
            viewModel.error.setValue("Please select shipping.");
            return false;
        }

        if (binding.edtDesc.getText().toString().isEmpty()) {
            binding.edtDesc.setError("");
            return false;
        }
        if (binding.etAddress.getText().toString().isEmpty() || lat.length() < 3) {
            binding.etAddress.setText("");
            binding.etAddress.setError("");
            return false;
        }

        if (binding.edtPrePhone.getText().toString().isEmpty()) {
            binding.edtPrePhone.setError("Please add Country code");
            return false;
        }
        if (binding.edtPhone.getText().toString().isEmpty()) {
            binding.edtPhone.setError("Please add  Phone number");
            return false;
        } if (binding.edtPhone.getText().toString().length()<9) {
            viewModel.error.setValue("Minimum 9 digits required");
            return false;
        }
        if (binding.edtHighlight.getText().toString().isEmpty()) {
            binding.edtHighlight.setError("");
            return false;
        }

        if (imageList.size() <= 1 && !isEdit) {
            viewModel.error.setValue("Please add a image.");
            return false;
        }

        if (isEdit) {
            if ((imgLinkList.size() + imageList.size()) <= 1) {
                viewModel.error.setValue("Please add a image.");
                return false;
            }
        }
        return true;
    }

    private void PostJob() {
        if (binding.cbShowPhone.isChecked()) {
            is_phone_show = "1";
        } else {
            is_phone_show = "0";
        }
        MultipartBody.Part[] imageBody = new MultipartBody.Part[imageList.size()];
        for (int i = 0; i < imageList.size() - 1; i++) {
            Uri imagePath = Uri.parse(imageList.get(i).getName());
            MultipartBody.Part image = ApiUtils.createMultipartBodySingle(new File(imagePath.getPath()), "image");
            imageBody[i] = image;
        }

        RequestBody postIdBody = RequestBody.create(MediaType.parse("text/plain"), postId);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtTitle.getText().toString());
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtDesc.getText().toString());
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), binding.txtCategory.getText().toString());
        RequestBody productTypeBody = RequestBody.create(MediaType.parse("text/plain"), productType);//Sale,Rent
        RequestBody post_typeBody = RequestBody.create(MediaType.parse("text/plain"), postType);//Sale,Rent
        RequestBody shippingBody = RequestBody.create(MediaType.parse("text/plain"), shipping);//Yes,No
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtPrice.getText().toString());
        RequestBody fixed_priceBody = RequestBody.create(MediaType.parse("text/plain"), binding.txtFixedPrice.getText().toString());
        RequestBody tagBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtHighlight.getText().toString());
        RequestBody owner_typeBody = RequestBody.create(MediaType.parse("text/plain"), ownerType);//Private,Commercial
        RequestBody locationBody = RequestBody.create(MediaType.parse("text/plain"), binding.etAddress.getText().toString());
        RequestBody latitudeBody = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody longitudeBody = RequestBody.create(MediaType.parse("text/plain"), lng);
        RequestBody search_keywordBody = RequestBody.create(MediaType.parse("text/plain"), binding.txtSearched.getText().toString());
        RequestBody phoneBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtPhone.getText().toString());
        RequestBody is_phone_show1 = RequestBody.create(MediaType.parse("text/plain"), is_phone_show);
        RequestBody countryCodeBody = RequestBody.create(MediaType.parse("text/plain"), binding.edtPrePhone.getText().toString().trim());
        RequestBody Shop_id = RequestBody.create(MediaType.parse("text/plain"), shop_id);


        viewModel.marketAddPost(isEdit, postIdBody, titleBody, descriptionBody, categoryBody, productTypeBody, post_typeBody,
                shippingBody, priceBody, fixed_priceBody, tagBody, owner_typeBody,
                locationBody, latitudeBody, longitudeBody, search_keywordBody, is_phone_show1, phoneBody, countryCodeBody, Shop_id, imageBody);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CategoryRequestCode) {
                String categoryName = data.getStringExtra(Constants.CATEGORY_NAME);
                binding.txtCategory.setText(categoryName);

            } else if (requestCode == SearcedRequestCode) {
                String categoryName = data.getStringExtra(Constants.CATEGORY_NAME);
                binding.txtSearched.setText(categoryName);

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                resultUri = result.getUri();
                imageList.remove(imageList.size() - 1);
                imageList.add(new MoreBean(1, String.valueOf(resultUri), 0));
                imageList.add(new MoreBean(0, "", 1));
                imageAdapter.setList(imageList);


            } else if (requestCode == CreateShopRequestCode) {
                showDialog = data.getBooleanExtra(Constants.HIT_API, false);

            }
        }
    }
}
