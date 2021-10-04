package com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Gallary_Pics_Get_Set;
import com.marius.valeyou.market_place.Utils.My_Click;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details.after_one_pic_RL;
import static com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details.camera_RL;
import static com.marius.valeyou.market_place.Drawer.Home.PostAd.Ad_Details.num_images;

public class Select_Pic_Adp extends RecyclerView.Adapter<Select_Pic_Adp.ViewHolder> {
    Ad_Details myActivity;
    private SendDataToFragment sendDataToFragment;
    My_Click ml;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallary_select_pic,null);
        return new ViewHolder(v);
    }

    //Interface to send data from adapter to fragment
    public interface SendDataToFragment {
        void sendData(String Data);
    }

   private List<Gallary_Pics_Get_Set> Pics_list;
//    private List<CategoryModel> Category_list_Filtered;
    Context context;
    TextView text;
    public Select_Pic_Adp(List<Gallary_Pics_Get_Set> Pics_list, Context context, TextView text) {
       this.Pics_list=Pics_list;
        this.context = context;
        this.text = text;

    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Gallary_Pics_Get_Set pics = Pics_list.get(i);

            if(pics.getImage_Uri().toString().startsWith("http")){
                // If Image is Link

//                String image_name_from_url = Methods.getFileNameFromURL (API_LINKS.BASE_URL + pics.getImage_Uri());
//                Methods.toast_msg(context,"File name " + image_name_from_url );
//
//                String image_150_thumb = pics.getImage_Uri().toString().replace(image_name_from_url ,
//                        Variables.Var_thumb_prefix +  image_name_from_url
//                );
//                Methods.toast_msg(context," File name thumb " + image_150_thumb );

                Picasso.get()
                        .load(pics.getImage_Uri())
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(viewHolder.imageView);

            }else{

                //viewHolder.imageView.setImageURI(pics.getImage_Uri());

                Picasso.get()
                        .load(API_LINKS.BASE_URL + pics.getImage_Uri())
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(viewHolder.imageView);


            }

            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                int rem = Pics_list.size()-1;
                num_images.setText(rem + "/" + Variables.Var_num_pics_in_upload_Ads);

                if(rem == 0){
                    camera_RL.setVisibility(View.VISIBLE);
                    after_one_pic_RL.setVisibility(View.GONE);
                }
                Methods.toast_msg(context,"" + Pics_list.size());
                // Calling an API.

                Ad_Details.del_old_image_on_editing(
                        pics.getImg_id(),
                        "" + pics.getImage_Uri(),
                        context);

                Methods.toast_msg(context,"" + pics.getImg_id() + " URL " + pics.getImage_Uri());

                removeAt(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Pics_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, cancel;
//        TextView textView,textView1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.pic);
            cancel = itemView.findViewById(R.id.cancel);

        }





    }

    public void removeAt(int position) {
        Pics_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Pics_list.size());
        //text.setText("" + "yes " + Pics_list.size());
        //num_pics.setText("" + "yes " + Pics_list.size());
    }
}
