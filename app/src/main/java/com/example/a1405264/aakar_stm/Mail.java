package com.example.a1405264.aakar_stm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mail extends Fragment implements View.OnClickListener {


    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    private Button buttonSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mail, container, false);


        editTextEmail = (EditText)view.findViewById(R.id.editText8);
        editTextSubject = (EditText)view.findViewById(R.id.editText9);
        editTextMessage = (EditText)view.findViewById(R.id.editText10);

        buttonSend = (Button)view.findViewById(R.id.button5);

        buttonSend.setOnClickListener(this);


        return  view;
    }

    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(getActivity(), email, subject, message);

        //Executing send mail to send email
        sm.execute();
    }
    @Override
    public void onClick(View view) {

        sendEmail();

        startActivity(new Intent(getActivity(),Home.class));
    }
}
