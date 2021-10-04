package com.marius.valeyou.ui.fragment.loadtype.removaljob.whentype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.BookingModel;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityWhenTypeBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.fragment.loadtype.removaljob.when.WhenActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WhenTypeActivity extends AppActivity<ActivityWhenTypeBinding, WhenTypeActivityVM> {
    private static final int WHEN_TYPE_RESULT = 3;
    private String job_type = "";

    private long start_time_stamp;
    private String start_time;
    private long time_stamp;
    long endTimeStamp;
    String comeFrom;
    int provider_id;

    String type;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, WhenTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<WhenTypeActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_when_type, WhenTypeActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhenTypeActivityVM vm) {
        binding.header.tvTitle.setText(getResources().getString(R.string.when));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));

        comeFrom = getIntent().getStringExtra("comeFrom");
        if (comeFrom.equalsIgnoreCase("direct")) {
            provider_id = getIntent().getIntExtra("provider_id",0);
            type = getIntent().getStringExtra("job_type");
        }

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                Intent intent;
                switch (view != null ? view.getId() : 0) {
                   /* case R.id.cv_now:
                        job_type = "1";
                        intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);

                       *//* Calendar c = Calendar.getInstance();
                        SimpleDateFormat startFrmt = new SimpleDateFormat("MMM dd yyyy");
                        long start_time_stamp = c.getTimeInMillis() / 1000;
                        String start_time = startFrmt.format(c.getTime());

                        intent = new Intent();
                        intent.putExtra("start", start_time_stamp);
                        intent.putExtra("end",  0);
                        intent.putExtra("time", start_time_stamp);
                        intent.putExtra("job_type", job_type);
                        intent.putExtra("start_time", start_time);
                        intent.putExtra("end_time", "");
                        setResult(Activity.RESULT_OK, intent);*//*

                        break;*/
                    case R.id.cv_today:
                        job_type = "2";
                      /*  intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);*/


                        showDialog(WhenTypeActivity.this);

                        break;
                    case R.id.cv_future:
                        job_type = "3";
                        intent = WhenActivity.newIntent(WhenTypeActivity.this,job_type,provider_id,type);
                        startActivityForResult(intent, WHEN_TYPE_RESULT);
                        break;
                }
            }
        });



        vm.bookingsEvent.observe(this, new Observer<Resource<List<BookingModel>>>() {
            @Override
            public void onChanged(Resource<List<BookingModel>> listResource) {
                switch (listResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();

                        List<BookingModel> bookingsList = listResource.data;

                        if (bookingsList.size()>0) {

                            AlertDialog alertDialog1 = new AlertDialog.Builder(
                                    WhenTypeActivity.this).create();
                            alertDialog1.setTitle("Note");
                            alertDialog1.setMessage("This provider already has "+bookingsList.size()+ " bookings in this time slot. Please try with another date.");
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            alertDialog1.show();

                        }else{

                            Intent intent = new Intent();
                            intent.putExtra("start", start_time_stamp);
                            intent.putExtra("end", endTimeStamp);
                            intent.putExtra("time", time_stamp);
                            intent.putExtra("category_type", job_type);
                            intent.putExtra("start_time", start_time_stamp);
                            intent.putExtra("end_time", endTimeStamp);
                            setResult(Activity.RESULT_OK, intent);
                            finish();



                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(listResource.message);
                        break;
                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(listResource.message);
                        break;
                }
            }
        });


    }

    private void setStartDate(Calendar startDate) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat startFrmt = new SimpleDateFormat("MMM dd yyyy");
        start_time_stamp = startDate.getTimeInMillis() / 1000;
        String date = fmtOut.format(startDate.getTime());
        String split[] = date.split("-");

        start_time = startFrmt.format(startDate.getTime());

        time_stamp = startDate.getTimeInMillis() / 1000;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WHEN_TYPE_RESULT:
                if (Activity.RESULT_OK == resultCode) {
                    Intent intent = new Intent();
                    intent.putExtra("start", data.getLongExtra("start", 0));
                    intent.putExtra("end", data.getLongExtra("end", 0));
                    intent.putExtra("time", data.getLongExtra("time", 0));
                    intent.putExtra("category_type", data.getStringExtra("category_type"));
                    intent.putExtra("start_time", data.getStringExtra("start_time"));
                    intent.putExtra("end_time", data.getStringExtra("end_time"));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }


    public void showDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.holder_set_hours_dialog);

        EditText hoursetxt = dialog.findViewById(R.id.totalHours);

        Button cancelButton = dialog.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button save = dialog.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hoursetxt.getText().toString().isEmpty()){

                    Toast.makeText(context, getResources().getString(R.string.enter_number_of_hours), Toast.LENGTH_SHORT).show();

                }else{
                    int hours = Integer.parseInt(hoursetxt.getText().toString().trim());
                    Calendar calendarObj = Calendar.getInstance();
                    SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
                    SimpleDateFormat startFrmt = new SimpleDateFormat("MMM dd yyyy");
                    start_time_stamp = calendarObj.getTimeInMillis() / 1000;
                    String date = fmtOut.format(calendarObj.getTime());
                    time_stamp = calendarObj.getTimeInMillis() / 1000;
                    long milliseconds = hours*60*60;
                    endTimeStamp = start_time_stamp + milliseconds;

                    Intent intent = new Intent();
                    intent.putExtra("category_type", job_type);
                    intent.putExtra("start", start_time_stamp);
                    intent.putExtra("end", endTimeStamp);
                    intent.putExtra("time", time_stamp);
                    intent.putExtra("start_time", start_time_stamp);
                    intent.putExtra("end_time", endTimeStamp);
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                   /* if (comeFrom.equalsIgnoreCase("direct")){

                      if (type.equalsIgnoreCase("1")){

                          viewModel.getBoolings(start_time_stamp+"", String.valueOf(provider_id));

                      }else{

                          Intent intent = new Intent();
                          intent.putExtra("start", start_time_stamp);
                          intent.putExtra("end", endTimeStamp);
                          intent.putExtra("time", time_stamp);
                          intent.putExtra("category_type", job_type);
                          intent.putExtra("start_time", start_time_stamp);
                          intent.putExtra("end_time", endTimeStamp);
                          setResult(Activity.RESULT_OK, intent);
                          finish();


                      }

                    }else{

                        Intent intent = new Intent();
                        intent.putExtra("start", start_time_stamp);
                        intent.putExtra("end", endTimeStamp);
                        intent.putExtra("time", time_stamp);
                        intent.putExtra("start_time", start_time_stamp);
                        intent.putExtra("end_time", endTimeStamp);
                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    }*/

                    dialog.dismiss();


                }




            }
        });

        dialog.show();

    }


}
