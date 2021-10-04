package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.ActivityPaymentSuccessBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.main.MainActivity;

import androidx.lifecycle.Observer;

public class PaymentSuccessActivity extends AppActivity<ActivityPaymentSuccessBinding, PaymentSuccessActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity,PaymentSuccessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<PaymentSuccessActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_payment_success, PaymentSuccessActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(PaymentSuccessActivityVM vm) {

        binding.header.tvTitle.setText("Payment");
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0) {
                    case R.id.btn_my_jobs:
                        Intent intent = MainActivity.newIntent(PaymentSuccessActivity.this,"payment");
                        startNewActivity(intent);
                        finishAffinity();
                        break;
                }
            }
        });

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

    }
}
