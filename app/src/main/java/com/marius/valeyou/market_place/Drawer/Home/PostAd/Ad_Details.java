package com.marius.valeyou.market_place.Drawer.Home.PostAd;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.Adapters.Select_Pic_Adp;
import com.marius.valeyou.market_place.Drawer.Home.PostAd.DataModel.Gallary_Pics_Get_Set;
import com.marius.valeyou.market_place.Drawer.Msgs_Notifications.Dialog_Fragment;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Splashscreen.SplashScreen;
import com.marius.valeyou.market_place.Utils.My_Click;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.IResult;
import com.marius.valeyou.market_place.Volley_Package.VolleyService;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.marius.valeyou.market_place.Volley_Package.other_call_back;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import static android.app.Activity.RESULT_OK;

public class Ad_Details extends Fragment implements My_Click {

    View view;
    RelativeLayout rl1;
    private File destination = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private Bitmap bitmap;
    RelativeLayout select_images;
    int PICK_IMAGE_MULTIPLE = 2;
    String imageEncoded, city_id, city_name;
    List<String> imagesEncodedList;
    List<String> Download_imgs = new ArrayList<>();
    int numberOfLines = 0;
    LinearLayout ll, Li_country_spinner;
    // Pictures RV
    RecyclerView RV_pictures;
    Select_Pic_Adp Selected_pic_adp;
    private List<Gallary_Pics_Get_Set> Gallary_pic_List = new ArrayList<>();
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    TextView upload_button_text;
    JSONObject mainObject = new JSONObject();
    JSONArray recipients = new JSONArray();
    JSONArray Form_data_Array = new JSONArray();
    JSONObject options_mainObject = new JSONObject();
    //List<EditText> allEds = new ArrayList<EditText>();
    EditText ET_name, ET_email, ET_title;
    JSONObject form_data_main = new JSONObject();
    ArrayList<String> Array_form_ids = new ArrayList<String>();
    ArrayList<String> Array_option_ids = new ArrayList<String>();
    ArrayList<String> Array_value = new ArrayList<String>();
    public static RelativeLayout camera_RL;

    public static LinearLayout after_one_pic_RL;
    ///int i=0;
    ArrayList<String> All_Array_form_ids = new ArrayList<String>();
    ArrayList<String> All_Array_option_ids = new ArrayList<String>();
    ArrayList<String> All_array_country = new ArrayList<String>();
    ArrayList<String> All_array_country_name = new ArrayList<String>();
    ArrayList<Integer> Edittext_Array_ids = new ArrayList<Integer>();
    ArrayList<String> All_Array_value = new ArrayList<String>();
    ArrayList<String> list = new ArrayList<String>();
    int iCurrentSelection;
    public static String prefix_form = "form";
    public static String prefix_option = "option";
    public static String prefix_value = "value";
    String country_id;
    List<EditText> allEds = new ArrayList<EditText>();
    EditText locality;
    EditText phone_num, price, desc_text_1;
    String sub_cate_id, main_cate_id, sub_cate_name, cate_name, post_id, all_sub_cate;
    TextView text_cate_name, text_sub_cate_name, tb_title_id;

    RelativeLayout category, sub_cate, select_images_1;
    ScrollView scrollview_id;
    String city_id_for_edit_post, country_dial_code, which_class = "";
    Bitmap rotatedBitmap_new;
    ProgressDialog pd;
    public static Activity Ad_detail_activity;

    // TODO: (MyAccount.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic() {

        try {
            upload_button_text.setBackgroundColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));
            // TODO: Changing Glow Color of Scrolview
            Methods.setEdgeEffectL(scrollview_id, Color.parseColor(Variables.Var_App_Config_header_bg_color));

        } catch (Exception v) {

        } // End Catch of changing Toolbar Color


    } // End method to change Color Dynamically

    public static TextView num_images;
    public static TextView num_pics;
    Toolbar tb;
    ImageView back_id, contry_flag;

    LinearLayout country_ll;
    TextView country_coke;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_details, container, false);
        Ad_detail_activity = getActivity();
        country_ll = (LinearLayout) view.findViewById(R.id.country_ll_id);
        country_coke = (TextView) view.findViewById(R.id.country_code_id);
        contry_flag = (ImageView) view.findViewById(R.id.country_flag_id);
        desc_text_1 = view.findViewById(R.id.desc_text_1);
        try {
            tb = view.findViewById(R.id.tb_id);
            Methods.Change_header_color(tb, getActivity());
        } catch (Exception v) {

        } // End Catch of changing Toolbar Color

        back_id = view.findViewById(R.id.back_id);
        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                close_dialogue();
            }
        });


        tb_title_id = view.findViewById(R.id.tb_title_id);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {


                    close_dialogue();

                    return true;
                }
                return false;
            }
        });

        after_one_pic_RL = view.findViewById(R.id.after_one_pic_RL);
        select_images_1 = view.findViewById(R.id.select_images_1);
        num_images = view.findViewById(R.id.num_images);
        pd = new ProgressDialog(getContext());
        pd.setMessage(getResources().getString(R.string.loading));
        pd.setCancelable(false);
        after_one_pic_RL.setVisibility(View.GONE);
        camera_RL = view.findViewById(R.id.camera_RL);
        num_pics = view.findViewById(R.id.num_pics);
        num_pics.setText("You can add up to " + Variables.Var_num_pics_in_upload_Ads + " photos");
        try {
//            PostFreeAd.tv.setVisibility(View.GONE);
//            PostFreeAd.tb.setVisibility(View.VISIBLE);
//            PostFreeAd.tv.setVisibility(View.GONE);

        } catch (Exception c) {
            // MyAds.toolbar_text.setText("Edit Post");
            //  MyAds.toolbar_text.setVisibility(View.GONE);
        }

        category = view.findViewById(R.id.category);
        sub_cate = view.findViewById(R.id.sub_cate);
        scrollview_id = view.findViewById(R.id.scrollview_id);

        price = view.findViewById(R.id.price);
        upload_button_text = view.findViewById(R.id.tv_id);

        ET_name = view.findViewById(R.id.ET_name);
        ET_email = view.findViewById(R.id.ET_email);
        ET_title = view.findViewById(R.id.text_post_title);
        locality = view.findViewById(R.id.locality);
        phone_num = view.findViewById(R.id.phone_num);
        text_cate_name = view.findViewById(R.id.cate_name);
        text_sub_cate_name = view.findViewById(R.id.sub_cate_name);

        // TODO: Disable Edit Texts

//        disableEditText(ET_name);
        disableEditText(ET_email);
