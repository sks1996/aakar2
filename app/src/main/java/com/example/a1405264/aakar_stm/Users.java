package com.example.a1405264.aakar_stm;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Users extends AppCompatActivity {

    private RecyclerView rc_name;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<User_detail,Holder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        rc_name=(RecyclerView)findViewById(R.id.rc_name);
        rc_name.hasFixedSize();
        rc_name.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<User_detail, Holder>(
                User_detail.class,
                R.layout.user_name_layout,
                Holder.class,
                databaseReference.child("Users")
        ) {
            @Override
            protected void populateViewHolder(Holder viewHolder, User_detail model, int position) {
                viewHolder.setName(model.getName());

            }
        };
        rc_name.setAdapter(firebaseRecyclerAdapter);
    }

    public  static class Holder extends RecyclerView.ViewHolder
    {

        ImageView pop;
        View mView;

        public Holder(View itemView) {
            super(itemView);
            mView=itemView;

            pop=(ImageView)mView.findViewById(R.id.imageView4);

            mView.getId();




            pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(mView.getContext(), pop);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.edit_user, popup.getMenu());

                    //registering popup with OnMenuItemClickListener

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();

                            switch (id)
                            {
                                case R.id.del:

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


                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                                    String s=((TextView)mView.findViewById(R.id.name)).getText().toString();
                                                    //i want to get the xxx from the listview when i press the screen
                                                    //Toast.makeText(mView.getContext(),date1,Toast.LENGTH_LONG).show();
                                                    Query applesQuery = ref.orderByChild("name").equalTo(s);

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
                            return true;
                        }
                    });
                    popup.show(); //showing popup menu
                }
            });
        }

        public  void setName(String title)
        {
            TextView post_title=(TextView)mView.findViewById(R.id.name);
            post_title.setText(title);
        }
    }
}
