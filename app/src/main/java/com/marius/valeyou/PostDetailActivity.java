package com.marius.valeyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.chat.UsersModel;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityPostDetailBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.base.view.BaseActivity;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.DetailMapFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.homeListDetail.HomeListDetailFragment;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivity;
import com.marius.valeyou.ui.activity.block.BlockActivity;
import com.marius.valeyou.ui.activity.block.BlockActivityVM;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.ui.activity.post_detail_sub.SubMapActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import javax.inject.Inject;

public class PostDetailActivity extends AppActivity<ActivityPostDetailBinding, PostDetailActivityVM> {

    public static final String TAG = "PostDetailActivity";
    @Inject
    SharedPref sharedPref;
    String phone = "";
    String fav = "0";
    String comefrom = "";
    UsersModel.ChatBean.ShopDataBean bean;
    public MarketPostModel marketPostModel;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @Override
    protected BindingActivity<PostDetailActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_post_detail, PostDetailActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(PostDetailActivityVM vm) {
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.imgCall:
                        if (comefrom.equalsIgnoreCase("profile")) {
                            phone = marketPostModel.getPhone();
                        } else {
                            phone = bean.getPhone();
                        }
                        if (phone != null) {
                            callPhoneNumber();

                        } else {
                            Log.e("NO", "Number not found");
                        }
                        break;

                    case R.id.imgShare:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        if (comefrom.equalsIgnoreCase("profile")) {
                            sendIntent.putExtra(Intent.EXTRA_TEXT,
                                    "Hey check out what i found on the local market at valeyou. https://valeyou.com.br/market-share-post/" + marketPostModel.getTagId());
                        } else {

                            sendIntent.putExtra(Intent.EXTRA_TEXT,
                                    "Hey check out what i found on the local market at valeyou. https://valeyou.com.br/market-share-post/" + bean.getTag_id());

                        }
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;

                    case R.id.imgLocation:
//                        marketActivity.isList = false;
//                        marketActivity.latLngFilter =  marketPostModel.getLatitude() + marketPostModel.getLongitude()+ marketPostModel.getTitle();
//                        marketActivity.lati = marketActivity.marketPostModel.getLatitude()  ;
//                        marketActivity.longi =   marketActivity.marketPostModel.getLongitude();
//                        marketActivity.click_tag_id =  marketActivity.marketPostModel.getTagId();
//                        marketActivity.fromMyProduct = true;
//                        //marketActivity.showMarker();
//                        marketActivity.addSubFragment(HomeListDetailFragment.TAG, DetailMapFragment.newInstance());
                        if (comefrom.equalsIgnoreCase("profile")) {
                            Intent intent1 = SubMapActivity.newIntent(PostDetailActivity.this);
                            intent1.putExtra("lati", marketPostModel.getLatitude());
                            intent1.putExtra("longi", marketPostModel.getLongitude());
                            intent1.putExtra("tag_id", marketPostModel.getTagId());
                            intent1.putExtra("lat_lng_filter", marketPostModel.getLatitude()
                                    + marketPostModel.getLongitude() + marketPostModel.getTitle());
                            startActivity(intent1);
                        } else {
                            Intent intent1 = SubMapActivity.newIntent(PostDetailActivity.this);
                            intent1.putExtra("lati", bean.getLatitude());
                            intent1.putExtra("longi", bean.getLongitude());
                            intent1.putExtra("tag_id", bean.getTag_id());
                            intent1.putExtra("lat_lng_filter", bean.getLatitude() + bean.getLongitude() + bean.getTitle());
                            startActivity(intent1);
                        }
                        break;


                    case R.id.imgFav:
                        if (comefrom.equalsIgnoreCase("profile")) {
                            if (fav.equals("0")) {
                                marketPostModel.setFav(1);
                                fav = "1";
                                viewModel.addToFavourite(marketPostModel.getId() + "", "1");
                            } else {
                                marketPostModel.setFav(0);
                                fav = "0";
                                viewModel.addToFavourite(marketPostModel.getId() + "", "0");
                            }
                        } else {

                            if (fav.equals("0")) {
                                bean.setFav("1");
                                fav = "1";
                                viewModel.addToFavourite(bean.getId() + "", "1");
                            } else {
                                bean.setFav("0");
                                fav = "0";
                                viewModel.addToFavourite(bean.getId() + "", "0");
                            }
                        }


                        break;
                }


            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            comefrom = intent.getStringExtra("comeFrom");
            String price = intent.getStringExtra("price");
            String name = intent.getStringExtra("name");

