package com.synerdy.kodekenobi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.synerdy.kodekenobi.Model.Products;
import com.synerdy.kodekenobi.R;
import com.synerdy.kodekenobi.ViewHolder.ProductViewHolder;

public class GraphicNovelsActivity extends AppCompatActivity {

    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic_novels);

        inputText = (EditText) findViewById(R.id.search_product_name);
        SearchBtn = (Button) findViewById(R.id.search_Product_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(GraphicNovelsActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = inputText.getText().toString();

                onStart();
            }
        });
    } //end of onCreate()

    @Override
    protected void onStart() {
        super.onStart();

        //A Reference represents a specific location in your Database and can be used for reading or
        // writing data to that Database location.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                //Generates a new Query object ordered by the specified child key.

                .setQuery(reference.orderByChild("category").equalTo("graphicNovels")
                        , Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                        holder.productName.setText(model.getPname());
                        holder.productDescription.setText(model.getDescription());
                        holder.productPrice.setText("R" + model.getPrice() + " ");
                        Picasso.get().load(model.getImage()).into(holder.productImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //putExtra() adds extended data to the intent. It has two parameters, first one specifies the name
                                // which of the extra data,and the second parameter is the data itself
                                Intent intent = new Intent(GraphicNovelsActivity.this, ProductDetailsActivity.class);
                                //will get product id for specific product
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //layout called R.layout.message for each item
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                        //a ViewHolder subclass for displaying each item
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
} //end of class SearchProductsActivity
