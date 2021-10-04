package com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.Chat_pkg.Adapters.Msg_adapter;
import com.marius.valeyou.market_place.Chat_pkg.Chat;
import com.marius.valeyou.market_place.Chat_pkg.Chat_Inbox.Inbox_Get_Set.Inbox_Get_Set_List;
import com.marius.valeyou.market_place.Chat_pkg.DataModel.Chat_Data_Model;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Utils.EdgeChanger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Chat_Inbox extends AppCompatActivity {
    private DatabaseReference mDatabase;
//    FirebaseDatabase fire_db = FirebaseDatabase.getInstance();
    List<Chat_Data_Model> Msg_list = new ArrayList<>();
    RecyclerView Msg_RV;
    Msg_adapter msg_adp;
    String rec_user_id = "25"; // For Demo Only
    String sender_id = "34"; // For Demo Only
    Inbox_Adapter adapter;
    Context context;
    ArrayList<Inbox_Get_Set_List> inbox_arraylist;
    DatabaseReference rootref;
    Toolbar header;
    ImageView back_id;

    ProgressBar progress_loader;
    // TODO: (Chat_Inbox.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic (){
        try{
            header = findViewById(R.id.tb_id);
            //change_toolbar_color(header);
            Methods.Change_header_color(header, Chat_Inbox.this);



        }catch (Exception v){

        } // End Catch of changing Toolbar Color


    } // End method to change Color Dynamically


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(Chat_Inbox.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_inbox);

       // mDatabase = FirebaseDatabase.getInstance().getReference("chat");

        context = Chat_Inbox.this;
        progress_loader = findViewById(R.id.loader);

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
        Msg_RV.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        // TODO: For Desc.
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        // TODO: For Divider in RV
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Msg_RV.getContext(),
                linearLayoutManager.getOrientation());
        Msg_RV.addItemDecoration(dividerItemDecoration);

        Msg_RV.setLayoutManager(linearLayoutManager);

        back_id = findViewById(R.id.back_id);



        // Get Data Ffrom Shared Pref

//        Variables.user_id = SharedPrefrence.get_user_id_from_json(getContext());
//        Variables.user_name = SharedPrefrence.get_user_name_from_json(getContext());
//        Variables.user_pic= SharedPrefrence.get_user_img_from_json(getContext());




        Variables.user_id = SharedPrefrence.get_user_id_from_json(context);
        Variables.user_name = SharedPrefrence.get_user_name_from_json(context);
        Variables.user_pic= SharedPrefrence.get_user_email_from_json(context);
        rootref = FirebaseDatabase.getInstance().getReference();
        inbox_arraylist=new ArrayList<>();

        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        // inntialize the adapter and set the adapter to recylerview
        adapter = new Inbox_Adapter(context, inbox_arraylist, new Inbox_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Inbox_Get_Set_List item) {
                //UIUtil.hideKeyboard(getActivity());

//                if(Methods.check_permissions(getActivity())){
//
//                }
                    chatFragment(item.getRid(),item.getName(),item.getPic());


            }
        }, new Inbox_Adapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(final Inbox_Get_Set_List item) {


            }
        });

        Msg_RV.setAdapter(adapter);

        //  read_Msgs();

        Change_Color_Dynmic();

    }

    // on start we get the all inbox data from database
    ValueEventListener eventListener;
    Query inbox_query;
    @Override
    public void onStart() {
        super.onStart();


        progress_loader.setVisibility(View.VISIBLE);
        inbox_query = rootref.child("Inbox").child(Variables.user_id).orderByChild("timestamp");
        inbox_query.keepSynced(true);

        eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                inbox_arraylist.clear();
                progress_loader.setVisibility(View.GONE);

                if(!dataSnapshot.exists()){
                    //no_data_layout.setVisibility(View.VISIBLE);
                }else {
                    //no_data_layout.setVisibility(View.GONE);


                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        Inbox_Get_Set_List model = ds.getValue(Inbox_Get_Set_List.class);
                        inbox_arraylist.add(model);
                        adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        inbox_query.addValueEventListener(eventListener);


    }




    @Override
    public void onStop() {
        super.onStop();
        inbox_query.removeEventListener(eventListener);

    }

    public void chatFragment(String receiverid, String name,String receiver_pic){

        // Open New Activity
        Intent myIntent = new Intent(Chat_Inbox.this, Chat.class);
        myIntent.putExtra("receiver_id", receiverid);
        myIntent.putExtra("receiver_name", name);
        myIntent.putExtra("receiver_pic", receiver_pic);
        startActivity(myIntent);


//        Chat_Activity chat_activity = new Chat_Activity();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        Bundle args = new Bundle();
//        args.putString("receiver_id", receiverid);
//        args.putString("receiver_name",name);
//        args.putString("receiver_pic",receiver_pic);
//        chat_activity.setArguments(args);
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.Inbox_F, chat_activity).commit();
    }



}
