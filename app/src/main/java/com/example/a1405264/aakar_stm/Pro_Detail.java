package com.example.a1405264.aakar_stm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pro_Detail extends AppCompatActivity {

    TextView title_tv, desc_tv,date_tv;
    ImageView image_iv;
    Bitmap bmp;
    DatabaseReference ref;
    private PopupWindow mPopupWindow;
    public static Activity THIS_;
    public static HashMap<Integer,String> Tasks;


    Button pop;
    Context context;

    Bitmap convert(byte[] bitmapdata)

    {
        //copy that code for converting byte array to bitmap here
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro__detail);
        ref = FirebaseDatabase.getInstance().getReference();
        pop=(Button)findViewById(R.id.pop);

        Tasks=new HashMap<>();

        THIS_=this;
        Log.d("image","activity created");

        title_tv=(TextView) findViewById(R.id.title_tv);
        desc_tv=(TextView) findViewById(R.id.desc_tv);
        image_iv=(ImageView)findViewById(R.id.image_iv);
        date_tv=(TextView)findViewById(R.id.date_tv);
        Bundle title_data = getIntent().getExtras();
        String titleString=title_data.get("title").toString();
        String desc=title_data.get("description").toString();
        String date=title_data.get("date").toString();

        context = getApplicationContext();

        byte[] b=getIntent().getBundleExtra("bundle").getByteArray("image");
        Log.d("image is-",""+b);
        image_iv.setImageBitmap(convert(b));

        title_tv.setText(titleString);
        desc_tv.setText(desc);
        date_tv.setText(date);

        //for FAB
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_add_2x);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.ic_person_add_black_24dp);

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.ic_event_note_black_24dp);

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(R.mipmap.ic_launcher);

        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pro_Detail.this,"hulla",Toast.LENGTH_LONG).show();
            }
        });

        //attach the sub buttons to the main button
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //for dialog box
                final Dialog dialog = new Dialog(Pro_Detail.this);
                dialog.setContentView(R.layout.add_user_dialog);
                dialog.setTitle("Title...");


                ref.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Is better to use a List, because you don't know the size
                        // of the iterator returned by dataSnapshot.getChildren() to
                        // initialize the array
                        final List<String> areas = new ArrayList<String>();

                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                            String areaName = areaSnapshot.child("name").getValue(String.class);
                            areas.add(areaName);
                        }

                        Spinner areaSpinner = (Spinner)dialog.findViewById(R.id.spinner);
                        ArrayAdapter<String> areasAdapter =
                                new ArrayAdapter<>(Pro_Detail.this, android.R.layout.simple_spinner_item, areas);
                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        areaSpinner.setAdapter(areasAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.add);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Pro_Detail.this);
                dialog.setContentView(R.layout.custom_dialog_addtask);

                final EditText task=(EditText)dialog.findViewById(R.id.task_name) ;
                Button add=(Button)dialog.findViewById(R.id.add);

                final EditText desc=(EditText)dialog.findViewById(R.id.task_dec);

                final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);

                ref.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Is better to use a List, because you don't know the size
                        // of the iterator returned by dataSnapshot.getChildren() to
                        // initialize the array
                        final ArrayList<String> areas = new ArrayList<String>();

                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                            String areaName = areaSnapshot.child("name").getValue(String.class);
                            areas.add(areaName);
                        }


                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(Pro_Detail.this, android.R.layout.simple_spinner_item, areas);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       // spinner.setAdapter(adapter);

                        spinner.setAdapter(new com.example.a1405264.aakar_stm.Adapters.SpinnerAdapter(Pro_Detail.this,R.layout.spinner_row,areas));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

             //   Spinner areaSpinner = (Spinner)dialog.findViewById(R.id.spinner);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String userId = ref.push().getKey();

                        dialog.show();
                        for(Map.Entry<Integer,String> entry:Tasks.entrySet())
                        {

                            String task1=task.getText().toString().trim();
                            String name1=entry.getValue();
                            String des=desc.getText().toString().trim();
                            String status="not done";

                            TasK_try ob=new TasK_try(task1,name1,des,status);
                            ref.child("Task").child("task1").child(userId).setValue(ob);

                        }
                        Tasks.clear();


                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


        pop.setOnClickListener  (new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Pro_Detail.this);
                dialog.setContentView(R.layout.pop_window);


                final ListView lv=(ListView)dialog.findViewById(R.id.ls);

                final ArrayList<String> use_name = new ArrayList<>();

                ref.child("Users").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                           String areaName = areaSnapshot.child("name").getValue(String.class);

                            use_name.add(areaName);
                        }


                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(Pro_Detail.this, android.R.layout.simple_list_item_1, use_name);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP | Gravity.LEFT;

                wlp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                ImageButton closeButton = (ImageButton)dialog.findViewById(R.id.ib_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        dialog.dismiss();
                    }
                });


                Button all_user=(Button)dialog.findViewById(R.id.all_user);

                all_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(Pro_Detail.this, Users.class));
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}
