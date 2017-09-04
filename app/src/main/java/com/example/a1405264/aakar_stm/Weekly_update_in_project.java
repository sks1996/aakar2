package com.example.a1405264.aakar_stm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Weekly_update_in_project extends AppCompatActivity {

    private RecyclerView rc_name;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Week_detail,Weekly_update_in_project.Holder> firebaseRecyclerAdapter;


    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_update_in_project);


        databaseReference= FirebaseDatabase.getInstance().getReference();

        rc_name=(RecyclerView)findViewById(R.id.week_up);
        rc_name.hasFixedSize();
        rc_name.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("name");
        Toast.makeText(Weekly_update_in_project.this,name,Toast.LENGTH_LONG).show();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Week_detail,Weekly_update_in_project.Holder>(
                Week_detail.class,
                R.layout.list_layout,
                Weekly_update_in_project.Holder.class,
                databaseReference.child("WEEK WORK").child("week").orderByChild("email").equalTo("acasdasg@gmail.com")

        )
        {
            @Override
            protected void populateViewHolder(Weekly_update_in_project.Holder viewHolder, Week_detail model, int position) {
                viewHolder.setWork(model.getWork());
                viewHolder.setDate(model.getDate());
            }
        };
        rc_name.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

    public  static class Holder extends RecyclerView.ViewHolder
    {
        View mView;
        public Holder(View itemView) {
            super(itemView);
            mView = itemView;
        }
//            mView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) mView.getContext());


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
