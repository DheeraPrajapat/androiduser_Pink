package com.marius.valeyou.data.remote.helper;

import android.content.Context;

import com.marius.valeyou.di.base.MyApplication;
import com.marius.valeyou.R;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import dagger.android.DaggerApplication;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class NetworkErrorHandler {

    private Context context;

    public NetworkErrorHandler(DaggerApplication context) {
        this.context = context;
    }

    public String getErrMsg(Throwable throwable) {
        String errMsg = context.getString(R.string.error_found);
        throwable.printStackTrace();
        if (throwable instanceof HttpException) {
            errMsg = getErrorMessage(throwable);
            HttpException exception = (HttpException) throwable;
            if (exception.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                MyApplication.getInstance().restartApp();
        } else if (throwable instanceof IOException) {
            errMsg = context.getString(R.string.network_error);
        } else {
            if (throwable.getMessage() != null)
                errMsg = throwable.getMessage();
        }

        return errMsg;
    }

    private String getErrorMessage(Throwable throwable) {
        try {

            HttpException httpException = (HttpException) throwable;
            ResponseBody errorBody = httpException.response().errorBody();
            String errMsg = context.getString(R.string.error_found);

            if (errorBody != null) {
                JSONObject jsonObject = new JSONObject(errorBody.string());
                errMsg = jsonObject.getString("msg");
            }

            return errMsg;

        } catch (Exception e) {
            return ((HttpException) throwable).message();
        }
    }
}
