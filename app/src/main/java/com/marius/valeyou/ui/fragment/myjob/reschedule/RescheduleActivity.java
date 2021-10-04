package com.marius.valeyou.ui.fragment.myjob.reschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.marius.valeyou.BR;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityRescheduleBinding;
import com.marius.valeyou.di.base.view.AppActivity;
import com.marius.valeyou.ui.activity.login.LoginActivity;
import com.marius.valeyou.util.AppUtils;
import com.marius.valeyou.util.databinding.ImageViewBindingUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RescheduleActivity extends AppActivity<ActivityRescheduleBinding,RescheduleActivityVM> {

    int job_id;
    Calendar myCalendar = Calendar.getInstance();
    Long timestamp;


    public static Intent newIntent(Activity activity, int job_id) {
        Intent intent = new Intent(activity, RescheduleActivity.class);
        intent.putExtra("id",job_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected BindingActivity<RescheduleActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_reschedule, RescheduleActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(RescheduleActivityVM vm) {
        binding.header.tvTitle.setText("Reschedule Job");
        job_id = getIntent().getIntExtra("id",0);

        vm.getJobDetaial(vm.sharedPref.getUserData().getAuthKey(), String.valueOf(job_id));

        vm.jobDetailBean.observe(this, new Observer<Resource<JobDetailsBean>>() {
            @Override
            public void onChanged(Resource<JobDetailsBean> jobDetailModelResource) {
                switch (jobDetailModelResource.status) {
                    case LOADING:
                        binding.setCheck(true);
                        break;
                    case SUCCESS:
                        binding.setCheck(false);
                        binding.setVariable(BR.bean, jobDetailModelResource.data);

                        if (jobDetailModelResource.data.getIsSchedule()==1){

                            String date = AppUtils.getDateTime(Long.parseLong(jobDetailModelResource.data.getReScheduleDate()));
                            binding.txtDate.setText(date);

                        }else{

                            if (jobDetailModelResource.data.getDate() != null) {
                                String date = AppUtils.getDateTime(Long.parseLong(jobDetailModelResource.data.getDate()));
                                binding.txtDate.setText(date);
                            }

                        }

                        if (jobDetailModelResource.data.getOrderImages().size() > 0) {
                            String image = jobDetailModelResource.data.getOrderImages().get(0).getImages();
                            ImageViewBindingUtils.setJobImage(binding.jobImage, image);

                        } else {

                            binding.jobImage.setImageResource(R.drawable.new_placeholder);

                        }

                        break;
                    case ERROR:
                        binding.setCheck(false);
                        vm.error.setValue(jobDetailModelResource.message);
                        if (jobDetailModelResource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(RescheduleActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;

                    case WARN:
                        binding.setCheck(false);
                        vm.warn.setValue(jobDetailModelResource.message);
                        break;
                }
            }
        });

        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onChanged(View view) {
                switch (view.getId()){

                    case R.id.select_date:
                        datePicker().show();
                        break;


                    case R.id.rescheduleBtn:

                        if (timestamp!=null){
                            vm.rescheduleJob(vm.sharedPref.getUserData().getAuthKey(),String.valueOf(job_id),String.valueOf(timestamp));
                        }else{
                            vm.error.setValue("Please select date and time");
                        }
                        break;

                }
            }
        });

        vm.rescheduleJobEvent.observe(this, new Observer<Resource<SimpleApiResponse>>() {
            @Override
            public void onChanged(Resource<SimpleApiResponse> simpleApiResponseResource) {
                switch (simpleApiResponseResource.status) {
                    case LOADING:
                        showProgressDialog(R.string.plz_wait);
                        break;
                    case SUCCESS:
                        dismissProgressDialog();
                        vm.success.setValue(simpleApiResponseResource.message);
                        finish(true);
                        break;

                    case ERROR:
                        dismissProgressDialog();
                        vm.error.setValue(simpleApiResponseResource.message);
                        if (simpleApiResponseResource.message.equalsIgnoreCase("unauthorised")) {

                            Intent intent1 = LoginActivity.newIntent(RescheduleActivity.this);
                            startNewActivity(intent1, true, true);

                        }
                        break;
                    case WARN:
                        dismissProgressDialog();
                        vm.warn.setValue(simpleApiResponseResource.message);
                        break;
                }
            }
        });





    }

    private DatePickerDialog datePicker() {
        DatePickerDialog dialog = new DatePickerDialog(RescheduleActivity.this,  new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                showHourPicker(myCalendar);


            }

        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis());

        return dialog;
    }

    public void showHourPicker(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    String myFormat = "YYYY-MM-dd hh:mm a";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    binding.selectDate.setText(sdf.format(calendar.getTime()));

                    timestamp = calendar.getTimeInMillis()/1000;



                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}