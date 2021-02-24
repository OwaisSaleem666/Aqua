package com.example.aqua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class dashboard extends AppCompatActivity {

    CardView card_ride, card_deliver, card_shop,card_service, card_career, card_buyandsale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        card_ride = (CardView) findViewById(R.id.card_ride);
        card_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencardride();
            }
        });

        card_deliver = (CardView) findViewById(R.id.card_deliver);
        card_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencarddeliver();
            }
        });

        card_shop = (CardView) findViewById(R.id.card_shop);
        card_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencardshop();
            }
        });

        card_service = (CardView) findViewById(R.id.card_service);
        card_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencardservice();
            }
        });

        card_career = (CardView) findViewById(R.id.card_career);
        card_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencardcareer();
            }
        });

        card_buyandsale = (CardView) findViewById(R.id.card_buyandsale);
        card_buyandsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencardbuyandsale();
            }
        });

    }
    public void opencardride(){
        Intent intent = new Intent(this,rides.class);
        Toast.makeText(dashboard.this,"moving to ride activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void opencarddeliver(){
        Intent intent = new Intent(this,delivery.class);
        Toast.makeText(dashboard.this,"moving to delivery activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void opencardshop(){
        Intent intent = new Intent(this,shops.class);
        Toast.makeText(dashboard.this,"moving to shop activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void opencardservice(){
        Intent intent = new Intent(this,services.class);
        Toast.makeText(dashboard.this,"moving to services activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void opencardcareer(){
        Toast.makeText(dashboard.this,"make the career activity", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(this,services.class);
       // startActivity(intent);
    }
    public void opencardbuyandsale(){
        Toast.makeText(dashboard.this,"make the Buy and Sale activity", Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(this,services.class);
        //startActivity(intent);
    }
}