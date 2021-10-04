package com.marius.valeyou.ui.fragment.message.chatview;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.chat.MessagesModel;
import com.marius.valeyou.data.beans.chat.UsersModel;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityChatBinding;
import com.marius.valeyou.databinding.DialogDeactivateAccopuontBinding;
import com.marius.valeyou.di.base.dialog.BaseCustomDialog;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.di.socket.SocketManager;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketPostModel;
import com.marius.valeyou.ui.activity.main.MainActivity;
import com.marius.valeyou.ui.activity.provider_profile.ProviderProfileActivity;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;
import com.marius.valeyou.util.permission.PermissionHandler;
import com.marius.valeyou.util.permission.Permissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatActivity extends AppActivity<ActivityChatBinding, ChatActivityVM> implements SocketManager.Observer {

    public SocketManager mSocketManager;
    ProviderDetails data1;
    List<MessagesModel> messagesList = new ArrayList<>();
    MessagesModel model;
    ChatAdapter adapter;
    UsersModel.ChatBean.ShopDataBean shopDataBean;
    File imageFile;
    String encodingStr;
    String ext = "";
    int msgType;

    int otherUserId;
    String otherUserName;
    String ohterUserImage;
    String isFav = "";
    int i_block, he_block;
    String postId;
    String postType;
    String app_type;

    String postTitle = "";
    String postPrice = "";

    List<MessagesModel> messageModelsList = new ArrayList<>();

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocketManager != null) {
            mSocketManager.disconnect();
        }
    }

    @Override
    protected BindingActivity<ChatActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_chat, ChatActivityVM.class);
    }

    @Override
    public void onBackPressed() {
        Intent intent = MainActivity.newIntent(ChatActivity.this,"chat");
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void subscribeToEvents(ChatActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.messsage));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        Intent intent = getIntent();
        String comefrom = intent.getStringExtra("comeFrom");

        if (comefrom.equalsIgnoreCase("profile")) {

            otherUserId = intent.getIntExtra("id", 0);
            otherUserName = intent.getStringExtra("name");
            ohterUserImage = intent.getStringExtra("image");

            postId = "0";
            postType = "0";
            app_type = "0";
            binding.tvName.setText(otherUserName);
            ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);

        } else if (comefrom.equalsIgnoreCase("chat")) {
            shopDataBean = intent.getParcelableExtra("shopData");

            otherUserId = intent.getIntExtra("id", 0);
            otherUserName = intent.getStringExtra("name");
            ohterUserImage = intent.getStringExtra("image");
            postId = intent.getStringExtra("post_id");
            postType = intent.getStringExtra("post_type");

            app_type = intent.getStringExtra("app_type");

            if (shopDataBean != null) {
                if (shopDataBean.getOwner_type() != null) {
                    if (shopDataBean.getOwner_type().equalsIgnoreCase("Private")) {
                        binding.tvName.setText(otherUserName);
                        ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);
                    } else {
                        binding.tvName.setText(shopDataBean.getShop_name());
                        binding.imgUser.setImageResource(R.drawable.local_market);

                    }
                }else {
                binding.tvName.setText(otherUserName);
                ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);
            }
            } else {
                binding.tvName.setText(otherUserName);
                ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);

            }

            if (app_type == null) {
                app_type = "0";
            }


            postTitle = intent.getStringExtra("post");
            postPrice = intent.getStringExtra("price");

            if (postTitle != null) {
                binding.llShop.setVisibility(View.VISIBLE);
                binding.tvPostName.setText(postTitle);
                binding.tvPrice.setText(postPrice + " brl");
            } else {
                binding.llShop.setVisibility(View.GONE);
            }


        } else if (comefrom.equalsIgnoreCase("bids")) {
            otherUserId = intent.getIntExtra("id", 0);
            otherUserName = intent.getStringExtra("name");
            ohterUserImage = intent.getStringExtra("image");

            postId = "0";
            postType = "0";
            app_type = "0";
            binding.tvName.setText(otherUserName);
            ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);

        } else if (comefrom.equalsIgnoreCase("job_detail")) {
            otherUserId = intent.getIntExtra("id", 0);
            otherUserName = intent.getStringExtra("name");
            ohterUserImage = intent.getStringExtra("image");

            postId = "0";
            postType = "0";
            app_type = "0";
            binding.tvName.setText(otherUserName);
            ImageViewBindingUtils.setProfileUrl(binding.imgUser, ohterUserImage);

        }


        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        binding.etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    binding.galary.setVisibility(View.VISIBLE);
                } else {
                    binding.galary.setVisibility(View.GONE);
                }
                ;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {

                    case R.id.galary:
                        AllowCameraPermision();
                        break;

                    case R.id.tv_block:
                        if (i_block == 0) {
                            blockAccount();
                        } else {
                            vm.blockUnblock(String.valueOf(otherUserId), "0");
                        }
                        break;

                    case R.id.img_user:

                        if (i_block == 0 && he_block == 0) {
                            Intent intent = new Intent(ChatActivity.this, ProviderProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("id", otherUserId);
                            intent.putExtra("fav", "0");
                            startActivity(intent);
                        } else if (i_block == 1 && he_block == 0) {

                            Toast.makeText(ChatActivity.this, getResources().getString(R.string.you_blocked_this_user), Toast.LENGTH_SHORT).show();

                        } else if (i_block == 0 && he_block == 1) {

                            Toast.makeText(ChatActivity.this, getResources().getString(R.string.your_have_blocked), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ChatActivity.this, getResources().getString(R.string.your_have_blocked), Toast.LENGTH_SHORT).show();

                        }

                        break;

                    case R.id.iv_send:
                        if (binding.etChat.getText().toString().isEmpty()) {

                            vm.info.setValue(getResources().getString(R.string.type_your_message));

                        } else {

                            msgType = 0;
                            JSONObject jsonObject = getJsonData();

                            if (jsonObject != null) {

                                mSocketManager.sendMessage(jsonObject);
                                binding.etChat.setText("");

                            }

                        }
                        break;
                }
            }
        });

        vm.blockUnblockEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();


                        if (i_block == 1) {
                            binding.etChat.setText("");
                            binding.ivSend.setVisibility(View.VISIBLE);
                            binding.etChat.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            binding.etChat.setTextColor(getResources().getColor(R.color.black));
                            binding.etChat.setEnabled(true);
                            binding.tvBlock.setText(getResources().getString(R.string.block));
                            i_block = 0;

                            Toast.makeText(ChatActivity.this, getResources().getString(R.string.you_unblocked) + " " + otherUserName, Toast.LENGTH_SHORT).show();

                        } else {
                            i_block = 1;
                            binding.etChat.setText(getResources().getString(R.string.you_blocked) + " " + otherUserName);
                            binding.etChat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            binding.etChat.setTextColor(getResources().getColor(R.color.red));
                            binding.ivSend.setVisibility(View.GONE);
                            binding.etChat.setEnabled(false);
                            binding.tvBlock.setText(getResources().getString(R.string.unblock));

                            Toast.makeText(ChatActivity.this, getResources().getString(R.string.you_blocked) + " " + otherUserName, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });

        if (mSocketManager == null) {
            mSocketManager = new SocketManager(this, this, sharedPref);
        }
        if (mSocketManager.getmSocket() == null) {
            mSocketManager.init();
        }

        getMessagesList();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessagesList();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupAdater(List<MessagesModel> list) {
        int id = sharedPref.getUserData().getId();
        Collections.reverse(list);
        adapter = new ChatAdapter(ChatActivity.this, list, id, sharedPref.getUserData().getImage(), i_block, he_block);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        binding.rvChatList.setLayoutManager(linearLayoutManager);
        if (list.size() > 2) {
            binding.rvChatList.smoothScrollToPosition(adapter.getItemCount() - 1);
        } else {
            binding.rvChatList.smoothScrollToPosition(adapter.getItemCount());
        }
        binding.rvChatList.setAdapter(adapter);
    }

    private void getMessagesList() {
        try {

            binding.swipeRefreshLayout.setRefreshing(true);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, otherUserId);
            jsonObject.put(Constants.PAGE, "");
            jsonObject.put(Constants.LIMIT, "");
            jsonObject.put(Constants.TYPE, 0);
            jsonObject.put(Constants.APP_TYPE, app_type);
            jsonObject.put(Constants.POST_ID, postId);
            jsonObject.put(Constants.POST_TYPE, postType);


            Log.d("params", "" + jsonObject);

            mSocketManager.getMessageList(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getChatList(String event, JSONArray args) {

    }

    @Override
    public void getMessages(String event, JSONArray args) {
        binding.swipeRefreshLayout.setRefreshing(false);
        messagesList.clear();
        Type type = TypeToken.getParameterized(ArrayList.class, MessagesModel.class).getType();
        messagesList = new Gson().fromJson(args.toString(), type);
        if (messagesList.size() > 0) {
            setupAdater(messagesList);

            if (app_type.equals("1")) {
                binding.tvBlock.setVisibility(View.GONE);
                return;
            }

            i_block = messagesList.get(messagesList.size() - 1).getI_block();
            he_block = messagesList.get(messagesList.size() - 1).getHe_block();

            if (i_block == 1 && he_block == 0) {

                binding.etChat.setText(getResources().getString(R.string.you_blocked) + " " + otherUserName);
                binding.etChat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                binding.etChat.setTextColor(getResources().getColor(R.color.red));
                binding.tvBlock.setVisibility(View.VISIBLE);
                binding.ivSend.setVisibility(View.GONE);
                binding.etChat.setEnabled(false);
                binding.tvBlock.setText(getResources().getString(R.string.unblock));


            } else if (i_block == 0 && he_block == 1) {

                binding.etChat.setText(otherUserName + " " + getResources().getString(R.string.blocked_you));
                binding.etChat.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                binding.etChat.setTextColor(getResources().getColor(R.color.red));
                binding.ivSend.setVisibility(View.GONE);
                binding.tvBlock.setVisibility(View.GONE);
                binding.etChat.setEnabled(false);

            } else if (i_block == 0 && he_block == 0) {
                binding.etChat.setText("");
                binding.etChat.setEnabled(true);
                binding.tvBlock.setVisibility(View.VISIBLE);
                binding.tvBlock.setText("Block");
                binding.ivSend.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public void sendMessageResponse(String event, JSONObject args) {
        dismissProgressDialog();
        model = new Gson().fromJson(args.toString(), MessagesModel.class);

        if (adapter != null) {

            if (messagesList.size() > 0) {
                adapter.sendNewMessage(model);
            } else {
                messagesList.add(model);
                setupAdater(messagesList);
            }

            Toast.makeText(this, "sent", Toast.LENGTH_SHORT).show();

            binding.rvChatList.smoothScrollToPosition(adapter.getItemCount() - 1);

        }

    }


    private JSONObject getJsonData() {
        showProgressDialog(R.string.sending);
        String timestamp = (System.currentTimeMillis() / 1000) + "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.SENDERID, sharedPref.getUserData().getId());
            jsonObject.put(Constants.RECIEVERID, otherUserId);

            if (msgType == 0) {
                jsonObject.put(Constants.MESSAGE, binding.etChat.getText().toString());
            } else {
                jsonObject.put(Constants.MESSAGE, encodingStr);

            }

            jsonObject.put(Constants.EXTENSION, ext);
            jsonObject.put(Constants.USERNAME, sharedPref.getUserData().getFirstName() + " " + sharedPref.getUserData().getLastName());
            jsonObject.put(Constants.USERIMAGE, sharedPref.getUserData().getImage());
            jsonObject.put(Constants.RECIEVERNAME, otherUserName);
            jsonObject.put(Constants.RECIEVERIMAGE, ohterUserImage);
            jsonObject.put(Constants.MSGTYPE, msgType);
            jsonObject.put(Constants.TIMESTAMP, timestamp);
            jsonObject.put(Constants.TYPE, 0);
            jsonObject.put(Constants.APP_TYPE, app_type);
            jsonObject.put(Constants.POST_ID, postId);
            jsonObject.put(Constants.POST_TYPE, postType);


            return jsonObject;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }


    }


    private BaseCustomDialog<DialogDeactivateAccopuontBinding> BlockAccountDialog;

    private void blockAccount() {
        BlockAccountDialog = new BaseCustomDialog<>(ChatActivity.this, R.layout.holder_block_account, new BaseCustomDialog.Listener() {
            @Override
            public void onViewClick(View view) {
                if (view != null && view.getId() != 0) {
                    switch (view.getId()) {
                        case R.id.btn_submit:
                            BlockAccountDialog.dismiss();

                            viewModel.blockUnblock(String.valueOf(otherUserId), "1");

                            break;
                        case R.id.btn_cancel:
                            BlockAccountDialog.dismiss();
                            break;

                        case R.id.iv_cancel:
                            BlockAccountDialog.dismiss();
                            break;
                    }


                }
            }
        });
        BlockAccountDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        BlockAccountDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        BlockAccountDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        BlockAccountDialog.show();
    }

    private void AllowCameraPermision() {
        Permissions.check(this, Manifest.permission.CAMERA, 0, new PermissionHandler() {
            @Override
            public void onGranted() {
                galaryCameraIntent();
            }
        });
    }

    private void galaryCameraIntent() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                String frontpathString = getRealPathFromURI(imageUri);
                encodingStr = getFileToByte(frontpathString);
                msgType = 1;
                ext = getMimeType(this, imageUri);

                if (encodingStr != null) {
                    JSONObject jsonObject = getJsonData();
                    if (jsonObject != null) {
                        mSocketManager.sendMessage(jsonObject);
                    }
                }

            }

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

}
