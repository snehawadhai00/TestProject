package com.technocrate.testproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout etFName,etUsername,etEmail,etmonileNo,etPassword,etCpasswoed;
    private Button save,loginback;

    DBHelper helper;
    SQLiteDatabase db;

    private String name,uname,email,mobile,pass,cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialise();

        helper = new DBHelper(this);
        this.save.setOnClickListener(this::saveData);
        this.loginback.setOnClickListener(this::loginScreen);
    }

    private void loginScreen(View view) {
        Intent intent = new Intent (RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    private void saveData(View view) {
       name = etFName.getEditText().getText().toString();
       uname = etUsername.getEditText().getText().toString();
       email = etEmail.getEditText().getText().toString();
       mobile = etmonileNo.getEditText().getText().toString();
       pass = etPassword.getEditText().getText().toString();
       cpass = etCpasswoed.getEditText().getText().toString();

       if(name.isEmpty()){
           etFName.setError("Name is Required");
           etFName.requestFocus();
           return;
       }
        if(uname.isEmpty()) {
           etUsername.setError("Username is Required");
            etUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Email is Required");
           etEmail.requestFocus();
            return;
        }
        if(mobile.isEmpty()){
           etmonileNo.setError("Mobile is Required");
            etmonileNo.requestFocus();
            return;
        }
        if(pass.isEmpty()){
           etPassword.setError("Name is Required");
            etPassword.requestFocus();
            return;
        }
        if(pass.length()<8){
            etPassword.setError("Atleast 8 character required");
            etPassword.requestFocus();
            return;
        }
        if(cpass.isEmpty()){
            etCpasswoed.setError("Name is Required");
            etCpasswoed.requestFocus();
            return;
        }
        if(cpass.length()<8){
            etCpasswoed.setError("Atleast 8 character required");
           etCpasswoed.requestFocus();
            return;
        }

        if(isRegistered(email).equals("EXIXTS")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("EMAIL ALREADY EXISTS");
            builder.setMessage("The Email you are Trying is Already Registered with App");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return;
        }

        if(isRegistered(email).equals("NOT EXIST")){
        boolean isInserted = helper.insertData(name,uname,email,mobile,pass,cpass);
        if(isInserted == true) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            RegisterActivity.this.finish();

        }else {
            Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT).show();
        }

        }
    }

    public String isRegistered(String email){
        db = helper.getReadableDatabase();
        String EMAIL = DBHelper.COL_4 + "=?";
        Cursor cursor = db.query(DBHelper.TABLE_NAME,null,EMAIL,new String[]{email},null,null,null);

        if (cursor.getCount() < 1){
            cursor.close();
            return "";

        }
        cursor.close();
        return "Email Already Exixts";
    }

    private void initialise() {
        etFName =findViewById(R.id.full_name);
        etUsername = findViewById(R.id.User_N);
        etEmail = findViewById(R.id.Email);
        etmonileNo = findViewById(R.id.Mobile_no);
        etPassword = findViewById(R.id.Password_1);
        etCpasswoed = findViewById(R.id.Password_2);

        save = findViewById(R.id.btnSubmit);
        loginback = findViewById(R.id.btnLoginAgain);


    }
}