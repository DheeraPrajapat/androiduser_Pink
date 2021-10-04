package com.marius.valeyou.market_place.Volley_Package;

import android.app.Activity;
import android.content.Context;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;

import org.json.JSONArray;
import org.json.JSONObject;

public class API_Calling_methods {
    // TODO: ==> Method of SIgnUp And SignIn , Depends upon Flag.

    public static void Sign_Up(String name, String email, String password, Context context, String flag) {
        JSONObject user_data_objs = null;
        String API_LINK = "";
        /// String full_name = "", password = "";
        //email = "jkjn@kjl";

        try {
            String phone = "00";
            user_data_objs = new JSONObject("{'email': '" + email + "'," +
                    " 'full_name' : '" + name + "'," +
                    " 'password' : '" + password + "', 'phone' : '"+ phone +"' }");

        } catch (Exception v) {

        }
        // If flag equal to Login

//            }

        try {
            Volley_Requests.API_Calling(
                    context,
                    "" + API_LINKS.API_SIGN_UP,
                    user_data_objs,
                    "" + flag
            );


        } catch (Exception v) {
            Methods.Log_d_msg(context, "" + v.toString());
        }
//        Volley_Requests.API_Calling(
//                Login.this,
//                "okk",
//
//
//        )

    }

    // TODO: ==> Method of SignIn

    public static void Sign_In(String email, String password, Context context, String flag) {
        JSONObject user_data_objs = null;
        String API_LINK = "";
        /// String full_name = "", password = "";
        //email = "jkjn@kjl";

        try {
            user_data_objs = new JSONObject("{'email': '" + email + "'," +
                    " 'device_token' : ' '," +
                    " 'password' : '" + password + "' }");

        } catch (Exception v) {
            Methods.Log_d_msg(context, "" + v.toString());
        }
        // If flag equal to Login

        try {
            Volley_Requests.API_Calling(
                    context,
                    "" + API_LINKS.API_SIGN_IN,
                    user_data_objs,
                    "" + flag
            );


        } catch (Exception v) {
            Methods.Log_d_msg(context, "" + v.toString());
        }


    } // End Sign In method


    public static void Upload_Post(String email, String password, Context context, String flag) {
        JSONObject user_data_objs = null;
        String API_LINK = "";
        /// String full_name = "", password = "";
        //email = "jkjn@kjl";

        try {
            user_data_objs = new JSONObject("{'email': '" + email + "'," +
                    " 'device_token' : ' '," +
                    " 'password' : '" + password + "' }");

        } catch (Exception v) {
            Methods.Log_d_msg(context, "" + v.toString());
        }
        // If flag equal to Login

        try {

            Volley_Requests.API_Calling(
                    context,
                    "" + API_LINKS.API_SIGN_IN,
                    user_data_objs,
                    "" + flag
            );


        } catch (Exception v) {
            Methods.Log_d_msg(context, "" + v.toString());
        }


    } // End Sign In method


    // TODO: Updated Calling API methods.   :-)

