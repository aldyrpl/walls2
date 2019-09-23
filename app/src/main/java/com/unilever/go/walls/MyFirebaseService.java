package com.unilever.go.walls;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.models.Group;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unilever.go.walls.Activity.CometChatActivity;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseService";

    private static final String CHANNEL_ID = "2";

    private String GROUP_ID = "group_id";

    private JSONObject json;

    private JSONObject messageData;

    private static final int REQUEST_CODE = 12;


    public static void subscribeUser(String UID) {
        FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID + "_"+ CometChatConstants.RECEIVER_TYPE_USER +"_" +
                UID);
    }

    public static void subscribeGroup(String GUID){
        FirebaseMessaging.getInstance().subscribeToTopic(StringContract.AppDetails.APP_ID+"_"+CometChatConstants.RECEIVER_TYPE_GROUP+"_"+GUID);
    }



    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "onNewToken: "+s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());

        try {
            json = new JSONObject(remoteMessage.getData());
            Log.d(TAG, "JSONObject: "+json.toString());
            messageData = new JSONObject(json.getString("message"));

            Intent intent = new Intent(this, CometChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            showNotifcation(pendingIntent);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showNotifcation(PendingIntent pendingIntent) {

        try {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.badge)
                    .setContentTitle(json.getString("title"))
                    .setContentText(json.getString("alert"))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setGroup(GROUP_ID)
                    .setGroupSummary(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
                String channelId = "Your_channel_id";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        importance);
                mNotificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelId);
            }
            notificationManager.notify(2, builder.build());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}