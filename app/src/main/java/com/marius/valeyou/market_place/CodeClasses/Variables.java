package com.marius.valeyou.market_place.CodeClasses;

import android.app.ProgressDialog;

import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.IResult;
import com.marius.valeyou.market_place.Volley_Package.VolleyService;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class Variables {

    public static String Key_u_id = "u_id";
    public static SharedPrefrence Shared_pref;

    public static String u_id = "";
    public static String Var_App_Config;
    public static String Var_App_Config_header_bg_color;
    public static String Var_App_Config_header_text_color;

    //public static String App_Config_header_text_color;
    public static String App_Config_contact_us_email;
    public static String App_Config_contact_us_phone_number;
    public static String App_Config_contact_us_facebook;
    public static String App_Config_contact_us_twiter;
    public static String App_Config_contact_us_instagram;
    public static String App_Config_contact_us_linkdin;
    public static String App_status_bar_color_code;
    public static String App_Privacy_Policy;
    public static String App_term_condition;
    public static String admob_banner_300x250 = "";
    public static String facebook_banner_ad = "";
    public static String google_analytics_id = "";

    public static String google_analytics_id_prefix =    "UA-";

    public static String App_Privacy_Policy_new = "https://www.google.com";

    public static String App_Terms_Condition = "https://www.google.com";

    public static String Var_thumb_prefix = "150_";

    public static String Var_fragment = "fragment";

    public static String Var_activity = "activity";

    public static int img_size = 1500;


    // TODO: Get App config Variables
    public static String Var_App_Config_mob_slider = "mobile_slider" ;
    public static String Var_App_Config_header_bg_col = "app_header_bg_color";
    public static String Var_App_Config_header_txt_color = "app_header_text_color";

    public static String Var_App_Config_contact_us_email = "contact_us_email" ;
    public static String Var_App_Config_contact_us_phone_number = "contact_us_phone_number";
    public static String Var_App_contact_us_facebook = "contact_us_facebook";

    public static String Var_App_Config_contact_us_twiter = "contact_us_twiter" ;
    public static String Var_App_Config_contact_us_instagram = "contact_us_instagram";
    public static String Var_App_Config_contact_us_linkdin = "contact_us_linkdin";

    public static String Var_App_Config_contact_us_address = "contact_us_address";

    public static String Var_default_city = "";

    public static int Var_num_click_to_show_Ads = 3;
    public static int Var_num_pics_in_upload_Ads = 10;

    // Confidentials Variables
    public static String Var_com_where_page_all_ads = "all_ads_page";





    public static double height;
    public static double width;
    public static String download_pref="download_pref";

    public static IResult mResultCallback = null;
    public static VolleyService mVolleyService;
    public static String res;
    public static ProgressDialog pDialog;
    public static int permission_camera_code=786;
    public static int permission_write_data=788;
    public static int permission_Read_data=789;
    public static int permission_Recording_audio=790;
    public static int vedio_request_code = 9;

    public static SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
    public static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

    public static String Opened_Chat_id;
    public static String user_name;
    public static String user_pic;
    public static String user_id = "90";
    public static Boolean check = true;
    public static int MY_SOCKET_TIMEOUT_MS = 30000;


    // TODO: ==>  Important Flags are here which are used in calling APIs.
    public static String flag_login = "login";
    public static String flag_Sign_up = "Sign_up";
    public static String flag_upload_post = "upload_ad";
    public static String flag_Send_phone = "Send_phone_num";
    public static String flag_phone_no_verified = "phone_num_verified";
    public static String flag_Add_Contacts = "AddContact";
    public static String flag_Social_Login_Sign_up = "Social_login_sign_up";
    public static String flag_fb_login = "fb_login";
    public static String flag_gmail_login = "gmail_login";
}
