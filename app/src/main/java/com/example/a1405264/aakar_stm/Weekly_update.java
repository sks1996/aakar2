package com.example.a1405264.aakar_stm;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import rjsv.floatingmenu.floatingmenubutton.subbutton.FloatingSubButton;

public  class Weekly_update extends AppCompatActivity {

    private RecyclerView rc_name;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Week_detail,Holder> firebaseRecyclerAdapter;


    FloatingSubButton add_work , share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_update);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        rc_name=(RecyclerView)findViewById(R.id.week_up);
        rc_name.hasFixedSize();
        rc_name.setLayoutManager(new LinearLayoutManager(this));


        share=(FloatingSubButton)findViewById(R.id.sub_button_2);

        add_work = (FloatingSubButton) findViewById(R.id.sub_button_1);
        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = Weekly_update.this;
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Work");
                dialog.show();


                final EditText et=(EditText)dialog.findViewById(R.id.text1);


                Button b=(Button)dialog.findViewById(R.id.cdb);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datepick);
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth()+1;
                        int year = datePicker.getYear();

                        Date d = new Date();
                        String weekday = (String) android.text.format.DateFormat.format("EEEE", d);

                        Toast.makeText(Weekly_update.this,weekday,Toast.LENGTH_LONG).show();

                        String date= Integer.toString(day)+"-"+ Integer.toString(month)+ "-"+Integer.toString(year);
                        String detail =et.getText().toString().trim();


                        String currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid() ;

                        databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child("week").child(currentFirebaseUser);

/*
                        if (detail=="Sunday") {
                            Calendar calender = Calendar.getInstance();
                            int ee=calender.get(Calendar.WEEK_OF_YEAR);
                            String up1="week"+Integer.toString(ee);
                            databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child(up1);
                        }
                        else
                        {

                        }
                        */
                        if(!TextUtils.isEmpty(detail)){


                            FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            String email=firebaseUser.getEmail();

                            String id=databaseReference.push().getKey();

                            Week_detail week_detail=new Week_detail(id,date,detail,email);

                            databaseReference.child(id).setValue(week_detail);

                            Toast.makeText(getApplicationContext(), "Data Entered", Toast.LENGTH_LONG).show();

                            dialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Enter today's task", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String abc=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Toast.makeText(this,abc, Toast.LENGTH_SHORT).show();
        //currentfirebaseuser to uniquely identify the user
        String currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Week_detail,Holder>(
                Week_detail.class,
                R.layout.list_layout,
                Holder.class,
                databaseReference.child("WEEK WORK").child("week").child(currentFirebaseUser)
        ) {
            @Override
            protected void populateViewHolder(Holder viewHolder, Week_detail model, int position) {
                viewHolder.setWork(model.getWork());
                viewHolder.setDate(model.getDate());
            }
        };
        rc_name.setAdapter(firebaseRecyclerAdapter);
    }

    public  static class Holder extends RecyclerView.ViewHolder
    {
        View mView;
        public Holder(View itemView) {
            super(itemView);
            mView=itemView;
//            mView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) mView.getContext());

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(mView.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(mView.getContext());
                    }
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("WEEK WORK").child("week");

                                    String s=((TextView)mView.findViewById(R.id.tvl1)).getText().toString();
                                    //i want to get the xxx from the listview when i press the screen
                                    //Toast.makeText(mView.getContext(),date1,Toast.LENGTH_LONG).show();
                                    Query applesQuery = ref.orderByChild("work").equalTo(s);

                                    //    ref.child("WEEK WORK").child("week").child(date1).setValue("suraj");

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e("fail 123..", "onCancelled", databaseError.toException());
                                            //dialog1.dismiss();
                                        }
                                    });

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }

        public  void setWork(String title)
        {
            TextView post_title=(TextView)mView.findViewById(R.id.tvl1);
            post_title.setText(title);
        }

        public  void setDate(String desc)
        {
            TextView post_desc=(TextView)mView.findViewById(R.id.tvl2);
            post_desc.setText(desc);
        }

    }
}
