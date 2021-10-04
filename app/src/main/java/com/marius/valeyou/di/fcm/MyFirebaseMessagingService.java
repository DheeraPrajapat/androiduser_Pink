package com.marius.valeyou.di.fcm;
/**
 * Created by jatin on 11/30/2016.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.marius.valeyou.R;
import com.marius.valeyou.ui.activity.notification.NotificationActivity;
import com.marius.valeyou.ui.activity.welcome.WelcomeActivity;

import org.json.JSONObject;

import java.util.Map;

import androidx.core.app.NotificationCompat;

/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, " onMessageReceived " + remoteMessage.getData());
        if (remoteMessage.getData() != null && !remoteMessage.getData().isEmpty())
            sendNotification(remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, " onNewToken " + token);
    }


    private void sendNotification(Map<String, String> messageBody) {
        try {
            String messge = (String) new JSONObject(messageBody).get("body");
            Gson gson = new Gson();
            NotificationPojo notificationPojo = gson.fromJson(messge, NotificationPojo.class);
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

                notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

            } else {
                Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);

                if (true) {
                    sendNotification(getApplicationContext(), notificationPojo.getTitle(), notificationPojo.getMessage(), " ", resultIntent);
                } else {
                    sendNotification(getApplicationContext(), notificationPojo.getTitle(), notificationPojo.getMessage(), " ", resultIntent);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Showing ic_notifications_black_24dp with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    private void sendNotification(Context context, String title, String messg, String timeStamp, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        /*  .setSmallIcon(R.drawable.app_sign_in_logo)*/
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messg)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    /**
     * Showing ic_notifications_black_24dp with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}