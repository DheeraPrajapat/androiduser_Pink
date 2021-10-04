package com.marius.valeyou.data.beans;

import android.content.Intent;

public class OnActivityResult {
    private int requestcode;
    private  int resultcode;
    private Intent data;

    public OnActivityResult(int requestcode, int resultcode, Intent data) {
        this.requestcode = requestcode;
        this.resultcode = resultcode;
        this.data = data;
    }

    public int getRequestcode() {
        return requestcode;
    }

    public int getResultcode() {
        return resultcode;
    }

    public Intent getData() {
        return data;
    }
}
