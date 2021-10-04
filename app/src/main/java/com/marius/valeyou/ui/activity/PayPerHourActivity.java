package com.marius.valeyou.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityPayPerHourBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.util.misc.BackStackManager;

import androidx.lifecycle.Observer;

public class PayPerHourActivity extends AppActivity<ActivityPayPerHourBinding, PayPerHourActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, PayPerHourActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<PayPerHourActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_pay_per_hour, PayPerHourActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(PayPerHourActivityVM vm) {
        binding.header.tvTitle.setText("Pay Details");
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
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
                    case R.id.btn_pay:
                        Intent intent = PaymentActivity.newIntnt(PayPerHourActivity.this,"payPerHour");
                        startNewActivity(intent);
                        break;
                    case R.id.btn_cancel:
                        finish(true);
                        break;
                }
            }
        });
    }
}
