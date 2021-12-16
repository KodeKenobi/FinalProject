package com.synerdy.kodekenobi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.synerdy.kodekenobi.Prevalent.Prevalent;
import com.synerdy.kodekenobi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {
    private String check="";
    private TextView title,titleQuestions;
    private EditText phoneNumber,question1,question2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        check=getIntent().getStringExtra("check");
        title=(TextView) findViewById(R.id.reset_password);
        titleQuestions=(TextView) findViewById(R.id.title_questions);
        phoneNumber=(EditText) findViewById(R.id.find_phone_number);
        question1=(EditText) findViewById(R.id.question_1);
        question2=(EditText) findViewById(R.id.question_2);
        verifyButton=(Button) findViewById(R.id.verify_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (check.equals("settings")) {
            title.setText("Set Questions");
            phoneNumber.setVisibility(View.GONE);
            titleQuestions.setText("Answer the following security questions");
            verifyButton.setText("Set");
            displayAnswers();
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();
                }
            });
        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forgotPassword();
                }
            });

        }
    }

    private void forgotPassword() {
        String phnNum=phoneNumber.getText().toString();
        final String answ1=question1.getText().toString().toLowerCase();
        final String answ2=question2.getText().toString().toLowerCase();
        if(!TextUtils.isEmpty(phnNum) && !TextUtils.isEmpty(answ1) && !TextUtils.isEmpty(answ2))
        {
            final DatabaseReference qref=FirebaseDatabase.getInstance().getReference().child("Users").child(phnNum);
            qref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        String mphone=snapshot.child("phone").getValue().toString();
                        if(snapshot.hasChild("Security Questions"))
                        {
                                String one=snapshot.child("Security Questions").child("answer1").getValue().toString();
                                String two=snapshot.child("Security Questions").child("answer2").getValue().toString();
                                if(one.equals(answ1) && two.equals(answ2))
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(ForgotPasswordActivity.this);
                                    builder.setTitle("Set new Password");
                                    final EditText newPassword=new EditText(ForgotPasswordActivity.this);
                                    newPassword.setHint("Please enter new password");
                                    builder.setView(newPassword);
                                    builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String nP=newPassword.getText().toString();
                                            if(!TextUtils.isEmpty(nP))
                                            {
                                                qref.child("password").setValue(nP).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            Toast.makeText(ForgotPasswordActivity.this,"Successfully changed password",Toast.LENGTH_SHORT).show();
                                                            Intent intent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                                                            startActivity(intent);
                                                        }

                                                    }
                                                });

                                            }

                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();

                                        }
                                    });

                                    builder.show();
                                }
                                else
                                {
                                    Toast.makeText(ForgotPasswordActivity.this,"Incorrect answers",Toast.LENGTH_SHORT).show();
                                }
                        }
                        else
                        {
                            Toast.makeText(ForgotPasswordActivity.this,"Security questions are not set",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this,"Not a registered user",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            Toast.makeText(ForgotPasswordActivity.this,"Please fill all the above",Toast.LENGTH_SHORT).show();
        }

    }

    private void setAnswers() {

                String answer1=question1.getText().toString().toLowerCase();
                String answer2=question2.getText().toString().toLowerCase();
                if(answer1.equals("") && answer2.equals(""))
                {
                    Toast.makeText(ForgotPasswordActivity.this,"Please anwser both the questions",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentUser.getPhone());
                    HashMap<String,Object> qMap=new HashMap<>();
                    qMap.put("answer1",answer1);
                    qMap.put("answer2",answer2);
                    ref.child("Security Questions").updateChildren(qMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPasswordActivity.this,"Security Questions are set successfully!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ForgotPasswordActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                }
            }

        private void displayAnswers()
        {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").
                    child(Prevalent.currentUser.getPhone());
            ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        String ans1=snapshot.child("answer1").getValue().toString();
                        String ans2=snapshot.child("answer2").getValue().toString();
                        question1.setText(ans1);
                        question2.setText(ans2);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

