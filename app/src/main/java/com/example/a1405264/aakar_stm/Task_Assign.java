package com.example.a1405264.aakar_stm;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Task_Assign extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView tasklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__assign);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Task").child("task1");

        tasklist=(RecyclerView)findViewById(R.id.task_list);
        tasklist.hasFixedSize();
        tasklist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<TasK_try,Holder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<TasK_try, Holder>(
                TasK_try.class,
                R.layout.carview_task,
                Holder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(Holder viewHolder, TasK_try model, int position) {
                viewHolder.setTask(model.getTask());
                viewHolder.setName(model.getName());
                viewHolder.setDesc(model.getDesc());

            }
        };
        tasklist.setAdapter(firebaseRecyclerAdapter);
    }

    public static  class Holder extends RecyclerView.ViewHolder
    {
        View mview;

        public Holder(View itemView)
        {
            super(itemView);
            mview=itemView;

            Button del=(Button)mview.findViewById(R.id.button5);

            Button done=(Button)mview.findViewById(R.id.button6);

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(mview.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(mview.getContext());
                    }
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Task").child("task1");

                                    String s=((TextView)mview.findViewById(R.id.post_desc)).getText().toString();
                                    //Toast.makeText(mView.getContext(),date1,Toast.LENGTH_LONG).show();
                                    Query applesQuery = ref.orderByChild("desc").equalTo(s);

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


            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(mview.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(mview.getContext());
                    }
                    builder.setTitle("Done")
                            .setMessage("Have u completed the task?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Task").child("task1");

                                    String s=((TextView)mview.findViewById(R.id.post_desc)).getText().toString();
                                    //i want to get the xxx from the listview when i press the screen
                                    //Toast.makeText(mView.getContext(),date1,Toast.LENGTH_LONG).show();
                                    Query applesQuery = ref.orderByChild("desc").equalTo(s);

                                    //    ref.child("WEEK WORK").child("week").child(date1).setValue("suraj");

                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().child("status").setValue("done");
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

        public  void setTask(String t)
        {
            TextView  task=(TextView)mview.findViewById(R.id.post_title);
            task.setText(t);
        }

        public  void  setName(String v) {
            TextView name = (TextView)mview.findViewById(R.id.name);
            name.setText(v);
        }

        public void setDesc(String f)
        {
            TextView description = (TextView)mview.findViewById(R.id.post_desc);
            description.setText(f);
        }

    }
}
