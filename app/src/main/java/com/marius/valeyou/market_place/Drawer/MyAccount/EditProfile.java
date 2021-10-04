package com.marius.valeyou.market_place.Drawer.MyAccount;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.marius.valeyou.R;
import com.marius.valeyou.market_place.CodeClasses.Methods;
import com.marius.valeyou.market_place.CodeClasses.Variables;
import com.marius.valeyou.market_place.Drawer.Home.City_Listt.City_loc;
import com.marius.valeyou.market_place.Shared_Prefs.SharedPrefrence;
import com.marius.valeyou.market_place.Volley_Package.API_LINKS;
import com.marius.valeyou.market_place.Volley_Package.CallBack;
import com.marius.valeyou.market_place.Volley_Package.Volley_Requests;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    AppBarLayout abl;
    RelativeLayout city_rl;
    ImageView add_mbl, add_email, close;
    TextView email_user;
    String user_info;
    EditText name_tie_id;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    ImageView profile_pic, edit_img;
    TextView save_button;



    CollapsingToolbarLayout ctb_id;
    RelativeLayout gradient_RL;
    EditText last_name_id, first_name_id;
    TextInputLayout name_til_id, name_last_id;

    ProgressDialog pd;


    // TODO: (EditProfile.java) Method to change colors Dynamically.
    // ==> You must call it at the end of on create Method. :-)
    public void Change_Color_Dynmic (){

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }
            gradient_RL.setBackground(Methods.getColorScala());
            ctb_id.setContentScrimColor(Color.parseColor(Variables.Var_App_Config_header_bg_color));

            name_last_id.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));
            name_til_id.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor(Variables.Var_App_Config_header_bg_color)));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor(Variables.App_status_bar_color_code));
            }

        }catch (Exception b){

        } // End Try catch Body

    } // End method to change Color Dynamically

    //SwipeRefreshLayout swipe_loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

       // swipe_loader = findViewById(R.id.srl_id);
        name_til_id = findViewById(R.id.name_til_id);
        name_last_id = findViewById(R.id.name_last_id);
        // Progres bar;
        pd = new ProgressDialog(EditProfile.this);
        pd.setMessage(getResources().getString(R.string.loading));
        pd.setCancelable(false);
        edit_img = findViewById(R.id.edit_img);

        gradient_RL = findViewById(R.id.gradient_RL);
        name_tie_id = findViewById(R.id.name_tie_id);
        abl = (AppBarLayout) findViewById(R.id.abl_id);
        city_rl = (RelativeLayout) findViewById(R.id.city_rl_id);
        email_user = findViewById(R.id.email_user);
        profile_pic = findViewById(R.id.profile_pic);
        save_button = findViewById(R.id.save_button);
        //first_name_id = findViewById(R.id.name_tie_id);
        last_name_id = findViewById(R.id.last_name_id);

