package com.marius.valeyou.ui.fragment.more.profile.addportfolio;

import android.app.Activity;
import android.content.Intent;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityAddPortfolioBinding;
import com.marius.valeyou.di.base.view.AppActivity;

import androidx.lifecycle.Observer;

public class AddPortfolioActivity extends AppActivity<ActivityAddPortfolioBinding,AddPortfolioActivityVM> {
    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity,AddPortfolioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<AddPortfolioActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_add_portfolio,AddPortfolioActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(AddPortfolioActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.portfolio));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });
    }
}
