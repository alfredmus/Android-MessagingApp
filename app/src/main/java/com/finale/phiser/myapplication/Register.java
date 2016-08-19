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
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    RegisterDB entry = new RegisterDB(Register.this);
    Button bRegister;
    EditText etName, etRegNo, etPhone, etUsername, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etRegNo = (EditText) findViewById(R.id.etRegNo);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void onClick(View v) {

         switch (v.getId()) {
            case R.id.bRegister:

          if (!etName.getText().toString().trim().equals("") && !etRegNo.getText().toString().trim().equals("")
                        && !etPhone.getText().toString().trim().equals("") && !etUsername.getText().toString().equals("")
                        && !etPassword.getText().toString().equals("")) {

              String Name = etName.getText().toString();
              int RegNo = Integer.parseInt(etRegNo.getText().toString());
              int Phone = Integer.parseInt(etPhone.getText().toString());
              String Username = etUsername.getText().toString();
              String Password = etPassword.getText().toString();


              entry.open();
              entry.createEntry(Name, RegNo, Phone, Username, Password);
              entry.close();
              Toast.makeText(this, "User details Saved", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(Register.this, Login.class));



          }
          else {
                Toast.makeText(this, "Enter full user details", Toast.LENGTH_SHORT).show();

            }

                    break;
                }


            }

}
