package com.example.a1405264.aakar_stm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Message extends Fragment {


    DatabaseReference databaseReference;
    EditText ed;
    Button bt;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);


        ed=(EditText)view.findViewById(R.id.mesg);
        bt=(Button)view.findViewById(R.id.button6);
        database   = FirebaseDatabase.getInstance();


        databaseReference= database.getReference("notifications");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.setValue(ed.getText().toString());

            }
        });


        return view;
    }

}
