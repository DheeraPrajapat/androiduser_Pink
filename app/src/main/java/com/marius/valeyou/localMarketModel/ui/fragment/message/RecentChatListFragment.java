package com.marius.valeyou.localMarketModel.ui.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.chat.UsersModel;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.databinding.FragmentRecentChatListBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.localMarketModel.ToolClickListener;
import com.marius.valeyou.localMarketModel.socketLocalMarket.SocketManager;
import com.marius.valeyou.localMarketModel.ui.activity.main.MainLocalMarketActivity;
import com.marius.valeyou.localMarketModel.ui.fragment.message.chatview.MessagingActivity;
import com.marius.valeyou.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecentChatListFragment extends AppFragment<FragmentRecentChatListBinding, RecentChatListFragmentVM> implements SocketManager.Observer {

    @Inject
    SharedPref sharedPref;
    public static final String TAG = "RecentChatListFragment";
    SocketManager mSocketManager;
    List<UsersModel> usersList = new ArrayList<>();
    RecentChatAdapter chatUsersAdapter;
    MainLocalMarketActivity marketActivity;

    public static Fragment newInstance() {
        return new RecentChatListFragment();
    }

    @Override
    protected BindingFragment<RecentChatListFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_recent_chat_list, RecentChatListFragmentVM.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mSocketManager == null) {
            mSocketManager = new SocketManager(getActivity(), this, sharedPref);
        }
        if (mSocketManager.getmSocket() == null) {
            mSocketManager.init();
        }
    }

    @Override
    protected void subscribeToEvents(RecentChatListFragmentVM vm) {
        marketActivity = (MainLocalMarketActivity) requireActivity();
        marketActivity.viewHeaderItems(true, false, false);
        marketActivity.setHeaderString("");

        marketActivity.clickListener = new ToolClickListener() {
            @Override
            public void OnToolClick(String type) {
                Log.d("input", "===" + type);
                if (chatUsersAdapter != null)
                    chatUsersAdapter.getFilter().filter(type);
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();
        getChatList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSocketManager != null) {
            mSocketManager.disconnect();
        }
    }

    private void setRecyclerView(List<UsersModel> list) {
        if (list.size() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
            binding.rvChatList.setVisibility(View.VISIBLE);
            chatUsersAdapter = new RecentChatAdapter(getContext(), list, new RecentChatAdapter.Listner() {
                @Override
                public void onItemClick(View v, int position, UsersModel model) {

                    Intent intent = MessagingActivity.newIntent(getActivity());
                    intent.putExtra("comeFrom", "chat");
                    intent.putExtra("shopData",model.getChat().getShop_data());
                    intent.putExtra("id", model.getChat().getUser().getId());
                    intent.putExtra("name", model.getChat().getUser().getName());
                    intent.putExtra("image", model.getChat().getUser().getImage());
                         if(model.getChat().getShop_data().getPost_images()!=null){intent.putExtra("image_display", model.getChat().getShop_data().getPost_images().get(0).getImage());}


                    String postId = "0";
                    String post_type = "0";
                    if (null != model.getChat().getShop_data().getId()+""){
                        postId = model.getChat().getShop_data().getId()+"";
                        if (!postId.equals("0")) {
                            post_type = "2";
                        }
                        intent.putExtra("post", model.getChat().getShop_data().getTitle());
                        intent.putExtra("price", model.getChat().getShop_data().getPrice());

                    }

                    intent.putExtra("post_id", postId);
                    intent.putExtra("post_type", post_type);
                    intent.putExtra("app_type", model.getChat().getApp_type()+"");

                    startNewActivity(intent);
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.rvChatList.setLayoutManager(layoutManager);
            binding.rvChatList.setAdapter(chatUsersAdapter);

        } else {

            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.rvChatList.setVisibility(View.GONE);

        }
    }

    public void getChatList() {
        try {
            binding.setCheck(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SOCKET_USER_ID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.TYPE, "0");
            jsonObject.put(Constants.PAGE, "");
            jsonObject.put(Constants.LIMIT, "");
            mSocketManager.getChatList(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getChatList(String event, JSONArray args) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.setCheck(false);
                usersList.clear();
                Type type = TypeToken.getParameterized(ArrayList.class, UsersModel.class).getType();
                usersList = new Gson().fromJson(args.toString(), type);

                setRecyclerView(usersList);
            }
        });
    }

    @Override
    public void getMessages(String event, JSONArray args) {

    }

    @Override
    public void sendMessageResponse(String event, JSONObject args) {
        getChatList();
    }
}
