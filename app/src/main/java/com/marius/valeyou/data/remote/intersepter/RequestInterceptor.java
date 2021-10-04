package com.marius.valeyou.data.remote.intersepter;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sanjeev Sharma on 23/11/2020.
 */
public class RequestInterceptor implements Interceptor {

    String credentials;

    public RequestInterceptor() {
        this.credentials = Credentials.basic("security_key", "QnVpbGRlciBBcHAgQ3JlYXRlZCBCeSBDaGFuZGFu=");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder().
                header("Authorization", credentials);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }

}
