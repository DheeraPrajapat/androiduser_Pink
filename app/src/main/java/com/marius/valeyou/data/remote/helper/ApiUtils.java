package com.marius.valeyou.data.remote.helper;

import android.util.Log;

import com.marius.valeyou.data.remote.retrofit.CountingRequestBody;

import java.io.File;

import io.reactivex.FlowableEmitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiUtils {

    public static MultipartBody.Part createMultipartBody(String filePath, FlowableEmitter<Resource<String>> emitter) {
        File file = new File(filePath);
        return MultipartBody.Part.createFormData("file", file.getName(), createCountingRequestBody(file, emitter));
    }

    public static MultipartBody.Part createMultipartBody(byte[] bytes,String fileName, FlowableEmitter<Resource<String>> emitter) {
        return MultipartBody.Part.createFormData("file",fileName, createCountingRequestBody(bytes, emitter));
    }


    private static RequestBody createCountingRequestBody(File file, final FlowableEmitter<Resource<String>> emitter) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.d("bytes", "==" + bytesWritten + "==" + contentLength);
                int progress = (int) (100 * ((1.0 * bytesWritten) / contentLength));
                emitter.onNext(Resource.loading(String.valueOf(progress)));
            }
        });


    }
    private static RequestBody createCountingRequestBody(byte[] bytes, final FlowableEmitter<Resource<String>> emitter) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);

        return new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                Log.d("bytes", "==" + bytesWritten + "==" + contentLength);
                int progress = (int) (100 * ((1.0 * bytesWritten) / contentLength));
                emitter.onNext(Resource.loading(String.valueOf(progress)));
            }
        });


    }


    public static MultipartBody.Part createMultipartBodySingle(File file, String key) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData(key, file.getName(), requestFile);

        return body;
    }

}
