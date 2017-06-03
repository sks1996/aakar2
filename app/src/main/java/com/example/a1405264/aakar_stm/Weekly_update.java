package com.example.a1405264.aakar_stm;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Weekly_update extends AppCompatActivity {
    private FloatingActionButton fab;
    ListView lv;
    List<Week_detail> week_details;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_update);

        lv=(ListView)findViewById(R.id.lv);
      //  fab=(FloatingActionButton)findViewById(R.id.fab);

        week_details=new ArrayList<>();
        registerForContextMenu(lv);
        databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child("week");

        BottomNavigationView bnv=(BottomNavigationView)findViewById(R.id.bottom_navigation);


        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_work:
                                Context mContext = Weekly_update.this;
                                final Dialog dialog = new Dialog(mContext);
                                dialog.setContentView(R.layout.custom_dialog);
                                dialog.setTitle("Daily Work");

                                dialog.show();
                                final EditText et=(
                                        EditText)dialog.findViewById(R.id.text1);


                                Button b=(Button)dialog.findViewById(R.id.cdb);

                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datepick);
                                        int day = datePicker.getDayOfMonth();
                                        int month = datePicker.getMonth();
                                        int year = datePicker.getYear();

                                        Date d = new Date();
                                        String weekday = (String) android.text.format.DateFormat.format("EEEE", d);

                                        Toast.makeText(Weekly_update.this,weekday,Toast.LENGTH_LONG).show();

                                        String date= Integer.toString(day)+"-"+ Integer.toString(month)+ "-"+Integer.toString(year);
                                        String detail =et.getText().toString().trim();

                                        if (detail=="Sunday") {
                                            Calendar calender = Calendar.getInstance();
                                            int ee=calender.get(Calendar.WEEK_OF_YEAR);
                                            String up1="week"+Integer.toString(ee);
                                            databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child(up1);
                                        }
                                        else
                                        {
                                            databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child("week");
                                        }
                                        if(!TextUtils.isEmpty(detail)){

                                            String id=databaseReference.push().getKey();

                                            Week_detail week_detail=new Week_detail(id,date,detail);

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

                                break;
                            case R.id.action_update:

                                 mContext = Weekly_update.this;
                                final Dialog dialog1 = new Dialog(mContext);
                                dialog1.setContentView(R.layout.custom_dialog1);
                                dialog1.setTitle("Daily Work");

                                dialog1.show();


                                DatePicker datePicker = (DatePicker)dialog1.findViewById(R.id.datepick);
                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth() + 1;
                                int year = datePicker.getYear();


                                final String date1= Integer.toString(day)+"-"+ Integer.toString(month)+ "-"+Integer.toString(year);


                                Button ba=(Button)dialog1.findViewById(R.id.cdb1);

                                ba.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                        //i want to get the xxx from the listview when i press the screen
                                        Query applesQuery = ref.child("WEEK WORK").child("week").orderByChild("date").equalTo(date1);

                                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                                                    appleSnapshot.getRef().removeValue();
                                                   // dialog1.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("fail 123..", "onCancelled", databaseError.toException());
                                                //dialog1.dismiss();
                                            }
                                        });
                                        dialog1.dismiss();
                                    }
                                });

                                break;
                            case R.id.action_music:

                                break;
                        }
                        return false;
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                week_details.clear();


                for(DataSnapshot weeksnapshot :dataSnapshot.getChildren())
                {
                    Week_detail week_detail=weeksnapshot.getValue(Week_detail.class);
                    week_details.add(week_detail);
                }

                Week_detail_list adapter =new Week_detail_list(Weekly_update.this, week_details);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.floating_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        Toast.makeText(Weekly_update.this,item.getTitle(),Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.delete:

                final AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("R u sure??")
                        .setView(R.layout.custom_dialog_delete)
                        .create();

                dialog.show();

                Button b=(Button)dialog.findViewById(R.id.cdb2);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                        //i want to get the xxx from the listview when i press the screen
                        Query applesQuery = ref.child("WEEK WORK").orderByChild("date").equalTo("xxxxx");


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
                            }
                        });



                        dialog.dismiss();

                    }

                });
            default:
                return super.onContextItemSelected(item);
        }

    }

}


/*


   fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Context mContext = Weekly_update.this;
final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Daily Work");

        dialog.show();
final EditText et=(
        EditText)dialog.findViewById(R.id.text1);


        Button b=(Button)dialog.findViewById(R.id.cdb);

        b.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datepick);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        Date d = new Date();
        String weekday = (String) android.text.format.DateFormat.format("EEEE", d);

        Toast.makeText(Weekly_update.this,weekday,Toast.LENGTH_LONG).show();

        String date= Integer.toString(day)+"-"+ Integer.toString(month)+ "-"+Integer.toString(year);
        String detail =et.getText().toString().trim();

        if (detail=="Saturday") {
        Calendar calender = Calendar.getInstance();
        int ee=calender.get(Calendar.WEEK_OF_YEAR);
        databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child("week"+Integer.toString(ee));
        }
        else
        {
        databaseReference = FirebaseDatabase.getInstance().getReference("WEEK WORK").child("week");
        }
        if(!TextUtils.isEmpty(detail)){

        String id=databaseReference.push().getKey();

        Week_detail week_detail=new Week_detail(id,date,detail);

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
        }); */


