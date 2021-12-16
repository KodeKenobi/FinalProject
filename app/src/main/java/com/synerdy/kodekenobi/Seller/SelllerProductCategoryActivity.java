package com.synerdy.kodekenobi.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.synerdy.kodekenobi.Activities.CGCActivity;
import com.synerdy.kodekenobi.Activities.ComicsActivity;
import com.synerdy.kodekenobi.Activities.DCActivity;
import com.synerdy.kodekenobi.Activities.ExclusivesActivity;
import com.synerdy.kodekenobi.Activities.FirstAppActivity;
import com.synerdy.kodekenobi.Activities.FunkoActivity;
import com.synerdy.kodekenobi.Activities.GalleriesActivity;
import com.synerdy.kodekenobi.Activities.GraphicNovelsActivity;
import com.synerdy.kodekenobi.Activities.KeysActivity;
import com.synerdy.kodekenobi.Activities.LatestsActivity;
import com.synerdy.kodekenobi.Activities.MangaActivity;
import com.synerdy.kodekenobi.Activities.MarvelActivity;
import com.synerdy.kodekenobi.Activities.RecommendationsActivity;
import com.synerdy.kodekenobi.R;
import com.synerdy.kodekenobi.Activities.SignedActivity;
import com.synerdy.kodekenobi.Activities.StatuesActivity;
import com.synerdy.kodekenobi.Activities.UpcomingActivity;

public class SelllerProductCategoryActivity extends AppCompatActivity {

    //Variables for clicking on the item and display the item
    private ImageView latests, statues, funkopops;
    private ImageView graphicNovels, recommendations, cgc;
    private ImageView dcbooks, keys, marvelbooks;
    private ImageView signed, manga, indies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_category);

        latests = (ImageView) findViewById(R.id.latests);
        statues = (ImageView) findViewById(R.id.statues);
        funkopops = (ImageView) findViewById(R.id.funkopops);
        graphicNovels = (ImageView) findViewById(R.id.graphicNovels);
        recommendations = (ImageView) findViewById(R.id.recommendations);
        cgc = (ImageView) findViewById(R.id.cgc);
        dcbooks = (ImageView) findViewById(R.id.dcbooks);
        keys = (ImageView) findViewById(R.id.keys);
        marvelbooks = (ImageView) findViewById(R.id.marvelbooks);
        signed = (ImageView) findViewById(R.id.signed);
        indies = (ImageView) findViewById(R.id.indies);
        manga = (ImageView) findViewById(R.id.manga);


        latests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "latests");
                startActivity(intent);
            }
        }); //end of latests

        statues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "statues");
                startActivity(intent);
            }
        });//end  of statues

        funkopops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "funkopops");
                startActivity(intent);
            }
        });//end  of funkoPops


        graphicNovels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "graphicnovels");
                startActivity(intent);
            }
        });//end  of graphicNovels

        recommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "recommendations");
                startActivity(intent);
            }
        });//end  of upcoming

        cgc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "cgc");
                startActivity(intent);
            }
        });//end  of walletBagPurses


        dcbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "dcbooks");
                startActivity(intent);
            }
        });//end  of dcbooks

        keys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "keys");
                startActivity(intent);
            }
        });//end  of keys

        marvelbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "marvelbooks");
                startActivity(intent);
            }
        });//end  of dcbooks

        signed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "signed");
                startActivity(intent);
            }
        });//end  of dcbooks

        manga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "manga");
                startActivity(intent);
            }
        });//end  of dcbooks

        indies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelllerProductCategoryActivity.this, SellerAddProductActivty.class);
                //need data from an activity to be in another activity, you can pass data between then while starting the activities
                intent.putExtra("category", "indies");
                startActivity(intent);
            }
        });//end  of dcbooks

    }
}
