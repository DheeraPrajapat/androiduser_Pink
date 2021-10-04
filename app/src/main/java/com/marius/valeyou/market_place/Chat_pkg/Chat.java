package com.marius.valeyou.market_place.Chat_pkg;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Adapters.Msg_adapter;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_Data_Model;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_GetSet;
import com.marius.valeyou.market_place.Chat_pkg.Fragments.See_Full_Image_F;
import com.marius.valeyou.market_place.Chat_pkg.Videos.Chat_Send_file_Service;
import com.marius.valeyou.market_place.Chat_pkg.Videos.Play_Video_F;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Chat extends AppCompatActivity {
    TextView send_text;
    EditText msg;
    RecyclerView Msg_RV;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    public static SharedPreferences download_pref;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    // Firebase reference1, reference2;
    private List<Chat_GetSet> mChats=new ArrayList<>();
    ImageView iv, ic_add, ic_img, ic_voice  ;
    private Uri filePath;
    Context context;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    public static String token="null";
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private final int PICK_IMAGE_REQUEST = 71;
    Msg_adapter msg_adp;
    List<Chat_Data_Model> Msg_list = new ArrayList<>();
    FirebaseDatabase fire_db;
    List<String> chat_list = new ArrayList<String>();
    String Receiver_name = "Rec Name";
    String Receiver_pic = "Image";
    Query query_getchat;
    DatabaseReference rootref;
    String Receiverid = "890";
    ProgressBar p_bar;
    private DatabaseReference Adduser_to_inbox;
    private DatabaseReference mchatRef_reteriving;
    private DatabaseReference send_typing_indication;
    private DatabaseReference receive_typing_indication;
   // Query query_getchat;
    ImageView profileimage;
    public static String uploading_image_id="none";
    ProgressDialog pd;
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(msg_adp!=null){
                msg_adp.notifyDataSetChanged();
            }

        }
    };


    TextView t_user_rec_name;

    Toolbar header;

    // TODO: (Chat.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic (){
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, Chat.this);



        }catch (Exception v){

        } // End Catch of changing Toolbar Color


    } // End method to change Color Dynamically


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_one);

        context=Chat.this;
        // Progres bar;
        pd = new ProgressDialog(context);
        pd.setMessage(getResources().getString(R.string.loading));
        pd.setCancelable(false);
        Msg_RV  = findViewById(R.id.chatlist);
        Msg_RV.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try{
                    EdgeChanger.setEdgeGlowColor(Msg_RV, Color.parseColor(Variables.Var_App_Config_header_bg_color));
                }catch (Exception v){

                } // End Catch Statement
            }
        });

        t_user_rec_name = findViewById(R.id.user_name);

        p_bar = findViewById(R.id.progress_bar);


        Variables.user_id = SharedPrefrence.get_user_id_from_json(Chat.this);
        Variables.user_name = SharedPrefrence.get_user_name_from_json(Chat.this);
        Variables.user_pic = SharedPrefrence.get_user_img_from_json(Chat.this);

        Log.d("resp",Variables.user_id+" "+Variables.user_name+" "+Variables.user_pic);

        // Get vallues from Previou Activity

        Intent intent = getIntent();
        Receiverid = intent.getStringExtra("receiver_id");
        Receiver_name = intent.getStringExtra("receiver_name");
        Receiver_pic = intent.getStringExtra("receiver_pic");
        t_user_rec_name.setText("" + Receiver_name);

        // Call API to get Recever updated Data
        get_latest_user_profile(Receiverid);


       findViewById(R.id.uploadimagebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectfile();

            }
        });
        download_pref = context.getSharedPreferences(Variables.download_pref,Context.MODE_PRIVATE);


        rootref = FirebaseDatabase.getInstance().getReference();
        Adduser_to_inbox= FirebaseDatabase.getInstance().getReference();



        iv = (ImageView) findViewById(R.id.back_id);


        send_text = findViewById(R.id.sendbtn);

        msg = findViewById(R.id.msgedittext);

        msg.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                //text = mEdit.toString();



            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(msg.getText().toString().length() == 0){
                    // If lenght is zerooo
                    send_text.setVisibility(View.GONE);
                    show_media_buttons();
                }else if(msg.getText().toString().length() > 0){
                    // if length is greater than 0
                    send_text.setVisibility(View.VISIBLE);
                    hide_media_buttons();


                }

            }
        });

               iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage(msg.getText().toString());
                msg.setText("");
               // send_msg(msg.getText().toString());
            }
        });



        Msg_RV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Chat.this);
        linearLayoutManager.setStackFromEnd(true);
        Msg_RV.setLayoutManager(linearLayoutManager);

        msg_adp = new Msg_adapter(mChats, Variables.user_id, context, new Msg_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chat_GetSet item, View v) {
                if(item.getType().equals("image"))
                    OpenfullsizeImage(item);

                if(item.getType().equals("video")){

                    File fullpath = new File(Environment.getExternalStorageDirectory() +"/Chatbuzz/"+item.chat_id+".mp4");
                    if(fullpath.exists()) {
                        OpenVideo(fullpath.getAbsolutePath());
                    }
                    else {
                        if(download_pref.getString(item.getChat_id(),"").equals("")) {
                            long downlodid = Methods.DownloadData_forChat(context, item);
                            download_pref.edit().putString(item.getChat_id(), "" + downlodid).commit();
                            msg_adp.notifyDataSetChanged();
                        }
                    }

                }




            }



        } ,new Msg_adapter.OnLongClickListener() {
            @Override
            public void onLongclick(Chat_GetSet item, View view) {


            }
        });

        Msg_RV.setAdapter(msg_adp);


        Change_Color_Dynmic();

    }

    // Hide all media icons in sending text chat box

    public void hide_media_buttons(){

    }

    // Show all media icons in sending text chat box
    public void show_media_buttons(){

    }

    ValueEventListener valueEventListener;

    ChildEventListener eventListener;
    @Override
    public void onStart() {
        super.onStart();

        Variables.Opened_Chat_id = Variables.user_id;

        mChats.clear();
        mchatRef_reteriving = FirebaseDatabase.getInstance().getReference();
        query_getchat = mchatRef_reteriving.child("chat").child(Variables.user_id + "-" + Receiverid);


        // this will get all the messages between two users
        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {

                    Log.d("resp", dataSnapshot.toString());
                    Chat_GetSet model = dataSnapshot.getValue(Chat_GetSet.class);
                    mChats.add(model);
                    msg_adp.notifyDataSetChanged();
                    Msg_RV.scrollToPosition(mChats.size() - 1);

                } catch (Exception ex) {
                    Log.d("", ex.getMessage());

                }
                ChangeStatus();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot != null && dataSnapshot.getValue() != null) {

                    try {
                        Chat_GetSet model = dataSnapshot.getValue(Chat_GetSet.class);

                        for (int i = mChats.size() - 1; i >= 0; i--) {
                            if (mChats.get(i).getTimestamp().equals(dataSnapshot.child("timestamp").getValue())) {
                                mChats.remove(i);
                                mChats.add(i, model);
                                break;
                            }
                        }
                        msg_adp.notifyDataSetChanged();
                    } catch (Exception ex) {
                        Log.d("", ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("", databaseError.getMessage());
            }
        };

        // this will check the two user are do chat before or not
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Variables.user_id + "-" + Receiverid)){
                    p_bar.setVisibility(View.GONE);
                    mchatRef_reteriving.removeEventListener(valueEventListener);
                }
                else {
                     p_bar.setVisibility(View.GONE);
                    mchatRef_reteriving.removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };



        query_getchat.limitToLast(20).addChildEventListener(eventListener);

        mchatRef_reteriving.child("chat").addValueEventListener(valueEventListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(query_getchat!=null && eventListener!=null)
        query_getchat.removeEventListener(eventListener);
    }

    // this method will change the status to ensure that
    // user is seen all the message or not (in both chat node and Chatinbox node)
    public void ChangeStatus(){

        final Date c = Calendar.getInstance().getTime();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference.child("chat").child(Receiverid+"-"+Variables.user_id).orderByChild("status").equalTo("0");
        final Query query2 = reference.child("chat").child(Variables.user_id+"-"+Receiverid).orderByChild("status").equalTo("0");

        final DatabaseReference inbox_change_status_1=reference.child("Inbox").child(Variables.user_id+"/"+Receiverid);
        final DatabaseReference inbox_change_status_2=reference.child("Inbox").child(Receiverid+"/"+Variables.user_id);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot nodeDataSnapshot : dataSnapshot.getChildren()) {
                    if(!nodeDataSnapshot.child("sender_id").getValue().equals(Variables.user_id)){
                        String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                        String path = "chat" + "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        result.put("time", Variables.df2.format(c));
                        reference.child(path).updateChildren(result);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot nodeDataSnapshot : dataSnapshot.getChildren()) {
                    if(!nodeDataSnapshot.child("sender_id").getValue().equals(Variables.user_id)){
                        String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                        String path = "chat" + "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        result.put("time",Variables.df2.format(c));
                        reference.child(path).updateChildren(result);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        inbox_change_status_1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("rid").getValue().equals(Receiverid)){
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        inbox_change_status_1.updateChildren(result);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        inbox_change_status_2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("rid").getValue().equals(Receiverid)){
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        inbox_change_status_2.updateChildren(result);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    // this will add the new message in chat node and update the ChatInbox by new message by present date
    public void SendMessage(final String message) {

        Date c = Calendar.getInstance().getTime();
        final String formattedDate = Variables.df.format(c);

        final String current_user_ref = "chat" + "/" + Variables.user_id + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + Variables.user_id;

        DatabaseReference reference = rootref.child("chat").child(Variables.user_id + "-" + Receiverid).push();
        final String pushid = reference.getKey();
        final HashMap message_user_map = new HashMap<>();
        message_user_map.put("chat_id",pushid);
        message_user_map.put("sender_id", Variables.user_id);
        message_user_map.put("receiver_id", Receiverid);
        message_user_map.put("sender_name", Variables.user_name);

        message_user_map.put("rec_img","" + Receiver_pic);  // Rec Pic
        message_user_map.put("pic_url","");
        message_user_map.put("lat","");
        message_user_map.put("long","");

        message_user_map.put("text", message);
        message_user_map.put("type","text");
        message_user_map.put("status", "0");
        message_user_map.put("time", "");
        message_user_map.put("timestamp", formattedDate);

        final HashMap user_map = new HashMap<>();
        user_map.put(current_user_ref + "/" + pushid, message_user_map);
        user_map.put(chat_user_ref + "/" + pushid, message_user_map);

        rootref.updateChildren(user_map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //if first message then set the visibility of whoops layout gone
                String inbox_sender_ref = "Inbox" + "/" + Variables.user_id + "/" + Receiverid;
                String inbox_receiver_ref = "Inbox" + "/" + Receiverid + "/" + Variables.user_id;



                HashMap<String, String> sendermap=new HashMap<>();
                sendermap.put("rid",Variables.user_id);
                sendermap.put("name",Variables.user_name);
                sendermap.put("msg",message);
                sendermap.put("pic",Variables.user_pic);
                sendermap.put("timestamp",formattedDate);
                sendermap.put("date",formattedDate);

                sendermap.put("sort",""+Calendar.getInstance().getTimeInMillis());
                sendermap.put("status","0");
                sendermap.put("block","0");
                sendermap.put("read","0");

                HashMap<String, String> receivermap=new HashMap<>();
                receivermap.put("rid",Receiverid);
                receivermap.put("name",Receiver_name);
                receivermap.put("msg",message);
                receivermap.put("pic",Receiver_pic);
                receivermap.put("timestamp",formattedDate);
                receivermap.put("date",formattedDate);

                receivermap.put("sort",""+Calendar.getInstance().getTimeInMillis());
                receivermap.put("status","0");
                receivermap.put("block","0");
                receivermap.put("read","0");



                HashMap both_user_map = new HashMap<>();
                both_user_map.put(inbox_sender_ref , receivermap);
                both_user_map.put(inbox_receiver_ref , sendermap);

                Adduser_to_inbox.updateChildren(both_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                       // SendPushNotification(message);

                    }
                });
            }
        });
    }


    // this method will upload the image in chhat
    public void UploadImage(ByteArrayOutputStream byteArrayOutputStream){
        byte[] data = byteArrayOutputStream.toByteArray();

        Date c = Calendar.getInstance().getTime();
        final String formattedDate = Variables.df.format(c);

        StorageReference reference= FirebaseStorage.getInstance().getReference();
        DatabaseReference dref=rootref.child("chat").child(Variables.user_id+"-"+Receiverid).push();
        final String key=dref.getKey();
        uploading_image_id=key;
        final String current_user_ref = "chat" + "/" + Variables.user_id + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + Variables.user_id;

        HashMap my_dummi_pic_map = new HashMap<>();
        my_dummi_pic_map.put("receiver_id", Receiverid);
        my_dummi_pic_map.put("sender_id", Variables.user_id);
        my_dummi_pic_map.put("sender_name", Variables.user_name);
        my_dummi_pic_map.put("chat_id",key);

        my_dummi_pic_map.put("rec_img","");
        my_dummi_pic_map.put("pic_url","none");
        my_dummi_pic_map.put("lat","");
        my_dummi_pic_map.put("long","");

        my_dummi_pic_map.put("text", "");
        my_dummi_pic_map.put("type","image");
        my_dummi_pic_map.put("status", "0");
        my_dummi_pic_map.put("time", "");
        my_dummi_pic_map.put("timestamp", formattedDate);

        HashMap dummy_push = new HashMap<>();
        dummy_push.put(current_user_ref + "/" + key, my_dummi_pic_map);
        rootref.updateChildren(dummy_push);

        reference.child("images").child(key+".jpg").putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploading_image_id="none";

                HashMap message_user_map = new HashMap<>();
                message_user_map.put("receiver_id", Receiverid);
                message_user_map.put("sender_id", Variables.user_id);
                message_user_map.put("sender_name", Variables.user_name);
                message_user_map.put("chat_id",key);

                message_user_map.put("rec_img","");
                message_user_map.put("pic_url",taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                        message_user_map.put("lat","");
                message_user_map.put("long","");

                message_user_map.put("text", "");
                message_user_map.put("type","image");
                message_user_map.put("status", "0");
                message_user_map.put("time", "");
                message_user_map.put("timestamp", formattedDate);

                HashMap user_map = new HashMap<>();

                user_map.put(current_user_ref + "/" + key, message_user_map);
                user_map.put(chat_user_ref + "/" + key, message_user_map);

                rootref.updateChildren(user_map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        String inbox_sender_ref = "Inbox" + "/" + Variables.user_id + "/" + Receiverid;
                        String inbox_receiver_ref = "Inbox" + "/" + Receiverid + "/" + Variables.user_id;


                        HashMap<String, String> sendermap=new HashMap<>();
                        sendermap.put("rid",Variables.user_id);
                        sendermap.put("name",Variables.user_name);
                        sendermap.put("msg","Send an Image");
                        sendermap.put("pic",Variables.user_pic);
                        sendermap.put("timestamp",formattedDate);
                        sendermap.put("date",formattedDate);

                        sendermap.put("sort",""+Calendar.getInstance().getTimeInMillis());
                        sendermap.put("status","0");
                        sendermap.put("block","0");
                        sendermap.put("read","0");

                        HashMap<String, String> receivermap=new HashMap<>();
                        receivermap.put("rid",Receiverid);
                        receivermap.put("name",Receiver_name);
                        receivermap.put("msg","Send an Image");
                        receivermap.put("pic",Receiver_pic);
                        receivermap.put("timestamp",formattedDate);
                        receivermap.put("date",formattedDate);

                        receivermap.put("sort",""+Calendar.getInstance().getTimeInMillis());
                        receivermap.put("status","0");
                        receivermap.put("block","0");
                        receivermap.put("read","0");




                        HashMap both_user_map = new HashMap<>();
                        both_user_map.put(inbox_sender_ref , receivermap);
                        both_user_map.put(inbox_receiver_ref , sendermap);

                        Adduser_to_inbox.updateChildren(both_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                //SendPushNotification("Send an image");


                            }
                        });
                    }
                });
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean check_camrapermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA}, Variables.permission_camera_code);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean check_ReadStoragepermission(){
        if (ContextCompat.checkSelfPermission(Chat.this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            try {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Variables.permission_Read_data );
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean check_writeStoragepermission(){
        if (ContextCompat.checkSelfPermission(Chat.this.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Variables.permission_write_data );
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Variables.permission_camera_code) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Tap again", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }

        if (requestCode == Variables.permission_Read_data) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Tap again", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == Variables.permission_write_data) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Tap Again", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == Variables.permission_Recording_audio) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Tap Again", Toast.LENGTH_SHORT).show();
            }
        }


    }


    // this method will show the dialog of selete the either take a picture form camera or pick the image from gallary
    private void selectfile() {

//        final CharSequence[] options = { "Take Photo", "Choose Photo from Gallery","Select video","Cancel" };

        final CharSequence[] options = { "Take Photo", "Choose Photo from Gallery","Cancel" };


        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);

        builder.setTitle("Select");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))
                {

                    Intent pictureIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    if(pictureIntent.resolveActivity(getPackageManager()) != null){
                        //Create a file to store the image
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Methods.Log_d_msg(Chat.this,"" + ex.toString());
                        }
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", photoFile);
                            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(pictureIntent, CAMERA_REQUEST);
                        }
                    }



                }

                else if (options[item].equals("Choose Photo from Gallery"))

                {

                    if(check_ReadStoragepermission()) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                }


                else if (options[item].equals("Select video"))

                {
                    if(check_ReadStoragepermission()) {
                        chooseVideo();
                    }
                }


                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }


    // below three method is related with taking the picture from camera
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openCameraIntent() {
//        Intent pictureIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        if(pictureIntent.resolveActivity(getPackageManager()) != null){
//            //Create a file to store the image
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            if (photoFile != null) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, PICK_IMAGE_CAMERA);
//            }
//        }

