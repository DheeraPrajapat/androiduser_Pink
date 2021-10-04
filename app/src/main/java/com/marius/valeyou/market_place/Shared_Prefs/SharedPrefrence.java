package com.marius.valeyou.market_place.Shared_Prefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;

import org.json.JSONObject;

public class SharedPrefrence {

    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;


    public static String shared_user_login_detail_key = "user_info";

    public static String share_default_country_info = "default_country";
    public static String share_all_country_list = "all_country";
    public static String share_today_date = "today";
    public static String share_num_home_visit = "click";
    public static String share_viwed_posts = "viwed_posts";
    public static String share_default_city_info = "default_city";

    public static String share_app_Config_key = "App_config";


    public static String share_filter_main_cate = "main_cate_filter";
    public static String share_filter_sub_cate = "sub_cate_filter";
    public static String share_filter_result = "result_filter";
    public static String share_section_filter = "section_filter";


    public static String share_inactive_ads = "in_active_ads";




    public static void init_share(Context context){
        pref = context.getSharedPreferences("Advilla", 0); // 0 - for private mode
        editor = pref.edit();
    }

    public static void save_info_share(Context context,String value,String data_key)
    {
        init_share(context);
        editor.putString(data_key, value); // Storing string
        editor.commit();
    }

    public static void logout_user(Context context)
    {
        init_share(context);
        boolean ch = pref.edit().remove(shared_user_login_detail_key).commit();
// Open a home Screen
//        Intent sendIntent = new Intent(context, Login.class);
//        sendIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(sendIntent);

        Intent intent = new Intent(context, Drawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    // Clear Key
    public static void remove_value_of_key (Context context, String key){
        init_share(context);
        boolean ch = pref.edit().remove(key).commit();
    }

    public static void remove_value (Context context,String remove_key){
        init_share(context);
        boolean ch = pref.edit().remove(remove_key).commit();
    }


    public static String get_offline(Context context, String key){
        init_share(context);
        pref.getString(key, null);
        // Variables.toast_msg(context,"Value "+pref.getString(key, null));
        return pref.getString(key, null);

    }

    public static String get_user_id_from_json(Context context){
        String user_json = get_offline(context,SharedPrefrence.shared_user_login_detail_key);
        // Get User Data from
        // Methods.toast_msg(context,"" + user_json);
        String user_id = "0";

        try{
            JSONObject user_obj = new JSONObject(user_json);

            user_id = user_obj.getString("id");
//            user.getString("first_name");
//            user.getString("email");
            // user_id = user.getString("id");

        }catch (Exception b){
            Methods.toast_msg(context,"" + b.toString());
        }

        return user_id;
    }


    ///////// ======= Get User Name
    public static String get_user_name_from_json (Context context){

//        user_info = SharedPrefrence.get_offline(Drawer.this,
//                "" + SharedPrefrence.shared_user_login_detail_key
//        );

        String user_json = get_offline(context,shared_user_login_detail_key);
        //  Methods.toast_msg(context,"" + user_json);
        // Get User Data from
        int user_id = 0;
        String user_name = "";
        try{


            JSONObject user_obj = new JSONObject(user_json);

            user_name = user_obj.getString("full_name");


        }catch (Exception b){
            // Methods.toast_msg(context,"" + b.toString());
        }

        return user_name;
    }
    //////////// ========= ENd get User Name


    // Get user phone From Json
    public static String get_user_phone_from_json (Context context){

//        user_info = SharedPrefrence.get_offline(Drawer.this,
//                "" + SharedPrefrence.shared_user_login_detail_key
//        );

        String user_json = get_offline(context,shared_user_login_detail_key);
        //  Methods.toast_msg(context,"" + user_json);
        // Get User Data from
        int user_id = 0;
        String phone = "";
        try{


            JSONObject user_obj = new JSONObject(user_json);

            phone = user_obj.getString("phone");


        }catch (Exception b){
            // Methods.toast_msg(context,"" + b.toString());
        }

        return phone;
    }

    // End get User Phone



    ///////// ======= Get User Name
    public static String get_user_img_from_json(Context context){
        String user_json = get_offline(context,shared_user_login_detail_key);
        //  Methods.toast_msg(context,"" + user_json);
        // Get User Data from
        int user_id = 0;
        String user_img = "";
        try{
            JSONObject user_obj = new JSONObject(user_json);

            user_img = user_obj.getString("image");


        }catch (Exception b){
            Methods.toast_msg(context,"" + b.toString());
        }

        return user_img;
    }




    ///////// ======= Get User Name
    public static String get_user_email_from_json (Context context){
        String user_json = get_offline(context,shared_user_login_detail_key);
        //  Methods.toast_msg(context,"" + user_json);
        // Get User Data from
        int user_id = 0;
        String email = "";
        try{


            JSONObject user_obj = new JSONObject(user_json);

            // user_name = user_obj.getString("full_name");
            email = user_obj.getString("email");

//            user.getString("email");
            //          user_id = user.getInt("id");

        }catch (Exception b){
            // Methods.toast_msg(context,"" + b.toString());
        }
        return email;
    }
    //////////// ========= ENd get User Name

    public static String get_user_img_org (Context context){

        String user_json = get_offline(context,shared_user_login_detail_key);
        //  Methods.toast_msg(context,"" + user_json);
        // Get User Data from
        int user_id = 0;
        String user_pic = "";
        try{


            JSONObject user_obj = new JSONObject(user_json);

            // user_name = user_obj.getString("full_name");
            user_pic = API_LINKS.BASE_URL +  user_obj.getString("image");

//            user.getString("email");
            //          user_id = user.getInt("id");

        }catch (Exception b){
            // Methods.toast_msg(context,"" + b.toString());
        }
        return user_pic;


    }

    // Get Country ID from Json WHich is saved into Shared Prefrence

    public static String Country_id_from_Json (Context context) {

        String country_id = "";
        try{
            String default_country = SharedPrefrence.get_offline(context,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
            //  country_name = findViewById(R.id.country_name);
            //country_name.setText("" + de.getString("country_name"));
//            de.getString("");
            country_id = de.getString("country_id");

            // Methods.toast_msg(getContext(),"" + de.getString("country_name") + " " + de.getString("country_id"));
        }catch (Exception b){
            Methods.toast_msg(context,"" + b.toString());
        }
        return country_id;
    }

    // Get Current

    public static String Currancy_name_from_Json (Context context) {

        String country_id = "";
        try{
            String default_country = SharedPrefrence.get_offline(context,
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);

            country_id = de.getString("country_currancy");

            // Methods.toast_msg(getContext(),"" + de.getString("country_name") + " " + de.getString("country_id"));
        }catch (Exception b){
            Methods.toast_msg(context,"" + b.toString());
        }
        return country_id;
    }




}
