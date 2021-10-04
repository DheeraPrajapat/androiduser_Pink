package com.marius.valeyou.ui.activity.selectcategory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.reqbean.RequestBean;
import com.marius.valeyou.data.beans.reqbean.RequestSubCategory;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivitySelectCategoryBinding;
import com.marius.valeyou.databinding.DialogSetPriceBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.ui.fragment.SubCatAdapter;
import com.marius.valeyou.ui.fragment.home.HomeFragment;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.JunkRemovalJobFragment;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectCategoryActivity extends AppActivity<ActivitySelectCategoryBinding, SelectCategoryActivityVM> {

   int providerid;
    String comeFrom;
    public static Intent newIntent(Activity activity,int provider_id,String comefrom) {
        Intent intent = new Intent(activity, SelectCategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("provider_id",provider_id);
        intent.putExtra("comeFrom",comefrom);
        return intent;
    }

    private List<RequestBean> selected = new ArrayList<>();
    public String category_type = "0";

    @Override
    protected BindingActivity<SelectCategoryActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_select_category, SelectCategoryActivityVM.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    @Override
    protected void subscribeToEvents(SelectCategoryActivityVM vm) {
        binding.header.tvTitle.setText(R.string.select_category);
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        providerid = getIntent().getIntExtra("provider_id",0);
        comeFrom = getIntent().getStringExtra("comeFrom");
        if (comeFrom.equalsIgnoreCase("provider_profile")){

            vm.getListOfProviderCategories(providerid);
            binding.llTopView.setVisibility(View.GONE);

        }else {

            vm.getListOfCateegory(0, "");

        }
        setActionOnCatSearch();
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

                    case R.id.cv_remote_job:
                        binding.setCheck(false);
                        vm.getListOfCateegory(0, "");
                        break;
                    case R.id.cv_local_job:
                        vm.getListOfCateegory(1, "");
                        binding.setCheck(true);
                        break;

                    case R.id.btn_next:

                        break;
                }
            }
        });

        vm.categoryList.observe(this, new SingleRequestEvent.RequestObserver<List<CategoryListBean>>() {
            @Override
            public void onRequestReceived(@NonNull Resource<List<CategoryListBean>> resource) {
                switch (resource.status) {
                    case LOADING:
                        binding.setLoading(true);
                        break;
                    case SUCCESS:
                        binding.setLoading(false);
                        moreBeans = resource.data;
                        categoryAdapter.setProductList(moreBeans);
                        break;
                    case WARN:
                        binding.setLoading(false);
                        vm.warn.setValue(resource.message);
                        break;
                    case ERROR:
                        binding.setLoading(false);
                        vm.error.setValue(resource.message);
                        break;
                }
            }
        });

        setRecyclerView();
    }

    private CategoryAdapter categoryAdapter;
    private List<CategoryListBean> moreBeans;

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.rvCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, new CategoryAdapter.ProductCallback() {
            @Override
            public void onItemClick(View v, List<CategoryListBean> m, int position) {
                if (m.get(position).getType()==0) {

                    if (m.get(position).getSubCategories().size()>0) {

                        showDialog(SelectCategoryActivity.this, m, position);

                    }else{
                        RequestBean requestBean = new RequestBean();
                        List<RequestSubCategory> subList= new ArrayList<>();
                        requestBean.setCategory_id(String.valueOf(m.get(position).getId()));
                        requestBean.setSubcategory(subList);
                        selected.add(requestBean);
                        String json = new Gson().toJson(selected);

                        Intent intent = JunkRemovalJobFragment.newInstance(SelectCategoryActivity.this,
                                json, category_type, "", providerid, String.valueOf(m.get(position).getType()));
                        startNewActivity(intent);
                    }

                }else{

                    RequestBean requestBean = new RequestBean();
                    List<RequestSubCategory> subList= new ArrayList<>();
                    requestBean.setCategory_id(String.valueOf(m.get(position).getId()));
                    requestBean.setSubcategory(subList);
                    selected.add(requestBean);
                    String json = new Gson().toJson(selected);

                    Intent intent = JunkRemovalJobFragment.newInstance(SelectCategoryActivity.this,
                                    json, category_type, "", getIntent().getIntExtra("provider_id", 0), String.valueOf(m.get(position).getType()));
                            startNewActivity(intent);



                }

            }
        });
        binding.rvCategory.setAdapter(categoryAdapter);
        if (moreBeans != null)
            categoryAdapter.setProductList(moreBeans);
    }

    private BaseCustomDialog<DialogSetPriceBinding> dialogSuccess;

    private void openDialog() {
        dialogSuccess = new BaseCustomDialog<>(SelectCategoryActivity.this, R.layout.dialog_set_price, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            dialogSuccess.dismiss();
                            break;
                        case R.id.iv_cancel:
                            dialogSuccess.dismiss();
                            break;
                    }
                }
            }
        });
        dialogSuccess.getWindow().setBackgroundDrawableResource(R.color.transparance_whhite);
        dialogSuccess.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogSuccess.show();
    }

    private void setActionOnCatSearch() {
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(binding.etSearch.getWindowToken(), 0);
                    viewModel.getListOfCateegory(Integer.valueOf(category_type), binding.etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }


    public void showDialog(Activity activity, List<CategoryListBean> categoryListBeans,int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sub_cat_custom_layout);

        RecyclerView recycler_view = dialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        SubCatAdapter subCatAdapter = new SubCatAdapter(activity, categoryListBeans.get(position).getSubCategories());
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(subCatAdapter);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button done = dialog.findViewById(R.id.done);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBean requestBean = new RequestBean();
                List<RequestSubCategory> subcategory = new ArrayList<>();
                requestBean.setCategory_id(String.valueOf(categoryListBeans.get(position).getId()));
                for (int i = 0; i < categoryListBeans.get(position).getSubCategories().size();i++){
                    if (categoryListBeans.get(position).getSubCategories().get(i).isCheck()){
                        subcategory.add(new RequestSubCategory(String.valueOf(categoryListBeans.get(position).getSubCategories().get(i).getId()),categoryListBeans.get(position).getSubCategories().get(i).getPrice()));
                    }
                    requestBean.setSubcategory(subcategory);
                }

                selected.add(requestBean);

                if (subcategory.size()>0) {
                    String json = new Gson().toJson(selected);

                    Intent intent = JunkRemovalJobFragment.newInstance(SelectCategoryActivity.this,
                            json, category_type, "",providerid,String.valueOf(categoryListBeans.get(position).getType()));
                    startNewActivity(intent);


                    dialog.cancel();

                }else{

                    viewModel.info.setValue(getResources().getString(R.string.plsease_select_atleast_one_category));

                }


        }
        });

        dialog.show();
        }

        }
