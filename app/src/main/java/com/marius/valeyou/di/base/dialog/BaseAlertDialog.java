package com.marius.valeyou.di.base.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.view.LayoutInflater;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.DialogBaseAlertBinding;


public class BaseAlertDialog extends Dialog {
    private Context context;
    private ClickListener latiner;
    private DialogBaseAlertBinding binding;

    public BaseAlertDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;

    }

    public void setLabels(@StringRes int title, @StringRes int message, @StringRes int ok, @StringRes int cancel) {
        initView();
        setTitle(title);
        setMessage(message);
        setOkText(ok);
        setCancelText(cancel);
    }

    public void setLabels(String title, String message, String ok, String cancel) {
        initView();
        setTitle(title);
        setMessage(message);
        setOkText(ok);
        setCancelText(cancel);
    }

    private void initView() {
        if (binding == null)
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_base_alert, null, false);
    }

    public void setListener(ClickListener listener) {
        this.latiner = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if (latiner != null) {
            binding.setCallback(latiner);
        }
        setContentView(binding.getRoot());

    }

    public void setTitle(String title) {
        initView();
        binding.setTitle(title);
    }

    public void setTitle(@StringRes int title) {
        initView();
        binding.setTitle(context.getString(title));
    }

    public void setMessage(String title) {
        initView();
        binding.setMessage(title);
    }

    public void setMessage(@StringRes int title) {
        initView();
        binding.setMessage(context.getString(title));
    }

    public void setOkText(String okText) {
        initView();
        binding.setOk(okText);
    }

    public void setOkText(@StringRes int okText) {
        initView();
        binding.setOk(context.getString(okText));
    }

    public void setCancelText(String cancelText) {
        initView();
        binding.setCancel(cancelText);
    }

    public void setCancelText(@StringRes int cancelText) {
        initView();
        binding.setCancel(context.getString(cancelText));
    }

    public interface ClickListener {
        void onOkClick();

        void onCancelClick();

    }
}
