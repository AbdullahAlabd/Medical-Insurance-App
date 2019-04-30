package com.example.db2medicalinsurance;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class addNewService extends AppCompatActivity {
    EditText name, description, start, end;
    EditText pname, paddress, pid, ptype;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference serviceRef = db.collection("services");

    Button add;
    private static int RESULT_LOAD_IMAGE = 1;

    Geocoder geocoder = null;

    ImageButton img;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service);
        geocoder = new Geocoder(this);
        img = findViewById(R.id.imageButton);
        add = findViewById(R.id.add);


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
                description = findViewById(R.id.service_description);
                name = findViewById(R.id.service_name);
                end = findViewById(R.id.service_endtime);
                start = findViewById(R.id.service_starttime);


                String descriptionS = description.getText().toString();
                String nameS = name.getText().toString();
                String startS = start.getText().toString();
                String endS = end.getText().toString();

                /************************************************************/
                pname = findViewById(R.id.service_provname);
                paddress = findViewById(R.id.service_provaddress);
                pid = findViewById(R.id.service_provid);
                ptype = findViewById(R.id.service_provtype);


                String pnameS = pname.getText().toString();
                String paddressS = paddress.getText().toString();
                String pidS = pid.getText().toString();
                String ptypeS = ptype.getText().toString();
                /**********************************************************/
                String latitude = null;
                String longitude = null;
                try {
                    String locationName = paddressS;

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
                System.out.println(descriptionS + nameS + startS + endS);
                /***************************************
                 ***************Firebase Code************
                 ****************************************/
                ProviderInfo p = new ProviderInfo();
                Date startS1 = new Date(startS);
                Date endS1 = new Date(endS);

                ServiceInfo s = new ServiceInfo(startS1, endS1, p.getType(), p.getLocation(), descriptionS, nameS, p.getName() , pidS);
                DocumentReference serviceRef = db.collection("services").document(pidS);

                serviceRef.set(s);
                Intent i = new Intent(addNewService.this, admin.class);
                startActivity(i);
            }
        });
    }


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
