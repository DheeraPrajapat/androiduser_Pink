package com.marius.valeyou.localMarketModel.ui.fragment.createAd;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marius.valeyou.R;
import com.marius.valeyou.data.remote.helper.ApiUtils;
import com.marius.valeyou.localMarketModel.ui.fragment.home.HomeFragmentMarket;
import com.marius.valeyou.util.AppUtils;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {

    private CompanyAdapter.OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    private List<CompanyListModel> company_list;
    Context context;
    boolean check = false;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private int lastCheckedPosition = 0;
    private int mSelectedItem = 0;
    HomeFragmentMarket homeFragmentMarket;

    public CompanyAdapter(HomeFragmentMarket homeFragmentMarket, List<CompanyListModel> company_list, OnItemClickListener onItemClickListener) {
        this.company_list = company_list;
        this.homeFragmentMarket = homeFragmentMarket;
        this.onItemClickListener = onItemClickListener;
        this.context = homeFragmentMarket.requireActivity();
    }

    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_companies_name, null);


        return new CompanyAdapter.ViewHolder(v);
    }


    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder viewHolder, final int i) {
        if (company_list.get(i).getComapny_name() != null) {
            String name = AppUtils.capitalize(company_list.get(i).getComapny_name());
            viewHolder.radio.setText(name);
        }

        viewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));

        viewHolder.radio.setChecked(i == 0);

        if (mSelectedItem == 0) {
            if (company_list.size() > 0)
                setData(0);
        }
        viewHolder.radio.setChecked(i == mSelectedItem); // from this you only get one radio button selected in recycler view


//        viewHolder.radio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lastCheckedPosition = i;
//                setData(i);
//                notifyItemChanged(lastCheckedPosition);
//            }
//        });


    }

    public void setData(int position) {
        homeFragmentMarket.shop_id = company_list.get(position).getShop_id();
        homeFragmentMarket.phone = company_list.get(position).getPhone();
        homeFragmentMarket.address = company_list.get(position).getAddress();
        homeFragmentMarket.lati = company_list.get(position).getLatitude();
        homeFragmentMarket.longi = company_list.get(position).getLongitude();
        homeFragmentMarket.selected_category = company_list.get(position).getCategory();
        homeFragmentMarket.country_code = company_list.get(position).getCountry_code();
    }


    @Override
    public int getItemCount() {
        return company_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RadioButton radio;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radio = itemView.findViewById(R.id.radio);
            // use from here*********************
            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, company_list.size());
                    setData(mSelectedItem);
                }
            };

            itemView.setOnClickListener(l);
            radio.setOnClickListener(l);
            // till here************************
        }


    }

    public void removeAt(int position) {

    }
}
