package com.marius.valeyou.ui.fragment.more.profile.selectlanguage;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.databinding.ActivitySelectLanguageBinding;
import com.marius.valeyou.databinding.HoldetSelectLanguageBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SelectLanguageActivity extends AppActivity<ActivitySelectLanguageBinding, SelectLanguageActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, SelectLanguageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<SelectLanguageActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_select_language, SelectLanguageActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(SelectLanguageActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.languages));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });
        seytRecyclerViewForLanguage();
        seytRecyclerViewForProficiency();
        adapter.setList(getData());
    }

    private List<MoreBean> getData() {
        List<MoreBean> beanList = new ArrayList<>();
        beanList.add(new MoreBean(1,"",1));
        beanList.add(new MoreBean(1,"",1));
        beanList.add(new MoreBean(1,"",1));
        return beanList;
    }

    private void seytRecyclerViewForLanguage() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvLanguage.setLayoutManager(layoutManager);
        binding.rvLanguage.setAdapter(adapter);
    }

    private void seytRecyclerViewForProficiency() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvProficiency.setLayoutManager(layoutManager);
        binding.rvProficiency.setAdapter(adapter);
    }

    SimpleRecyclerViewAdapter<MoreBean, HoldetSelectLanguageBinding> adapter
            = new SimpleRecyclerViewAdapter(R.layout.holdet_select_language, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
        @Override
        public void onItemClick(View v, MoreBean o) {

        }
    });

}
