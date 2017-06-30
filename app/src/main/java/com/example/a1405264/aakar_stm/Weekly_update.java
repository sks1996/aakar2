package com.example.a1405264.aakar_stm;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public  class Weekly_update extends AppCompatActivity {

    private RecyclerView rc_name;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Week_detail,Holder> firebaseRecyclerAdapter;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_update);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        rc_name=(RecyclerView)findViewById(R.id.week_up);
        rc_name.hasFixedSize();
        rc_name.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Week_detail,Holder>(
                Week_detail.class,
                R.layout.list_layout,
                Holder.class,
                databaseReference.child("WEEK WORK").child("week")
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.floating_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();



        Toast.makeText(this,""+info, Toast.LENGTH_LONG).show();

        switch (item.getItemId()) {
            case R.id.delete:

                default:
                return super.onContextItemSelected(item);
        }

    }


}
