package com.synerdy.kodekenobi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.synerdy.kodekenobi.Admins.AdminHomeActivity;
import com.synerdy.kodekenobi.Model.Users;
import com.synerdy.kodekenobi.Prevalent.Prevalent;
import com.synerdy.kodekenobi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText loginNumber,loginPassword;
    private Button buttonLogin;
    private TextView adminLink,notAdminLink,forgotPassword;
    private ProgressDialog lBar;
    private CheckBox rememberMe;
    private String parentDBname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginNumber=(EditText)findViewById(R.id.login_phoneNumber);
        loginPassword=(EditText)findViewById(R.id.login_passWord);
        buttonLogin=(Button) findViewById(R.id.ConfirmLogin);
        adminLink=(TextView) findViewById(R.id.admin);
        notAdminLink=(TextView) findViewById(R.id.NotAdmin);
        forgotPassword=(TextView) findViewById(R.id.ForgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });

        lBar=new ProgressDialog(this);
        rememberMe=(CheckBox) findViewById(R.id.RemeberMe);
        Paper.init(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogin.setText("Login admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDBname="Admins";
            }
        });
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogin.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDBname="Users";

            }
        });

    }

    private void login() {

        final String phone=loginNumber.getText().toString();
        final String password=loginPassword.getText().toString();
        lBar=new ProgressDialog(this);
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(LoginActivity.this,"Please enter name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this,"Please enter phone number",Toast.LENGTH_SHORT).show();
        }
        else {
            lBar.setTitle("Logging In..");
            lBar.setMessage("Please wait..");
            lBar.setCanceledOnTouchOutside(false);
            lBar.show();
            allowAccesstoAccount(phone,password);

        }
    }

    private void allowAccesstoAccount(final String phone, final String password) {
        if(rememberMe.isChecked())
        {
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBname).child(phone).exists()){
                    Users usersData=dataSnapshot.child(parentDBname).child(phone).getValue(Users.class);
                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            if(parentDBname.equals("Admins"))
                            {
                                lBar.dismiss();
                                Toast.makeText(LoginActivity.this,"Admin Logged in successfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                            }
                            else if(parentDBname.equals("Users"))
                            {
                                lBar.dismiss();

                                Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                Intent intents=new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.currentUser=usersData;
                                startActivity(intents);


                            }
                        }
                        else
                        {
                            lBar.dismiss();
                            Toast.makeText(LoginActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    lBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Phone Number doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