    public static void Get_App_Config (final Context context, final Activity activity, final String user_info) {

        // Get All App Config Data Like Color code of App header.
        JSONArray jsonArray = new JSONArray();
        try{

            String App_config_param[] = context.getResources().getStringArray(R.array.array_app_config);
            for(int i=0; i< App_config_param.length; i++){
                JSONObject sendObj_1 = new JSONObject();
                sendObj_1.put("type","" + App_config_param[i]);
                jsonArray.put(sendObj_1);
            }
            final JSONObject sending_Obj = new JSONObject();
            sending_Obj.put("types", jsonArray);

           // Methods.alert_dialogue(context,"Data", "" + sending_Obj.toString());

            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_HOME_SLIDER,
                    sending_Obj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                    SharedPrefrence.save_info_share(
                    context,
                    "" + response.toString(),
                    SharedPrefrence.share_app_Config_key
                                  );



                            try{
                                //JSONObject response = new JSONObject(confid);
                                JSONArray msg = response.getJSONArray("msg");
                                for(int i=0; i< msg.length();i++) {
                                    JSONObject json = msg.getJSONObject(i);
                                    JSONObject Slider_obj = json.getJSONObject("Setting");
                                    Slider_obj.getString("image");


                                    if(Slider_obj.getString("type").equals("app_header_bg_color" )){
                                        // Header bg Color
                                        if(Slider_obj.getString("source").equals("")){
                                            // if Color Empty
                                            Variables.Var_App_Config_header_bg_color = String.valueOf(context.getResources().getColor(R.color.blue));
                                        }else{
                                            Variables.Var_App_Config_header_bg_color =  Slider_obj.getString("source");
                                        }


                                    }else if(Slider_obj.getString("type").equals("app_header_statusBar_color")){
                                        // Header Font Color

                                        if(Slider_obj.getString("source").equals("")){
                                            // if Color Empty
                                            Variables.App_status_bar_color_code = String.valueOf(context.getResources().getColor(R.color.blue));
                                        }else{
                                            Variables.App_status_bar_color_code =  Slider_obj.getString("source");
                                        }

                                     }
                                    else if(Slider_obj.getString("type").equals("contact_us_email"))  {
                                        Variables.App_Config_contact_us_email = Slider_obj.getString("source");
                                    }
                                    else if(Slider_obj.getString("type").equals("contact_us_phone_number")) {
                                        Variables.App_Config_contact_us_phone_number = Slider_obj.getString("source");

                                    } else if(Slider_obj.getString("type").equals("contact_us_facebook")) {
                                        Variables.App_Config_contact_us_facebook = Slider_obj.getString("source");

                                    }else if(Slider_obj.getString("type").equals("contact_us_twiter")) {
                                        Variables.App_Config_contact_us_twiter = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("contact_us_instagram")) {
                                        Variables.App_Config_contact_us_instagram = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("contact_us_linkdin")) {
                                        Variables.App_Config_contact_us_linkdin = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("privacy_policy")) {
                                        Variables.App_Privacy_Policy = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("terms_conditions")) {
                                        Variables.App_term_condition = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("admob_banner_300x250")){
                                        // Banner Ad Ids.
                                        Variables.admob_banner_300x250 = Slider_obj.getString("source");

                                    }else if(Slider_obj.getString("type").equals("facebook_banner_ad")){
                                        // Banner Ad Ids.
                                        Variables.facebook_banner_ad = Slider_obj.getString("source");
                                    }else if(Slider_obj.getString("type").equals("facebook_banner_ad")){
                                        // Google Analytics Tracking ID
                                        Variables.google_analytics_id = Variables.google_analytics_id_prefix + Slider_obj.getString("source");
                                    }


//                                    else if(Slider_obj.getString("type").equals("" + Variables.Var_App_Config_mob_slider)){
//                                        Methods.toast_msg(context,"" +  Variables.Var_App_Config_mob_slider);
//                                        Methods.Download_image(context,"" + API_LINKS.BASE_URL + Slider_obj.getString("image"), "advilla_silider_" + i + ".jpg");
//
//                                    }





                                   // Methods.Change_header_color(header, Drawer.this);

                                } // End for Loop
                            }catch (Exception b){
                                Methods.Log_d_msg(context,"Err " + b.toString());
                            }

                        }
                    }






            );


        }catch (Exception b){

            Methods.Log_d_msg(context,"" + b.toString());

        }






    }


    // Add Fav or Un Fav

    public static void add_fav_ads (String fav_or_not, String post_id ,final Context context){
        // Add Fav Ad Code
        // Apply API

        try{
            //JSONObject sendObj = new JSONObject("{ '': '' }");


            //  JSONObject sendObj = new JSONObject();
            //sendObj.put("" , "");
            String user_id = SharedPrefrence.get_user_id_from_json(context);
            JSONObject sendObj = new JSONObject();
            sendObj.put("user_id","" + user_id);
            sendObj.put("post_id","" + post_id);
            sendObj.put("favourite",fav_or_not);


            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_ADD_AD_FAV ,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                          //  Methods.toast_msg(context,"Fav " + response.toString());




                        }
                    }


            );



        }catch (Exception v){
           // Methods.toast_msg(context,"" + v.toString());
        }


    }




}

