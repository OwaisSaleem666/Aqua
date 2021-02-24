package com.example.aqua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class rides extends AppCompatActivity {

    CardView card_bike, card_auto, card_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        card_bike = (CardView) findViewById(R.id.card_bike);
        card_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmapactivity();
            }
        });

        card_auto = (CardView) findViewById(R.id.card_auto);
        card_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmapactivity();
            }
        });

        card_car = (CardView) findViewById(R.id.card_car);
        card_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmapactivity();
            }
        });
    }

    public void openmapactivity(){
        Intent intent = new Intent(rides.this, MAP.class);
        startActivity(intent);
    }

    public void click_button(View view) {
        Toast.makeText(this, "My Click Button", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(rides.this,MAP.class);
        startActivity(intent);
    }
}