package com.marius.valeyou.ui.fragment.loadtype.removaljob.when;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.BookingModel;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.databinding.ActivityWhenBinding;
import com.marius.valeyou.di.base.view.AppActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.Observer;

public class WhenActivity extends AppActivity<ActivityWhenBinding, WhenActivityVM> {

    final Calendar myCalendar = Calendar.getInstance();
    private long start_time_stamp= 0;
    private long end_time_stamp;
    private long time_stamp;
        int provider_id;
    private String start_time = "";
    private String end_time = "null";

    private String job_type;
    private String type;

    public static Intent newIntent(Activity activity, String job_type,int provider_id,String type) {
        Intent intent = new Intent(activity, WhenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("job_type", job_type);
        intent.putExtra("provider_id", provider_id);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    protected BindingActivity<WhenActivityVM> getBindingActivity() {
        return new BindingActivity<>(R.layout.activity_when, WhenActivityVM.class);
    }

    @Override
    protected void subscribeToEvents(WhenActivityVM vm) {
        job_type = getIntent().getStringExtra("job_type");
        provider_id = getIntent().getIntExtra("provider_id",0);
        type = getIntent().getStringExtra("type");



        binding.header.tvTitle.setText(getResources().getString(R.string.when));
        binding.header.tvTitle.setTextColor(getResources().getColor(R.color.white));
        setCurrentTime();
        vm.base_back.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                finish(true);
            }
        });

        vm.base_click.observe(this, new Observer<View>() {
            @Override
            public void onChanged(View view) {
                switch (view != null ? view.getId() : 0) {
                    case R.id.btn_save:

                        switch (job_type) {
                           /* case "1":
                                if (start_time != null && start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue("please select date");
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue("please select time");
                                    return;
                                }
                                break;
                            case "2":
                                if (start_time == null || start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue("please select date");
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue("please select time");
                                    return;
                                }
                                break;*/
                            case "3":
                                if (start_time != null && start_time.equalsIgnoreCase("")) {
                                    vm.error.setValue(getResources().getString(R.string.select_start_date));
                                    return;
                                }
                                if (end_time != null && end_time.equalsIgnoreCase("null")) {
                                    vm.error.setValue(getResources().getString(R.string.select_end_date));
                                    return;
                                }
                                if (time_stamp == 0) {
                                    vm.error.setValue(getResources().getString(R.string.select_time));
                                    return;
                                }

                                if (start_time != null && !start_time.equalsIgnoreCase("")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("start", start_time_stamp);
                                    intent.putExtra("end", end_time_stamp);
                                    intent.putExtra("time", time_stamp);
                                    intent.putExtra("category_type", job_type);
                                    intent.putExtra("start_time", start_time_stamp);
                                    intent.putExtra("end_time", end_time_stamp);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                }


                                break;
                        }




                       /* if (provider_id!=0){

                            if (type.equalsIgnoreCase("1")) {
                                if (start_time_stamp != 0) {
                                    vm.getBoolings(String.valueOf(start_time_stamp), String.valueOf(provider_id));
                                }
                            }else{
                                if (start_time != null && !start_time.equalsIgnoreCase("")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("start", start_time_stamp);
                                    intent.putExtra("end", end_time_stamp);
                                    intent.putExtra("time", time_stamp);
                                    intent.putExtra("start_time", start_time_stamp);
                                    intent.putExtra("end_time", end_time_stamp);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                }
                            }
                        }else{

                            if (start_time != null && !start_time.equalsIgnoreCase("")) {
                                Intent intent = new Intent();
                                intent.putExtra("start", start_time_stamp);
                                intent.putExtra("end", end_time_stamp);
                                intent.putExtra("time", time_stamp);
                                intent.putExtra("start_time", start_time_stamp);
                                intent.putExtra("end_time", end_time_stamp);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        }*/



                        break;
                    case R.id.ll_time_picker:
                        new TimePickerDialog(WhenActivity.this, date, myCalendar
                                .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),
                                false).show();
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
                                    WhenActivity.this).create();
                            alertDialog1.setTitle("Note");
                            alertDialog1.setMessage("This provider already has "+bookingsList.size()+ " booking in this time slot. Please try with another date.");
                            alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            alertDialog1.show();

                        }else{
                            if (start_time != null && !start_time.equalsIgnoreCase("")) {
                                Intent intent = new Intent();
                                intent.putExtra("start", start_time_stamp);
                                intent.putExtra("end", end_time_stamp);
                                intent.putExtra("time", time_stamp);
                                intent.putExtra("start_time", start_time_stamp);
                                intent.putExtra("end_time", end_time_stamp);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
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

        binding.calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                setStartDate(startDate);
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                setStartDate(startDate);
                setEndDate(endDate);

            }
        });
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        binding.calendar.setSelectableDateRange(Calendar.getInstance(), cal);
    }

    private void setCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        String time = sdf.format(currentTime);
        String s[] = time.split(" ");
        String s1[] = s[0].split(":");
        binding.tvHour.setText(s1[0]);
        binding.tvMinutes.setText(s1[1]);
        binding.tvAmPm.setText(s[1]);
        SimpleDateFormat sdfcurrent = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date = sdfcurrent.format(currentTime);
        String date_split[] = date.split(" ");
        binding.tvStartMonth.setText(date_split[1]);
        binding.tvStartYear.setText(date_split[2]);
        binding.tvStartDate.setText(date_split[0]);
        binding.tvEndMonth.setText(date_split[1]);
        binding.tvEndYear.setText(date_split[2]);
        binding.tvEndDate.setText(date_split[0]);
    }

    private void setStartDate(Calendar startDate) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat startFrmt = new SimpleDateFormat("MMM dd yyyy");
        start_time_stamp = startDate.getTimeInMillis() / 1000;
        String date = fmtOut.format(startDate.getTime());
        String split[] = date.split("-");
        binding.tvStartDate.setText(split[0]);
        binding.tvStartMonth.setText(split[1]);
        binding.tvStartYear.setText(split[2]);
        start_time = startFrmt.format(startDate.getTime());
    }

    private void setEndDate(Calendar endDate) {
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat endfrmt = new SimpleDateFormat("MMM dd yyyy");
        end_time_stamp = endDate.getTimeInMillis() / 1000;
        String date = fmtOut.format(endDate.getTime());
        String split[] = date.split("-");
        binding.tvEndDate.setText(split[0]);
        binding.tvEndMonth.setText(split[1]);
        binding.tvEndYear.setText(split[2]);
        end_time = endfrmt.format(endDate.getTime());
    }

    TimePickerDialog.OnTimeSetListener date = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            Calendar c = Calendar.getInstance();
            if (job_type.equalsIgnoreCase("3")) {
                updateLabel();
            } else {
                if (myCalendar.getTimeInMillis() > c.getTimeInMillis()) {
                    //it's after current
                    updateLabel();
                } else {
                    //it's before current'
                    viewModel.error.setValue("Invalid Time");
                }
            }
        }
    };

    private void updateLabel() {
        String myFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String time = sdf.format(myCalendar.getTime());
        time_stamp = myCalendar.getTimeInMillis() / 1000;
        String splite_am[] = time.split(" ");
        String split_time[] = splite_am[0].split(":");
        binding.tvAmPm.setText(splite_am[1]);
        binding.tvHour.setText(split_time[0]);
        binding.tvMinutes.setText(split_time[1]);
    }

}
