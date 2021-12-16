package com.synerdy.kodekenobi.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.synerdy.kodekenobi.Admins.AdminMaintainProductsActivity;
import com.synerdy.kodekenobi.Model.Products;
import com.synerdy.kodekenobi.Prevalent.Prevalent;
import com.synerdy.kodekenobi.ViewHolder.ProductViewHolder;
import com.synerdy.kodekenobi.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String type="";
    DrawerLayout drawerLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            type = getIntent().getExtras().get("Admins").toString();
        }
        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Synerdy App");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Admins"))
                {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview=navigationView.getHeaderView(0);
        TextView fullNameEditText = headerview.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerview.findViewById(R.id.user_profile_image);
        if (!type.equals("Admins"))
        {

            fullNameEditText.setText(Prevalent.currentUser.getName());
            Picasso.get().load(Prevalent.currentUser.getImage()).placeholder(R.mipmap.ic_launcher_round).into(profileImageView);
        }
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("productState").equalTo("approved"), Products.class)
                        .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                holder.productName.setText(model.getPname());
                //holder.productDescription.setText(model.getDescription());
                holder.productPrice.setText("R" + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.productImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equals("Admins"))
                        {
                            Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }

                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings(value = "StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_cart)
        {
            if (!type.equals("Admins")) {
                Intent i = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(i);
            }
        }

        /*else if (id == R.id.nav_search) {
            Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
            startActivity(intent);
        }*/

        else if (id == R.id.nav_latests) {
            Intent intent = new Intent(HomeActivity.this, LatestsActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_keys) {
            Intent intent = new Intent(HomeActivity.this, KeysActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_cgc) {
            Intent intent = new Intent(HomeActivity.this, CGCActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_statues) {
            Intent intent = new Intent(HomeActivity.this, StatuesActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.funkopops) {
            Intent intent = new Intent(HomeActivity.this, FunkoActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.graphicNovels) {
            Intent intent = new Intent(HomeActivity.this, GraphicNovelsActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_marvel_books) {
            Intent intent = new Intent(HomeActivity.this, MarvelActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_dc_books) {
            Intent intent = new Intent(HomeActivity.this, DCActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_indies) {
            Intent intent = new Intent(HomeActivity.this, IndieBooksActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_signed) {
            Intent intent = new Intent(HomeActivity.this, SignedActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_manga) {
            Intent intent = new Intent(HomeActivity.this, MangaActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_recommendations) {
            Intent intent = new Intent(HomeActivity.this, RecommendationsActivity.class);
            startActivity(intent);
        }

        /*else if (id == R.id.nav_settings) {
            Intent intent = new Intent(HomeActivity.this, Settings.class);
            startActivity(intent);
        }*/

        else if (id == R.id.nav_legal) {
            Intent intent = new Intent(HomeActivity.this, LegalActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_about_developer) {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_contact) {
            Intent intent = new Intent(HomeActivity.this, ContactActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_logout)
        {

                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}
