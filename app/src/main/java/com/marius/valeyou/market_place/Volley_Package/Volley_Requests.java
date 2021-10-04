package com.marius.valeyou.market_place.Volley_Package;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Drawer;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;

import org.json.JSONObject;


public class Volley_Requests {
    ProgressDialog pDialog;
    public static void API_Calling(final Context context, final String API_link, final JSONObject Send_Data, final String flag){
         Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

                Variables.pDialog.hide();

                Variables.res = response.toString();


//                Methods.alert_dialogue(
//                        context,
//                        "Res",
//                        "" + response.toString()
//                );
               // Methods.toast_msg(context,"Res " + response.toString());

                if(flag.equals("" + Variables.flag_Sign_up)){
                    // TODo: ==> Handle Sign Up  Response
                        try{
                            String code = response.getString("code");
                            if(code.equals("200")){
                                // Successfully Sign Up , Go to Drawer Activity
                                JSONObject msg_obj = response.getJSONObject("msg");
                               // JSONObject user_obj = msg_obj.getJSONObject("User");
//                                Methods.toast_msg(context,
//                                        "" + user_obj.toString() + " Flag " + flag);
                            /// Save data into Shared Prefrence

                                JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                                SharedPrefrence.save_info_share(
                                        context,
                                        user_obj.toString(),
                                        SharedPrefrence.shared_user_login_detail_key
                                );

                                Intent sendIntent = new Intent(context, Drawer.class);
                                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(sendIntent);


                            }else if(code.equals("201")){

//                                Methods.alert_dialogue(
//                                        context,
//                                        "Messege",
//                                        "" + response.getString("msg") );

                                // Calling API for Sign IN
                                String email = Send_Data.getString("email");
                                String password = Send_Data.getString("password");
                               // Methods.toast_msg(context,"" + email + " " + password);

                                // Calling a SignIn Method.
                                API_Calling_methods.Sign_In(
                                  "" + email,
                                        "" + password,
                                        context,
                                        "" + Variables.flag_login



                                );



                            }

                        }catch (Exception si){
                          //  Methods.toast_msg(context,"Error " + flag + " Body: " + si.toString());
                        }


                }else if(flag.equals("" + Variables.flag_login)){
                     // If Flag equal to Login.

                    try{
                        if(response.getString("code").equals("200")){
                            // Succcessfully Login
                            JSONObject msg_obj = response.getJSONObject("msg");
                           // JSONObject user_obj = msg_obj.getJSONObject("User");
//                            Methods.toast_msg(
//                                    context,
//                                    "" + user_obj.toString() + " Flag " + flag);
                            // Save into Shared Pref....
                            JSONObject user_obj = response.getJSONObject("msg").getJSONObject("User");

                            SharedPrefrence.save_info_share(
                                    context,
                                    user_obj.toString(),
                                    SharedPrefrence.shared_user_login_detail_key
                            );
                            Intent sendIntent = new Intent(context, Drawer.class);
                            sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(sendIntent);


                        }

                    }catch (Exception b){

                    }



                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.pDialog.hide();
                //            Variables.res = "Error "+error;

                // Methods.Open_Activities_from_APIs_method(flag,context);

                // Methods.toast_msg(context,"Msg " + error.toString() + " Flag " + flag);

                // todo Volley error Display here

              //    Methods.alert_dialogue(context,"Info", ""+error.toString() + "\n Flag "+flag);

            }
        };

        /// ENd Interface


        Variables.pDialog = new ProgressDialog(context);
        Variables.pDialog.setMessage(context.getResources().getString(R.string.loading_text));
        Variables.pDialog.setCancelable(false);
        Variables.pDialog.show();



        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,context);
        JSONObject sendObj = null;


        Variables.mVolleyService.postDataVolley("POSTCALL", API_link, Send_Data);



    }





    // TODO: New mEthod

    public static void New_Volley(final Context context, final String API_link,  final JSONObject Send_Data, final String flag, final CallBack get_resp ){

       // final ProgressDialog pDialog = new ProgressDialog(context);


        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {

//               pDialog.hide();
//                Variables.res = response.toString();


                get_resp.Get_Response(requestType,response);



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
               // Variables.pDialog.hide();
                //            Variables.res = "Error "+error;

                // Methods.Open_Activities_from_APIs_method(flag,context);

              //   Methods.toast_msg(context,"Msg " + error.toString() + " Flag " + flag);

                // todo Volley error Display here
               // pDialog.hide();
             //   Methods.alert_dialogue(context,"Info", ""+error.toString() + "\n Flag "+flag);

            }
        };

        /// ENd Interface

//

//        pDialog.setMessage(context.getResources().getString(R.string.loading_text));
//        pDialog.setCancelable(false);
//        pDialog.show();

       // Methods.sh


        Variables.mVolleyService = new VolleyService(Variables.mResultCallback,context);
        JSONObject sendObj = null;


        Variables.mVolleyService.postDataVolley("POSTCALL", API_link, Send_Data);


    }



}