//        Intent pictureIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        if(pictureIntent.resolveActivity(getPackageManager()) != null){
//            //Create a file to store the image
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            if (photoFile != null) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, PICK_IMAGE_CAMERA);
//            }
//        }

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

    }

//    String imageFilePath;
//    private File createImageFile() throws IOException {
//        String timeStamp =
//                new SimpleDateFormat("yyyyMMdd_HHmmss",
//                        Locale.getDefault()).format(new Date());
//        String imageFileName = "IMG_" + timeStamp + "_";
////        File storageDir =
////                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM), "Camera");
//
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//        imageFilePath =  image.getAbsolutePath();
//
//       // imageFilePath = image.getAbsolutePath();
//        return image;
//    }

    String imageFilePath;
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        Methods.Log_d_msg(Chat.this,"" + imageFilePath);

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    public String getPath(Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }



    private void chooseVideo() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(intent, Variables.vedio_request_code);
    }



    //on image select activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Methods.Log_d_msg(Chat.this,"In Camera 1");
        if (resultCode == RESULT_OK) {
            Methods.Log_d_msg(Chat.this,"In Camera 2");
            if (requestCode == CAMERA_REQUEST) {
                Methods.Log_d_msg(Chat.this,"In Camera 3");
                Matrix matrix = new Matrix();
                try {
                    ExifInterface exif = new ExifInterface(imageFilePath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
               // Uri selectedImage =(Uri.fromFile(new File(imageFilePath)));

                Methods.Log_d_msg(Chat.this,"" + imageFilePath);
                               InputStream imageStream = null;
                Uri selectedImage =(Uri.fromFile(new File(imageFilePath)));
                Methods.Log_d_msg(Chat.this,"ok " + selectedImage);
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Methods.Log_d_msg(Chat.this,"" + e.toString());
                }

                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);


                UploadImage(baos);


            }

            else if (requestCode == PICK_IMAGE_GALLERY) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path=getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                UploadImage(baos);

            }

            else if(requestCode == Variables.vedio_request_code){
                Uri selectedImageUri = data.getData();
                Chat_Send_file_Service mService = new Chat_Send_file_Service();
                if (!Methods.isMyServiceRunning(context,mService.getClass())) {
                    Intent mServiceIntent = new Intent(context.getApplicationContext(), mService.getClass());
                    mServiceIntent.setAction("startservice");
                    mServiceIntent.putExtra("uri",""+selectedImageUri);
                    mServiceIntent.putExtra("type","video");

                    mServiceIntent.putExtra("sender_id",Variables.user_id);
                    mServiceIntent.putExtra("sender_name",Variables.user_name);
                    mServiceIntent.putExtra("sender_pic",Variables.user_pic);

                    mServiceIntent.putExtra("receiver_id",Receiverid);
                    mServiceIntent.putExtra("receiver_name",Receiver_name);
                    mServiceIntent.putExtra("receiver_pic",Receiver_pic);

                    mServiceIntent.putExtra("token",token);

                    context.startService(mServiceIntent);
                }
                else {
                    Toast.makeText(context, "Please wait video already in uploading progress", Toast.LENGTH_LONG).show();
                }

            }

        }

    }

    //this method will get the big size of image in private chat
    public void OpenfullsizeImage(Chat_GetSet item){
        See_Full_Image_F see_image_f = new See_Full_Image_F();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      //  transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        Bundle args = new Bundle();
        args.putSerializable("image_url", item.getPic_url());
        args.putSerializable("chat_id", item.getChat_id());
        see_image_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.Chat_F, see_image_f).commit();

    }

    //this method will get the big size of image in private chat
    public void OpenVideo(String path){
        Play_Video_F play_video_f = new Play_Video_F();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("path", path);
        play_video_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.Chat_F, play_video_f).commit();

    }


    // API to get User updated Profile.

    public void get_latest_user_profile (String user_id){

        try{
            JSONObject  user_data_objs = new JSONObject();
            user_data_objs.put("user_id", user_id);

            Volley_Requests.New_Volley(
                    Chat.this,
                    "" + API_LINKS.API_Show_user_prof,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try{

                                Methods.toast_msg(Chat.this,"" + response);
                                // swipe_loader.setRefreshing(false);

                                JSONObject msg_obj = response.getJSONObject("msg");
                                JSONObject User_obj = msg_obj.getJSONObject("User");
                                // Assigning value to Rec Variables.
                                Receiver_pic = User_obj.getString("image");
                                Receiver_name = User_obj.getString("full_name");




                            }catch (Exception b){
                                Methods.Log_d_msg(Chat.this,"" + b.toString());

                            }





                        }
                    }


            );




        }catch (Exception b){
            //swipe_loader.setRefreshing(false);
            Methods.Log_d_msg(Chat.this ,"" + b.toString());

        }



    }  // End Method to get user Updated Profile.




}
