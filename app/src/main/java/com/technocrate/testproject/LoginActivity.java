package com.technocrate.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {

    private Button login, register;

    private TextInputLayout etemail,etpassword;
    private DBHelper helper;
    private SQLiteDatabase db;
    private  String email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialise();

        this.register.setOnClickListener(this::goRegistration);
        this.login.setOnClickListener(this::gologin);

    }

    private void goRegistration(View view) {
        Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
        startActivity(intent);

    }

    private void gologin(View view) {
        helper = new DBHelper(this);
        email = etemail.getEditText().getText().toString();
        pass = etpassword.getEditText().getText().toString();

        if(email.isEmpty()){
            etemail.setError("Email is Required");
            etemail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            etpassword.setError("Name is Required");
            etpassword.requestFocus();
            return;
        }
        if(pass.length()<8){
            etpassword.setError("Atleast 8 character required");
            etpassword.requestFocus();
            return;
        }
        String storedPassword = getSingleEntry(email);
       if (!email.equals("")&& !pass.equals("")){
         if (pass.equals(storedPassword)){

             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setMessage("Login Success");
             builder.show();

         }else {
             Toast.makeText(this, "Email & Password dont Match", Toast.LENGTH_SHORT).show();
         }
       }else {
           Toast.makeText(this, "field is Empty", Toast.LENGTH_SHORT).show();
       }



    }

    private String getSingleEntry(String email) {

        String EMAIL = DBHelper.COL_4 +"=?";
        Cursor cursor=db.query(DBHelper.TABLE_NAME,null,EMAIL,new String[]{email},null,null,null);

        if (cursor.getCount()<1){
            cursor.close();
            return "Email Not Exists";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(DBHelper.COL_6));
        cursor.close();
        return password;

    }


    private void initialise() {

        register = findViewById(R.id.btnRegister);
        login = findViewById(R.id.btnLogin);

        etemail = findViewById(R.id.username);
        etpassword = findViewById(R.id.password);



    }
}