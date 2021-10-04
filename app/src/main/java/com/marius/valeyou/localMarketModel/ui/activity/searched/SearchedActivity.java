package com.marius.valeyou.localMarketModel.ui.activity.searched;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySearchedBinding;
import com.marius.valeyou.databinding.HolderSearchedBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.responseModel.SearchedModel;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;

public class SearchedActivity extends AppActivity<ActivitySearchedBinding, SearchedActivityVM> {


    public static Intent newIntent(Activity activity) {
        return new Intent(activity, SearchedActivity.class);
    }

    @Override
    protected BindingActivity<SearchedActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_searched, SearchedActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeToEvents(SearchedActivityVM vm) {

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                onBackPressed();
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {


                }
            }
        });


        SimpleRecyclerViewAdapter<SearchedModel.Datum, HolderSearchedBinding> recyclerViewAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_searched, BR.bean,
                new SimpleRecyclerViewAdapter.SimpleCallback<SearchedModel.Datum>() {
                    @Override
                    public void onItemClick(View v, SearchedModel.Datum searchModel) {
                        if (v.getId() == R.id.ll_items) {
                            Intent intent = new Intent();
                            intent.putExtra(Constants.CATEGORY_NAME, searchModel.getKeyword());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });


        binding.rvSearched.setLayoutManager(new LinearLayoutManager(SearchedActivity.this));
        binding.rvSearched.setAdapter(recyclerViewAdapter);

        vm.getSearchedModel();

        vm.searchedModelEvent.observe(this, new SingleRequestEvent.RequestObserver<SearchedModel>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SearchedModel> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        if (resource.data != null) {
                            recyclerViewAdapter.setList(resource.data.getData());
                        } else {
                            binding.rvSearched.setVisibility(View.GONE);
                            binding.txtNoData.setVisibility(View.VISIBLE);
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
                            Intent in = LoginActivity.newIntent(SearchedActivity.this);
                            startNewActivity(in, true, true);

                        }
                        break;
                }
            }
        });

    }
}