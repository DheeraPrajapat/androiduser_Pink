package com.marius.valeyou.ui.fragment.myjob.upcoming;

import android.view.View;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.MoreBean;
import com.marius.valeyou.databinding.FragmentUpcomingJobBinding;
import com.marius.valeyou.databinding.HolderUpcomingJobBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.fragment.myjob.MyJobFragment;
import com.marius.valeyou.ui.fragment.myjob.jobdetails.JobDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class UpComingJobFragment extends AppFragment<FragmentUpcomingJobBinding,UpComingJobFragmentVM> {

    private MainActivity mainActivity;
    private String SUB_TAG= MyJobFragment.TAG;

    public static Fragment newInstance() {
        return new UpComingJobFragment();
    }

    @Override
    protected BindingFragment<UpComingJobFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_upcoming_job,UpComingJobFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(UpComingJobFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        setRecyclerView();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvUpcoming.setLayoutManager(layoutManager);
        //binding.rvUpcoming.setAdapter(adapter);
        //adapter.setList(getList());
    }

    private List<MoreBean> getList() {
        List<MoreBean> moreBeans = new ArrayList<>();

        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));
        moreBeans.add(new MoreBean(1,"",1));

        return moreBeans;
    }

   /* SimpleRecyclerViewAdapter<MoreBean, HolderUpcomingJobBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_upcoming_job, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<MoreBean>() {
        @Override
        public void onItemClick(View v, MoreBean o) {
            mainActivity.addSubFragment(SUB_TAG, JobDetailsFragment.newInstance());
        }
    });*/

}
