package com.marius.valeyou.ui.activity.provider_profile;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.ProviderBusinessHoursBean;
import com.marius.valeyou.data.beans.respbean.ProviderCertificateBean;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.beans.respbean.ProviderLanguageBean;
import com.marius.valeyou.data.beans.respbean.ProviderPortfolioBean;
import com.marius.valeyou.data.beans.respbean.ProviderReviewBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityProviderProfileBinding;
import com.marius.valeyou.databinding.HolderBusinessHourBinding;
import com.marius.valeyou.databinding.HolderCertificateItemBinding;
import com.marius.valeyou.databinding.HolderLanguageItemBinding;
import com.marius.valeyou.databinding.HolderPortfolioItemsBinding;
import com.marius.valeyou.databinding.HolderReviewsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.selectcategory.SelectCategoryActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.ui.fragment.message.chatview.ChatActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProviderProfileActivity extends AppActivity<ActivityProviderProfileBinding, ProviderProfileActivityVM> {
    private static final String PROVIDER_ID = "id";
    private static final String PROVIDER_FAV = "fav";
    private int isFav = 0;
    boolean check = false;
    ProviderDetails modelData;
    int providerId;
    String catjson="";
    String catType="";
    String tagId;

    List<ProviderPortfolioBean> portfolioList;

    public static Intent newIntent(Activity activity, int provider_id, int fav,String catJson,String catType) {
        Intent intent = new Intent(activity, ProviderProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PROVIDER_ID, provider_id);
        intent.putExtra(PROVIDER_FAV, fav);
        intent.putExtra("json", catJson);
        intent.putExtra("type", catType);
        return intent;
    }

    @Override
    protected BindingActivity<ProviderProfileActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_provider_profile, ProviderProfileActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(ProviderProfileActivityVM vm) {
       // isFav = getIntent().getIntExtra(PROVIDER_FAV, 0);
        //binding.setFav(isFav);

         providerId =  getIntent().getIntExtra(PROVIDER_ID, 0);
         catjson = getIntent().getStringExtra("json");
         catType = getIntent().getStringExtra("type");

        Log.d("paramsssss : ",vm.sharedPref.getUserData().getAuthKey()+"==="+providerId);

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
                    case R.id.rl_language:
                        if (binding.llLanguage.getVisibility()==View.GONE){
                            binding.setType("2");
                            binding.llLanguage.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llLanguage.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.rl_portfolio:
                        if (binding.llPortfolio.getVisibility()==View.GONE){
                            binding.setType("3");
                            binding.llPortfolio.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llPortfolio.setVisibility(View.GONE);
                        }

                        if (portfolioList.size()>2){
                            binding.txtViewMore.setVisibility(View.VISIBLE);
                        }else{
                            binding.txtViewMore.setVisibility(View.GONE);
                        }


                        break;
                    case R.id.rl_businesshour:
                        if (binding.llBusinessHours.getVisibility()==View.GONE){
                            binding.setType("5");
                            binding.llBusinessHours.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llBusinessHours.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.rl_awards:
                        if (binding.llCertificate.getVisibility()==View.GONE){
                            binding.setType("6");
                            binding.llCertificate.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llCertificate.setVisibility(View.GONE);
                        }
                        break;

                    case R.id.rl_review:
                        if (binding.llReview.getVisibility()==View.GONE){
                            binding.setType("7");
                            binding.llReview.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llReview.setVisibility(View.GONE);
                        }
                        break;

                    case R.id.rl_my_service:
                        if (binding.llServices.getVisibility()==View.GONE){
                            binding.setType("4");
                            binding.llServices.setVisibility(View.VISIBLE);
                        }else{
                            binding.setType("0");
                            binding.llServices.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.iv_fav:
                        if (isFav == 1) {
                            check = false;
                            vm.addToFavUnfav(getIntent().getIntExtra(PROVIDER_ID, 0),2);
                        } else {
                            check = true;
                            vm.addToFavUnfav(getIntent().getIntExtra(PROVIDER_ID, 0),1);
                        }
                        break;
                    case R.id.b_hire:

                        Log.d("TestData","=== "+catType+"======"+catjson);

                        if (catjson==null||catjson.isEmpty()) {
                            Intent intent = SelectCategoryActivity.newIntent(ProviderProfileActivity.this,
                                    providerId, "provider_profile");
                            startNewActivity(intent);
                        }else{
                            Intent intent = JunkRemovalJobFragment.newInstance(ProviderProfileActivity.this,
                                    catjson, "", "", providerId, catType);
                            startNewActivity(intent);
                        }
                        break;
                    case R.id.b_message:

                        Intent intent1 = ChatActivity.newIntent(ProviderProfileActivity.this);
                        intent1.putExtra("comeFrom","profile");
                        intent1.putExtra("id",modelData.getId());
                        intent1.putExtra("image",modelData.getImage());
                        intent1.putExtra("name",modelData.getFirstName()+" "+modelData.getLastName());

                        startActivity(intent1);
                        break;

                    case R.id.iv_share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out my app at: "+ Constants.BASE_URL +"provider/profile?id=" + tagId);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;

                    case R.id.txtViewMore:
                        if (binding.txtViewMore.getText().toString().equalsIgnoreCase("Show More")) {
                            setRecyclerPortfolio(portfolioList, true);
                            binding.txtViewMore.setText("Show Less");
                        }else{
                            setRecyclerPortfolio(portfolioList, false);
                            binding.txtViewMore.setText("Show More");
                        }
                        break;

                }
            }
        });

        vm.getProviderDetails(getIntent().getIntExtra(PROVIDER_ID, 0));
        vm.providerDetails.observe(this, new SingleRequestEvent.RequestObserver<ProviderDetails>() {
            @Override
            public void onRequestReceived(@NonNull Resource<ProviderDetails> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                         modelData = resource.data;
                        isFav = modelData.getLike_status();
                         tagId = modelData.getTagId();

                         portfolioList = resource.data.getProviderPortfolio();

                        binding.setProfile(resource.data);
                        if (resource.data.getProviderLanguage().size()>0) {
                            binding.rvLanguage.setVisibility(View.VISIBLE);
                            binding.tvNoLanguage.setVisibility(View.GONE);
                            adapterLanguage.setList(resource.data.getProviderLanguage());
                        }else{
                            binding.rvLanguage.setVisibility(View.GONE);
                            binding.tvNoLanguage.setVisibility(View.VISIBLE);
                        }

                        if (resource.data.getProviderPortfolio().size()>0) {
                            binding.rvPortfolio.setVisibility(View.VISIBLE);
                            binding.tvNoPortfolio.setVisibility(View.GONE);
                           setRecyclerPortfolio(portfolioList,false);
                        }else{
                            binding.rvPortfolio.setVisibility(View.GONE);
                            binding.tvNoPortfolio.setVisibility(View.VISIBLE);
                        }

                        if (resource.data.getProviderPortfolio().size()>2){

                            binding.txtViewMore.setVisibility(View.VISIBLE);

                        }else{

                            binding.txtViewMore.setVisibility(View.GONE);

                        }

                        if (resource.data.getProviderBusinessHours().size()>0) {
                            binding.rvBusinessHour.setVisibility(View.VISIBLE);
                            binding.tvNoBusinessHours.setVisibility(View.GONE);
                            adapterBusinesses.setList(resource.data.getProviderBusinessHours());
                        }else{
                            binding.rvBusinessHour.setVisibility(View.GONE);
                            binding.tvNoBusinessHours.setVisibility(View.VISIBLE);
                        }

                        if (resource.data.getProviderCertificate().size()>0) {
                            binding.rvCertificate.setVisibility(View.VISIBLE);
                            binding.tvNoCertificate.setVisibility(View.GONE);
                            adapterCertificate.setList(resource.data.getProviderCertificate());
                        }else{
                            binding.rvCertificate.setVisibility(View.GONE);
                            binding.tvNoCertificate.setVisibility(View.VISIBLE);
                        }

                        if (resource.data.getProviderReview().size()>0) {
                            binding.rvReviews.setVisibility(View.VISIBLE);
                            binding.tvNoReview.setVisibility(View.GONE);
                            setupReviewAdapter(resource.data.getProviderReview());
                        }else{
                            binding.rvReviews.setVisibility(View.GONE);
                            binding.tvNoReview.setVisibility(View.VISIBLE);
                        }

                        break;
                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        vm.favUnFavEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        if (check) {
                           binding.ivFav.setImageResource(R.drawable.ic_fav_selected);
                        } else {
                            binding.ivFav.setImageResource(R.drawable.ic_fav_unselect);
                        }

                        break;
                    case WARN:
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        setRecyclerView();
        setRecycleCertificate();
        setRecycleBusiness();


    }





    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvLanguage.setLayoutManager(layoutManager);
        binding.rvLanguage.setAdapter(adapterLanguage);
    }

    private void setRecyclerPortfolio(List<ProviderPortfolioBean> list,boolean isAll) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvPortfolio.setLayoutManager(layoutManager);
        CustomPortfolioAdapter portfolioAdapter = new CustomPortfolioAdapter(this,isAll,portfolioList);
        binding.rvPortfolio.setAdapter(portfolioAdapter);
    }



    private void setRecycleCertificate() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCertificate.setLayoutManager(layoutManager);
        binding.rvCertificate.setAdapter(adapterCertificate);
    }

    private void setRecycleBusiness() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvBusinessHour.setLayoutManager(layoutManager);
        binding.rvBusinessHour.setAdapter(adapterBusinesses);
    }

    SimpleRecyclerViewAdapter<ProviderLanguageBean, HolderLanguageItemBinding> adapterLanguage =
            new SimpleRecyclerViewAdapter(R.layout.holder_language_item, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderLanguageBean>() {
                @Override
                public void onItemClick(View v, ProviderLanguageBean o) {

                }
            });


    SimpleRecyclerViewAdapter<ProviderCertificateBean, HolderCertificateItemBinding> adapterCertificate =
            new SimpleRecyclerViewAdapter(R.layout.holder_certificate_item, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderCertificateBean>() {
                @Override
                public void onItemClick(View v, ProviderCertificateBean o) {

                }
            });

    SimpleRecyclerViewAdapter<ProviderBusinessHoursBean, HolderBusinessHourBinding> adapterBusinesses =
            new SimpleRecyclerViewAdapter(R.layout.holder_business_hour, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<ProviderBusinessHoursBean>() {
                @Override
                public void onItemClick(View v, ProviderBusinessHoursBean o) {

                }
            });


    private void setupReviewAdapter(List<ProviderReviewBean> list){

        ReviewAdapter adapter = new ReviewAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rvReviews.setLayoutManager(linearLayoutManager);
        binding.rvReviews.setAdapter(adapter);

    }

}
