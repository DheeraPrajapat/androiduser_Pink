package com.marius.valeyou.ui.activity.block;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
 import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.block.BlockModel;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityBlockBinding;
import com.marius.valeyou.databinding.HolderBlockAccountBinding;
import com.marius.valeyou.di.base.adapter.SimpleRecyclerViewAdapter;
import com.marius.valeyou.di.base.view.AppActivity;

import java.util.ArrayList;
import java.util.List;

public class BlockActivity extends AppActivity<ActivityBlockBinding,BlockActivityVM> {

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, BlockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @Override
    protected BindingActivity<BlockActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_block, BlockActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(BlockActivityVM vm) {

        binding.header.tvTitle.setText(getResources().getString(R.string.blocked_contacts));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        binding.header.imgBack.setVisibility(View.VISIBLE);

        vm.blockList();

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view.getId()){

                }
            }
        });

        vm.blockUnblockEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        Toast.makeText(BlockActivity.this, ""+simpleApiResponseResource.message, Toast.LENGTH_SHORT).show();

                        vm.blockList();

                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });


        vm.blockListEvent.observe(this, new Observer<Resource<List<BlockModel>>>() {
            @Override
            public void onChanged(Resource<List<BlockModel>> listResource) {
                switch (listResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();

                        List<BlockModel> list = listResource.data;

                        if (list.size()>0) {
                            adapter.setList(list);
                            binding.rvBlockList.setVisibility(View.VISIBLE);
                            binding.noData.setVisibility(View.GONE);
                        }else{
                            binding.rvBlockList.setVisibility(View.GONE);
                            binding.noData.setVisibility(View.VISIBLE);
                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(listResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(listResource.message);
                        break;
                }
            }
        });

        setRecyclerView();

    }
    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(BlockActivity.this);
        binding.rvBlockList.setLayoutManager(layoutManager);
        binding.rvBlockList.setAdapter(adapter);
    }


    SimpleRecyclerViewAdapter<BlockModel, HolderBlockAccountBinding> adapter = new SimpleRecyclerViewAdapter(R.layout.holder_block_contacts, BR.bean,
            new SimpleRecyclerViewAdapter.SimpleCallback<BlockModel>() {
        @Override
        public void onItemClick(View v, BlockModel o) {
           switch (v.getId()){
               case R.id.unblockBtn:
                    viewModel.blockUnblock(String.valueOf(o.getProvider().getId()),"0");
                   break;
           }

        }

    });


}