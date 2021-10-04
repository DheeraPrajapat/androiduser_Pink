package com.marius.valeyou.util.databinding;

import androidx.databinding.BindingAdapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.di.module.GlideApp;
import com.marius.valeyou.di.module.GlideRequest;
import com.marius.valeyou.util.Constants;

import java.util.List;

public class ImageViewBindingUtils {
 /*   @BindingAdapter(value = {"image_url"}, requireAll = false)
    public static void setImageUrl(final ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }*/

    @BindingAdapter(value = {"profile_url"}, requireAll = false)
    public static void setProfileUrl(final ImageView imageView, String imageUrl) {
        GlideApp.with(imageView.getContext())
                .load(Constants.IMAGE_BASE_URL+imageUrl)
                .placeholder(R.drawable.provider_placeholder)
                .into(imageView);
    }

    @BindingAdapter(value = {"job_image_src"}, requireAll = false)
    public static void setJobImage(final ImageView imageView, List<GetAllJobBean.OrderImagesBean> list) {
        if (list.size()>0) {
            GlideApp.with(imageView.getContext())
                    .load(Constants.IMAGE_BASE_URL+list.get(0).getImages())
                    .placeholder(R.drawable.new_placeholder)
                    .into(imageView);

        }
    }

    @BindingAdapter(value = {"job_detail_image_src"}, requireAll = false)
    public static void setJobDetailImage(final ImageView imageView, List<JobDetailsBean.OrderImagesBean> list) {
        if (list!=null&&list.size()>0) {
            GlideApp.with(imageView.getContext())
                    .load(Constants.IMAGE_BASE_URL+list.get(0).getImages())
                    .placeholder(R.drawable.new_placeholder)
                    .into(imageView);

        }
    }

    @BindingAdapter(value = {"job_image"}, requireAll = false)
    public static void setJobImage(final ImageView imageView, String image) {
            GlideApp.with(imageView.getContext())
                    .load(Constants.IMAGE_BASE_URL+image)
                    .placeholder(R.drawable.new_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);


    }

    @BindingAdapter(value = {"provider_image"}, requireAll = false)
    public static void setProviderImage(final ImageView imageView, String image) {
        GlideApp.with(imageView.getContext())
                .load(Constants.IMAGE_BASE_URL+image)
                .placeholder(R.drawable.provider_placeholder)
                .into(imageView);


    }

    @BindingAdapter(value = {"profile_image"}, requireAll = false)
    public static void setProfileImage(final ImageView imageView, String image) {
        GlideApp.with(imageView.getContext())
                .load(Constants.IMAGE_BASE_URL+image)
                .placeholder(R.drawable.new_placeholder)
                .into(imageView);


    }

    @BindingAdapter(value = {"image_url", "resize", "placeholder"}, requireAll = false)
    public static void setImageUrl(final ImageView imageView, String image_url, boolean resize, Drawable placeholder) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(Constants.IMAGE_BASE_URL + image_url);
        if (resize)
            requests.override(120, 100);
        if (placeholder != null)
            requests.placeholder(placeholder);
        requests.centerCrop();
        requests.into(imageView);
    }

    @BindingAdapter(value = {"rectangle", "view_width", "view_height", "placeholder"}, requireAll = false)
    public static void rectangle(final ImageView imageView, String image_url, Integer view_width, Integer view_height, Drawable placeholder) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        if (view_width != null && view_height != null)
            options.override(view_width, view_height);
        if (placeholder != null)
            options.placeholder(placeholder);
        GlideApp.with(imageView.getContext()).load(image_url).apply(options).into(imageView);
    }


    @BindingAdapter(value = {"square", "placeholder"}, requireAll = false)
    public static void setSqure(final ImageView imageView, String image_url, Drawable placeholder) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        requests.override(120);
        if (placeholder != null)
            requests.placeholder(placeholder);
        requests.centerCrop();
        requests.into(imageView);
    }

    @BindingAdapter(value = {"android:view_image"})
    public static void setImageUrl(final ImageView imageView, String image_url) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(Constants.IMAGE_BASE_URL+image_url).fitCenter();
       requests.placeholder(R.drawable.new_placeholder);
        requests.into(imageView);
    }




/*
    @BindingAdapter(value = {"profile_url"}, requireAll = false)
    public static void setProfileUrl(final ImageView imageView, String image_url) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load("" + image_url);
        requests.placeholder(R.drawable.ic_dummy_profile);
        requests.circleCrop();
        requests.override(150);
        requests.into(imageView);
    }*/


    @BindingAdapter(value = {"simpleResourse"})
    public static void setStepGroupIcon(final ImageView imageView, int simpleResourse) {

        if (simpleResourse != -1) {
            imageView.setImageResource(simpleResourse);

        }
    }




  /*  @BindingAdapter(value = {"request_status", "is_read"})
    public static void setMeetIcon(final ImageView imageView, Integer status, Integer read) {

        if (status == null || read == null) {
            imageView.setVisibility(View.GONE);
            return;
        }
        imageView.setVisibility(View.VISIBLE);
        if (status == 1) {
            imageView.setImageResource(R.drawable.ic_two_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.green), PorterDuff.Mode.SRC_IN);
        } else if (read == 1) {
            imageView.setImageResource(R.drawable.ic_two_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.dim_black), PorterDuff.Mode.SRC_IN);
        } else {
            imageView.setImageResource(R.drawable.ic_one_check);
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.dim_black), PorterDuff.Mode.SRC_IN);
        }

    }
*/

    @BindingAdapter(value = {"chat_thumbnil", "corner"}, requireAll = false)
    public static void setThumnilUrl(final ImageView imageView, String image_url, String message) {
        GlideRequest<Drawable> requests = GlideApp.with(imageView.getContext()).load(image_url);
        requests.centerCrop();
     /*   if (message != null && message.length() > 0)
            requests.transform(new RoundedCornersTransformation(AppUtils.convertDpToPixel(20), AppUtils.convertDpToPixel(5), RoundedCornersTransformation.CornerType.TOP));
        else
            requests.transform(new RoundedCornersTransformation(AppUtils.convertDpToPixel(20), AppUtils.convertDpToPixel(5)));*/
        requests.into(imageView);
    }

    @BindingAdapter("android:rating")
    public static void setRating(RatingBar view, String rating) {
        if (view != null) {
            float rate = Float.parseFloat(rating);
            view.setRating(rate);
        }
    }

    @BindingAdapter(value = {"image_path_url"}, requireAll = false)
    public static void setImagePathUrl(final ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl.startsWith("file://") ? imageUrl : "file://" + imageUrl)
                .override(150,150)
                .into(imageView);
    }

}
