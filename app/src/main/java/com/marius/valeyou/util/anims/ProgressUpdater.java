package com.marius.valeyou.util.anims;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marius.valeyou.R;


public class ProgressUpdater extends Dialog {

    private Context context;

    private TextView textView;
    private ProgressBar timer_progress;


    public ProgressUpdater(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        setCancelable(false);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.dialog_progress);
        timer_progress = findViewById(R.id.timer_progress);
        textView = findViewById(R.id.tv_message);*/


    }


    public void setProgress(int progress) {
        if (progress < 100) {
            textView.setText(String.valueOf(progress));
            timer_progress.startAnimation(new ProgressBarAnimation(timer_progress, progress));
        }
    }
}
