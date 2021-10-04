package com.marius.valeyou.localMarketModel.ui.fragment.userPost;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.HolderViewImageBinding;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.PostImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    List<PostImage> somethingImages;

    public ViewPagerAdapter(List<PostImage> somethingImages) {
        this.somethingImages = somethingImages;
    }

    public interface SimpleCallback<SomethingImages> {
        void onItemClick(View v, SomethingImages somethingImages);
    }

    @Override
    public int getCount() {
        return somethingImages.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {
        HolderViewImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.holder_view_image, container, false);
        PostImage model = somethingImages.get(position);
        binding.setBean(model);

        ViewPager vp = (ViewPager) container;
        vp.addView(binding.getRoot(), 0);

        Log.d("ViewPagerAdapter", "instantiateItem: " + model.getImage() + ",  Type: " + model.getImage());

        return binding.getRoot();

    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}