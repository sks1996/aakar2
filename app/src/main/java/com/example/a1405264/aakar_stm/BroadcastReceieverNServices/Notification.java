package com.example.a1405264.aakar_stm.BroadcastReceieverNServices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.a1405264.aakar_stm.Home;
import com.example.a1405264.aakar_stm.R;

public class Notification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving


        Log.v("SurajSingh","87");
        // an Intent broadcast.
        Intent intent2 = new Intent(context, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent2,
                PendingIntent.FLAG_ONE_SHOT);

        String MessageBody=intent.getExtras().getString("Message","You have a new task ");

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle("Aakar")
                .setContentText(MessageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }
}
