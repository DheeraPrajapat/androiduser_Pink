package com.marius.valeyou.localMarketModel.ui.activity.allCategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityAllCategoryBinding;
import com.marius.valeyou.databinding.HolderAllCategoryBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.util.Constants;

public class AllCategoryActivity extends AppActivity<ActivityAllCategoryBinding, AllCategoryActivityVM> {

    public MarketCategoryModel categoryListBeans;

    public static Intent newIntent(Activity activity, MarketCategoryModel categoryBeans) {
        Intent intent = new Intent(activity, AllCategoryActivity.class);
        intent.putExtra("categoryListBeans", new Gson().toJson(categoryBeans));
        return intent;
    }

    @Override
    protected BindingActivity<AllCategoryActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_all_category, AllCategoryActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeToEvents(AllCategoryActivityVM vm) {

        categoryListBeans = new Gson().fromJson(this.getIntent().getStringExtra("categoryListBeans"), MarketCategoryModel.class);

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

        SimpleRecyclerViewAdapter<MarketCategoryModel.Datum, HolderAllCategoryBinding> recyclerViewAdapter = new SimpleRecyclerViewAdapter(R.layout.holder_all_category, BR.bean,
                new SimpleRecyclerViewAdapter.SimpleCallback<MarketCategoryModel.Datum>() {
                    @Override
                    public void onItemClick(View v, MarketCategoryModel.Datum category) {
                        switch (v != null ? v.getId() : 0) {
                            case R.id.holderItem:
                                Intent intent = new Intent();
                                intent.putExtra(Constants.CATEGORY_NAME, category.getName());
                                setResult(RESULT_OK, intent);
                                finish();
                                break;
                        }
                    }
                });

        if(categoryListBeans.getData().size()>0){
            recyclerViewAdapter.setList(categoryListBeans.getData());
        }

        binding.rvCommonList.setLayoutManager(new LinearLayoutManager(AllCategoryActivity.this));
        binding.rvCommonList.setAdapter(recyclerViewAdapter);


    }
}