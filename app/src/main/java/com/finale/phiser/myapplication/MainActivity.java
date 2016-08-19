package com.finale.phiser.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bMessageLink,bMapLink, bLogout;
    EditText etName, etRegNo, etPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etRegNo = (EditText) findViewById(R.id.etRegNo);
        etPhone = (EditText) findViewById(R.id.etPhone);
        bMessageLink = (Button) findViewById(R.id.bMessageLink);
        bMapLink = (Button) findViewById(R.id.bMapLink);
        bLogout = (Button) findViewById(R.id.bLogout);


        bMessageLink.setOnClickListener(this);

        bMapLink.setOnClickListener(this);

        bLogout.setOnClickListener(this);


        RegisterDB info = new RegisterDB(this);
        info.open();
        String returnedName = info.getName();
        String returnedRegNo = info.getRegNo();
        String returnedPhone = info.getPhone();

        etName.setText(returnedName);
        etRegNo.setText(returnedRegNo);
        etPhone.setText(returnedPhone);

        etName.setFocusable(false);
        etRegNo.setFocusable(false);
        etPhone.setFocusable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bMessageLink:

                startActivity(new Intent(this, Messaging.class));
                break;

            case R.id.bMapLink:

                startActivity(new Intent(this, MapsActivity.class));
                break;

            case R.id.bLogout:

                startActivity(new Intent(this, Login.class));
                break;


        }

    }



}