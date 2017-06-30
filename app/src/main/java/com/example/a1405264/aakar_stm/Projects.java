package com.example.a1405264.aakar_stm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Projects extends AppCompatActivity {

    private RecyclerView mBloglist;
    private DatabaseReference mDatabase;
    Bitmap bitmap;
    CardView Card;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabase.orderByChild("timestamp");
        CardView Card = (CardView) findViewById(R.id.Card);

        mBloglist=(RecyclerView) findViewById(R.id.blog_list);
        mBloglist.setHasFixedSize(true);
        mBloglist.setLayoutManager(new LinearLayoutManager(this));
        mProgress = new ProgressDialog(this);


        // Now set the properties of the LinearLayoutManager

        // And now set it to the RecyclerView




    }



    @Override
    protected void onStart() {
        super.onStart();


        final FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(

                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase.orderByChild("timestamp")

        ) {


            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder, final Blog model, int position) {


                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setDate(model.getDate());
//                viewHolder.setStatus(model.getStatus());

                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.Card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mProgress.setMessage("loading..");
                                Intent intent= new Intent(Projects.this,Pro_Detail.class);
                                Log.d("image","clicked");
                                Drawable drawable=viewHolder.image_new.getDrawable();
                                Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                //Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);


                                byte[] b = baos.toByteArray();
                                Log.d("image","  "+b.length);
                                Bundle bundle =new Bundle();
                                bundle.putByteArray("image",b);
                                intent.putExtra("title",model.getTitle());
                                intent.putExtra("description",model.getDesc());
                                intent.putExtra("date",model.getDate());
                                intent.putExtra("bundle",bundle);
                                startActivity(intent);
                            }
                        }).start();
                    }
                });
            }
        };

        mBloglist.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView image_new;
        CardView Card;

        public BlogViewHolder(View itemView) {
            super(itemView);
            Card=(CardView)itemView.findViewById(R.id.Card);
            mView= itemView;
            image_new=(ImageView)itemView.findViewById(R.id.post_image);

        }




        public void setTitle(String title){

            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc){

            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);

        }

        public void setDate(String date){

            TextView post_date = (TextView) mView.findViewById(R.id.post_date);
            post_date.setText(date);

        }
        public void setImage(Context ctx, String image){
            ImageView post_image=(ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
//        public void setStatus(String status){
//
//            TextView post_title = (TextView) mView.findViewById(R.id.post_status);
//            post_title.setText(status);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(Projects.this, Add_projects.class));

        }
        return super.onOptionsItemSelected(item);


        //Requesting permission
    }



}
