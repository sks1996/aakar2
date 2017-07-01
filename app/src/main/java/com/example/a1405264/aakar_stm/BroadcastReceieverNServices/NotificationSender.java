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
import com.example.a1405264.aakar_stm.Utils.GetNetworkState;

import static com.example.a1405264.aakar_stm.BroadcastReceieverNServices.Notification_search.IS_FIRST_TIME;

public class NotificationSender extends BroadcastReceiver {

    public static Boolean START_SERVICE=true;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras().equals("New_Notification"))
        {

            Log.v("SurajSingh","98");


            Intent intent2 = new Intent(context, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            String MessageBody=intent.getExtras().getString("Message","You have a new notification (1)");

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_lock_lock)
                    .setContentTitle("TNP")
                    .setContentText(MessageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 , notificationBuilder.build());

        }
        else
        {
            String STATUS= GetNetworkState.getConnectivityStatusString(context);

            if (STATUS.equals("Wifi enabled")||STATUS.equals("Mobile data enabled")) {

                if(START_SERVICE) {
                    try {
                        IS_FIRST_TIME=true;
                        context.startService(new Intent(context, Notification_search.class));
                      //  Toast.makeText(context, "Started Service", Toast.LENGTH_LONG).show();
                        START_SERVICE = false;
                    }
                    catch (Exception e)
                    {
                        Log.v("SurajSingh","Yes"+e.getLocalizedMessage());
                    }
                }
            }
            else
            {

                START_SERVICE=true;
               // Toast.makeText(context,"Stopped Service",Toast.LENGTH_LONG).show();
                context.stopService(new Intent(context,Notification_search.class));
            }
        }

    }
}
