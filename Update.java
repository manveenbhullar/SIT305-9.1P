package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Update extends AppCompatActivity {

    EditText title, phone, desc, date, location;
    Button delete;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_advert);

        title = findViewById(R.id.title1);
        desc = findViewById(R.id.desc);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        delete = findViewById(R.id.deleteAdvert);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        phone.setText(i.getStringExtra("phone"));
        desc.setText(i.getStringExtra("desc"));
        date.setText(i.getStringExtra("date"));
        location.setText(i.getStringExtra("location"));
        id = i.getStringExtra("id");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }
    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title.getText().toString() + " ?");
        builder.setMessage("Confirm Delete ? " + title.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database database = new Database(Update.this);
                database.delete(id);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}