package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.vo.Field;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;

import java.util.Arrays;
import java.util.List;

public class Add extends AppCompatActivity {

    private RadioGroup radio;
    String radiotype;
    TextView text;
    EditText title, desc, phone, date, location;
    Button save, getLocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advert);

        text = findViewById(R.id.text1);
        title = findViewById(R.id.title1);
        desc = findViewById(R.id.desc);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        save = findViewById(R.id.save);
        radio = (RadioGroup) findViewById(R.id.types);
        getLocation = findViewById(R.id.getLocation);
        radio.clearCheck();

        radio.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        RadioButton
                                radioButton
                                = (RadioButton) group
                                .findViewById(checkedId);
                        radiotype = radioButton.getText().toString();
                    }
                });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(desc.getText().toString()) && !TextUtils.isEmpty(phone.getText().toString()) && !TextUtils.isEmpty(date.getText().toString()) && !TextUtils.isEmpty(location.getText().toString())) {
                    Database db = new Database(Add.this);
                    db.add((radiotype + " " + title.getText().toString()), phone.getText().toString(), desc.getText().toString(), date.getText().toString(), location.getText().toString());
                    Intent intent = new Intent(Add.this, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Add.this, "Fields are Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Places.initialize(getApplicationContext(), "AIzaSyCSJa2s7y5Ppl7mV9hvf0b612jlyysjBbI");
        location.setFocusable(false);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent1 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Add.this);
                startActivityForResult(intent1,100);
            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Add.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(Add.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location locations = task.getResult();
                if (locations != null) {
                    try {
                        Geocoder geocoder = new Geocoder(Add.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(locations.getLatitude(), locations.getLongitude(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        location.setText(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            location.setText(place.getAddress());
        }

        else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}