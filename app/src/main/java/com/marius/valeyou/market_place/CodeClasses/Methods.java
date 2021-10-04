package com.marius.valeyou.market_place.CodeClasses;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_GetSet;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Login_Register.Login;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.TimeAgo2;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.widget.EdgeEffectCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.DOWNLOAD_SERVICE;

public class Methods {

    public static void Log_d_msg(Context contect, String Msg) {
        Log.d("" + contect, "Log \n " + Msg);
    }

    public static void toast_msg(Context context, String msg) {
        //Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
        Methods.Log_d_msg(context, "Log \n " + msg);
    }

    public static String get_time_ago_org (String date_time){
        //String time = news.getString("created");

        TimeAgo2 timeAgo2 = new TimeAgo2();
        String MyFinalValue = timeAgo2.covertTimeToText(date_time);

        return MyFinalValue;

    }

    /// ==> VEmail Validation
    public static boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    /// Alert Dialogue
    public static void alert_dialogue(final Context context, String title, String msg) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("" + title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });

        AlertDialog alert11 = builder1.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

            alert11.show();

        }else{
            alert11.show();
        }


    }


    public static void get_default_country_id (final Context context,
                                               final Activity activity,
                                               final String user_info){
        final JSONObject def_country_info = new JSONObject();


        try{
            //JSONObject sendObj = new JSONObject("{ '': '' }");


          //  JSONObject sendObj = new JSONObject();
            //sendObj.put("" , "");
            JSONObject sendObj = new JSONObject("{ }");


            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Country_List,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                           // Methods.toast_msg(context,"Country " + requestType+  " " + response.toString());
                            // Maipulate Response
                            // pullToRefresh.setEnabled(true);

                            // Save All countries into Local Storage

                            SharedPrefrence.save_info_share(
                                    context,
                                    "" + response.toString(),
                                    "" + SharedPrefrence.share_all_country_list
                            );


                            try{

                                // Save Data Into Shared Pref;

                               JSONArray msg = response.getJSONArray("msg");

                                for(int op = 0; op< msg.length(); op++) {

                                    JSONObject option_obj = msg.getJSONObject(op);
                                    JSONObject option_val = option_obj.getJSONObject("Country");
                                    option_val.getString("name");
                                    option_val.getString("id");
                                    option_val.getString("default");
                                    if(option_val.getString("default").equals("1")){
                                         // Save into Shared Pref

                                        def_country_info.put("country_id" , "" + option_val.getString("id"));
                                        def_country_info.put("country_name" , "" + option_val.getString("name"));
                                        def_country_info.put("country_currancy" , "" + option_val.getString("currency"));

                                    }



                                } // End For Loop




                            }catch (Exception b){
                                Methods.toast_msg(context,"cc " + b.toString());
                            }

                            SharedPrefrence.save_info_share(
                                    context,
                                    "" + def_country_info.toString(),
                                    "" + SharedPrefrence.share_default_country_info
                            );








                        }
                    }


            );



        }catch (Exception v){
            Methods.Log_d_msg(context,"" + v.toString());
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {




                if(user_info != null){
                    // If user info is nt full , Go to Drawer Activity
                    activity.startActivity(new Intent(context, Drawer.class));
                    activity.finish();
                }else{
                    // Go to Login Activity
                    activity.startActivity(new Intent(context, Drawer.class));
                    //startActivity(new Intent(SplashScreen.this, Chat.class));
                    //startActivity(new Intent(SplashScreen.this, Chat_Inbox.class));
                    activity.finish();
                }

                // todo Old and orginal code SplashScreen.java


            }
        }, 3000);




    }


    public static void get_default_country_id_1 (final Context context,
                                               final Activity activity,
                                               final String user_info){
        final JSONObject def_country_info = new JSONObject();


        try{
            //JSONObject sendObj = new JSONObject("{ '': '' }");


            //  JSONObject sendObj = new JSONObject();
            //sendObj.put("" , "");
            JSONObject sendObj = new JSONObject("{ }");


            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Country_List,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Methods.toast_msg(context,"Country " + requestType+  " " + response.toString());
                            // Maipulate Response
                            // pullToRefresh.setEnabled(true);

                            // Save All countries into Local Storage

                            SharedPrefrence.save_info_share(
                                    context,
                                    "" + response.toString(),
                                    "" + SharedPrefrence.share_all_country_list
                            );


                            try{

                                // Save Data Into Shared Pref;

                                JSONArray msg = response.getJSONArray("msg");

                                for(int op = 0; op< msg.length(); op++) {

                                    JSONObject option_obj = msg.getJSONObject(op);
                                    JSONObject option_val = option_obj.getJSONObject("Country");
                                    option_val.getString("name");
                                    option_val.getString("id");
                                    option_val.getString("default");
                                    if(option_val.getString("default").equals("1")){
                                        // Save into Shared Pref

                                        def_country_info.put("country_id" , "" + option_val.getString("id"));
                                        def_country_info.put("country_name" , "" + option_val.getString("name"));
                                        def_country_info.put("country_currancy" , "" + option_val.getString("currency"));

                                    }



                                } // End For Loop




                            }catch (Exception b){
                                Methods.Log_d_msg(context,"cc " + b.toString());
                            }

                            SharedPrefrence.save_info_share(
                                    context,
                                    "" + def_country_info.toString(),
                                    "" + SharedPrefrence.share_default_country_info
                            );








                        }
                    }


            );



        }catch (Exception v){
            Methods.toast_msg(context,"" + v.toString());
        }




    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void show_Keyboard(Context context, View view){
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static boolean isMyServiceRunning(Context context,Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }



    public static long DownloadData_forChat (Context context, Chat_GetSet item) {

        long downloadReference;
        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(item.getPic_url()));

        //Setting title of request
        request.setTitle(item.getSender_name());

        //Setting description of request
        if(item.getType().equals("video")) {
            request.setDescription("Downloading Video");
            request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Chatbuzz/" + item.getChat_id() + ".mp4")));
        }
        else if(item.getType().equals("audio")){
            request.setDescription("Downloading Audio");
            request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Chatbuzz/" + item.getChat_id() + ".mp3")));
        }
        else if(item.getType().equals("pdf")){

            request.setDescription("Downloading Pdf Document");
            request.setDestinationUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Chatbuzz/" + item.getChat_id() + ".pdf")));

        }


        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }




    //todo: Change Toolbar Color i.e Header

    public static void Change_header_color(androidx.appcompat.widget.Toolbar header, Activity activity) {
        try{
            //Toast.makeText(activity, "" + Variables.App_Config_contact_us_email, Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }

            // Tool bar color Change
            header.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

        }catch (Exception v){

        }

    }

    // put the image file path into this method
    public static String getFileToByte(String filePath){
        // filePath = "/storage/emulated/0/photo.jpg";
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
            Log.d("Base 64","" + encodeString);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }


    public static void share_app (Context context,String text){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
            String shareMessage= "\n " +
                    "Valeyou Marketplace  \n\n" +
                    "\n "+text + "\n" +

                    " \n";
            shareMessage = shareMessage + "https://valeyou.com.br/";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }

    }


    public static void Manipulate_app_Setting_data(Context context){

        // Manipulate App Setting Data
        try{
            //JSONObject response = new JSONObject(confid);
            String res = SharedPrefrence.get_offline(context,"" + SharedPrefrence.share_app_Config_key);
            JSONObject response = new JSONObject(res);

            JSONArray msg = response.getJSONArray("msg");
            for(int i=0; i< msg.length();i++) {
                JSONObject json = msg.getJSONObject(i);
                JSONObject Slider_obj = json.getJSONObject("Setting");
                Slider_obj.getString("image");

                // Methods.toast_msg(context,"" + Slider_obj.getString("source") + " Ty " + Slider_obj.getString("type"));

                if(Slider_obj.getString("type").equals("app_header_bg_color" )){
                    // Header bg Color
                    Variables.Var_App_Config_header_bg_color =  Slider_obj.getString("source");
                    // Methods.toast_msg(Drawer.this,"BG col " + Variables.Var_App_Config_header_bg_color);
                }else if(Slider_obj.getString("type").equals("app_header_statusBar_color")){
                    // Header Font Color
                    Variables.App_status_bar_color_code = Slider_obj.getString("source");
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
                }





                // Methods.Change_header_color(header, Drawer.this);

            } // End for Loop
        }catch (Exception b){
            // Methods.toast_msg(context,"Err " + b.toString());
        }

    }


