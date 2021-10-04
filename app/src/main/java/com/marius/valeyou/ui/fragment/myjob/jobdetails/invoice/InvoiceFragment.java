package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.comission.ComissionModel;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.FragmentInvoiceBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.detail.JobDetailActivity;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment.PaymentActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.misc.BackStackManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class InvoiceFragment extends AppFragment<FragmentInvoiceBinding, InvoiceFragmentVM> {

    private static final String BEAN = "bean";
    private MainActivity mainActivity;
    private JobDetailsBean jobDetailsBean;
    Double total;
    double servicefree;
    int orderId;
    String  price;
    int jobId;

    public static Fragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        InvoiceFragment invoiceFragment = new InvoiceFragment();
        invoiceFragment.setArguments(bundle);
        return invoiceFragment;
    }

    @Override
    protected BindingFragment<InvoiceFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_invoice, InvoiceFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(InvoiceFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        mainActivity.setHeader(getResources().getString(R.string.invoice));
        jobId = getArguments().getInt("id");
        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view!=null?view.getId():0) {
                    case R.id.btn_pay:

                        BackStackManager.getInstance(getActivity()).clear();
                        Intent intent = PaymentActivity.newIntnt(getActivity(),"invoice");
                        intent.putExtra("total",price);
                        intent.putExtra("orderId",jobDetailsBean.getId());
                        intent.putExtra("fee",servicefree+"");
                        intent.putExtra("job_type",jobDetailsBean.getJobType());
                        startNewActivity(intent);

                        break;
                    case R.id.btn_cancel:
                       mainActivity.onBackPressed();
                        break;
                }
            }
        });

        vm.singleRequestEvent.observe(this, new Observer<Resource<ComissionModel>>() {
            @Override
            public void onChanged(Resource<ComissionModel> comissionModelResource) {
                switch (comissionModelResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:

                        dismissProgressDialog();
                        double comission = Double.parseDouble(comissionModelResource.data.getComission());
                        double endprice;


                       if (jobDetailsBean.getBidPrice()==0){

                           endprice = Double.parseDouble(jobDetailsBean.getTotal_amount()+"");

                       }else {

                           if (jobDetailsBean.getNo_access_charges().size()<=0) {

                               endprice = Double.parseDouble(jobDetailsBean.getTotal_amount() + "");

                           }else{

                               if (jobDetailsBean.getNo_access_charges().get(0).getPaid()==0){
                                   endprice = Double.parseDouble(jobDetailsBean.getNo_access_charges().get(0).getPrice() + "");

                               }else{
                                   endprice = Double.parseDouble(jobDetailsBean.getTotal_amount() + "");

                               }



                           }
                       }

                        binding.tvEndPrice.setText(String.format("%.2f", endprice)+" brl");

                        price = String.valueOf(endprice);
                        servicefree = (comission*endprice)/100;
                        binding.tvServiceFee.setText(String.format("%.2f", servicefree)+" brl");
                        total = endprice+servicefree;
                        binding.tvTotal.setText(String.format("%.2f", total)+" brl");
                        orderId = jobDetailsBean.getId();

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(comissionModelResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(comissionModelResource.message);
                        break;
                }
            }
        });

        vm.jobDetailsEvent.observe(this, new SingleRequestEvent.RequestObserver<JobDetailsBean>() {
            @Override
            public void onRequestReceived(@NonNull Resource<JobDetailsBean> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.getComission();
                        binding.setVariable(BR.bean,resource.data);
                        jobDetailsBean = resource.data;

                        String time = resource.data.getStartTime();
                        String convertedTime = AppUtils.getDateTime(Long.parseLong(time));
                        binding.txtTime.setText(convertedTime);

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });


        vm.getJobDetails(jobId);

    }

}
