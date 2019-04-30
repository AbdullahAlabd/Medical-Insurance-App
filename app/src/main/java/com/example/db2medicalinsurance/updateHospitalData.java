package com.example.db2medicalinsurance;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class updateHospitalData extends AppCompatActivity {
    EditText owner, name, type, phone, city, country, street,id;
    Button add;
    ImageButton img;
    ImageView imgview;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static int RESULT_LOAD_IMAGE = 1;

    Geocoder geocoder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hospital_data);
        add = findViewById(R.id.update);
        img = findViewById(R.id.imageButton);
        geocoder = new Geocoder(this);

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);*/
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                owner = findViewById(R.id.hospital_manager);
                name = findViewById(R.id.hospital_name);
                type = findViewById(R.id.hospital_serviceType);
                phone = findViewById(R.id.hospital_phone);
                city = findViewById(R.id.hospital_city);
                country = findViewById(R.id.hospital_country);
                street = findViewById(R.id.hospital_street);
                id = findViewById(R.id.hospital_id);
                String ownerS = owner.getText().toString();
                String nameS = name.getText().toString();
                String typeS = type.getText().toString();
                String phoneS = phone.getText().toString();
                String cityS = city.getText().toString();
                String countryS = country.getText().toString();
                String streetS = street.getText().toString();
                String ids = id.getText().toString();
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
                Map<String, String> address = new HashMap();
                address.put("city" , cityS);
                address.put("country" ,countryS);
                address.put("street" , streetS);
                GeoPoint locationS = new GeoPoint(Double.parseDouble(latitude) , Double.parseDouble(longitude));

                ProviderInfo p = new ProviderInfo(address , nameS , typeS , ownerS , phoneS , locationS);
                DocumentReference serviceRef = db.collection("providers").document(ids);

                serviceRef.set(p);

                Intent i = new Intent(updateHospitalData.this, admin.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imgview = findViewById(R.id.imageView);
            Uri w = data.getData();
            imgview.setImageURI(w);
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            /********************************************************************/



            String picturePath = cursor.getString(columnIndex);
            System.out.println(picturePath);


            /********************************************************************/
            cursor.close();

            // String picturePath contains the path of selected Image
        }
    }
}