//        disableEditText(phone_num);

        String phone = SharedPrefrence.get_user_phone_from_json(getContext());
        //phone_num.setText("" + phone);
        All_Array_option_ids.clear();
        All_Array_form_ids.clear();

        Bundle bundle = getArguments();
        if (bundle != null) {
            main_cate_id = bundle.getString("main_cate_id");
            sub_cate_name = bundle.getString("sub_cate_name");
            cate_name = bundle.getString("main_cate_name");
            sub_cate_id = bundle.getString("sub_cate_id");
            post_id = bundle.getString("post_id");
            all_sub_cate = bundle.getString("sub_cates_all");
            city_id_for_edit_post = bundle.getString("city_id");
            which_class = bundle.getString("class");

            // Set Up
            text_cate_name.setText("" + Html.fromHtml(cate_name) + " ");
            text_sub_cate_name.setText("" + Html.fromHtml(sub_cate_name));


        }
        if (!post_id.equals("")) {
            // If Edit Post

            tb_title_id.setText("Edit Ad");
            upload_button_text.setText("Edit Ad");
        }

        ET_name.setText("" + SharedPrefrence.get_user_name_from_json(getContext()));
        ET_email.setText("" + SharedPrefrence.get_user_email_from_json(getContext()));


        if (post_id.equals("")) {
            // If New Post
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getContext(), PostFreeAd.class);
                    myIntent.putExtra("post_id", post_id); //Optional parameters
                    myIntent.putExtra("from_ad", "1"); //Optional parameters

                    startActivity(myIntent);
                    ///getActivity().finish();
                }
            });

            sub_cate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo: Remember ==> :-D

                    Car_subcategory f = new Car_subcategory();
                    Bundle args = new Bundle();
                    args.putString("main_cate_id", main_cate_id);
                    args.putString("sub_cate", all_sub_cate);
                    args.putString("main_cate_name", cate_name);
                    args.putString("post_id", post_id);
                    args.putString("where", "");
                    f.setArguments(args);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.fl_id, f).commit();


                }
            });

        } else {
            // If Edit Post
            Methods.Log_d_msg(getContext(), "Post Edit ");
        }


        upload_button_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cou = 1;
                if (Gallary_pic_List.size() > 0) {

                    if (price.getText().toString().equals("")) {
                        // If price fields is not empty
                        price.setError("Please fill this.");
                    } else if (ET_title.getText().toString().equals("")) {
                        // If price fields is not empty
                        ET_title.setError("Please fill this.");
                    } else if (Gallary_pic_List.size() > Variables.Var_num_pics_in_upload_Ads) {
                        Methods.alert_dialogue(getContext(), "Info", "Please select pictures less than 10.");
                    } else if (ET_email.getText().toString().equals("")) {
                        // If email fields is empty
                        ET_email.setError("Please fill this.");
                    } else if (ET_name.getText().toString().equals("")) {
                        // If name fields is empty
                        ET_name.setError("Please fill this.");
                    } else if (phone_num.getText().toString().equals("")) {
                        phone_num.setError("Please fill this.");
                    } else if (desc_text_1.getText().toString().equals("")) {
                        desc_text_1.setError("Please fill this.");
                    } else {
                        // If Done

                        // TODO: Start Progress Dialogue.
                        try {
                            // pd.show();
                        } catch (Exception b) {
                            Methods.toast_msg(getContext(), "" + b.toString());
                        }

                        // Now Uploading
//                        for(int i=0;i< Gallary_pic_List.size();i++) {
//                            Gallary_Pics_Get_Set pics = Gallary_pic_List.get(i);
//                            cou = cou + 1;
//                            //uploadImage(pics.getImage_Uri());  // todo: Firebase Uploading
//
//                            try {
//                                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pics.getImage_Uri());
//                                Bitmap resized = ThumbnailUtils.extractThumbnail(mBitmap, 1500, 1500);
//                                Upload_ad_image(Methods.create_base64(resized), i);
//                                //final Uri imageURI = imageReturnedIntent.getData();
//
//                            } catch (Exception n) {
//
//                            }
//
//                        } // End For Loop

                        pd.show();

                        create_json_arr_ad_images();

                        API_Call(new other_call_back() {
                            @Override
                            public void Get_Response_ok(String done) {
                                Add_Post_Method();
                            }
                        });

                    }


                } // End if Condition
                else {
                    Methods.alert_dialogue(getContext(), "Info", "Pleae select Images");
                }


            }
        });

        country_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.show(getFragmentManager(), "COUNTRY_PICKER");

                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        // Implement your code here
                        contry_flag.setImageResource(flagDrawableResID);
                        country_dial_code = dialCode;
                        country_coke.setText(dialCode);
                        picker.dismiss();
                    }
                });*/
            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        rl1 = (RelativeLayout) view.findViewById(R.id.rl1_id);
        select_images = view.findViewById(R.id.select_images);

        Add_Line();
        Get_City();

        RV_pictures = view.findViewById(R.id.RV_picture);

        select_images_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                selectImage();
                //openCameraIntent();
            }
        });

        select_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                //openCameraIntent();
                selectImage();

            }
        });

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_Fragment f = new Dialog_Fragment();
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.fl_id, f).commit();
            }
        });
        Change_Color_Dynmic();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (pd != null) {
            pd.hide();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            Methods.Log_d_msg(getContext(), "City " + city_id + " Name " + city_name);
            // after_one_pic_RL.setVisibility(View.VISIBLE);
            //camera_RL.setVisibility(View.GONE);


            //Methods.alert_dialogue(getContext(),"Title","Pick Image " + PICK_IMAGE_CAMERA + " Req code " + requestCode);
            if (requestCode == PICK_IMAGE_CAMERA) {
                try {

                    // New Code
                    Matrix matrix = new Matrix();
                    try {
                        ExifInterface exif = new ExifInterface(imageFilePath);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        Methods.Log_d_msg(getContext(), "Angel " + orientation);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                Methods.Log_d_msg(getContext(), "Angel 90 " + ExifInterface.ORIENTATION_ROTATE_90);
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                Methods.Log_d_msg(getContext(), "Angel 180 " + ExifInterface.ORIENTATION_ROTATE_180);
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                matrix.postRotate(0);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Uri selectedImage = (Uri.fromFile(new File(imageFilePath)));
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                    //int new_width = (int) (imagebitmap.getWidth() * 0.5);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);

                    int width = rotatedBitmap.getWidth();
                    int height = rotatedBitmap.getHeight();
                    if (width > height) {
                        // Ig width is greater than its height then it is cover photo
                        // No need to crop

                        rotatedBitmap_new = rotatedBitmap;
                    } else if (height < Variables.img_size) {
                        rotatedBitmap_new = rotatedBitmap;
                    } else {
                        // If Height is greater than Width.

                        rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4), false);

                    }


//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//                    rotatedBitmap.getHeight();
//                    rotatedBitmap.getWidth();
//                    Methods.toast_msg(getContext(),"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight());
//                    Methods.Log_d_msg(getContext(),"Path " + imageFilePath);
//                    Methods.getImageUri(getContext(),rotatedBitmap);

                    //Upload_ad_image()

                    try {
                        //Bitmap resized = ThumbnailUtils.extractThumbnail(mBitmap, 1500, 1500);
                        Upload_ad_image_on_adding_post(Methods.create_base64(rotatedBitmap_new), 1);
                        //final Uri imageURI = imageReturnedIntent.getData();

                    } catch (Exception n) {

                    }

                } catch (Exception b) {
                    Methods.Log_d_msg(getContext(), "ok " + b.toString());
                    // Methods.alert_dialogue(getContext(),"Title","Pick Image " + PICK_IMAGE_CAMERA + " Req code " + requestCode + " " + b.toString());
                }


            }

            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if (data.getData() != null) {

                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            //Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Methods.getImageUri(getContext(),bitmap));

                            /// Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(),imagebitmap.getHeight() , matrix, true);

                            //Bitmap rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4) , false);

//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//                            rotatedBitmap.getHeight();
//                            rotatedBitmap.getWidth();
//                            Methods.toast_msg(getContext(),"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight());
//                            Methods.Log_d_msg(getContext(),"Path " + imageFilePath);
//                            Methods.getImageUri(getContext(),rotatedBitmap);

                            //Upload_ad_image()
                            int width = rotatedBitmap.getWidth();
                            int height = rotatedBitmap.getHeight();
                            Methods.toast_msg(getContext(), "Size :  W " + width + " H " + height);
                            if (width > height) {
                                // Ig width is greater than its height then it is cover photo
                                // No need to crop
                                rotatedBitmap_new = rotatedBitmap;
                            } else if (height < Variables.img_size) {
                                rotatedBitmap_new = rotatedBitmap;
                            } else {
                                // If Height is greater than Width.

                                rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4), false);

                            }

                            try {
                                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Methods.getImageUri(getContext(), rotatedBitmap));
                                //Bitmap resized = ThumbnailUtils.extractThumbnail(mBitmap, 1500, 1500);
                                Upload_ad_image_on_adding_post(Methods.create_base64(rotatedBitmap_new), 1);
                                //final Uri imageURI = imageReturnedIntent.getData();

                            } catch (Exception n) {
                                Methods.toast_msg(getContext(), "" + n.toString());
                            }


                        } // End For Loop


                        // Setup Adapter
