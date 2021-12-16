package com.synerdy.kodekenobi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.synerdy.kodekenobi.Model.Users;
import com.synerdy.kodekenobi.Prevalent.Prevalent;
import com.synerdy.kodekenobi.Seller.SellerHomeActivity;
import com.synerdy.kodekenobi.Seller.SellerRegistration;
import com.synerdy.kodekenobi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button loginButton,signupButton;
    private ProgressDialog lBar;
    private TextView sellerLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=(Button) findViewById(R.id.Login);
        signupButton=(Button) findViewById(R.id.signUp);
        sellerLink=(TextView) findViewById(R.id.seller_link);
        sellerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, SellerRegistration.class);
                startActivity(i);

            }
        });
        lBar=new ProgressDialog(this);
        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent2);
            }
        });
        String UserPhoneKey=Paper.book().read(Prevalent.userPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.userPasswordKey);
        if(UserPasswordKey!="" && UserPhoneKey!="")
        {
            if(!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserPhoneKey))
            {
                allow(UserPasswordKey,UserPhoneKey);
                lBar.setTitle("Already logged in..");
                lBar.setMessage("Please wait..");
                lBar.setCanceledOnTouchOutside(false);
                lBar.show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent intent=new Intent(MainActivity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void allow(final String password, final String phone) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    Users usersData=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            lBar.dismiss();
                            Toast.makeText(MainActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                            Intent intents=new Intent(MainActivity.this,HomeActivity.class);
                            Prevalent.currentUser=usersData;
                            startActivity(intents);
                        }
                        else
                        {
                            lBar.dismiss();
                            Toast.makeText(MainActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    lBar.dismiss();
                    Toast.makeText(MainActivity.this,"Phone Number doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
