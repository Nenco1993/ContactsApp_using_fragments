package com.example.neven.fragmentsv2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts_Add extends Fragment {

    public EditText etFirstName;

    DatabaseHandler handler;
    sendData commander;


    public interface sendData {

        public void etValues(String fname, String lname, String phone, String email);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        commander = (sendData) context;

    }


    public Contacts_Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_add, container, false);


        Button bSave = (Button) view.findViewById(R.id.bSaveID);
        etFirstName = (EditText) view.findViewById(R.id.etFirstNameID);
        final EditText etLastName = (EditText) view.findViewById(R.id.etLastNameID);
        final EditText etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumberID);
        final EditText etEmail = (EditText) view.findViewById(R.id.etEmailID);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                commander.etValues(etFirstName.getText().toString(), etLastName.getText().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString());


            }
        });


        return view;
    }






}
