package com.marius.valeyou.util.misc;

import androidx.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.marius.valeyou.data.beans.ConnectionBean;

public class ConnectionHandler extends LiveData<ConnectionBean> {

    private Context context;
    private boolean connected = true;

    public ConnectionHandler(Context context) {
        this.context = context;
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                NetworkInfo activeNetwork = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
                if (isConnected) {
                    switch (activeNetwork.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            if (!connected) {
                                connected = true;
                                postValue(new ConnectionBean(ConnectionBean.State.WIFI, true));
                            }
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            if (!connected) {
                                connected = true;
                                postValue(new ConnectionBean(ConnectionBean.State.MOBILE, true));
                            }
                            break;
                    }
                } else {
                    if (connected) {
                        connected = false;
                        postValue(new ConnectionBean(ConnectionBean.State.NONE, false));
                    }
                }
            }
        }
    };
}