//                        Selected_pic_adp = new Select_Pic_Adp(Gallary_pic_List,getContext(), num_pics);
//
//                        LinearLayoutManager layoutManager
//                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//
//
//                        RV_pictures.setLayoutManager(layoutManager);
//                        RV_pictures.setAdapter(Selected_pic_adp);
//                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());


                        // Setup Adapter
                    } else {
                        Uri mImageUri = data.getData();

//                        Gallary_Pics_Get_Set gallary_pic_1 = new Gallary_Pics_Get_Set(
//                                mImageUri
//                        );
//                        Gallary_pic_List.add(gallary_pic_1);
//                        // Setup Adapter
//                        Selected_pic_adp = new Select_Pic_Adp(Gallary_pic_List,getContext(),num_pics);
//
//                        LinearLayoutManager layoutManager_1
//                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//
//
//                        RV_pictures.setLayoutManager(layoutManager_1);
//                        RV_pictures.setAdapter(Selected_pic_adp);
                        Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mImageUri);
                        //Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Methods.getImageUri(getContext(),bitmap));

                        /// Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(),imagebitmap.getHeight() , matrix, true);

                        // Bitmap rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4) , false);

//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//                        rotatedBitmap.getHeight();
//                        rotatedBitmap.getWidth();
//                        Methods.toast_msg(getContext(),"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight());
//                        Methods.Log_d_msg(getContext(),"Path " + imageFilePath);
//                        Methods.getImageUri(getContext(),rotatedBitmap);

                        //Upload_ad_image()

                        int width = rotatedBitmap.getWidth();
                        int height = rotatedBitmap.getHeight();
                        Methods.toast_msg(getContext(), "Size :  W " + width + " H " + height);
                        if (width > height) {
                            // Ig width is greater than its height then it is cover photo
                            // No need to crop
                            rotatedBitmap_new = rotatedBitmap;
                        } else if (height < Variables.img_size) {
                            rotatedBitmap_new = rotatedBitmap;
                        } else {
                            // If Height is greater than Width.
                            rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4), false);
                        }


                        try {
                            //Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Methods.getImageUri(getContext(),rotatedBitmap));
                            //Bitmap resized = ThumbnailUtils.extractThumbnail(mBitmap, 1500, 1500);

                            Upload_ad_image_on_adding_post(Methods.create_base64(rotatedBitmap_new), 1);
                            //final Uri imageURI = imageReturnedIntent.getData();

                        } catch (Exception n) {

                        }


                    }


                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            // GETTER SETTER
//                            Gallary_Pics_Get_Set gallary_pic = new Gallary_Pics_Get_Set(
//                                    uri
//                            );
                            // Gallary_pic_List.add(gallary_pic);

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            // Upload Single Image in Multiple Selection Image Mood.

                            Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            //Bitmap rotatedBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Methods.getImageUri(getContext(),bitmap));

                            /// Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(),imagebitmap.getHeight() , matrix, true);

                            //Bitmap rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4) , false);
// Todo:Remove
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//                            rotatedBitmap.getHeight();
//                            rotatedBitmap.getWidth();
//                            Methods.toast_msg(getContext(),"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight());
//                            Methods.Log_d_msg(getContext(),"Path " + imageFilePath);
//                            Methods.getImageUri(getContext(),rotatedBitmap);

                            //Upload_ad_image()

                            int width = rotatedBitmap.getWidth();
                            int height = rotatedBitmap.getHeight();
                            Methods.toast_msg(getContext(), "Size :  W " + width + " H " + height);
                            if (width > height) {
                                // Ig width is greater than its height then it is cover photo
                                // No need to crop
                                rotatedBitmap_new = rotatedBitmap;
                            } else if (height < Variables.img_size) {
                                rotatedBitmap_new = rotatedBitmap;
                            } else {
                                // If Height is greater than Width.
                                rotatedBitmap_new = Bitmap.createScaledBitmap(rotatedBitmap, (int) (rotatedBitmap.getWidth() * 0.4), (int) (rotatedBitmap.getHeight() * 0.4), false);
                            }


                            try {
                                Bitmap mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Methods.getImageUri(getContext(), rotatedBitmap));
                                //Bitmap resized = ThumbnailUtils.extractThumbnail(mBitmap, 1500, 1500);
                                Upload_ad_image_on_adding_post(Methods.create_base64(rotatedBitmap_new), 1);
                                //final Uri imageURI = imageReturnedIntent.getData();

                            } catch (Exception n) {

                            }


                        }


                        // Setup Adapter
