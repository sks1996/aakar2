package com.example.a1405264.aakar_stm;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
  //      FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        lv=(ListView)findViewById(R.id.lv);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        week_details=new ArrayList<>();

        registerForContextMenu(lv);

        databaseReference= FirebaseDatabase.getInstance().getReference("WEEK WORK");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = Weekly_update.this;
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Daily Work");

                dialog.show();
                final EditText et=(EditText)dialog.findViewById(R.id.text1);


                Button b=(Button)dialog.findViewById(R.id.cdb);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker)dialog.findViewById(R.id.datepick);
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        String date= Integer.toString(day)+"-"+ Integer.toString(month)+ "-"+Integer.toString(year);
                        String detail =et.getText().toString().trim();

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


        Toast.makeText(Weekly_update.this,info.toString(),Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.delete:


                Toast.makeText(Weekly_update.this,item.toString(),Toast.LENGTH_LONG).show();
              /*  Context mContext = Weekly_update.this;
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_dialog_delete);
                dialog.setTitle("R u Sure");

                */
                final AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("R u sure??")
                        .setView(R.layout.custom_dialog_delete)
                        .create();

                dialog.show();

                Button b=(Button)dialog.findViewById(R.id.cdb2);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                 //      final String id_1=databaseReference.push().getKey();

//                        Firebase.setAndroidContext(this);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                        Query applesQuery = ref.child("WEEK WORK").orderByChild("work").equalTo("lalal");

                        Toast.makeText(Weekly_update.this,databaseReference.getRef().child("WEEK").child("work").toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(Weekly_update.this,id_1,Toast.LENGTH_LONG).show();
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                                    String childKey = appleSnapshot.getKey();
                                    appleSnapshot.getRef().removeValue();
                                    Toast.makeText(Weekly_update.this,childKey,Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("fail 123..", "onCancelled", databaseError.toException());
                            }
                        });



                        dialog.dismiss();
                        //Toast.makeText(Weekly_update.this,databaseReference.getRef().child("WEEK").toString(),Toast.LENGTH_LONG).show();
                    }

                });
            default:
                return super.onContextItemSelected(item);
        }

    }


}


