package com.example.a1405264.aakar_stm.BroadcastReceieverNServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.a1405264.aakar_stm.TasK_try;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Notification_search extends Service {

    public static Boolean IS_FIRST_TIME;
    public Notification_search() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v("SurajSingh","1");

        DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        root.child("Task").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Log.v("SurajSingh","2");

             //   TasK_try New_task=dataSnapshot.getValue(TasK_try.class);

              /*  try {
                    TasK_try New_task=dataSnapshot.getValue(TasK_try.class);
                    sendBroadcast(new Intent(Notification_search.this, Notification.class).setAction("Send_notification_now").putExtra("Message",New_task.getTask()));

                }
                catch (Exception e)
                {


                    Log.v("SurajSingh","Yes"+e.getLocalizedMessage());
                } */
            /*  if(!IS_FIRST_TIME)
              {
                  try {



                      TasK_try New_task=dataSnapshot.getValue(TasK_try.class);
                      sendBroadcast(new Intent(Notification_search.this, Notification.class).setAction("Send_notification_now").putExtra("Message",New_task.getTask()));

                  }
                  catch (Exception e)
                  {


                      Log.v("SurajSingh","Yes"+e.getLocalizedMessage());
                  }
              }
              else
              {
                  IS_FIRST_TIME=false;
              } */
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                try {
                    TasK_try New_task=dataSnapshot.getValue(TasK_try.class);

                    Log.v("SurajSingh123",New_task.toString()+"...");

                    sendBroadcast(new Intent(Notification_search.this, Notification.class).setAction("Send_notification_now").putExtra("Message",New_task.getTask()));

                }
                catch (Exception e)
                {


                    Log.v("SurajSingh","Yes"+e.getLocalizedMessage());
                }
         }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


                Log.v("SurajSingh","4");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


                Log.v("SurajSingh","5");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                Log.v("SurajSingh","6");
            }
        });




        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