//                        Selected_pic_adp = new Select_Pic_Adp(Gallary_pic_List,getContext(), num_pics);
//
//                        LinearLayoutManager layoutManager
//                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//
//
//                        RV_pictures.setLayoutManager(layoutManager);
//                        RV_pictures.setAdapter(Selected_pic_adp);
//                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());

                    }
                }
            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        num_images.setText("" + Gallary_pic_List.size() + " / " + Variables.Var_num_pics_in_upload_Ads);
        // Gallary_pic_List.clear();
        super.onActivityResult(requestCode, resultCode, data);
    }


    // Get form Dynamically


    public void Add_Line() {


        ll = (LinearLayout) view.findViewById(R.id.linearLayoutDecisions);

        initVolleyCallback();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback, getContext());
        try {

            if (post_id.equals("")) {
                // If new Post


                JSONObject sendObj = new JSONObject("{'sub_category_id': '" + sub_cate_id + "' }");
                Variables.pDialog = new ProgressDialog(getContext());
                Variables.pDialog.setMessage(getContext().getResources().getString(R.string.loading_text));
                Variables.pDialog.setCancelable(false);
                Variables.pDialog.show();
                Variables.mVolleyService.postDataVolley("POSTCALL", API_LINKS.API_DYNAMIC_FORM, sendObj);

            } else {
                // Post Edited

                // Get_City();

                Post_Editing_Form();


            }


        } catch (Exception v) {
            v.printStackTrace();
            Methods.Log_d_msg(getContext(), "" + v.toString());
        }


    } // End method to get home upload

    // TODO init for Home Posts

    // Initialize Interface Call Backs
    void initVolleyCallback() {
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Variables.pDialog.hide();

                try {
                    JSONArray Msg_arr = response.getJSONArray("msg");
                    for (int i = 0; i < Msg_arr.length(); i++) {

                        JSONObject obj = Msg_arr.getJSONObject(i);
                        JSONObject Form_obj = obj.getJSONObject("Form");
                        //   int order = i+1;

                        Form_obj.getString("id");
                        Form_obj.getString("type");
                        if (Form_obj.getString("type").equals("select")) {
                            // If form type is select
                            JSONArray option_Arr = Form_obj.getJSONArray("select");
                            final TextView rowTextView = new TextView(getContext());
                            // set some properties of rowTextView or something
                            float dpRatio = getContext().getResources().getDisplayMetrics().density;
                            //int pixelForDp = (int)dpValue * dpRatio;
                            rowTextView.setText("" + Form_obj.getString("name"));
                            rowTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                            rowTextView.setTextSize(12);
//                                rowTextView.setPadding(47,0,0,0);
                            rowTextView.setPadding(41, 0, 0, 0);
                            // add the textview to the linearlayout

                            LinearLayout.LayoutParams TextParams_1 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            TextParams_1.setMargins(0, 25, 0, 0);
                            rowTextView.setLayoutParams(TextParams_1);
                            ll.addView(rowTextView);


                            ArrayList<String> spinnerArray = new ArrayList<String>();
                            final ArrayList<String> spinnerArray_options = new ArrayList<String>();
                            final ArrayList<String> spinnerArray_form_ids = new ArrayList<String>();
                            String[] types = {"By Zip", "By Category"};
                            for (int op = 0; op < option_Arr.length(); op++) {
                                // Add selection options Dynamicallu
                                JSONObject option_obj = option_Arr.getJSONObject(op);
                                JSONObject option_val = option_obj.getJSONObject("Option");
                                option_val.getString("name");
                                String name_fields = option_val.getString("name");
                                spinnerArray.add(name_fields);
                                spinnerArray_options.add("" + option_val.getString("id"));
                                spinnerArray_form_ids.add("" + option_val.getString("form_id"));


                            }
                            final SearchableSpinner spinner = new SearchableSpinner(getContext());
                            //spinner.setBackground();

                            LinearLayout.LayoutParams Spinner_design = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            Spinner_design.setMargins(12, 0, 0, 0);
                            //spinner.setLayoutParams(Spinner_design);
                            spinner.setPadding(18, 0, 0, 0);
                            //  spinner.setId();
                            spinner.setBackground(getContext().getResources().getDrawable(R.drawable.bottom_gray_line));
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, R.id.text, spinnerArray);
                            spinner.setAdapter(spinnerArrayAdapter);
                            iCurrentSelection = spinner.getSelectedItemPosition();
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                //                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    //((TextView) parent.getChildAt(0)).setTextColor(getContext().getResources().getColor(R.color.dark_gray));
                                    //((TextView) parent.getChildAt(0)).setTextSize(14);
//                                        if (iCurrentSelection != pos){

                                    Methods.toast_msg(getContext(), "You Sele " + spinnerArray_form_ids.get(pos));
                                    if (All_Array_form_ids.contains(spinnerArray_form_ids.get(pos))) {

                                        SharedPrefrence.save_info_share(
                                                getContext(),
                                                "" + spinnerArray_options.get(pos),
                                                "" + prefix_option + "_" + spinnerArray_form_ids.get(pos)
                                        );


                                    } else {
                                        // All_Array_form_ids.add(spinnerArray_form_ids.get(pos));
                                        All_Array_form_ids.add(spinnerArray_form_ids.get(pos));

                                        Methods.toast_msg(getContext(), "Add " + spinnerArray_form_ids.get(pos));

                                        SharedPrefrence.save_info_share(
                                                getContext(),
                                                "" + spinnerArray_options.get(pos),
                                                "" + prefix_option + "_" + spinnerArray_form_ids.get(pos)
                                        );

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
//                                        tmpBranch = spinner.getSelectedItem().toString();
                                }


                            });


                            ll.addView(spinner);
                        } else if (Form_obj.getString("type").equals("input")) {
                            final TextView rowTextView = new TextView(getContext());
                            // set some properties of rowTextView or something
                            rowTextView.setText("" + Form_obj.getString("name"));
                            rowTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                            rowTextView.setPadding(41, 0, 0, 0);
                            rowTextView.setTextSize(12);


                            LinearLayout.LayoutParams editTextParams_1 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            editTextParams_1.setMargins(0, 30, 0, 12);

                            rowTextView.setLayoutParams(editTextParams_1);

                            // add the textview to the linearlayout
                            ll.addView(rowTextView);

                            LinearLayout.LayoutParams editTextParams_2 = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            editTextParams_2.setMargins(0, 30, 0, 12);
                            //rowTextView.setLayoutParams(editTextParams_2);


                            EditText editText = new EditText(getContext());
                            editText.setHintTextColor(getContext().getResources().getColor(R.color.black));
                            editText.setBackground(getContext().getResources().getDrawable(R.drawable.bottom_gray_line));
                            editText.setTextColor(getContext().getResources().getColor(R.color.dark_gray));
                            editText.setTextSize(14);
                            editText.setPadding(41, 0, 0, 50);
                            editText.setLayoutParams(editTextParams_2);
                            editText.setId(Integer.parseInt(Form_obj.getString("id")));
                            allEds.add(editText);
                            // editText.setTransformationMethod(NumberFormat.getInstance());

                            if (Form_obj.getString("field_type").equals("numeric")) {
                                // If form field type is numeric then
                                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            } else if (Form_obj.getString("field_type").equals("text")) {
                                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                            }

                            Edittext_Array_ids.add(Integer.valueOf(Form_obj.getString("id")));
                            // eryeyey

                            LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            editTextParams.setMargins(10, 25, 0, 12);
                            editText.setId(numberOfLines + 1);
                            ll.addView(editText);
                            numberOfLines++;

                        } else if (Form_obj.getString("type").equals("radio")) {
                            // If type is Radio

                            final TextView rowTextView = new TextView(getContext());
                            // set some properties of rowTextView or something

                            rowTextView.setText("" + Form_obj.getString("name"));

                            // add the textview to the linearlayout
                            ll.addView(rowTextView);
                            JSONArray option_radio_Arr = Form_obj.getJSONArray("select");

                            ArrayList<String> spinnerArray = new ArrayList<String>();
                            final ArrayList<String> spinnerArray_options = new ArrayList<String>();
                            final ArrayList<String> spinnerArray_form_ids = new ArrayList<String>();

                            for (int op = 0; op < option_radio_Arr.length(); op++) {
                                // Add selection options Dynamicallu
                                JSONObject option_obj = option_radio_Arr.getJSONObject(op);
                                JSONObject option_val = option_obj.getJSONObject("Option");
                                option_val.getString("name");

                                spinnerArray.add("" + option_val.getString("name"));
                                spinnerArray_options.add("" + option_val.getString("id"));
                                spinnerArray_form_ids.add("" + option_val.getString("form_id"));

                            }

                            RadioGroup ll_radio = new RadioGroup(getContext());
                            // ll_radio.setId();
                            ll_radio.setOrientation(LinearLayout.HORIZONTAL);

                            ll_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    // TODO Auto-generated method stub

                                    checkedId = checkedId - 1;

//

                                }
                            });

                            JSONArray option_Arr = Form_obj.getJSONArray("select");
                            for (int op_1 = 0; op_1 < option_Arr.length(); op_1++) {
                                // Add selection options Dynamically
                                JSONObject option_obj = option_Arr.getJSONObject(op_1);
                                JSONObject option_val = option_obj.getJSONObject("Option");
                                option_val.getString("name");
                                RadioButton rdbtn = new RadioButton(getContext());
                                rdbtn.setId(View.generateViewId());
                                rdbtn.setText(option_val.getString("name"));
                                ll_radio.addView(rdbtn);
                            } // End For Loop
                            ll.addView(ll_radio);
                        }


                    } // End for Loop
                    // Setting up adapters


                } catch (Exception v) {

                    Variables.pDialog.hide();
                    Methods.Log_d_msg(getContext(), "Error " + v.toString());
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Variables.pDialog.hide();
            }
        };
    }
    // ENd Data From API


    private void uploadImage(Uri filePath) {

        if (filePath != null && !filePath.toString().startsWith("http")) {
            Methods.Log_d_msg(getContext(), "" + !filePath.toString().startsWith("http"));

//            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try {
                                //progressDialog.dismiss();
                            } catch (Exception b) {

                            }

                            Uri downloadUrl = Uri.parse(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            Download_imgs.add(downloadUrl.toString());


                            // Add data into Json Array
                            try {
                                JSONObject idsJsonObject = new JSONObject();
                                idsJsonObject.put("image", downloadUrl.toString().replace("150_", ""));
                                recipients.put(idsJsonObject);

                            } catch (Exception v) {
                                Methods.Log_d_msg(getContext(), "Error in Json Objects");
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // progressDialog.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());

                        }
                    });
        } else {

            // If image is link
            try {
                Methods.Log_d_msg(getContext(), "" + filePath.toString());
                JSONObject idsJsonObject = new JSONObject();
                idsJsonObject.put("image", filePath.toString());
                recipients.put(idsJsonObject);


            } catch (Exception v) {
                Methods.Log_d_msg(getContext(), "Error in Json Objects");
            }

        } // End else part
    }

    public void API_Call(final other_call_back json_ready) {
        int oo = Gallary_pic_List.size() * 1000;


//        Gallary_Pics_Get_Set gallary_pic = new Gallary_Pics_Get_Set(
//                Uri.parse(nn)
//        );
//        Gallary_pic_List.add(gallary_pic);


        try {

//            for(int ed_i = 0; ed_i< allEds.size(); ed_i++){
//                allEds.get(i).getText().toString();
//
//            }

            for (int i = 0; i < allEds.size(); i++) {
                try {
//                    Edittext_Array_ids
                    JSONObject form_data = new JSONObject();
                    form_data.put("form_id", Edittext_Array_ids.get(i));
                    form_data.put("option_id", "");
                    form_data.put("value", "" + allEds.get(i).getText().toString());
                    Form_data_Array.put(form_data);
                    Methods.Log_d_msg(getContext(), "Edit Text " + Form_data_Array.toString() + " " + Edittext_Array_ids.get(i));
                } catch (Exception v) {
                    Methods.Log_d_msg(getContext(), "0 in All EditTexts " + v.toString());
                }

            }


            for (int i = 0; i < All_Array_form_ids.size(); i++) {
                // Get All values from Shared Prefrence
                String key = "" + prefix_option + "_" + All_Array_form_ids.get(i);
                String option_id = SharedPrefrence.get_offline(getContext(), key);


                try {
                    JSONObject form_data = new JSONObject();
                    form_data.put("form_id", All_Array_form_ids.get(i));
                    form_data.put("option_id", option_id);
                    form_data.put("value", "");
                    Form_data_Array.put(form_data);
                } catch (Exception v) {
                    Methods.Log_d_msg(getContext(), "0 " + v.toString());
                }


            } // End For Loop

//////////////////////////////////

            if (post_id.equals("")) {
                // if new Post then images add into Array
                mainObject.put("images", recipients);
            } else {
                // If old post is going to edit then no need

            }


            mainObject.put("field_values", Form_data_Array);

        } catch (JSONException e) {
            Methods.Log_d_msg(getContext(), "" + e.toString());
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONObject user_data_objs;
                try {

                    json_ready.Get_Response_ok(mainObject.toString());

                } catch (Exception v) {
                    Methods.Log_d_msg(getContext(), "Err in Posting " + v.toString());
                }


            }
        }, 8000);


        // Download_imgs
    }


    public void Add_Post_Method() {
        try {
            Methods.Log_d_msg(getContext(), "" + ET_email.getText().toString() + " 2 " + phone_num.getText().toString() + "" +
                    " " + price.getText().toString());

            String name = SharedPrefrence.get_user_name_from_json(getContext());
            String email = SharedPrefrence.get_user_email_from_json(getContext());
            String phone = SharedPrefrence.get_user_phone_from_json(getContext());
            final JSONObject user_data_objs;
//            sendObj.put("type" , "mobile_slider");
            String user_id = SharedPrefrence.get_user_id_from_json(getContext());
            Methods.Log_d_msg(getContext(), "User Id " + user_id);
            if (post_id.equals("")) {
                // If user want to create new Posts

                phone = country_dial_code + phone_num.getText().toString();

                user_data_objs = new JSONObject("{'user_id': '" + user_id + "'," +
                        " 'description' : '" + Methods.replaceString(desc_text_1.getText().toString()) + "', 'language_id' : '1', 'title' : '" + ET_title.getText().toString() + "',  " +
                        " 'city_id' : '" + city_id + "', 'locality' : '' , 'name' : '" + ET_name.getText().toString() + "' , " +
                        " 'email' : '" + email + "' , " +
                        "  'mobile' : '" + phone + "', 'main_category_id' : '" + main_cate_id + "' , 'country_id' : '" + country_id + "' , 'sub_category_id' : '" + sub_cate_id + "' , 'price': '" + price.getText().toString() + "' ,  " +
                        "  " + mainObject.toString().replaceFirst("\\{", "") + "  " +
                        "} ");

            } else {

                phone = country_dial_code + phone_num.getText().toString();
                // If user wants to edit Posts
                user_data_objs = new JSONObject("{'user_id': '" + user_id + "',  'id' : '" + post_id + "', " +
                        " 'description' : '" + Methods.replaceString(desc_text_1.getText().toString()) + "', 'language_id' : '1', 'title' : '" + ET_title.getText().toString() + "',  " +
                        " 'locality' : 'local' , 'city_id': '" + city_id + "' , 'name' : '" + ET_name.getText().toString() + "' , " +
                        " 'email' : '" + email + "' , " +
                        "  'mobile' : '" + phone + "', 'main_category_id' : '" + main_cate_id + "' , 'country_id' : '" + country_id + "' , 'sub_category_id' : '" + sub_cate_id + "' , 'price': '" + price.getText().toString() + "' ,  " +
                        "  " + mainObject.toString().replaceFirst("\\{", "") + "  " +
                        "} ");


            }


            Methods.Log_d_msg(getContext(), "" + user_data_objs.toString());

            Log.d("resp_string", user_data_objs.toString());

            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_LINKS.API_Add_Post,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {


                            pd.hide();
                            Methods.toast_msg(getContext(), "Res " + response.toString());

                            //pd.hide();

                            try {
                                if (which_class.equals("" + Variables.Var_fragment)) {
                                    // Fragment
                                    // If user com from Existing Post
                                    getFragmentManager().popBackStack();
                                } else {
                                    getActivity().finish();
                                }
                            } catch (Exception b) {

                                getActivity().finish();

                            }
//                            pd.hide();
//                           getActivity().finish();


                        }
                    }


            );


        } catch (Exception v) {
            pd.hide();


            Methods.Log_d_msg(getContext(), "Err " + v.toString());
        }


    }


    // Get Country List

    public void Get_City() {

        // Get Default CCountry ID


        try {
            String default_country = SharedPrefrence.get_offline(getContext(),
                    SharedPrefrence.share_default_country_info
            );
            JSONObject de = new JSONObject(default_country);
            country_id = de.getString("country_id");

        } catch (Exception b) {
            Methods.Log_d_msg(getContext(), "" + b.toString());
        }


        Li_country_spinner = view.findViewById(R.id.country_spinner);


        initVolleyCallback_country();

        Variables.mVolleyService = new VolleyService(Variables.mResultCallback, getContext());
//        pd.show();
        try {

            JSONObject sendObj = new JSONObject();
            sendObj.put("country_id", "" + country_id);

            Variables.mVolleyService.postDataVolley("POSTCALL", API_LINKS.API_CITY_LIST, sendObj);

        } catch (Exception v) {
            v.printStackTrace();
            Methods.Log_d_msg(getContext(), "" + v.toString());
        }

    } // End method to get home upload

    // TODO init for Home Posts

    // Initialize Interface Call Backs
    void initVolleyCallback_country() {
        Variables.mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                pd.hide();
                try {
                    Methods.Log_d_msg(getContext(), "City" + response.toString());
                    JSONArray msg = response.getJSONArray("msg");

                    final TextView rowTextView = new TextView(getContext());

                    // set some properties of rowTextView or something
                    rowTextView.setText("City ");
                    // rowTextView.setTex
                    rowTextView.setTextSize(14);
                    rowTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                    rowTextView.setPadding(41, 0, 0, 0);
                    // add the textview to the linearlayout

                    Li_country_spinner.addView(rowTextView);

                    ArrayList<String> spinnerArray = new ArrayList<String>();
                    String def_city = SharedPrefrence.get_offline(getContext(), "" + SharedPrefrence.share_default_city_info);
                    JSONObject city_obj = new JSONObject(def_city);
                    city_obj.getString("city_name");
                    Variables.Var_default_city = city_obj.getString("city_name");
                    for (int op = 0; op < msg.length(); op++) {
                        // Add selection options Dynamicallu
                        JSONObject option_obj = msg.getJSONObject(op);
                        JSONObject option_val = option_obj.getJSONObject("City");
                        option_val.getString("name");


                        if (post_id.equals("")) {
                            // If fresh Post or New Post
                            if (Variables.Var_default_city.equals("" + option_val.getString("name"))) {
                                spinnerArray.add(0, "" + option_val.getString("name"));
                                All_array_country.add(0, "" + option_val.getString("id"));
                                All_array_country_name.add(0, "" + option_val.getString("name"));
                            } else {
                                spinnerArray.add("" + option_val.getString("name"));
                                All_array_country.add("" + option_val.getString("id"));
                                All_array_country_name.add("" + option_val.getString("name"));
                            }
                        } else {
                            // If post editing

                            Methods.Log_d_msg(getContext(), "Post Edit New " + post_id + " " + city_id_for_edit_post);
                            if (city_id_for_edit_post.equals("" + option_val.getString("id"))) {
                                spinnerArray.add(0, "" + option_val.getString("name"));
                                All_array_country.add(0, "" + option_val.getString("id"));
                                All_array_country_name.add(0, "" + option_val.getString("name"));
                            } else {
                                spinnerArray.add("" + option_val.getString("name"));
                                All_array_country.add("" + option_val.getString("id"));
                                All_array_country_name.add("" + option_val.getString("name"));
                            }


                        }

                    }
                    SearchableSpinner spinner = new SearchableSpinner(getContext());
                    spinner.setPadding(18, 0, 0, 20);
                    LinearLayout.LayoutParams Spinner_design = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    Spinner_design.setMargins(0, 20, 0, 0);
                    //spinner.setLayoutParams(Spinner_design);

                    spinner.setLayoutParams(Spinner_design);
                    city_id = All_array_country.get(0);
                    city_name = All_array_country_name.get(0);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                            city_id = All_array_country.get(pos);
                        }

                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spinner.setBackground(getContext().getResources().getDrawable(R.drawable.bottom_gray_line));
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, R.id.text, spinnerArray);

                    spinner.setAdapter(spinnerArrayAdapter);

                    Li_country_spinner.addView(spinner);


                } catch (Exception b) {
                    Methods.toast_msg(getContext(), "Err in City " + b.toString());
                    pd.hide();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                pd.hide();

                Methods.toast_msg(getContext(), "Err " + error.toString());

            }
        };
    }
    // ENd Data From API


    // todo: Form for Post Editing

    public void Post_Editing_Form() {

        try {
            //JSONObject sendObj = new JSONObject("{ '': '' }");
            pd.show();

//            pd.show();

            JSONObject sendObj = new JSONObject();
            sendObj.put("post_id", "" + post_id);
            Volley_Requests.New_Volley(
                    getContext(),
                    "" + API_LINKS.API_Post_Detail_For_Edit_Form,
                    sendObj,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            Methods.Log_d_msg(getContext(), "From Class " + requestType + " Edit " + response.toString());
                            // Maipulate Response
                            pd.hide();
                            try {

                                JSONObject msg_obj = response.getJSONObject("msg");
                                JSONObject Post_obj = msg_obj.getJSONObject("Post");
                                JSONArray Img_arr = msg_obj.getJSONArray("PostImage");
                                JSONObject PostContact = msg_obj.getJSONObject("PostContact");
                                JSONObject PostTranslation_obj = msg_obj.getJSONObject("PostTranslation");
                                PostTranslation_obj.getString("description");
                                desc_text_1.setText("" + PostTranslation_obj.getString("description"));
                                //city_id_for_edit_post = Post_contact.getString("city_id");
                                String num = PostContact.getString("mobile").replace("null", "");
                                if (num.startsWith("+")) {
                                    num = num.substring(3);
                                }
                                phone_num.setText("" + num);

                                Methods.Log_d_msg(getContext(), "City_id " + city_id_for_edit_post);

                                if (Img_arr.length() > 0) {
                                    // If image array greater than 0
                                    num_images.setText(Img_arr.length() + "/" + Variables.Var_num_pics_in_upload_Ads);


                                    // after_one_pic_RL.setVisibility(View.VISIBLE);
                                    //camera_RL.setVisibility(View.GONE);

                                }

                                JSONObject sub_cate_obj = msg_obj.getJSONObject("SubCategory");

                                JSONObject PostTranslation = msg_obj.getJSONObject("PostTranslation");

                                ET_title.setText("" + PostTranslation.getString("title"));
                                JSONArray PostValue_arr = msg_obj.getJSONArray("PostValue");
                                Post_obj.getString("price");
                                price.setText("" + Post_obj.getString("price"));
                                // For Loop for PostValues

                                for (int o = 0; o < PostValue_arr.length(); o++) {
                                    JSONObject get_obj = PostValue_arr.getJSONObject(o);
                                    // End Selected Val Objects

                                    JSONObject Form_obj = get_obj.getJSONObject("Form");
                                    Form_obj.getString("id");
                                    Form_obj.getString("type");
                                    if (Form_obj.getString("type").equals("select")) {
                                        // If form type is select
                                        JSONArray option_Arr = Form_obj.getJSONArray("Option");
                                        final TextView rowTextView = new TextView(getContext());
                                        // set some properties of rowTextView or something

                                        rowTextView.setText("" + Form_obj.getString("name"));
                                        rowTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                                        rowTextView.setPadding(41, 0, 0, 0);
                                        rowTextView.setTextSize(12);
                                        LinearLayout.LayoutParams editTextParams_1 = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        editTextParams_1.setMargins(0, 30, 0, 12);

                                        rowTextView.setLayoutParams(editTextParams_1);
                                        // add the textview to the linearlayout
                                        ll.addView(rowTextView);


                                        ArrayList<String> spinnerArray = new ArrayList<String>();
                                        final ArrayList<String> spinnerArray_options = new ArrayList<String>();
                                        final ArrayList<String> spinnerArray_form_ids = new ArrayList<String>();
                                        String[] types = {"By Zip", "By Category"};
                                        for (int op = 0; op < option_Arr.length(); op++) {
                                            // Add selection options Dynamicallu
                                            JSONObject option_obj = option_Arr.getJSONObject(op);
                                            //JSONObject option_val =  option_obj.getJSONObject("Option");
                                            option_obj.getString("name");

                                            if (get_obj.getString("option_id").equals("0")) {
                                                // If Value type Input or Statement

                                                get_obj.getString("value");

                                            } else {

                                                /// Selected Val Object
                                                JSONObject selected_val_obj = get_obj.getJSONObject("Option");
                                                selected_val_obj.getString("id");
                                                selected_val_obj.getString("name");
                                                selected_val_obj.getString("form_id");

                                                if (selected_val_obj.getString("id").equals("" + option_obj.getString("id"))) {
                                                    // If selected option id is equal to option ID
                                                    spinnerArray.add(0, "" + option_obj.getString("name"));
//                                                    spinnerArray_options.add(0,"" + option_obj.getString("name"));
                                                    spinnerArray_options.add(0, "" + option_obj.getString("id"));
                                                    spinnerArray_form_ids.add(0, "" + option_obj.getString("form_id"));
                                                } else {
                                                    spinnerArray.add("" + option_obj.getString("name"));
                                                    spinnerArray_options.add("" + option_obj.getString("id"));
                                                    spinnerArray_form_ids.add("" + option_obj.getString("form_id"));
                                                }

                                            }


                                            //spinnerArray.add("" + option_obj.getString("name"));
                                            //todo: Old Code
//                                            spinnerArray_options.add("" + option_obj.getString("id"));
//                                            spinnerArray_form_ids.add("" + option_obj.getString("form_id"));


                                        } // Else Inner For Loop
                                        final SearchableSpinner spinner = new SearchableSpinner(getContext());
                                        //  spinner.setId();

                                        LinearLayout.LayoutParams Spinner_design = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        Spinner_design.setMargins(12, 0, 0, 0);
                                        //spinner.setLayoutParams(Spinner_design);
                                        spinner.setPadding(18, 0, 0, 0);

                                        spinner.setBackground(getContext().getResources().getDrawable(R.drawable.bottom_gray_line));
                                        // ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text, R.id.text, spinnerArray);

                                        spinner.setAdapter(spinnerArrayAdapter);
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                                    spinner.setLayoutMode();
//                                }
                                        // oioi
                                        //iCurrentSelection = spinner.getSelectedItemPosition();
                                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            //                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                                // if (iCurrentSelection != pos){

                                                if (All_Array_form_ids.contains(spinnerArray_form_ids.get(pos))) {

                                                    SharedPrefrence.save_info_share(
                                                            getContext(),
                                                            "" + spinnerArray_options.get(pos),
                                                            "" + prefix_option + "_" + spinnerArray_form_ids.get(pos)
                                                    );


                                                } else {
                                                    // All_Array_form_ids.add(spinnerArray_form_ids.get(pos));
                                                    All_Array_form_ids.add(spinnerArray_form_ids.get(pos));

                                                    SharedPrefrence.save_info_share(
                                                            getContext(),
                                                            "" + spinnerArray_options.get(pos),
                                                            "" + prefix_option + "_" + spinnerArray_form_ids.get(pos)
                                                    );

                                                }
                                                //}
                                                //iCurrentSelection = pos;
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }


                                        });


                                        ll.addView(spinner);
                                    } else if (Form_obj.getString("type").equals("input")) {

                                        final TextView rowTextView = new TextView(getContext());
                                        // set some properties of rowTextView or something
                                        rowTextView.setText("" + Form_obj.getString("name"));
                                        rowTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                                        rowTextView.setPadding(41, 0, 0, 0);
                                        rowTextView.setTextSize(12);


                                        LinearLayout.LayoutParams editTextParams_1 = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        editTextParams_1.setMargins(0, 30, 0, 12);

                                        rowTextView.setLayoutParams(editTextParams_1);

                                        // add the textview to the linearlayout
                                        ll.addView(rowTextView);


                                        LinearLayout.LayoutParams editTextParams_2 = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        editTextParams_2.setMargins(0, 30, 0, 12);
                                        //rowTextView.setLayoutParams(editTextParams_2);


                                        EditText editText = new EditText(getContext());
                                        editText.setHintTextColor(getContext().getResources().getColor(R.color.black));
                                        editText.setBackground(getContext().getResources().getDrawable(R.drawable.bottom_gray_line));
                                        editText.setTextColor(getContext().getResources().getColor(R.color.dark_gray));
                                        editText.setTextSize(14);
                                        editText.setText("" + get_obj.getString("value"));
                                        editText.setPadding(41, 0, 0, 50);
                                        editText.setLayoutParams(editTextParams_2);
                                        editText.setId(Integer.parseInt(Form_obj.getString("id")));
                                        allEds.add(editText);


                                        if (Form_obj.getString("field_type").equals("numeric")) {
                                            // If form field type is numeric then
                                            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        } else if (Form_obj.getString("field_type").equals("text")) {
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT);
                                        }

                                        Edittext_Array_ids.add(Integer.valueOf(Form_obj.getString("id")));


                                        ll.addView(editText);
                                        numberOfLines++;

                                    } else if (Form_obj.getString("type").equals("radio")) {
                                        // If type is Radio

                                        final TextView rowTextView = new TextView(getContext());
                                        // set some properties of rowTextView or something

                                        rowTextView.setText("" + Form_obj.getString("name"));

                                        // add the textview to the linearlayout
                                        ll.addView(rowTextView);
                                        JSONArray option_radio_Arr = Form_obj.getJSONArray("select");

                                        ArrayList<String> spinnerArray = new ArrayList<String>();
                                        final ArrayList<String> spinnerArray_options = new ArrayList<String>();
                                        final ArrayList<String> spinnerArray_form_ids = new ArrayList<String>();

                                        for (int op = 0; op < option_radio_Arr.length(); op++) {
                                            // Add selection options Dynamicallu
                                            JSONObject option_obj = option_radio_Arr.getJSONObject(op);
                                            JSONObject option_val = option_obj.getJSONObject("Option");
                                            option_val.getString("name");

                                            spinnerArray.add("" + option_val.getString("name"));
                                            spinnerArray_options.add("" + option_val.getString("id"));
                                            spinnerArray_form_ids.add("" + option_val.getString("form_id"));


//
                                        }

                                        RadioGroup ll_radio = new RadioGroup(getContext());
                                        // ll_radio.setId();
                                        ll_radio.setOrientation(LinearLayout.HORIZONTAL);

                                        ll_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                // TODO Auto-generated method stub

                                                checkedId = checkedId - 1;


                                            }
                                        });

                                        JSONArray option_Arr = Form_obj.getJSONArray("select");
                                        for (int op_1 = 0; op_1 < option_Arr.length(); op_1++) {
                                            // Add selection options Dynamically
                                            JSONObject option_obj = option_Arr.getJSONObject(op_1);
                                            JSONObject option_val = option_obj.getJSONObject("Option");
                                            option_val.getString("name");

                                            RadioButton rdbtn = new RadioButton(getContext());
                                            rdbtn.setId(View.generateViewId());
                                            rdbtn.setText(option_val.getString("name"));
                                            ll_radio.addView(rdbtn);
                                        } // End For Loop
                                        ll.addView(ll_radio);
                                    }


                                } // End For Loop


                                for (int i = 0; i < Img_arr.length(); i++) {
                                    JSONObject img = Img_arr.getJSONObject(i);
                                    img.getString("image");
                                    String img_id = img.getString("id");

                                    Gallary_Pics_Get_Set gallary_pic = new Gallary_Pics_Get_Set(
                                            Uri.parse(img.getString("image")),
                                            "" + img.getString("id"),
                                            "" + img.getString("post_id")
                                    );
                                    Gallary_pic_List.add(gallary_pic);
                                    // Alread images


                                }// End images For Loop


                                Selected_pic_adp = new Select_Pic_Adp(Gallary_pic_List, getContext(), num_pics);

                                LinearLayoutManager layoutManager
                                        = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

                                RV_pictures.setLayoutManager(layoutManager);
                                RV_pictures.setAdapter(Selected_pic_adp);

                            } catch (Exception v) {
                                Methods.Log_d_msg(getContext(), "Error In Post Editing " + v.toString());
//                                Methods.alert_dialogue(getContext(),"Title", "" + v.toString());
                                pd.hide();
                            }

                        }
                    }


            );


        } catch (Exception v) {
            pd.hide();
        }


        //  Get_City();

    }


    // Select image from camera and gallery
    private void selectImage() {

        try {
            PackageManager pm = getContext().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getContext().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            //Crop.pickImage(getActivity());
                            openCameraIntent();
                        } else if (options[item].equals("Choose From Gallery")) {
//                            Crop.pickImage(getActivity());
                            dialog.dismiss();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                try {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                                } catch (Exception e) {
                                    Intent photoPickerIntent = new Intent(getContext(), SplashScreen.class);
                                    startActivityForResult(photoPickerIntent, PICK_IMAGE_MULTIPLE);
                                }
                            }
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(getContext(), "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    //public static TextView counter_value;
    public static void update_counter(String value) {
        try {
//            num_pics =
//            num_pics.setText(value);
        } catch (Exception ex) {
            Log.d("Exception", "Exception of type" + ex.getMessage());
        }
    }

    @Override
    public void callback(View b, String ok) {
        //set this data to your textView
        // num_pics.setText(ok);
    }

    private void openCameraIntent() {

        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Methods.Log_d_msg(getContext(), "" + ex.toString());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext().getApplicationContext(), getActivity().getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, PICK_IMAGE_CAMERA);
            }
        }
    }

    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix /
                ".jpg",         // suffix /
                storageDir      // directory /
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }


    public void close_dialogue() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        //builder1.setTitle("Would you like to stop posting?");
        builder1.setMessage("Would you like to cancel posting?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (post_id.equals("")) {
                            // If user come from Ad New Post
                            getActivity().finish();
                        } else {
                            // If user com from Existing Post
                            getFragmentManager().popBackStack();
                        }

                        try {
                            // MyAds.header.setVisibility(View.VISIBLE);
                        } catch (Exception b) {

                        }
                        dialog.cancel();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });


        AlertDialog alert11 = builder1.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

            alert11.show();

        } else {
            alert11.show();
        }

    }


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }


    public void Upload_ad_image_on_adding_post(String base64, final int num_iteration) {
        //  Methods.toast_msg(EditProfile.this,"Base " + base64);
        String url;
        try {
            pd.show();

            JSONObject user_data_objs = new JSONObject();
            //user_data_objs.put("user_id","" + user_id);
            JSONObject file_Data = new JSONObject();


            if (post_id.equals("")) {

                // If User post new ad
                file_Data.put("file_data", base64);
                user_data_objs.put("image", file_Data);
                url = "" + API_LINKS.API_Demo_pic;  // If user add new ad post

                Methods.toast_msg(getContext(), " " + user_data_objs.toString() + " " + url + " New Post Image");


            } else {
                // If user Edit the post
                file_Data.put("file_data", base64);
                user_data_objs.put("image", file_Data);
                user_data_objs.put("post_id", post_id);
                url = "" + API_LINKS.API_update_Post_Image_On_Post_Updating;

                Methods.toast_msg(getContext(), " " + user_data_objs.toString() + " " + url + " Edit Post Image");


            }


            Volley_Requests.New_Volley(
                    getContext(),
                    "" + url,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            //Methods.alert_dialogue(getContext(),"Pic ","From Class " + requestType+  " " + response.toString());
                            Methods.toast_msg(getContext(), "Pic Name " + response.toString());
                            // pd.hide();
                            // swipe_loader.setRefreshing(false);

                            pd.hide();
                            try {

                                JSONObject msg_obj = response.getJSONObject("msg");
                                String image_name = msg_obj.getString("image");
                                String thumb_path = msg_obj.optString("thumb_path");
                                Methods.toast_msg(getContext(), "Image " + image_name);
//                                JSONObject user_obj = msg_obj.getJSONObject("User");

                                Download_imgs.add(image_name);

                                // Add data into Json Array
//                                try{
//                                    JSONObject idsJsonObject = new JSONObject();
//                                    idsJsonObject.put("image", image_name);
//                                    recipients.put(idsJsonObject);
//
//                                }catch (Exception v){
//                                    Methods.Log_d_msg(getContext(),"Error in Json Objects");
//                                }

                                String nn = API_LINKS.BASE_URL + thumb_path;

                                Gallary_Pics_Get_Set gallary_pic = new Gallary_Pics_Get_Set(
                                        Uri.parse(nn),
                                        "",
                                        "" + post_id
                                );
                                Gallary_pic_List.add(gallary_pic);

                                // Setup Adapter
                                Selected_pic_adp = new Select_Pic_Adp(Gallary_pic_List, getContext(), num_pics);

                                LinearLayoutManager layoutManager
                                        = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


                                RV_pictures.setLayoutManager(layoutManager);
                                RV_pictures.setAdapter(Selected_pic_adp);

                                if (num_iteration == Gallary_pic_List.size() - 1) {
                                    int pp = Gallary_pic_List.size() - 1;
                                    Methods.toast_msg(getContext(), "" + num_iteration + " API RN " + pp);

                                    //Add_Post_Method();
//                                    API_Call(new other_call_back() {
//                                        @Override
//                                        public void Get_Response_ok(String done) {
//                                            Add_Post_Method();
//                                        }
//                                    });

                                }


                            } catch (Exception b) {
                                pd.hide();
                                Methods.Log_d_msg(getContext(), "" + b.toString());
                            }


                        }
                    }


            );


        } catch (Exception b) {
            pd.hide();
        }


    }


    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    // Method to change images link to Array of JSON
    public void create_json_arr_ad_images() {

        for (int i = 0; i < Gallary_pic_List.size(); i++) {
            Gallary_Pics_Get_Set gallary_pic = Gallary_pic_List.get(i);
            gallary_pic.getImage_Uri();

            try {
                JSONObject idsJsonObject = new JSONObject();
                idsJsonObject.put("image", Methods.getFileNameFromURL(gallary_pic.getImage_Uri().toString().replace("150_", "")));
                recipients.put(idsJsonObject);

            } catch (Exception v) {
                Methods.Log_d_msg(getContext(), "Error in Json Objects");
            }

        } // End For Loop

    } // End Method

    /// API Calling when user dellete image

    public static void del_old_image_on_editing(String ad_id, String img_url_without_base_url, final Context context) {


//        API_Del_Existing_image_on_Post
        // pd.hide();
        try {
            JSONObject user_data_objs = new JSONObject();
            user_data_objs.put("id", ad_id);
            user_data_objs.put("image", img_url_without_base_url);
            Methods.toast_msg(context, "" + user_data_objs.toString());

            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_Del_Existing_image_on_Post,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try {
                                //pd.hide();
                                Methods.toast_msg(context, "Deleting Response" + response);
                                // swipe_loader.setRefreshing(false);
                                String code = response.getString("code");
                                if (code.equals("200")) {
                                    Methods.toast_msg(context, "Success");
                                    // Successfully Sign Up , Go to Drawer Activity
                                    JSONObject msg_obj = response.getJSONObject("msg");


                                }


                            } catch (Exception b) {
                                Methods.Log_d_msg(context, "" + b.toString());
                                //pd.hide();
                            }


                        }
                    }


            );


        } catch (Exception b) {
            //swipe_loader.setRefreshing(false);
            Methods.Log_d_msg(context, "" + b.toString());
            //pd.hide();
        }


    }


    public static void Update_image_for_Editing(String ad_id, String img_url_without_base_url, String post_id, final Context context) {


//        API_Del_Existing_image_on_Post

        try {
            JSONObject user_data_objs = new JSONObject();
            user_data_objs.put("id", post_id); // Basically this is Post ID not Image ID
            user_data_objs.put("image", img_url_without_base_url);

            Volley_Requests.New_Volley(
                    context,
                    "" + API_LINKS.API_update_Post_Image_On_Post_Updating,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try {
                                //pd.hide();
                                Methods.toast_msg(context, "Updating Res " + response);
                                // swipe_loader.setRefreshing(false);
                                String code = response.getString("code");
                                if (code.equals("200")) {
                                    Methods.toast_msg(context, "Success");

                                    // Successfully Sign Up , Go to Drawer Activity
                                    JSONObject msg_obj = response.getJSONObject("msg");

                                }


                            } catch (Exception b) {
                                Methods.Log_d_msg(context, "" + b.toString());
                                //pd.hide();
                            }


                        }
                    }


            );


        } catch (Exception b) {
            //swipe_loader.setRefreshing(false);
            Methods.Log_d_msg(context, "" + b.toString());
            //pd.hide();
        }
    }


    public void upload_image() {

    }


}
