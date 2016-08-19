package com.finale.phiser.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    RegisterDB helper = new RegisterDB(this);

    Button bLogin;
    //EditText etUsername, etPassword;
    TextView tvRegisterLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // etUsername = (EditText) findViewById(R.id.etUsername);
       // etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.bLogin:

                EditText a = (EditText) findViewById(R.id.etUsername);
                String Username = a.getText().toString();
                EditText b = (EditText) findViewById(R.id.etPassword);
                String Password = b.getText().toString();


            if (!a.getText().toString().trim().equals("") && !b.getText().toString().trim().equals("")) {


                helper.open();
                String password = helper.searchPass(Username);


            if(Password.equals(password)){

                startActivity(new Intent(this, MainActivity.class));
                }
            else {

                    Toast.makeText(this, "Username and Password dont match", Toast.LENGTH_SHORT).show();

                }

            }
            else {

                Toast.makeText(this, "Enter full user details", Toast.LENGTH_SHORT).show();

            }
            break;

            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }

    }


}