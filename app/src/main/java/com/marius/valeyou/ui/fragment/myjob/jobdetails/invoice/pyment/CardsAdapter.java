package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.databinding.HolderCvvBinding;
import com.marius.valeyou.databinding.HolderDeleteCardBinding;
import com.marius.valeyou.databinding.HolderSavedCardBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

class CardsAdaptere extends RecyclerView.Adapter<CardsAdaptere.MyViewHolder> {

    List<GetCardBean> list;
    Context baseContext;
    private int selectedPosition = -1;
    OnClickItem onClickItem;

    public CardsAdaptere(Context baseContext,  List<GetCardBean> list,CardsAdaptere.OnClickItem onClickItem) {
        this.baseContext = baseContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }


    public interface OnClickItem{
        void onClickItemClickListner(int position,List<GetCardBean> morebeanList);
        void onDeleteClickLisnter(int position,List<GetCardBean> morebeanList,int card_id);
    }
    @NonNull
    @Override
    public CardsAdaptere.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderSavedCardBinding holderSavedCardBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.holder_saved_card, parent, false);
        return new MyViewHolder(holderSavedCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdaptere.MyViewHolder holder, int position) {

        GetCardBean moreBean = list.get(position);
        holder.holderSavedCardBinding.setVariable(BR.bean,moreBean);

        if (selectedPosition == position) {
            holder.holderSavedCardBinding.rbSelect.setChecked(true);
        }else{
            holder.holderSavedCardBinding.rbSelect.setChecked(false);
        }

        holder.holderSavedCardBinding.cardSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cvvDialog(holder.getAdapterPosition());

            }
        });

        holder.holderSavedCardBinding.rbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.holderSavedCardBinding.rbSelect.setChecked(false);
                cvvDialog(holder.getAdapterPosition());
            }
        });

        holder.holderSavedCardBinding.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deledeDialog(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        HolderSavedCardBinding holderSavedCardBinding;
        public MyViewHolder(@NonNull HolderSavedCardBinding itemView) {
            super(itemView.getRoot());
            this.holderSavedCardBinding = itemView;
        }
    }

    private BaseCustomDialog<HolderCvvBinding> dialogsetCVV;
    private void cvvDialog(int postion) {
        dialogsetCVV = new BaseCustomDialog<>(baseContext, R.layout.holder_cvv, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:

                            if (dialogsetCVV.getBinding().tvTwo.getText().toString().isEmpty()){

                                Toast.makeText(baseContext,"Please enter cvv",Toast.LENGTH_SHORT);

                            }else {

                                dialogsetCVV.dismiss();
                                list.get(postion).setCvv(dialogsetCVV.getBinding().tvTwo.getText().toString());
                                if (selectedPosition >= 0) {
                                    notifyItemChanged(selectedPosition);
                                }
                                selectedPosition = postion;
                                onClickItem.onClickItemClickListner(postion, list);
                                notifyItemChanged(selectedPosition);

                            }

                            break;

                        case R.id.btn_cancel:
                            dialogsetCVV.dismiss();
                            break;
                    }
                }
            }
        });

        dialogsetCVV.setCancelable(false);
        dialogsetCVV.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogsetCVV.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogsetCVV.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogsetCVV.show();

    }

    private BaseCustomDialog<HolderDeleteCardBinding> deleteCardDialog;
    private void deledeDialog(int postion) {
        deleteCardDialog = new BaseCustomDialog<>(baseContext, R.layout.holder_delete_card, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            deleteCardDialog.dismiss();

                            onClickItem.onDeleteClickLisnter(postion,list,list.get(postion).getId());

                            break;

                        case R.id.btn_cancel:
                            deleteCardDialog.dismiss();
                            break;
                    }
                }
            }
        });

        deleteCardDialog.setCancelable(false);
        deleteCardDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        deleteCardDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        deleteCardDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        deleteCardDialog.show();

    }


}
