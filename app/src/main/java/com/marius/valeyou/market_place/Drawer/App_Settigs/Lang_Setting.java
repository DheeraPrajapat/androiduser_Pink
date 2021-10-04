package com.marius.valeyou.market_place.Drawer.App_Settigs;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;

import androidx.appcompat.app.AppCompatActivity;

public class Lang_Setting extends AppCompatActivity implements View.OnClickListener  {

    ImageView iv;
    TextView privacy_policy_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lang_setting);

        privacy_policy_show = findViewById(R.id.privacy_policy_show);
        try{

            //privacy_policy_show.setText(Html.fromHtml(Variables.App_Privacy_Policy));
            ///Methods.toast_msg(Lang_Setting.this,"" +  Html.fromHtml(Variables.App_Privacy_Policy));

           //  get our html content
           //// String htmlAsString = getString(R.string.html);      // used by WebView
           // Spanned htmlAsSpanned = Html.fromHtml(" <![CDATA[" + Variables.App_Privacy_Policy + " ]]>"); // used by TextView
            // set the html content on a TextView
           // TextView textView = (TextView) findViewById(R.id.textView);
            //privacy_policy_show.setText(htmlAsSpanned);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                privacy_policy_show.setText(Variables.App_Privacy_Policy);
//            } else {
//                privacy_policy_show.setText(Variables.App_Privacy_Policy);
//            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                privacy_policy_show.setText(Html.fromHtml(Variables.App_Privacy_Policy, Html.FROM_HTML_MODE_LEGACY));
            } else{
                privacy_policy_show.setText(Html.fromHtml(Variables.App_Privacy_Policy));
            }



//        WebView webView = (WebView) findViewById(R.id.webview);
//        webView.loadDataWithBaseURL(null, String.valueOf(htmlAsSpanned), "text/html", "utf-8", null);



        }catch (Exception v){

        }

//                    Spannable html;
//
//                   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                       html = (Spannable) Html.fromHtml(Variables.App_Privacy_Policy , Html.FROM_HTML_MODE_LEGACY, NewsDetail_F.this, null);
//                   } else {
//                       html = (Spannable) Html.fromHtml(Variables.App_Privacy_Policy , Lang_Setting.this, null);
//                   }

     //   privacy_policy_show.setText(html);



                iv = (ImageView) findViewById(R.id.back_id);

        iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;
        }
    }
}