            if (comefrom.equalsIgnoreCase("profile")) {
                marketPostModel = (MarketPostModel) intent.getSerializableExtra("shopData");
                if (marketPostModel.getPostImages().size() > 0&&marketPostModel!=null) {
                    binding.setImagePath(marketPostModel.getPostImages().get(0).getImage());
                }
                fav = String.valueOf(marketPostModel.getFav());
                if (fav.equals("0")) {
                    binding.imgFav.setImageResource(R.drawable.ic_fav);
                } else {
                    binding.imgFav.setImageResource(R.drawable.ic_fav_selected);
                }
                binding.txtTitle.setText(marketPostModel.getTitle());
                binding.tvCategory.setText(marketPostModel.getCategory());
                 binding.tvPrice.setText(price + " brl");

                if( marketPostModel.getOwnerType().equalsIgnoreCase("Private")){
                    binding.txtUser.setText(name);
                }else {
                    if( marketPostModel.getShopProfile().getCompanyName()!=null){
                        binding.txtUser.setText( marketPostModel.getShopProfile().getCompanyName());}
                }

                binding.tvTag.setText(marketPostModel.getTag());
                binding.tvDes.setText(marketPostModel.getDescription());


                if (marketPostModel.getUserId().equals(sharedPref.getUserData().getId())) {
                    binding.imgCall.setVisibility(View.GONE);

                } else {
                    binding.imgCall.setVisibility(View.VISIBLE);

                }
                if (String.valueOf(marketPostModel.getFav()).equals("0")) {
                    binding.imgFav.setImageResource(R.drawable.ic_fav);

                } else {
                    binding.imgFav.setImageResource(R.drawable.ic_fav_selected);

                }
            } else {
                bean = intent.getParcelableExtra("shopData");
                String image_display = intent.getStringExtra("image_display");


                if (bean != null) {

                    if (image_display != null) {
                        binding.setImagePath(image_display);
                    }
                    fav = String.valueOf(bean.getFav());
                    if (fav.equals("0")) {
                        binding.imgFav.setImageResource(R.drawable.ic_fav);
                    } else {
                        binding.imgFav.setImageResource(R.drawable.ic_fav_selected);
                    }
                    binding.txtTitle.setText(bean.getTitle());
                    binding.tvCategory.setText(bean.getCategory());
                    if( bean.getOwner_type().equalsIgnoreCase("Private")){
                        binding.txtUser.setText(name);
                    }else {
                        binding.txtUser.setText( bean.getShop_name());
                    }

                    binding.tvPrice.setText(price + " brl");

                    binding.tvTag.setText(bean.getTag());
                    binding.tvDes.setText(bean.getDescription());


                    if (bean.getUser_id().equals(sharedPref.getUserData().getId())) {
                        binding.imgCall.setVisibility(View.GONE);

                    } else {
                        binding.imgCall.setVisibility(View.VISIBLE);

                    }
                    // android:src='@{bean.fav == 0? @drawable/ic_fav:@drawable/ic_fav_selected}'
                    if (String.valueOf(bean.getFav()).equals("0")) {
                        binding.imgFav.setImageResource(R.drawable.ic_fav);

                    } else {
                        binding.imgFav.setImageResource(R.drawable.ic_fav_selected);

                    }

                }
            }


        }
        //  android:src='@{bean.fav == 0? @drawable/ic_fav:@drawable/ic_fav_selected}'

        vm.addFavouriteEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        Toast.makeText(PostDetailActivity.this, "" + resource.data.getMsg(), Toast.LENGTH_SHORT).show();

                        if (fav.equals("0")) {
                            binding.imgFav.setImageResource(R.drawable.ic_fav);
                        } else {
                            binding.imgFav.setImageResource(R.drawable.ic_fav_selected);
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
                            Intent in = LoginActivity.newIntent(PostDetailActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });


    }


    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);

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

}