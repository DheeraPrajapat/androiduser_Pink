package com.marius.valeyou.ui.fragment.more.helpnsupport.faq;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.FaqBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityFaqBinding;
import com.marius.valeyou.databinding.HolderFaqItemsBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class FAQActivity extends AppActivity<ActivityFaqBinding, FAQActivityVM> {

    private List<FaqBean> faqBeans;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, FAQActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<FAQActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_faq, FAQActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(FAQActivityVM vm) {
        binding.header.tvTitle.setText("FAQ");
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        vm.getFAQ();
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });
        vm.singleRequestEvent.observe(this, new SingleRequestEvent.RequestObserver<List<FaqBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<FaqBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        faqBeans = resource.data;
                        adapter.setList(faqBeans);
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
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvFaq.setLayoutManager(layoutManager);
        binding.rvFaq.setAdapter(adapter);
        if (faqBeans != null)
            adapter.setList(faqBeans);
    }

    SimpleRecyclerViewAdapter<FaqBean, HolderFaqItemsBinding> adapter =
            new SimpleRecyclerViewAdapter<>(R.layout.holder_faq_items, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<FaqBean>() {
                @Override
                public void onItemClick(View v, FaqBean bean) {
                    for (int j = 0; j < faqBeans.size(); j++) {
                        if (faqBeans.get(j).getId() == bean.getId()){
                            if (faqBeans.get(j).isCheck()){
                                faqBeans.get(j).setCheck(false);
                            } else {
                                faqBeans.get(j).setCheck(true);
                            }
                        } else {
                            faqBeans.get(j).setCheck(false);
                        }
                    }
                    adapter.setList(faqBeans);
                }
            });

}
