package com.example.a1405264.aakar_stm;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by 1405264 on 6/13/2017.
 */

public class Recycler_to_do_holder extends RecyclerView.ViewHolder {

    private static final String TAG = Recycler_to_do_holder.class.getSimpleName();
    public ImageView markIcon;
    public TextView categoryTitle;
    public ImageView deleteIcon;
    private List<Task> taskObject;

    public Recycler_to_do_holder(final View itemView, final List<Task> taskObject) {
        super(itemView);
        this.taskObject = taskObject;

        categoryTitle = (TextView)itemView.findViewById(R.id.task_title);

        markIcon = (ImageView)itemView.findViewById(R.id.task_icon);

        markIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                markIcon.setBackgroundResource(R.drawable.ic_done_all_black_24dp);
            }
        });

        deleteIcon = (ImageView)itemView.findViewById(R.id.task_delete);
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(itemView.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(itemView.getContext());
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String taskTitle = taskObject.get(getAdapterPosition()).getTask();
                                Log.d(TAG, "Task Title " + taskTitle);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Task");

                                Query applesQuery = ref.orderByChild("task").equalTo(taskTitle);

                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e(TAG, "onCancelled", databaseError.toException());
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

}
