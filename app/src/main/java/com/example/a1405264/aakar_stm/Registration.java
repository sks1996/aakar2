package com.example.a1405264.aakar_stm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText fname,lname,email,phone_no;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname=(EditText)findViewById(R.id.editText);
        lname=(EditText)findViewById(R.id.editText2);
        email=(EditText)findViewById(R.id.editText4);
        phone_no=(EditText)findViewById(R.id.editText5);
        reg=(Button)findViewById(R.id.button);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

                String userId = mDatabase.push().getKey();

                String name=fname.getText().toString().trim()+lname.getText().toString().trim();
                String emal= email.getText().toString().trim();
                String phone=phone_no.getText().toString().trim();

                Registraion_detail user = new Registraion_detail(name,emal,phone);

                mDatabase.child(userId).setValue(user);
            }
        });


    }
}
