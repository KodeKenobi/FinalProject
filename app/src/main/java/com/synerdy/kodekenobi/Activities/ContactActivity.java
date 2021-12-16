package com.synerdy.kodekenobi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.synerdy.kodekenobi.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ImageView imageg = findViewById(R.id.gmail);
        ImageView imagef = findViewById(R.id.facebook);
        ImageView imagei = findViewById(R.id.instagram);
        ImageView imagew = findViewById(R.id.whatsapp);

        imageg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ContactActivity.this, "Mail us here", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        imageg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "kodekenobi@gmail.com", Toast.LENGTH_SHORT).show();
            }
        });

        imagef.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ContactActivity.this, "Like our posts", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        imagef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "kodekenobi@facebook.com", Toast.LENGTH_SHORT).show();
            }
        });

        imagew.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ContactActivity.this, "Text us your suggestions here", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        imagew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "+27614983802", Toast.LENGTH_SHORT).show();
            }
        });

        imagei.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ContactActivity.this, "Follow our handles", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        imagei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContactActivity.this, "Kode Kenobi", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
