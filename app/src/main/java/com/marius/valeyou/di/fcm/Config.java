package com.marius.valeyou.di.fcm;

public class Config {
 
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the ic_notifications_black_24dp in the ic_notifications_black_24dp tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static final String FACEBOOK = "1";
    public static final String TWITTER = "2";
    public static final String GOOGLE = "3";
}