package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void display(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void add(View view) {
        Intent intent1 = new Intent(MainActivity.this, Add.class);
        startActivity(intent1);
    }
    public void mapView(View view)
    {
        Intent intent2 = new Intent(this, Map.class);
        startActivity(intent2);
    }

}