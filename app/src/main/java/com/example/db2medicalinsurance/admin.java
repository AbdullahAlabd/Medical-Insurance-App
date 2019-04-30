package com.example.db2medicalinsurance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin extends AppCompatActivity {
    Button logout, searchBtn,updateData,addService,addItem;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.signOutButton);
        updateData = findViewById(R.id.hospital_update);
        addService = findViewById(R.id.add_service);
        addItem = findViewById(R.id.add_item);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();

            }
        });
        updateData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(admin.this,updateHospitalData.class);
                startActivity(i);

            }
        });
        addService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(admin.this,addNewService.class);
                startActivity(i);

            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(admin.this,addItems.class);
                startActivity(i);

            }
        });
    }
    private void signOut() {
        mAuth.signOut();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }
}
