package kr.co.gusalnim.template.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import kr.co.gusalnim.template.AlertDialogActivity;
import kr.co.gusalnim.template.MainActivity;
import kr.co.gusalnim.template.R;
import kr.co.gusalnim.template.util.BadgeHelper;

public class ListenerService extends FirebaseMessagingService {

    private String link;
    private String title;
    private String body;
    private int badge;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Check if message contains a data payload.
        /*if (remoteMessage.getData().size() > 0) {
            Log.d("gusalnim", "Message data payload: " + remoteMessage.getData());

            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }*/




        //Map<String, String> pushDataMap = remoteMessage.getData();

        Map<String, String> pushDataMap = remoteMessage.getData();

        JSONObject jo = null;
        try {
            jo = new JSONObject(pushDataMap.get("notification"));
            link = pushDataMap.get("link");
            title = jo.optString("title");
            body = jo.optString("body");
            badge = Integer.parseInt(jo.optString("badge", "-1"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Log.i("gusalnim","link : " + link + " / body : " + body + " / title : " + title + " / badge : " + badge );
        sendNotification();

        if (badge != -1) BadgeHelper.sendBroadcast(this, badge);
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // 상단 노티
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                //.setVibrate(new long[]{1000, 1000})
                //.setLights(Color.WHITE, 1500, 1500)
                .setContentIntent(contentIntent);

        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0 /* ID of notification */, nBuilder.build());


        // 커스텀 팝업 띄우기
        if (StringUtils.isBlank(body)) {
            return;
        }
        Bundle bune = new Bundle();
        bune.putString("notiMessage", body);
        bune.putString("notiLink", link);

        Intent popupIntent = new Intent(this, AlertDialogActivity.class);

        popupIntent.putExtras(bune);
        PendingIntent pie = PendingIntent.getActivity(this, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
        try {
            pie.send();
        } catch (PendingIntent.CanceledException e) {

        }
    }
}