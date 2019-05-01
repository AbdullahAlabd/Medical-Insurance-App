package com.example.db2medicalinsurance;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class addNewService extends AppCompatActivity {
    EditText name, description, start, end;
    EditText pname, paddress, pid, ptype;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
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

        myCalendar = Calendar.getInstance();

        end = findViewById(R.id.service_endtime);
        start = findViewById(R.id.service_starttime);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addNewService.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        start.setText(sdf.format(myCalendar.getTime()));
                        //end.setText(sdf.format(myCalendar.getTime()));
                    }

                };


            }


        });
        end.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addNewService.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        // start.setText(sdf.format(myCalendar.getTime()));
                        end.setText(sdf.format(myCalendar.getTime()));
                    }

                };
            }


        });
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                description = findViewById(R.id.service_description);
                name = findViewById(R.id.service_name);



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
                /********************************************************************************************************************/
                String UNIX_DATE_FORMAT = "dd/MM/yyyy";
                SimpleDateFormat formatter = new SimpleDateFormat(UNIX_DATE_FORMAT);
                Date start_date = null;
                Date end_date = null;

                try {
                    start_date = (Date)formatter.parse(startS);
                    end_date = (Date)formatter.parse(endS);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(start_date.getDate()+"////////"+end_date.getTime());
                /********************************************************************************************************************/


                /***************************************
                 ***************Firebase Code************
                 ****************************************/
                ProviderInfo p = new ProviderInfo();
                //Date startS1 = new Date(startS);
                //Date endS1 = new Date(endS);

                //ServiceInfo s = new ServiceInfo(startS1, endS1, p.getType(), p.getLocation(), descriptionS, nameS, p.getName() , pidS);
                //DocumentReference serviceRef = db.collection("services").document(pidS);

                //serviceRef.set(s);
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
