package com.synerdy.kodekenobi.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.synerdy.kodekenobi.R;
import com.synerdy.kodekenobi.Seller.SelllerProductCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button applyChanges,deleteButton;
    private EditText name,price,description;
    private String productID;
    private DatabaseReference productsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        applyChanges = findViewById(R.id.apply_changes_btn);
        name = findViewById(R.id.product_name_maintain);
        price = findViewById(R.id.product_price_maintain);
        description = findViewById(R.id.product_description_maintain);
        imageView = findViewById(R.id.product_image_maintain);
        deleteButton = findViewById(R.id.delete_product_btn);
        productID=getIntent().getStringExtra("pid");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        displayDefault();
        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProducts();
            }
        });
    }

    private void deleteProducts() {

        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {

                    Intent i=new Intent(AdminMaintainProductsActivity.this, SelllerProductCategoryActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(AdminMaintainProductsActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void applyChanges() {

        String pName=name.getText().toString();
        String pPrice=price.getText().toString();
        String pDescription=description.getText().toString();
        if(TextUtils.isEmpty(pName))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter product name",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pPrice))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter product price",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pDescription))
        {
            Toast.makeText(AdminMaintainProductsActivity.this,"Please enter product description",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String,Object> productsMap=new HashMap<>();
            productsMap.put("pid",productID);
            productsMap.put("pname",pName);
            productsMap.put("price",pPrice);
            productsMap.put("description",pDescription);
            productsRef.updateChildren(productsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductsActivity.this,"Updated successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(AdminMaintainProductsActivity.this, SelllerProductCategoryActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            });
        }
    }

    private void displayDefault() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String pname=snapshot.child("pname").getValue().toString();
                    String pprice=snapshot.child("price").getValue().toString();
                    String pdescription=snapshot.child("description").getValue().toString();
                    String pimage=snapshot.child("image").getValue().toString();

                    name.setText(pname);
                    price.setText(pprice);
                    description.setText(pdescription);
                    Picasso.get().load(pimage).into(imageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
