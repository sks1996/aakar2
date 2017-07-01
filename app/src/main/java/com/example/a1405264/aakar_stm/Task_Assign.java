package com.example.a1405264.aakar_stm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                R.layout.cuttom_todo,
                Holder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(Holder viewHolder, TasK_try model, int position) {
                viewHolder.setTask(model.getTask());
                viewHolder.setName(model.getName());

            }
        };
        tasklist.setAdapter(firebaseRecyclerAdapter);
    }

    public static  class Holder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView del;



        public Holder(View itemView)
        {
            super(itemView);
            view=itemView;
            del=(ImageView)view.findViewById(R.id.done);
            del.setClickable(true);
        }
        public  void setTask(String t)
        {
            TextView  task=(TextView)view.findViewById(R.id.task);
            task.setText(t);
        }
        public  void  setName(String v) {
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(v);
        }

    }
}
