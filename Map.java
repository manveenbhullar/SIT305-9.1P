package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gmap;
    List<Model> locations;
    Database data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);
        locations = new ArrayList<>();
        data = new Database(this);
        fetchAllLocationsFromDatabase();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void fetchAllLocationsFromDatabase()
    {
        Cursor cursor = data.readAllData();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No saved locations", Toast.LENGTH_SHORT).show();
        }

        else
        {
            while(cursor.moveToNext())
            {
                locations.add(new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        List<Address> addList;
        double doubleLat = 0, doubleLong = 0;
        for (int i = 0; i< 1; i++)
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addList = geocoder.getFromLocationName(locations.get(i).getLocation(), 1);
                if (addList != null){
                    Address location = addList.get(i);
                    double Lat = location.getLatitude();
                    double Long = location.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            LatLng tempName = new LatLng(doubleLat, doubleLong);
            gmap.addMarker((new MarkerOptions().position(tempName)).title(locations.get(i).getTitle()));
            gmap.moveCamera(CameraUpdateFactory.newLatLng(tempName));
        }
    }
}