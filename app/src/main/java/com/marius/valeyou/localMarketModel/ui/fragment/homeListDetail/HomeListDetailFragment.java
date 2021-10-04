package com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.DialogBuyRentBinding;
import com.marius.valeyou.databinding.FragmentHomeDetailBinding;
import com.marius.valeyou.databinding.HolderImageBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.commonList.CommonListFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.createAd.CreateAdFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.userPost.UserPostFragment;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;

public class HomeListDetailFragment extends AppFragment<FragmentHomeDetailBinding, HomeListDetailFragmentVM> {
    public static final String TAG = "HomeListDetailFragment";
    @Inject
    SharedPref sharedPref;
    MainLocalMarketActivity marketActivity;
    String phone = "";

    public static Fragment newInstance() {
        return new HomeListDetailFragment();
    }


    public static Fragment newInstance(int visibility) {
        HomeListDetailFragment fragment = new HomeListDetailFragment();
        Bundle args = new Bundle();
        args.putInt("visibility", visibility);
        fragment.setArguments(args);
        return fragment;
    }


//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        marketActivity.viewHeaderDefault();
//    }

    @Override
    protected BindingFragment<HomeListDetailFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_home_detail, HomeListDetailFragmentVM.class);
    }


    @Override
    protected void subscribeToEvents(final HomeListDetailFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(false, false, false);

        if(marketActivity.marketPostModel.getTitle()!=null){
            String title= AppUtils.capitalize(marketActivity.marketPostModel.getTitle());
            marketActivity.setHeaderString(title);

        }

        if (HomeListDetailFragment.this.getArguments() != null) {
            binding.txtUser.setVisibility(HomeListDetailFragment.this.getArguments().getInt("visibility", View.VISIBLE));
        }

//        android:text='@{bean.capitalize(bean.user.name)}'

        if(marketActivity.marketPostModel.getOwnerType().equalsIgnoreCase("Private")){
            binding.txtUser.setText(marketActivity.marketPostModel.getUser().getName());
        }else {
            if(marketActivity.marketPostModel.getShopProfile()!=null){
                binding.txtUser.setText(marketActivity.marketPostModel.getShopProfile().getCompanyName());
            }
        }
        binding.setBean(marketActivity.marketPostModel);
        if (marketActivity.marketPostModel.getPostImages().size() > 0) {
            binding.setImagePath(marketActivity.marketPostModel.getPostImages().get(0).getImage());
        }

        marketActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {
                marketActivity.addSubFragment(CreateAdFragment.TAG, CreateAdFragment.newInstance(true));

            }
        };


        if (marketActivity.marketPostModel.getUser().getId() == sharedPref.getUserData().getId()) {
            binding.imgCall.setVisibility(View.GONE);
            binding.imgChat.setVisibility(View.GONE);
            marketActivity.viewHeaderItems(false, true, false);
            marketActivity.setRightText(R.string.edit);
            binding.imgDelete.setVisibility(View.VISIBLE);

        }

        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

                case R.id.imgDelete:
                    dialogDeletePost(String.valueOf(marketActivity.marketPostModel.getId()));
                    break;

                case R.id.imgFav:
                    viewModel.addToFavourite(String.valueOf(marketActivity.marketPostModel.getId()), marketActivity.marketPostModel.getFav() == 0 ? "1" : "0");
                    break;

                    case R.id.imgMain:
                        showFullImage(Constants.IMAGE_BASE_URL+binding.getImagePath());
                     break;
                case R.id.imgShare:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out what i found on the local market at valeyou. https://valeyou.com.br/market-share-post/" +binding.getBean().getTagId());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.txtUser:
                    marketActivity.addSubFragment(UserPostFragment.TAG, UserPostFragment.newInstance(String.valueOf(marketActivity.marketPostModel.getUser().getId()), marketActivity.marketPostModel.getUser().getName()));
                    break;

                case R.id.imgChat:

                    Intent intent1 = MessagingActivity.newIntent(requireActivity());
                    intent1.putExtra("comeFrom", "profile");
                    intent1.putExtra("shopData",marketActivity.marketPostModel);
                    intent1.putExtra("id", marketActivity.marketPostModel.getUser().getId());
                    intent1.putExtra("post_id", marketActivity.marketPostModel.getId().toString());
                    intent1.putExtra("image", marketActivity.marketPostModel.getUser().getImage());
                    intent1.putExtra("name", marketActivity.marketPostModel.getUser().getName());
                    intent1.putExtra("view_type", marketActivity.marketPostModel.getUser().getName());
                    intent1.putExtra("post", marketActivity.marketPostModel.getTitle());
                    intent1.putExtra("price", marketActivity.marketPostModel.getPrice());

                    startActivity(intent1);

                    break;

                case R.id.imgCall:
                    phone = marketActivity.marketPostModel.getPhone().equals("") ? marketActivity.marketPostModel.getUser().getPhone() : marketActivity.marketPostModel.getPhone();
                    callPhoneNumber();
                    break;

                case R.id.imgLocation:
                    marketActivity.isList = false;
                    marketActivity.latLngFilter = marketActivity.marketPostModel.getLatitude() + marketActivity.marketPostModel.getLongitude()+marketActivity.marketPostModel.getTitle();
                    marketActivity.lati = marketActivity.marketPostModel.getLatitude()  ;
                    marketActivity.longi =   marketActivity.marketPostModel.getLongitude();
                    marketActivity.click_tag_id =  marketActivity.marketPostModel.getTagId();
                    marketActivity.fromMyProduct = true;
                    //marketActivity.showMarker();

                    marketActivity.addSubFragment(HomeListDetailFragment.TAG, DetailMapFragment.newInstance());


                    break;
            }
        });

        vm.addFavouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        marketActivity.marketPostModel.setFav(marketActivity.marketPostModel.getFav() == 0 ? 1 : 0);
                        binding.setBean(marketActivity.marketPostModel);
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

        vm.deletePostEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(resource.message);
                        marketActivity.onBackPressed();
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

        setRecyclerView();

    }

    Dialog showImageDialog;
    public void showFullImage(String imgUrl) {
        showImageDialog = new Dialog(getActivity());
        showImageDialog.setContentView(R.layout.dialog_full_image);
        showImageDialog.setCanceledOnTouchOutside(true);
        //showImageDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        Window window = showImageDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);

        ImageView ivFullImage = showImageDialog.findViewById(R.id.ivFullImage);
        ImageView ivCancel = showImageDialog.findViewById(R.id.ivCancel);

        Glide.with(getActivity())
                .load(imgUrl)
                .placeholder(R.drawable.placeholder_new)
                .error(R.drawable.placeholder_new)
                .into(ivFullImage);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog.dismiss();

            }
        });
        showImageDialog.show();
    }

    private void setRecyclerView() {
        binding.rvImage.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.rvImage.setAdapter(imageAdapter);
        imageAdapter.setList(marketActivity.marketPostModel.getPostImages());
    }


    SimpleRecyclerViewAdapter<PostImage, HolderImageBinding> imageAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_image, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<PostImage>() {
                @Override
                public void onItemClick(View v, PostImage bean) {
                    if (v.getId() == R.id.relImage) {
                        binding.setImagePath(bean.getImage());
                    }
                }
            });


    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private BaseCustomDialog<DialogBuyRentBinding> dialogBuyRent;

    private void dialogDeletePost(String postId) {
        dialogBuyRent = new BaseCustomDialog<>(requireContext(), R.layout.dialog_buy_rent, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            viewModel.deletePost(postId);
                            dialogBuyRent.dismiss();

                            break;
                        case R.id.b_cancel:
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

        dialogBuyRent.getBinding().txtTitle.setText(requireContext().getResources().getString(R.string.are_you_sure_you_want_to_delete_this_post));
        dialogBuyRent.getBinding().btnSubmit.setText(requireContext().getResources().getString(R.string.yes));
        dialogBuyRent.getBinding().bCancel.setText(requireContext().getResources().getString(R.string.no));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Log.d(TAG, "Permission not Granted");
            }
        }
    }

}
