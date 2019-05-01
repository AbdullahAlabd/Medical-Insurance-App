package com.example.db2medicalinsurance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class addItems extends AppCompatActivity {
    EditText desc, name, discount, end, price, start, id,provid;
    Button add;
    EditText edittext;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        add = findViewById(R.id.add_item);
        myCalendar = Calendar.getInstance();

        end = findViewById(R.id.end_time);
        start = findViewById(R.id.start_time);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addItems.this, date, myCalendar
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
                new DatePickerDialog(addItems.this, date, myCalendar
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
                desc = findViewById(R.id.description);
                name = findViewById(R.id.name);
                discount = findViewById(R.id.discount);
                price = findViewById(R.id.price);
                id = findViewById(R.id.id);
                provid = findViewById(R.id.provid);

                String descS = desc.getText().toString();
                String nameS = name.getText().toString();
                String discountS = discount.getText().toString();
                String endS = end.getText().toString();
                String priceS = price.getText().toString();
                String startS = start.getText().toString();
                String idS = id.getText().toString();
                String providS = provid.getText().toString();

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

                /***************************************
                 ***************Firebase Code************
                 ****************************************/

                //Date startS1 = new Date(startS);
                //Date endS1 = new Date(endS);
                Double priceS1 = Double.parseDouble(priceS);
                Double discountS1 = Double.parseDouble(discountS);
                //ItemInfo S = new ItemInfo(descS, nameS, priceS1, discountS1);
                //DocumentReference serviceRef = db.collection("services").document("Jwe98DwLFM1PIN42lUL3").collection("items").document(idS);

                //serviceRef.set(S);

                Intent i = new Intent(addItems.this, admin.class);
                startActivity(i);
            }
        });
        /**********************************************************************************************************************
         * ********************************************************************************************************************
         * ********************************************************************************************************************
         */




    }



}
