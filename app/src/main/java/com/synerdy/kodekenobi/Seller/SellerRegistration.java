package com.synerdy.kodekenobi.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.synerdy.kodekenobi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistration extends AppCompatActivity {
    private EditText sName,sPhoneNum,sAddress,sEmail,sPassword;
    private Button sRegister,sLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog( this);
        sName=(EditText) findViewById(R.id.seller_name);
        sPhoneNum=(EditText) findViewById(R.id.seller_phone_number);
        sAddress=(EditText) findViewById(R.id.seller_address);
        sPassword=(EditText) findViewById(R.id.seller_password);
        sEmail=(EditText) findViewById(R.id.seller_email);
        sRegister=(Button) findViewById(R.id.seller_regsiter_button);
        sLogin=(Button) findViewById(R.id.seller_login_button);
        sLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SellerRegistration.this,SellerLoginActivity.class);
                startActivity(intent);
            }
        });
        sRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });
    }

    private void registerSeller() {
        final String name=sName.getText().toString().trim();
        final String number=sPhoneNum.getText().toString().trim();
        final String  password=sPassword.getText().toString().trim();
        final String email=sEmail.getText().toString().trim();
        final String address=sAddress.getText().toString().trim();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)&& !TextUtils.isEmpty(address))
        {
            loadingBar.setTitle("Creating seller account");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.setMessage("Please wait");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        final DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
                        String sid=mAuth.getCurrentUser().getUid();
                        HashMap<String,Object> sMap=new HashMap<>();
                        sMap.put("sid",sid);
                        sMap.put("sName",name);
                        sMap.put("sNumber",number);
                        sMap.put("sPassword",password);
                        sMap.put("sEmail",email);
                        sMap.put("sAddress",address);
                        rootref.child("Sellers").child(sid).updateChildren(sMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(SellerRegistration.this,"Successfully registered",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(SellerRegistration.this,SellerHomeActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }

                            }
                        });

                    }

                }
            });



        }
        else
        {
            Toast.makeText(SellerRegistration.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
    }

}
