package com.example.db2medicalinsurance;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addItems extends AppCompatActivity {
    EditText desc, name, discount, end, price, start, id;
    Button add;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        add = findViewById(R.id.add_item);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                desc = findViewById(R.id.description);
                name = findViewById(R.id.name);
                discount = findViewById(R.id.discount);
                end = findViewById(R.id.end_time);
                price = findViewById(R.id.price);
                start = findViewById(R.id.start_time);
                id = findViewById(R.id.id);

                String descS = desc.getText().toString();
                String nameS = name.getText().toString();
                String discountS = discount.getText().toString();
                String endS = end.getText().toString();
                String priceS = price.getText().toString();
                String startS = start.getText().toString();
                String idS = id.getText().toString();


                /***************************************
                 ***************Firebase Code************
                 ****************************************/

                Date startS1 = new Date(startS);
                Date endS1 = new Date(endS);
                Double priceS1 = Double.parseDouble(priceS);
                Double discountS1 = Double.parseDouble(discountS);
                ItemInfo S = new ItemInfo(descS , nameS ,priceS1 , discountS1 , startS1 , endS1);
                DocumentReference serviceRef = db.collection("services").document("Jwe98DwLFM1PIN42lUL3").collection("items").document(idS);

                serviceRef.set(S);

                Intent i = new Intent(addItems.this, admin.class);
                startActivity(i);
            }
        });
    }
}
