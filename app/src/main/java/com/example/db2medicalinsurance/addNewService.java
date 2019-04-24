package com.example.db2medicalinsurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addNewService extends AppCompatActivity {
    EditText name,description,start,end;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                description = findViewById(R.id.hospital_manager);
                name = findViewById(R.id.service_name);
                end = findViewById(R.id.hospital_serviceType);
                start = findViewById(R.id.hospital_phone);

                String descriptionS = description.getText().toString();
                String nameS = name.getText().toString();
                String startS = start.getText().toString();
                String endS = end.getText().toString();


                System.out.println(descriptionS+nameS+startS+endS);
                /***************************************
                 ***************Firebase Code************
                 ****************************************/


                Intent i = new Intent(addNewService.this,admin.class);
                startActivity(i);
            }
        });
    }
}
