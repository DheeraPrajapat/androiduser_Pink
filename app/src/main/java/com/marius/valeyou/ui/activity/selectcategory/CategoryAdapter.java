package com.marius.valeyou.ui.activity.selectcategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.databinding.DialogSetPriceBinding;
import com.marius.valeyou.databinding.HolderCategoryListBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductTitleHolder> {

    private List<CategoryListBean> moreBeans;
    private ProductCallback mcallback;
    private Context baseContext;

    public CategoryAdapter(Context baseContext, ProductCallback callback) {
        this.baseContext = baseContext;
        this.mcallback = callback;
    }

    public interface ProductCallback {
        void onItemClick(View v, List<CategoryListBean> m,int position);
    }

    @NonNull
    @Override
    public ProductTitleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderCategoryListBinding holderProductTitleBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.holder_category_list, parent, false);
        holderProductTitleBinding.setVariable(BR.callback, mcallback);
        return new ProductTitleHolder(holderProductTitleBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTitleHolder holder, int position) {
        CategoryListBean moreBean = moreBeans.get(position);
        holder.holderProductTitleBinding.setBean(moreBean);
        holder.holderProductTitleBinding.setPosition(position);
        holder.holderProductTitleBinding.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcallback.onItemClick(view,moreBeans,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (moreBeans != null) {
            return moreBeans.size();
        } else {
            return 0;
        }
    }

    public void setProductList(List<CategoryListBean> moreBeans) {
        this.moreBeans = moreBeans;
        notifyDataSetChanged();
    }

    public List<CategoryListBean> getListData() {
        return moreBeans;
    }

    public class ProductTitleHolder extends RecyclerView.ViewHolder {
        private HolderCategoryListBinding holderProductTitleBinding;

        public ProductTitleHolder(@NonNull HolderCategoryListBinding itemView) {
            super(itemView.getRoot());
            this.holderProductTitleBinding = itemView;

          /*  holderProductTitleBinding.rvSubCategory.setLayoutManager(new LinearLayoutManager(baseContext));
            adapter = new SimpleRecyclerViewAdapter<>(R.layout.holder_subcategory_list, BR.bean, new SimpleRecyclerViewAdapter.SimpleCallback<SubCategoryBean>() {
                @Override
                public void onItemClick(View v, SubCategoryBean moreBean) {
                    int j = getAdapterPosition();
                    //openDialog(j, moreBean, ((SelectCategoryActivity) baseContext).category_type);
                    String price = "101";
                    List<SubCategoryBean> subCategories = moreBeans.get(j).getSubCategories();
                    for (int i = 0; i < subCategories.size(); i++) {
                        if (subCategories.get(i).getId() == moreBean.getId()) {

                            if (subCategories.get(i).isCheck()){
                                subCategories.get(i).setCheck(false);
                            }else {
                                subCategories.get(i).setCheck(true);
                            }
                            subCategories.get(i).setPrice(price);
                        }
                    }
                    moreBeans.get(j).setCheck(true);
                    moreBeans.get(j).setSubCategories(subCategories);

                    notifyDataSetChanged();

                }
            });
            holderProductTitleBinding.rvSubCategory.setAdapter(adapter);*/
        }
    }

    private BaseCustomDialog<DialogSetPriceBinding> dialogSuccess;

    private void openDialog(int j, SubCategoryBean bean, String category_type) {
        dialogSuccess = new BaseCustomDialog<>(baseContext, R.layout.dialog_set_price, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:

                            if (!dialogSuccess.getBinding().etPrice.getText().toString().trim().equalsIgnoreCase("")) {
                                String price = dialogSuccess.getBinding().etPrice.getText().toString();


                                List<SubCategoryBean> subCategories = moreBeans.get(j).getSubCategories();
                                for (int i = 0; i < subCategories.size(); i++) {
                                    if (subCategories.get(i).getId() == bean.getId()) {
                                        subCategories.get(i).setCheck(true);
                                        subCategories.get(i).setPrice(price);
                                    }
                                }
                                moreBeans.get(j).setCheck(true);
                                moreBeans.get(j).setSubCategories(subCategories);
                                dialogSuccess.dismiss();
                            }
                            notifyDataSetChanged();
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
        if (category_type.equalsIgnoreCase("0")) {
            dialogSuccess.getBinding().tvTitle.setText("Set fixed price");
        } else {
            dialogSuccess.getBinding().tvTitle.setText("Set price per hour");
        }
        dialogSuccess.show();
    }

}
