package com.marius.valeyou.market_place.Drawer.Home.Country_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Drawer.Home.City_Listt.City_loc;
import com.marius.valeyou.market_place.Drawer.Home.Get_Set.Home_get_set;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class country_adp  extends RecyclerView.Adapter<country_adp.ViewHolder> {

    Context context;
    Home_get_set categories;
    click itemclick;
    List<Country_get_Set> country_list;
    JSONObject def_country_info = new JSONObject();
    String country_id;
    public interface click{
        void onclick(int pos);
    }
    public country_adp(Context context,  click itemclick ,List<Country_get_Set> country_list ){
        this.context = context;
        this.itemclick = itemclick;
        this.country_list = country_list;
    }

    public void updateList(ArrayList<Country_get_Set> list){
        country_list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_country, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Country_get_Set country = country_list.get(i);

        try{

            String default_country = SharedPrefrence.get_offline(context,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
            country_id = de.getString("country_id");


//            String default_country = SharedPrefrence.get_offline(City_loc.this,
//                    SharedPrefrence.share_default_country_info
//                    );
//            JSONObject de = new JSONObject(default_country);
//            de.getString("");
          //  country_id = de.getString("country_id");

          //  Methods.Log_d_msg(City_loc.this,"" + de.getString("country_name") + " " + de.getString("country_id"));
        }catch (Exception b){
          //  Methods.Log_d_msg(City_loc.this,"" + b.toString());
        }


        String def = country.getDefault_country();
//        if(def.equals("1")){
//            // If country Default ==> Bold Text
//           // viewHolder.country_name.setTypeface(viewHolder.country_name.getTypeface(), Typeface.BOLD);
//            viewHolder.country_name.setTypeface(null, Typeface.BOLD);
//            viewHolder.real_estate_rl_id.setBackgroundColor(context.getResources().getColor(R.color.color_selected));
//            viewHolder.country_name.setText("" + country.getName()  );
//        }else{
//            viewHolder.country_name.setTypeface(null, Typeface.NORMAL);
//            viewHolder.real_estate_rl_id.setBackgroundColor(context.getResources().getColor(R.color.white));
//            viewHolder.country_name.setText("" + country.getName());
//        }


        if(country_id.equals("" + country.getId())){
            viewHolder.country_name.setTypeface(null, Typeface.BOLD);
            viewHolder.real_estate_rl_id.setBackgroundColor(context.getResources().getColor(R.color.color_selected));
            viewHolder.country_name.setText("" + country.getName()  );
        }else{
            viewHolder.country_name.setTypeface(null, Typeface.NORMAL);
            viewHolder.real_estate_rl_id.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.country_name.setText("" + country.getName());
        }



        viewHolder.real_estate_rl_id.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                Methods.toast_msg(context,"Co" + country.getName() + "" + country.getId());

               // Country_get_Set country_info = Country_list.get(pos);
                            Methods.toast_msg(context,"" + country.getName());
//                try{
//                    def_country_info.put("country_id" , "" + country.getId());
//                    def_country_info.put("country_name" , "" + country.getName());
//
//                  ///  SharedPrefrence.remove_value(context,"" + SharedPrefrence.share_default_country_info);
//
//                    Methods.toast_msg(context,"Savdd " + def_country_info.toString());
//
//                    SharedPrefrence.save_info_share(
//                            context,
//                            "" + def_country_info.toString(),
//                            "" + SharedPrefrence.share_default_country_info
//                    );
//                }catch (Exception b){
//                    Methods.toast_msg(context,"Err in Cpj " + b.toString());
//                }

              //  Methods.toast_msg(context,"Saved in Cpj " + def_country_info.toString());



                            Intent myIntent = new Intent(context, City_loc.class);
                            myIntent.putExtra("country_id", country.getId()); //Optional parameters
                            myIntent.putExtra("country_name", country.getName()); //Optional parameters
                            context.startActivity(myIntent);
                            ((Activity)context).finish();


            }
        });



       // viewHolder.onbind(i,itemclick);
    }

    @Override
    public int getItemCount() {
        return country_list.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView country_name;
        RelativeLayout real_estate_rl_id;
       public ViewHolder(@NonNull View itemView) {
            super(itemView);

            country_name =  itemView.findViewById(R.id.country_name);
           real_estate_rl_id = itemView.findViewById(R.id.real_estate_rl_id);

        }

        public void onbind(final int pos, final click listner){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onclick(pos);
                }

            });

        }
    }

}
