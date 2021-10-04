package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityCardDetailsBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.carddetails.paymentsuccess.PaymentSuccessActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.stripe.android.model.Card;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public class PaymentCardDetails extends AppActivity<ActivityCardDetailsBinding, PaymentCardDetailsVM> {

    Calendar today = Calendar.getInstance();
    int orderID;
    String amount;
    String servicefee;
    int jobtype;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, PaymentCardDetails.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<PaymentCardDetailsVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_card_details, PaymentCardDetailsVM.class);
    }

    @Override
    protected void subscribeToEvents(PaymentCardDetailsVM vm) {

        binding.header.tvTitle.setText(getResources().getString(R.string.payments));
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

                            if (isEmpty()) {

                                vm.addCard(Long.parseLong(binding.etCardNumber.getText().toString()),
                                        Integer.parseInt(binding.etMonth.getText().toString()),
                                        Integer.parseInt(binding.etYear.getText().toString()),
                                        binding.etCvv.getText().toString(),
                                        binding.etHolderName.getText().toString()
                                );

                            }

                        break;

                    case R.id.btn_cancel:
                        finish();
                        break;

                    case R.id.et_month:
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(PaymentCardDetails.this,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        binding.etMonth.setText((selectedMonth + 1) + "");
                                    }
                                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
                        );
                        builder.showMonthOnly().build().show();
                        break;
                    case R.id.et_year:
                        MonthPickerDialog.Builder builder1 = new MonthPickerDialog.Builder(PaymentCardDetails.this,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        binding.etYear.setText(selectedYear + "");
                                    }
                                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
                        );
                        builder1.setMaxYear(2030).showYearOnly().build().show();
                        break;
                }
            }
        });

        vm.addCardEvent.observe(this, new SingleRequestEvent.RequestObserver<SimpleApiResponse>() {
            @Override
            public void onRequestReceived(@NonNull Resource<SimpleApiResponse> resource) {
                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        vm.success.setValue(resource.message);
                        onBackPressed();
                        break;
                    case WARN:
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

    }


    private boolean isEmpty() {
        if (TextUtils.isEmpty(binding.etHolderName.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etCardNumber.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etMonth.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etYear.getText().toString().trim()))
            return false;
        if (TextUtils.isEmpty(binding.etCvv.getText().toString().trim()))
            return false;
        if (binding.etCardNumber.length() < 16) {
            viewModel.error.setValue(getResources().getString(R.string.card_number_not_valid));
            return false;
        }

        return true;
    }

}
