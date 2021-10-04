package com.marius.valeyou.util.misc;
import android.text.format.DateFormat;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
public class RxBus {

    private final String TAG=RxBus.class.getSimpleName();
    public RxBus() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
        Log.d(TAG, "send message- Time: "+DateFormat.format("MM/dd/yy h:mm:ss aa", System.currentTimeMillis())+ " Event type: "+ o.toString());
    }

    public Observable<Object> toObserverable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

}