//        abl.setCon(Methods.getColorScala());

        ctb_id = findViewById(R.id.ctb_id);

        user_info = SharedPrefrence.get_offline(EditProfile.this,
                "" + SharedPrefrence.shared_user_login_detail_key
        );
        Methods.Log_d_msg(EditProfile.this,"" + SharedPrefrence.get_user_img_org(EditProfile.this));

        try {
            JSONObject user_obj = new JSONObject(user_info);

            name_tie_id.setText("" + user_obj.getString("full_name"));
            email_user.setText("" + user_obj.getString("email"));
           // last_name_id.setText("" + user_obj.getString("last_name"));

            Picasso.get()
                    .load(SharedPrefrence.get_user_img_org(EditProfile.this))
                    .placeholder(R.drawable.ic_profile_gray)
                    .error(R.drawable.ic_profile_gray)
                    .into(profile_pic);



        } catch (Exception b) {
            Methods.Log_d_msg(EditProfile.this,"Err " + b.toString());
           // Methods.alert_dialogue(EditProfile.this,"t" ,"Please try again later. " + user_info + " " + b.toString());
        }

        add_email = (ImageView) findViewById(R.id.add_email_id);
        add_mbl = (ImageView) findViewById(R.id.add_num_id);
        close = (ImageView) findViewById(R.id.close_id);

        double heightDp = getResources().getDisplayMetrics().heightPixels / 3.5;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) abl.getLayoutParams();
        lp.height = (int) heightDp;
        abl.setLayoutParams(lp);

        close.setOnClickListener(this);
        city_rl.setOnClickListener(this);
        add_email.setOnClickListener(this);
        add_mbl.setOnClickListener(this);
        profile_pic.setOnClickListener(this);
        save_button.setOnClickListener(this);
        edit_img.setOnClickListener(this);
        Change_Color_Dynmic();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_id:
                finish();
                break;

            case R.id.city_rl_id:
                startActivity(new Intent(EditProfile.this, City_loc.class));
                break;

            case R.id.add_email_id:
                AlertDialog.Builder build = new AlertDialog.Builder(EditProfile.this);
                final EditText et = new EditText(getApplicationContext());
                et.setHint("Your Email");
                et.setHintTextColor(getResources().getColor(R.color.black));
                et.setTextColor(getResources().getColor(R.color.black));
                et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                et.setSingleLine();
                FrameLayout container = new FrameLayout(EditProfile.this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.left);
                params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.left);
                params.topMargin = getResources().getDimensionPixelOffset(R.dimen.right);
                params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.left);
                et.setLayoutParams(params);
                container.addView(et);

                build.setTitle("Add New Email");
                build.setView(container);

                build.setPositiveButton("ADD & VERIFY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txt = et.getText().toString();
                    }
                });

                build.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                build.create();
                build.show();

                break;


            case R.id.add_num_id:
                AlertDialog.Builder build1 = new AlertDialog.Builder(EditProfile.this);
                final EditText et1 = new EditText(getApplicationContext());
                et1.setHint("Your Number");
                et1.setHintTextColor(getResources().getColor(R.color.black));
                et1.setTextColor(getResources().getColor(R.color.black));
                et1.setInputType(InputType.TYPE_CLASS_NUMBER);

                et1.setSingleLine();
                FrameLayout container1 = new FrameLayout(EditProfile.this);
                FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params1.leftMargin = getResources().getDimensionPixelSize(R.dimen.left);
                params1.rightMargin = getResources().getDimensionPixelOffset(R.dimen.left);
                params1.topMargin = getResources().getDimensionPixelOffset(R.dimen.right);
                params1.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.left);
                et1.setLayoutParams(params1);
                container1.addView(et1);

                build1.setTitle("Add New Number");
                build1.setView(container1);

                build1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String txt = et1.getText().toString();
                        TextView mobile_num = findViewById(R.id.mobile_num);
                        mobile_num.setText("" + txt);
                    }
                });

                build1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                build1.create();
                build1.show();

                break;
            case R.id.profile_pic:
                selectImage();
                break;
            case R.id.edit_img:
                selectImage();
                break;

            case R.id.save_button:

                //Edit_Profile("1","I","");
                String user_id = SharedPrefrence.get_user_id_from_json(EditProfile.this);
                Methods.Log_d_msg(EditProfile.this,"" + user_id);
                if(name_tie_id.getText().toString().length() == 0){
                    name_tie_id.setError("Please Fill");
                }else{
//                    Edit_Profile("" + user_id,
//                            "" + name_tie_id.getText().toString(),
//                            "" + last_name_id.getText().toString());

                    Edit_Profile("" + user_id,
                            "" + name_tie_id.getText().toString(),
                            "Last name" );
                }

                break;

        }
    }


    // Select image from camera and gallery
    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);

                            openCameraIntent();

                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(EditProfile.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(EditProfile.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                // New Code
                //Matrix matrix = new Matrix();
//                try {
//                   // Bitmap rotatedBitmap = null;
//                    ExifInterface exif = new ExifInterface(imageFilePath);
//                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
//                    switch (orientation) {
//                        case ExifInterface.ORIENTATION_ROTATE_90:
//                            matrix.postRotate(90);
//                            break;
//                        case ExifInterface.ORIENTATION_ROTATE_180:
//                            matrix.postRotate(180);
//                            break;
//                        case ExifInterface.ORIENTATION_ROTATE_270:
//                            matrix.postRotate(270);
//                            break;
//                        case ExifInterface.ORIENTATION_NORMAL:
//                        default:
//                            matrix.postRotate(0);
//
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                Uri selectedImage = (Uri.fromFile(new File(imageFilePath)));

                beginCrop(selectedImage);

                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
//                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                // todo: Demo Only Must be del later

               // Add_User_Image_new("" , Methods.create_base64(rotatedBitmap));


//                rotatedBitmap.getHeight();
//                rotatedBitmap.getWidth();
//                Methods.toast_msg(EditProfile.this,"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight());
//                //Bitmap resized = ThumbnailUtils.extractThumbnail(rotatedBitmap, 150, 150);

                //Bitmap selectImg = Methods.scaleBitmap(rotatedBitmap,1500,1500);
                //addPic.setImageBitmap(selectImg);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), resized, "Title", null);
                Uri.parse(path);


               // Crop.of(selectedImage, selectedImage).asSquare().start(EditProfile.this);

                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
               // Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);

                //UploadImage(baos);
//                profile_pic.setImageBitmap(resized);

                Methods.Log_d_msg(EditProfile.this,"Path " + imageFilePath);
//                Methods.toast_msg(EditProfile.this,"Width: " + rotatedBitmap.getWidth() + " Height: " +  rotatedBitmap.getHeight() + "" +
//                        "\n Comp h" + resized.getHeight() + " W " + resized.getWidth());

                Methods.Log_d_msg(EditProfile.this,"" + imageFilePath);
               // Add_User_Image("" + SharedPrefrence.get_user_id_from_json(EditProfile.this), Methods.create_base64(resized));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {

            try {
                Uri selectedImage = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.d("Activity", "Pick from Gallery::>>> ");
                bitmap.getWidth();
                bitmap.getHeight();
                Methods.toast_msg(EditProfile.this,"W " + bitmap.getWidth() + " H " + bitmap.getHeight());
                imgPath = getRealPathFromURI(selectedImage);
                beginCrop(selectedImage);
                // todo Add images
//                destination = new File(imgPath.toString());
//                profile_pic.setImageBitmap(bitmap);


                //Add_User_Image("" + SharedPrefrence.get_user_id_from_json(EditProfile.this), Methods.getFileToByte(imgPath));
                // Methods.Log_d_msg(getContext(),"Log \n " + Methods.getFileToByte(imgPath));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == 123) {
            handleCrop(resultCode, data);
        }



    }




    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = EditProfile.this.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void Add_User_Image(String user_id, String base64) {
          //  Methods.toast_msg(EditProfile.this,"Base " + base64);
        try{
            pd.show();

                      JSONObject  user_data_objs = new JSONObject();
            user_data_objs.put("user_id","" + user_id);
            JSONObject  file_Data  = new JSONObject();
            file_Data.put("file_data", base64);
            user_data_objs.put("image",file_Data );
            // Methods.alert_dialogue(EditProfile.this,"io","" + user_data_objs.toString());

            //swipe_loader.setRefreshing(true);

            Volley_Requests.New_Volley(
                    EditProfile.this,
                    "" + API_LINKS.API_User_Image,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                            // Methods.alert_dialogue(EditProfile.this,"Pic ","From Class " + requestType+  " " + response.toString());
                            pd.hide();
                            // swipe_loader.setRefreshing(false);

                            try{

                                JSONObject msg_obj = response.getJSONObject("msg");
                                JSONObject user_obj = msg_obj.getJSONObject("User");



                                SharedPrefrence.save_info_share(
                                        EditProfile.this,
                                        "" + user_obj.toString(),
                                        SharedPrefrence.shared_user_login_detail_key
                                );



                            }catch (Exception b){
                                pd.hide();
                                Methods.Log_d_msg(EditProfile.this,"" + b.toString());
                            }





                        }
                    }


            );




        }catch (Exception b){
            pd.hide();
        }


    }


    public void Add_User_Image_new (String user_id, String base64) {
        //  Methods.toast_msg(EditProfile.this,"Base " + base64);
        try{
            pd.show();

            JSONObject  user_data_objs = new JSONObject();
            //user_data_objs.put("user_id","" + user_id);
            JSONObject  file_Data  = new JSONObject();
            file_Data.put("file_data", base64);
            user_data_objs.put("image",file_Data );
            // Methods.alert_dialogue(EditProfile.this,"io","" + user_data_objs.toString());

            //swipe_loader.setRefreshing(true);

            Volley_Requests.New_Volley(
                    EditProfile.this,
                    "" + API_LINKS.API_Demo_pic,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {
                             Methods.alert_dialogue(EditProfile.this,"Pic ","From Class " + requestType+  " " + response.toString());
                            pd.hide();
                            // swipe_loader.setRefreshing(false);

                            try{

//                                JSONObject msg_obj = response.getJSONObject("msg");
//                                JSONObject user_obj = msg_obj.getJSONObject("User");



//                                SharedPrefrence.save_info_share(
//                                        EditProfile.this,
//                                        "" + user_obj.toString(),
//                                        SharedPrefrence.shared_user_login_detail_key
//                                );



                            }catch (Exception b){
                                pd.hide();
                                Methods.Log_d_msg(EditProfile.this,"" + b.toString());
                            }





                        }
                    }


            );




        }catch (Exception b){
            pd.hide();
        }


    }




    // Edit Profile pi

    public void Edit_Profile (String user_id,String first_Name, String last_name ){
        pd.show();
        try{
            JSONObject  user_data_objs = new JSONObject();
            user_data_objs.put("user_id", user_id);
            user_data_objs.put("full_name", first_Name );
            user_data_objs.put("last_name", last_name );
            //Methods.alert_dialogue(EditProfile.this,"io","" + user_data_objs.toString());
           // swipe_loader.setRefreshing(true);

            Volley_Requests.New_Volley(
                    EditProfile.this,
                    "" + API_LINKS.API_User_Edit_Profile,
                    user_data_objs,
                    "OK",

                    new CallBack() {
                        @Override
                        public void Get_Response(String requestType, JSONObject response) {

                            try{
                                pd.hide();
                                Methods.Log_d_msg(EditProfile.this,"" + response);
                               // swipe_loader.setRefreshing(false);
                                String code = response.getString("code");
                                if(code.equals("200")) {
                                    Methods.toast_msg(EditProfile.this,"Success" );

                                    // Successfully Sign Up , Go to Drawer Activity
                                    JSONObject msg_obj = response.getJSONObject("msg");
                                    JSONObject user_obj = msg_obj.getJSONObject("User");
                                    /// Save data into Shared Prefrence

                                    SharedPrefrence.save_info_share(
                                            EditProfile.this,
                                            user_obj.toString(),
                                            SharedPrefrence.shared_user_login_detail_key
                                    );
                                }



                                }catch (Exception b){
                                Methods.Log_d_msg(EditProfile.this,"" + b.toString());
                                pd.hide();
                            }





                        }
                    }


            );




        }catch (Exception b){
            //swipe_loader.setRefreshing(false);
            Methods.Log_d_msg(EditProfile.this ,"" + b.toString());
            pd.hide();
        }



    }


    private void openCameraIntent() {

        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Methods.Log_d_msg(EditProfile.this,"" + ex.toString());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileprovider", photoFile);
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
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix /
                ".jpg",         // suffix /
                storageDir      // directory /
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    public static Bitmap rotateImage (Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    // botoom there function are related to crop the image
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(500,500).start(EditProfile.this,123);

    }

    private void handleCrop (int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri userimageuri= Crop.getOutput(result);

            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(userimageuri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);



            String path=userimageuri.getPath();


            Matrix matrix = new Matrix();
            android.media.ExifInterface exif = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    exif = new android.media.ExifInterface(path);
                    int orientation = exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, 1);
                    switch (orientation) {
                        case android.media.ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case android.media.ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case android.media.ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //image_byteArray = out.toByteArray();


            profile_pic.setImageBitmap(rotatedBitmap);
            Add_User_Image("" + SharedPrefrence.get_user_id_from_json(EditProfile.this), Methods.create_base64(rotatedBitmap));


            // SavePicture();

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(EditProfile.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//    public android.support.v4.app.Fragment getCurrentFragment(){
//        return getSupportFragmentManager().findFragmentById(R.id.MainMenuFragment);
//
//    }


}
