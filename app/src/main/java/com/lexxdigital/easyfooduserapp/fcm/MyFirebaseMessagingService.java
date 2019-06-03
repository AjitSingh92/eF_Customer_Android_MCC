package com.lexxdigital.easyfooduserapp.fcm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.fragments.PreviousOrderFragment;
import com.lexxdigital.easyfooduserapp.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    SharedPreferencesClass sharePre;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "FCMToken: " + remoteMessage.getMessageId());

        sharePre = new SharedPreferencesClass(this);

        if (remoteMessage == null)
            return;
        else {

            try {

                String title = remoteMessage.getData().get("title");
                String message = remoteMessage.getData().get("message");
                String timestamp = remoteMessage.getData().get("timestamp");
                String notif_type = remoteMessage.getData().get("type");
                String order_id = remoteMessage.getData().get("order_number");

                if (!notif_type.equalsIgnoreCase(""))
                    sharePre.setInt(sharePre.NOTIFICATION_TYPE, Integer.parseInt(notif_type));

                if (!order_id.equalsIgnoreCase(""))
                    sharePre.setOrderIDKey(order_id);

                Intent resultIntent = null;
                if (OrderStatusActivity.getActivity() == null) {
                    resultIntent = new Intent(this, OrderStatusActivity.class);
                    resultIntent.putExtra(Constants.NOTIFICATION_TYPE, notif_type);
                    resultIntent.putExtra(Constants.NOTIFICATION_ORDER_ID, order_id);
                    resultIntent.putExtra("message", message);
                    showNotificationMessage(this, title, message, timestamp, resultIntent);

                    Intent pushNotification = new Intent("status");
                    pushNotification.putExtra("status", notif_type);
                    pushNotification.putExtra("order_id", order_id);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                } else {
                    Intent pushNotification = new Intent("status");
                    pushNotification.putExtra("status", notif_type);
                    pushNotification.putExtra("order_id", order_id);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                }
            } catch (Exception ex) {
                Log.e("Exception in mess : ", ex.toString());
            }

        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}