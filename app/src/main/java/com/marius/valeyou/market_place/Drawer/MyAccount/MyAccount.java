package com.marius.valeyou.market_place.Drawer.MyAccount;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Cash_Blnce;
import com.marius.valeyou.market_place.Drawer.MyAds.MyAds;
import com.marius.valeyou.market_place.Drawer.OrdersPayments;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccount extends AppCompatActivity implements View.OnClickListener {

    ImageView back, profile, menu;
    RelativeLayout order_rl, offer_rl, ads_rl, dashboard_rl, cash_rl;
    String user_info;
    TextView header_tv_id, name;
    LinearLayout gradient_RL;
    SimpleDraweeView profile_pic;

    // TODO: (MyAccount.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic() {

        try {
            gradient_RL.setBackground(Methods.getColorScala());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }
        } catch (Exception b) {

        } // End Try catch Body

    } // End method to change Color Dynamically


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        user_info = SharedPrefrence.get_offline(MyAccount.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );

        header_tv_id = findViewById(R.id.header_tv_id);
        profile_pic = findViewById(R.id.profile_pic);
        gradient_RL = findViewById(R.id.gradient_RL);
        name = findViewById(R.id.name);


        try {
            JSONObject user_obj = new JSONObject(user_info);

            //name = user_obj.getString("full_name");
            //email = user_obj.getString("email");

            //user_name.setText("" + name);
            header_tv_id.setText("" + user_obj.getString("email"));
            name.setText("" + user_obj.getString("full_name"));
//            Picasso.get()
//                    .load(SharedPrefrence.get_user_img_org(MyAccount.this))
//                    .placeholder(R.drawable.ic_profile_gray)
//                    .error(R.drawable.ic_profile_gray)
//                    .rotate(90)
//                    .into(profile_pic);


            // Fresco Library

            ImageRequest request =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(MyAccount.this)))
                            .setProgressiveRenderingEnabled(false)
                            .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(false)
                    .build();

            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setRoundAsCircle(true);
            //profile_pic.setRotation(90);
            profile_pic.getHierarchy().setPlaceholderImage(R.drawable.ic_profile_gray);
            profile_pic.getHierarchy().setFailureImage(R.drawable.ic_profile_gray);
            profile_pic.getHierarchy().setRoundingParams(roundingParams);
            profile_pic.setController(controller);


        } catch (Exception b) {
            Methods.toast_msg(MyAccount.this, "Please try again later. " + user_info + " " + b.toString());
        }


        back = (ImageView) findViewById(R.id.back_id);
        profile = (ImageView) findViewById(R.id.profile_id);
        menu = (ImageView) findViewById(R.id.menu_id);

        if (user_info != null) {
            // If user is Login

        } else {
            // If user is not Login
            menu.setVisibility(View.GONE);
        }

        order_rl = (RelativeLayout) findViewById(R.id.order_rl_id);
        offer_rl = (RelativeLayout) findViewById(R.id.offer_rl_id);
        ads_rl = (RelativeLayout) findViewById(R.id.ads_rl_id);
        dashboard_rl = (RelativeLayout) findViewById(R.id.dashboard_rl_id);
        cash_rl = (RelativeLayout) findViewById(R.id.cash_rl_id);

        back.setOnClickListener(this);
        profile.setOnClickListener(this);
        menu.setOnClickListener(this);
        order_rl.setOnClickListener(this);
        offer_rl.setOnClickListener(this);
        ads_rl.setOnClickListener(this);
        dashboard_rl.setOnClickListener(this);
        cash_rl.setOnClickListener(this);

        Change_Color_Dynmic();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_id:
                finish();
                break;

            case R.id.profile_id:
                startActivity(new Intent(MyAccount.this, UserProfile.class));
                break;

            case R.id.menu_id:
                PopupMenu popup = new PopupMenu(MyAccount.this, v);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.setGravity(Gravity.TOP | Gravity.END);
                }
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.editaccount_id:
                                startActivity(new Intent(MyAccount.this, EditProfile.class));
                                break;
                            case R.id.changepass_id:
//                                Toast.makeText(MyAccount.this, ""+ item, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MyAccount.this, ChangePass.class));

                                break;
                            case R.id.logout_id:
                                SharedPrefrence.logout_user(MyAccount.this);
                                break;
                        }
                        return true;
                    }
                });
                break;

            case R.id.order_rl_id:
                startActivity(new Intent(MyAccount.this, OrdersPayments.class));
                break;

            case R.id.offer_rl_id:
                startActivity(new Intent(MyAccount.this, OffersMade.class));
                break;

            case R.id.ads_rl_id:
                startActivity(new Intent(MyAccount.this, MyAds.class));
                break;

            case R.id.dashboard_rl_id:
                break;

            case R.id.cash_rl_id:
                startActivity(new Intent(MyAccount.this, Cash_Blnce.class));
                break;
        }
    }

    public void get_User_Profile() {
        try {
            //JSONObject sendObj = new JSONObject("{ '': '' }");


            JSONObject sendObj = new JSONObject();
            sendObj.put("user_id", "1");


            Volley_Requests.New_Volley(
                    MyAccount.this,
                    "" + API_LINKS.API_User_PROFILE,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Methods.toast_msg(PostFreeAd.this,"From Class " + requestType+  " " + response.toString());
                            // Maipulate Response
                            // pullToRefresh.setEnabled(true);


                        }
                    }


            );


        } catch (Exception v) {

        }


    }


    @Override
    public void onStart() {
        super.onStart();  // Always call the superclass method first
        try {
            user_info = SharedPrefrence.get_offline(MyAccount.this,
                    "" + SharedPrefrence.shared_user_login_detail_key
            );
            if (user_info != null) {
                // If User is Already Login
                try {
                    JSONObject user_obj = new JSONObject(user_info);
                    // name_text = user_obj.getString("full_name");
                    name.setText("" + user_obj.getString("full_name"));
                } catch (Exception n) {

                }
            }

//            Picasso.get()
//                    .load(SharedPrefrence.get_user_img_org(MyAccount.this))
//                    .placeholder(R.drawable.ic_profile_gray)
//                    .error(R.drawable.ic_profile_gray)
//                    .rotate(90)
//                    .into(profile_pic);

            // Fresco Image Code

            ImageRequest request =
                    ImageRequestBuilder.newBuilderWithSource(Uri.parse(SharedPrefrence.get_user_img_org(MyAccount.this)))
                            .setProgressiveRenderingEnabled(false)
                            .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(false)
                    .build();

            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setRoundAsCircle(true);
            //profile_pic.setRotation(90);
            profile_pic.getHierarchy().setRoundingParams(roundingParams);
            profile_pic.setController(controller);


        } catch (Exception v) {

        }


    }


}
