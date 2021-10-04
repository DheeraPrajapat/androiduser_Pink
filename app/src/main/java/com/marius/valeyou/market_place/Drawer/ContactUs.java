package com.marius.valeyou.market_place.Drawer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PHONE_CALL = 1 ;
    ImageView iv;
    Toolbar header;
    ImageView make_call, make_call_1, make_call_2 , email_us;
    LinearLayout Li_door_step,  Li_door_call, Li_door_call_2;
    TextView email_1, customer_care, phone_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        header = findViewById(R.id.tb_id);

        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, ContactUs.this);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color



        phone_num = findViewById(R.id.phone_num);
        email_1 = findViewById(R.id.email_1);
        customer_care = findViewById(R.id.customer_care);
        customer_care.setText("" + Variables.App_Config_contact_us_phone_number);
        email_1.setText("" + Variables.App_Config_contact_us_email);
        phone_num.setText("" + Variables.App_Config_contact_us_phone_number);
        make_call = findViewById(R.id.make_call);
        make_call_1 = findViewById(R.id.make_call_1);
        make_call_2 = findViewById(R.id.make_call_2);
        email_us = findViewById(R.id.email);

        Li_door_step = findViewById(R.id.door_step) ;
        Li_door_call = findViewById(R.id.door_call) ;
        Li_door_call_2 = findViewById(R.id.door_call_2);

//        Methods.toast_msg(ContactUs.this,"Status " + Variables.App_status_bar_color_code);

        if(Variables.App_Config_contact_us_email.equals("") || Variables.Var_App_Config_contact_us_phone_number.equals("")){
            Li_door_step.setVisibility(View.GONE);
        }


        if(Variables.Var_App_Config_contact_us_phone_number.equals("")){
            Li_door_call.setVisibility(View.GONE);
            Li_door_call_2.setVisibility(View.GONE);
        }


        LinearLayout layout = (LinearLayout)findViewById(R.id.imageLayout);

        try{
            JSONObject response = new JSONObject(Variables.Var_App_Config);
            JSONArray msg = response.getJSONArray("msg");
            for(int i=0; i< msg.length();i++) {
                JSONObject json = msg.getJSONObject(i);
                final JSONObject Slider_obj = json.getJSONObject("Setting");
                Slider_obj.getString("image");

                if(Slider_obj.getString("type").equals("contact_us_facebook")){

                    //Methods.toast_msg(ContactUs.this,"" + Slider_obj.getString("type"));
                    ImageView image = new ImageView(this);
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(180,160));
                    //image.setImageResource(R.drawable.circle_img);

                    Picasso.get()
                            .load(API_LINKS.BASE_URL + Slider_obj.getString("image"))
                            .placeholder(R.drawable.circle_img)
                            .error(R.drawable.circle_img)
                            .into(image);


                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    image.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try{
                                openBrowser(ContactUs.this,"" + Slider_obj.getString("source"));
                            }catch (Exception vq){
                                Methods.toast_msg(ContactUs.this,"" + vq.toString());
                            }

                        }
                    });
                    // Adds the view to the layout
                    layout.addView(image);


                }else if(Slider_obj.getString("type").equals("contact_us_twiter")){
                    // Header bg Color
                   // Methods.toast_msg(ContactUs.this,"" + Slider_obj.getString("type"));

                    ImageView image = new ImageView(this);
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(180,160));
                    //image.setImageResource(R.drawable.circle_img);
                    // Use Picasoo
                    Picasso.get()
                            .load(API_LINKS.BASE_URL  + Slider_obj.getString("image"))
                            .placeholder(R.drawable.circle_img)
                            .error(R.drawable.circle_img)
                            .into(image);

                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    image.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try{
                                openBrowser(ContactUs.this,"" + Slider_obj.getString("source"));
                            }catch (Exception vq){
                                Methods.toast_msg(ContactUs.this,"" + vq.toString());
                            }

                        }
                    });


                    // Adds the view to the layout
                    layout.addView(image);

                }else if(Slider_obj.getString("type").equals("contact_us_instagram")){
                    // Header Font Color
                   // Methods.toast_msg(ContactUs.this,"" + Slider_obj.getString("type"));

                    ImageView image = new ImageView(this);
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(180,160));
                    //image.setImageResource(R.drawable.circle_img);

                    Picasso.get()
                            .load(API_LINKS.BASE_URL  + Slider_obj.getString("image"))
                            .placeholder(R.drawable.circle_img)
                            .error(R.drawable.circle_img)
                            .into(image);

                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    image.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try{
                                openBrowser(ContactUs.this,"" + Slider_obj.getString("source"));
                            }catch (Exception vq){
                                Methods.toast_msg(ContactUs.this,"" + vq.toString());
                            }

                        }
                    });

                    // Adds the view to the layout
                    layout.addView(image);

                }else if(Slider_obj.getString("type").equals("contact_us_linkdin")){
                    // Header Font Color
                   // Methods.toast_msg(ContactUs.this,"" + Slider_obj.getString("type"));

                    ImageView image = new ImageView(this);
                    image.setLayoutParams(new android.view.ViewGroup.LayoutParams(180,160));
                    //image.setImageResource(R.drawable.circle_img);

                    Picasso.get()
                            .load(API_LINKS.BASE_URL  + Slider_obj.getString("image"))
                            .placeholder(R.drawable.circle_img)
                            .error(R.drawable.circle_img)
                            .into(image);

                    image.setMaxHeight(20);
                    image.setMaxWidth(20);

                    image.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            try{
                                openBrowser(ContactUs.this,"" + Slider_obj.getString("source"));
                            }catch (Exception vq){
                                Methods.toast_msg(ContactUs.this,"" + vq.toString());
                            }

                        }
                    });


                    // Adds the view to the layout
                    layout.addView(image);

                }








            } // End for Loop


        }catch (Exception b){
            Methods.toast_msg(ContactUs.this,"" + b.toString());
        }



        Methods.Change_header_color(header, ContactUs.this);
        iv = (ImageView) findViewById(R.id.back_id);

        iv.setOnClickListener(this);

        Li_door_step.setOnClickListener(this);
        Li_door_call.setOnClickListener(this);
        Li_door_call_2.setOnClickListener(this);
        //Li_door_step,  Li_door_call, Li_door_call_2


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;
            case R.id.make_call:
                // Make A call
                make_call();
                break;
            case R.id.door_step:
                // Make A call
                make_call();
                break;
            case R.id.door_call_2:
                // Make A call
                make_call();
                break;
            case R.id.door_call:
                // Send an Email
                Send_EMail();
                break;
        }
    }

    public void make_call(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ Variables.Var_App_Config_contact_us_phone_number));//change the number
            startActivity(callIntent);
            //startActivity(intent);
        }

//Methods.toast_msg(ContactUs.this,"" + Variables.Var_App_Config_contact_us_phone_number);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+ Variables.App_Config_contact_us_phone_number));//change the number
        startActivity(callIntent);
    }

    public void Send_EMail(){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Variables.App_Config_contact_us_email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Here is Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Here is Body");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }


    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    public static void openBrowser (final Context context, String url) {

        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, "Choose browser"));// Choose browser is arbitrary :)

    }


}