//    public static void Download_image (final Context context, String url, String fileName, String full_path){
//        // Method to save image into Local
//
//        // Make Directry
////        File direct = new File(Environment.getExternalStorageDirectory() + "/Advilla");
////
////        if (!direct.exists()) {
//////            File wallpaperDirectory = new File("/sdcard/DirName/");
////            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() +"/Advilla");
////
////            wallpaperDirectory.mkdirs();
////        }
////        // End  making Directry
////
////        DownloadManager downloadManager = (DownloadManager) ((Activity) context).getSystemService(Context.DOWNLOAD_SERVICE);
////        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
////        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
////                .setAllowedOverRoaming(false)
////                .setTitle(fileName)
////                .setDescription("file description")
////                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
////                .setDestinationInExternalPublicDir(direct.getPath(), fileName)
////                .setVisibleInDownloadsUi(true);
////
////        BroadcastReceiver onComplete = new BroadcastReceiver() {
////
////            public void onReceive(Context ctxt, Intent intent) {
////
////                //Download complete
////                Methods.toast_msg(context,"Complete");
////
////            }
////        };
////        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
////        downloadManager.enqueue(request);
////
//
//        // this is the third party library that will download the image
//        DownloadRequest prDownloader;
//        final File direct = new File(Environment.getExternalStorageDirectory() + "/DCIM/Binder/");
//
//        PRDownloader.initialize(context.getApplicationContext());
//        prDownloader.start(new OnDownloadListener() {
//            @Override
//            public void onDownloadComplete() {
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(Uri.parse(direct.getPath() + chat_id + ".jpg"));
//                context.sendBroadcast(intent);
//                //progressDialog.dismiss();
//
//
//
//            }
//
//            @Override
//            public void onError(Error error) {
//               /// progressDialog.dismiss();
//                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//
//
//
//
//
//    }


    public static PaintDrawable getColorScala() {
        PaintDrawable paint = new PaintDrawable();
        int[] colors =   new int[2];
        if(Variables.Var_App_Config_header_bg_color.equals(null) ||  Variables.App_status_bar_color_code.equals(null)){
            // If Both colors are Empty or only one Wmpty

             colors =   new int[] {
                    Color.parseColor("#F69518"),
                    Color.parseColor("#13D3CD"),
            };


        }else{

            colors =   new int[] {
                    Color.parseColor("#F69518"),
                    Color.parseColor("#13D3CD"),
            };
        }


        final int[] finalColors = colors;
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
                @Override
                public Shader resize(int width, int height) {

                        LinearGradient linearGradient = new LinearGradient(0, 0, width, height,
                                finalColors, //substitute the correct colors for these

                                new float[] {
                                        0, 0.40f },
                                Shader.TileMode.REPEAT);
                    return linearGradient;
                }
            };


            paint.setShape(new RectShape());
            paint.setShaderFactory(shaderFactory);


        return paint;

    }


    // TODO: No Record Yet Method;
    public static void No_Record_method (Context context, ImageView image, TextView text){
        image.setVisibility(View.VISIBLE);

    }

    // Show Custom Dialogue



        public static void showDialog (final Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog);

