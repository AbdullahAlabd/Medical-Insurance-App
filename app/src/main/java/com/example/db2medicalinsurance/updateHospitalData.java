package com.example.db2medicalinsurance;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


public class updateHospitalData extends AppCompatActivity {
    EditText owner, name, type, phone, city, country, street;
    Button add;


    Geocoder geocoder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hospital_data);
        add = findViewById(R.id.update);
        geocoder = new Geocoder(this);


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                owner = findViewById(R.id.hospital_manager);
                name = findViewById(R.id.hospital_name);
                type = findViewById(R.id.hospital_serviceType);
                phone = findViewById(R.id.hospital_phone);
                city = findViewById(R.id.hospital_city);
                country = findViewById(R.id.hospital_country);
                street = findViewById(R.id.hospital_street);
                String ownerS = owner.getText().toString();
                String nameS = name.getText().toString();
                String typeS = type.getText().toString();
                String phoneS = phone.getText().toString();
                String cityS = city.getText().toString();
                String countryS = country.getText().toString();
                String streetS = street.getText().toString();
                String latitude = null;
                String longitude = null;

                try {
                    String locationName = name+", "+streetS+", "+cityS+", "+countryS;

                    List<Address> addressList = geocoder.getFromLocationName(
                            locationName, 5);
                    if (addressList != null && addressList.size() > 0) {
                        //int lat = (int) (addressList.get(0).getLatitude() * 1e6);
                        //int lng = (int) (addressList.get(0).getLongitude() * 1e6);
                        latitude = addressList.get(0).getLatitude()+"";
                         longitude = addressList.get(0).getLongitude()+"";
                        System.out.println("lat: "
                                + latitude + " lng: "
                                + longitude);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(ownerS + nameS + typeS + phoneS + cityS + countryS + streetS+latitude+longitude);
                /***************************************
                 ***************Firebase Code************
                 ****************************************/


                Intent i = new Intent(updateHospitalData.this, admin.class);
                startActivity(i);
            }
        });

    }
}
