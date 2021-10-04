package com.marius.valeyou.market_place.Login_Register;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Phone_Verification extends AppCompatActivity implements View.OnClickListener {
    Toolbar header;
    Button btn;
    String name, email, password, mobile_num, verify_code;
    ImageView back;

    TextView tv1,tv2,tv3,tv4,edit_num;

    LinearLayout et1_ll,et2_ll,et3_ll,et4_ll;

    RelativeLayout rl1,rl2;

    EditText et1,et2,et3,et4;
    // TODO: Phone_Verification.java ==> Change Dynamic color
    public void Change_color_Dynamically (){
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
//            btn = findViewById(R.id.btn_id);
//            btn.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            Methods.Change_header_color(header, Phone_Verification.this);
        }catch (Exception v){

        } // End Catch of changing Toolbar Color
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        back =  findViewById(R.id.back_id);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                finish();
            }
        });


        METHOD_findviewbyid();
        METHOD_onclicklistner();
        METHOD_oneminutetimer();
        METHOD_modifyedittext();




        // This Method has also init Views
        Change_color_Dynamically();
    }


    public void METHOD_findviewbyid(){
        tv1 = (TextView) findViewById(R.id.tv1_id);
        tv2 = (TextView) findViewById(R.id.tv2_id);
        tv3 = (TextView) findViewById(R.id.tv3_id);
        tv4 = (TextView) findViewById(R.id.tv4_id);
        edit_num = (TextView) findViewById(R.id.edit_num_id);

        //back = (LinearLayout) findViewById(R.id.back_ll_id);
        et1_ll = (LinearLayout) findViewById(R.id.ll1_id);
        et2_ll = (LinearLayout) findViewById(R.id.ll2_id);
        et3_ll = (LinearLayout) findViewById(R.id.ll3_id);
        et4_ll = (LinearLayout) findViewById(R.id.ll4_id);

        rl1 = (RelativeLayout) findViewById(R.id.rl1_id);
        rl2 = (RelativeLayout) findViewById(R.id.rl2_id);

        et1 = (EditText) findViewById(R.id.et1_id);
        et2 = (EditText) findViewById(R.id.et2_id);
        et3 = (EditText) findViewById(R.id.et3_id);
        et4 = (EditText) findViewById(R.id.et4_id);
    }




    public void METHOD_onclicklistner(){
        back.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        edit_num.setOnClickListener(this);
        back.setOnClickListener(this);
    }




    public void METHOD_oneminutetimer(){

        rl1.setVisibility(View.VISIBLE);
        rl2.setVisibility(View.GONE);

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                tv1.setText("Resend Code 00:"+ l/1000);
                tv2.setText("Call Me 00:"+ l/1000);
            }

            @Override
            public void onFinish() {
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
            }

        }.start();

    }




    @SuppressLint("ClickableViewAccessibility")
    public void METHOD_modifyedittext(){

        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                et1_ll.setBackgroundResource(R.drawable.d_bottom_black_line);
//                et2_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et3_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et4_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);

                et1.setFocusableInTouchMode(true);
                et1.setFocusable(true);
                et1.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et1, InputMethodManager.SHOW_IMPLICIT);

                return true;
            }
        });

        et2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                et1_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et2_ll.setBackgroundResource(R.drawable.d_bottom_black_line);
//                et3_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et4_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);

                et2.setFocusableInTouchMode(true);
                et2.setFocusable(true);
                et2.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et2, InputMethodManager.SHOW_IMPLICIT);

                return true;
            }
        });

        et3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                et1_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et2_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et3_ll.setBackgroundResource(R.drawable.d_bottom_black_line);
//                et4_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);

                et3.setFocusableInTouchMode(true);
                et3.setFocusable(true);
                et3.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et3, InputMethodManager.SHOW_IMPLICIT);

                return true;
            }
        });

        et4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                et1_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et2_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et3_ll.setBackgroundResource(R.drawable.d_bottom_gray_bold_line);
//                et4_ll.setBackgroundResource(R.drawable.d_bottom_black_line);

                et4.setFocusableInTouchMode(true);
                et4.setFocusable(true);
                et4.requestFocus();


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et4, InputMethodManager.SHOW_IMPLICIT);

                return true;
            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = et1.length();
                if (len == 1) {
                    et1_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                    et2.setFocusableInTouchMode(true);
                    et2.setFocusable(true);
                    et2.requestFocus();
                    et2_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });


        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = et2.length();
                if (len == 1) {
                    et2_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                    et3.setFocusableInTouchMode(true);
                    et3.setFocusable(true);
                    et3.requestFocus();
                    et3_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = et3.length();
                if (len == 1) {
                    et3_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                    et4.setFocusableInTouchMode(true);
                    et4.setFocusable(true);
                    et4.requestFocus();
                    et4_ll.setBackgroundResource(R.drawable.bottom_gray_line);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = et4.length();
                if (len == 1) {
                    //startActivity(new Intent(Phone_Verification.this, CreatePassword.class));
                    Methods.toast_msg(Phone_Verification.this,"Calling API for SignUp");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_id:
                finish();
                break;
            case R.id.tv3_id:
                METHOD_oneminutetimer();
                break;


            case R.id.tv4_id:
                break;


            case R.id.edit_num_id:
                finish();
                break;

        }
    }




}