//            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//            text.setText(msg);

            TextView dialogButton =  dialog.findViewById(R.id.login_signup);
            ImageView cancel = dialog.findViewById(R.id.cancel);

            try{
                // Change Background Color Dynamuically.... :-)
                dialogButton.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            }catch (Exception V){

            }

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   
                    dialog.dismiss();
                }
            });

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, Login.class));
                    activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }


    public static void showDialog_2 (final Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

//            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//            text.setText(msg);

        TextView dialogButton =  dialog.findViewById(R.id.login_signup);
        ImageView cancel = dialog.findViewById(R.id.cancel);

        try{
            // Change Background Color Dynamuically.... :-)
            dialogButton.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception V){

        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Login.class));
                activity.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    public static void changeOverScrollGlowColor (Resources res, int colorID ) {
        try {
            final int glowDrawableId = res.getIdentifier("overscroll_glow", "drawable", "android");
            final Drawable overscrollGlow = res.getDrawable(glowDrawableId);
            overscrollGlow.setColorFilter(res.getColor(colorID), PorterDuff.Mode.SRC_ATOP);

            final int edgeDrawableId = res.getIdentifier("overscroll_edge", "drawable", "android");
            final Drawable overscrollEdge = res.getDrawable(edgeDrawableId);
            overscrollEdge.setColorFilter(res.getColor(colorID), PorterDuff.Mode.SRC_ATOP);
        } catch (Exception ignored) {
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setEdgeEffectL (View scrollableView, int color) {
        final String[] edgeGlows = {"mEdgeGlowTop", "mEdgeGlowBottom", "mEdgeGlowLeft", "mEdgeGlowRight"};
        for (String edgeGlow : edgeGlows) {
            Class<?> clazz = scrollableView.getClass();
            while (clazz != null) {
                try {
                    final Field edgeGlowField = clazz.getDeclaredField(edgeGlow);
                    edgeGlowField.setAccessible(true);
                    final EdgeEffect edgeEffect = (EdgeEffect) edgeGlowField.get(scrollableView);
                    edgeEffect.setColor(color);
                    break;
                } catch (Exception e) {
                    clazz = clazz.getSuperclass();
                }
            }
        }
    }



    // Chane Edge Color for Recycle View n android

    public static void setEdgeGlowColor (final RecyclerView recyclerView, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Class<?> clazz = RecyclerView.class;
                for (final String name : new String[] {"ensureTopGlow", "ensureBottomGlow"}) {
                    Method method = clazz.getDeclaredMethod(name);
                    method.setAccessible(true);
                    method.invoke(recyclerView);
                }
                for (final String name : new String[] {"mTopGlow", "mBottomGlow"}) {
                    final Field field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    final Object edge = field.get(recyclerView); // android.support.v4.widget.EdgeEffectCompat
                    final Field fEdgeEffect = edge.getClass().getDeclaredField("mEdgeEffect");
                    fEdgeEffect.setAccessible(true);
                    ((EdgeEffect) fEdgeEffect.get(edge)).setColor(color);
                }
            } catch (final Exception ignored) {}
        }
    }



    ///////// Ads methods are here....
    public static InterstitialAd interstitial;
    public static void createNewIntAd (Context context,String add_id)
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial = new InterstitialAd(context);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(add_id );
        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener

        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
    }
    public static void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial!=null && interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    ////// Banner ad Displaying Method
    //// Paasing paremeter of ADview on Calling, Then Banner ad Will display
    public static void Show_Banner_ad ( Context context, RelativeLayout layout){
//        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//        AdRequest request = new AdRequest.Builder().build();
//        mAdView.loadAd(request);

        //Variables.admob_banner_300x250 = "ca-app-pub-3940256099942544/6300978111";
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId("" + Variables.admob_banner_300x250);


        RelativeLayout.LayoutParams right_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        right_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        right_params.setMargins(20,20,20,20);
        adView.setLayoutParams(right_params); //causes layout updat

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        layout.addView(adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);

//        }


    }


    public static void display_fb_ad (final Context context, final ProgressDialog pd){
        try{
            pd.hide();
        }catch (Exception b){

        }
        final String  TAG = "Main";
        final com.facebook.ads.InterstitialAd interstitialAd;
        interstitialAd = new com.facebook.ads.InterstitialAd(context, "" + Variables.facebook_banner_ad);
        String num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);

        Methods.toast_msg(context,"Num Click " + num_click_post);

        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.d(TAG, "Interstitial ad displayed.");
                SharedPrefrence.save_info_share(context,"0","" + SharedPrefrence.share_num_home_visit);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.d(TAG, "Interstitial ad dismissed.");
                SharedPrefrence.save_info_share(context,"0","" + SharedPrefrence.share_num_home_visit);

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.d(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
                try{
                    pd.hide();
                }catch (Exception b){

                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
                try{
                    pd.hide();
                }catch (Exception b){

                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
                try{
                    pd.hide();
                }catch (Exception b){

                }


            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown

       // interstitialAd.loadAd();

        if(num_click_post != null){
            // If Num click Post is not Null
            if(Integer.parseInt(num_click_post) == Variables.Var_num_click_to_show_Ads){
                // Ad Show When Number of Click 3
                //Methods.toast_msg(context,"Num Click " + num_click_post);

                if(pd != null){
                    // If dialogue is not Null
                    pd.hide();
                    interstitialAd.loadAd();
                }





            }

        }



      //  interstitialAd.loadAd();

    } // End of method to display fb Ads.


    public static int Count_num_click (Context context) {

        int num_click;
        // Count Click for FB Ads Displaying
        String num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);
        if(num_click_post == null){
            // If no value save
            num_click = 1;
            SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
        }else{
            num_click = Integer.parseInt(num_click_post) + 1;
            SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
        }

        if(num_click_post != null){
            if(Integer.parseInt(num_click_post) >= Variables.Var_num_click_to_show_Ads ){
                num_click = 1;
                SharedPrefrence.save_info_share(context,"" + num_click,"" + SharedPrefrence.share_num_home_visit);
                // Shoe Ad
                //  display_fb_ad(context);
            }

        }

        num_click_post = SharedPrefrence.get_offline(context, "" + SharedPrefrence.share_num_home_visit);
        Methods.toast_msg(context,"Num Click " + num_click_post);



        return num_click;

    } // End method to count the number of click for FB Ads



    public static void changeGlowColor_Nested_Scrol (int color, NestedScrollView scrollView) {
        try {

            Field edgeGlowTop = NestedScrollView.class.getDeclaredField("mEdgeGlowTop");

            edgeGlowTop.setAccessible(true);

            Field edgeGlowBottom = NestedScrollView.class.getDeclaredField("mEdgeGlowBottom");

            edgeGlowBottom.setAccessible(true);

            EdgeEffectCompat edgeEffect = (EdgeEffectCompat) edgeGlowTop.get(scrollView);

            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollView.getContext());
                edgeGlowTop.set(scrollView, edgeEffect);
            }

            Methods.setEdgeGlowColor(edgeEffect, color);

            edgeEffect = (EdgeEffectCompat) edgeGlowBottom.get(scrollView);
            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollView.getContext());
                edgeGlowBottom.set(scrollView, edgeEffect);
            }

            Methods.setEdgeGlowColor(edgeEffect, color);

        }



    catch (Exception ex) {

        ex.printStackTrace();
    }

}


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public static void setEdgeGlowColor(@NonNull EdgeEffectCompat edgeEffect, @ColorInt int color) throws Exception {
        Field field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");

        field.setAccessible(true);
        EdgeEffect effect = (EdgeEffect) field.get(edgeEffect);

        if (effect != null)
            effect.setColor(color);
    }




    public static void changeGlowColor(int color, NestedScrollView scrollView) {
        try {

            Field edgeGlowTop = NestedScrollView.class.getDeclaredField("mEdgeGlowTop");

            edgeGlowTop.setAccessible(true);

            Field edgeGlowBottom = NestedScrollView.class.getDeclaredField("mEdgeGlowBottom");

            edgeGlowBottom.setAccessible(true);
            EdgeEffectCompat edgeEffect = (EdgeEffectCompat) edgeGlowTop.get(scrollView);

            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollView.getContext());
                edgeGlowTop.set(scrollView, edgeEffect);
            }

            Methods.setEdgeGlowColor(edgeEffect, color);

            edgeEffect = (EdgeEffectCompat) edgeGlowBottom.get(scrollView);
            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollView.getContext());
                edgeGlowBottom.set(scrollView, edgeEffect);
            }

            Methods.setEdgeGlowColor(edgeEffect, color);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setEdgeEffectL_nested (NestedScrollView scrollableView, int color, Context context) {
        final String[] edgeGlows = {"mEdgeGlowTop", "mEdgeGlowBottom"};

        try{
            Field edgeGlowTop = NestedScrollView.class.getDeclaredField("mEdgeGlowTop");

            edgeGlowTop.setAccessible(true);

            Field edgeGlowBottom = NestedScrollView.class.getDeclaredField("mEdgeGlowBottom");

            edgeGlowBottom.setAccessible(true);

            EdgeEffectCompat edgeEffect = (EdgeEffectCompat) edgeGlowTop.get(scrollableView);

            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollableView.getContext());
                edgeGlowTop.set(scrollableView, edgeEffect);
            }

            setEdgeGlowColor(edgeEffect, color);

            edgeEffect = (EdgeEffectCompat) edgeGlowBottom.get(scrollableView);
            if (edgeEffect == null) {
                edgeEffect = new EdgeEffectCompat(scrollableView.getContext());
                edgeGlowBottom.set(scrollableView, edgeEffect);
            }

            setEdgeGlowColor(edgeEffect, color);
        }catch (Exception b){
          //  Methods.toast_msg(context,"" + b.toString());
        }

    }

    public static Drawable changeDrawableColor (int drawableRes, int colorRes, Context context) {
        //Convert drawable res to bitmap
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
//        final Bitmap resultBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        final Paint p = new Paint();
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmap, 0, 0, p);

        //Create new drawable based on bitmap
        final Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        drawable.setColorFilter(new
                PorterDuffColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    public static Drawable getRoundRect() {
        RoundRectShape rectShape = new RoundRectShape(new float[]{
                25, 25, 25, 25,
                0, 25, 25, 25
        }, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        try{
            shapeDrawable.getPaint().setColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
        }catch (Exception b){

        }


        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        return shapeDrawable;
    }


    public static boolean is_internet_avail(Context context)
    {
        boolean avail = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

//For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);

        if (!is3g && !isWifi)
        {
            //       Toast.makeText(context,"Please make sure your Network Connection is ON ",Toast.LENGTH_LONG).show();
        }
        else
        {
            avail = true;
        }

        // New Code

        // // TODO: 4/5/2019 Please Remove
        avail = true;

        return avail;

    }


    // Getting Image URI from Bitmap

    public static Uri getImageUri (Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    // Detect Rotation
    private static int getRotation (Context context,Uri selectedImage) {

        int rotation = 0;
        ContentResolver content = context.getContentResolver();

        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { "orientation", "date_added" },
                null, null, "date_added desc");

        if (mediaCursor != null && mediaCursor.getCount() != 0) {
            while(mediaCursor.moveToNext()){
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
    }

    public static void printHashKey (Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Keyhash", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.d("Keyhash", "printHashKey()", e);
        } catch (Exception e) {
            Log.d("Keyhash", "printHashKey()", e);
        }
    }


    //
     public static String create_base64 (Bitmap bitmap){
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
         byte[] byteArray = byteArrayOutputStream .toByteArray();
         String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
         return encoded;
     }

    public static Bitmap scaleBitmap (Bitmap bitmapToScale, float newWidth, float newHeight) {
        if(bitmapToScale == null)
            return null;
//get the original width and height
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
// create a matrix for the manipulation
        Matrix matrix = new Matrix();

// resize the bit map
        matrix.postScale(newWidth / width, newHeight / height);

// recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
    }


    public static String getFileNameFromURL (String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    public static String parseDateToddMMyyyy (String time, Context context) {

        String inputPattern = "dd-MM-yyyy HH:mm:ss";
        String outputPattern = "yyyy-MM-dd HH:mm:ss";

        //  yyyy-MM-dd HH:mm:ss

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //  Methods.toast_msg(context,"" + e.toString());
        }
        //Methods.toast_msg(context,"" + str);
        return str;
    }

    public static String parseDateToddMMyyyy_only (String time, Context context) {

        String inputPattern = "dd-MM-yyyy HH:mm:ss";
        String outputPattern = "yyyy-MM-dd";

        //  yyyy-MM-dd HH:mm:ss

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            //  Methods.toast_msg(context,"" + e.toString());
        }
        //Methods.toast_msg(context,"" + str);
        return str;
    }



    public static int getMonth (String date) throws ParseException{
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    public static String getMonth_name (String date) throws ParseException{
        Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("MMMM").format(cal.getTime());
        String final_date = monthName;
        return final_date;
    }

    // Replace Special Characters From String

    public static String replaceString(String string) {
        return string.replaceAll("[;\\/:*?\"<>|&']","");
    }



}
