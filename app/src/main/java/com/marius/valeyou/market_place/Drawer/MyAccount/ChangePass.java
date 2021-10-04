package com.marius.valeyou.market_place.Drawer.MyAccount;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangePass extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    TextView change_password;
    EditText edit_old_password, edit_new_password, edit_confirm_password;
    Toolbar header;
    TextInputLayout curr_pass_til_id, new_pass_til_id, con_new_pass_til_id, pass_til_id, con_pass_til_id;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        pd = new ProgressDialog(ChangePass.this);
        pd.setMessage(getResources().getString(R.string.loading));
        curr_pass_til_id = findViewById(R.id.curr_pass_til_id);
        new_pass_til_id = findViewById(R.id.new_pass_til_id);
        con_new_pass_til_id = findViewById(R.id.con_new_pass_til_id);


        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, ChangePass.this);

            curr_pass_til_id.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            new_pass_til_id.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            con_new_pass_til_id.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));



        }catch (Exception v){

        } // End Catch of changing Toolbar Color


        edit_old_password = findViewById(R.id.current_password);
        edit_new_password = findViewById(R.id.new_password);
        edit_confirm_password = findViewById(R.id.confirm_password);

        change_password = findViewById(R.id.changepass_tv_id);
        change_password.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));




        back = (ImageView) findViewById(R.id.back_id);
        back.setOnClickListener(this);
        change_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_id:
                finish();
                break;
            case R.id.changepass_tv_id:
                change_password();
                break;
        }
    }

        public void change_password(){

        if(edit_old_password.getText().toString().length() == 0){
            edit_old_password.setError("Please fill this");
        }else if(edit_new_password.getText().toString().length() == 0){
            edit_new_password.setError("Please fill this");
        }else if(edit_confirm_password.getText().toString().length() == 0) {
            edit_confirm_password.setError("Please fill this");
        }else if(!edit_new_password.getText().toString().equals("" + edit_confirm_password.getText().toString())){

           Methods.alert_dialogue(ChangePass.this,"Info","Confirm Password and new Password are not Same.");


        }else{
            // Calling API

            try{
                //JSONObject sendObj = new JSONObject("{ '': '' }");

                pd.show();

                //  JSONObject sendObj = new JSONObject();
                //sendObj.put("" , "");
                JSONObject sendObj = new JSONObject();
                sendObj.put("user_id","" + SharedPrefrence.get_user_id_from_json(ChangePass.this));
                sendObj.put("old_password","" + edit_old_password.getText().toString());
                sendObj.put("new_password","" + edit_new_password.getText().toString());

                Volley_Requests.New_Volley(
                        ChangePass.this,
                        "" + API_LINKS.API_Change_Password,
                        sendObj,
                        "OK",

                        new CallBack() {
                            @Override
                            public void Get_Response(String requestType, JSONObject response) {

                                pd.hide();
                               Methods.Log_d_msg(ChangePass.this, response.toString());







                            }
                        }


                );



            }catch (Exception v){
                pd.hide();
                Methods.Log_d_msg(ChangePass.this,"" + v.toString());
            }


        }


    }




}
