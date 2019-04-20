package com.example.db2medicalinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    TextView nameView, emailView;
    Button logout, searchBtn;
    Spinner spinnerSrcCat;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView picView;


    ///firestore
    //private FirebaseFirestore db;
    //private CollectionReference serviceRef;
    //private ServiceInfo service;
    //private Query query;
    ////fire
    //private ArrayList<ServiceInfo> services;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout = findViewById(R.id.signOutButton);
        nameView = findViewById(R.id.userName);
        emailView = findViewById(R.id.userEmail);
        picView = findViewById(R.id.userPic);
        spinnerSrcCat = findViewById(R.id.spinnerSrcCat);
        searchBtn = findViewById(R.id.searchBtn);

        String name = getIntent().getStringExtra("userName");
        String email = getIntent().getStringExtra("userEmail");
       // String picURL = getIntent().getStringExtra("userPicURL");
        // spinner cats
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSrcCat.setAdapter(adapter);
        //spinnerSrcCat.setOnItemSelectedListener(this);



        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("480303209677-tqfb1o1lu67i3r8455l3tcdtp3u0o627.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.getPhotoUrl();
        nameView.setText(name);
        emailView.setText(email);
        //picView.setImageBitmap(doInBackground(picURL));
        Picasso.get().load(currentUser.getPhotoUrl()).into(picView);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*db = FirebaseFirestore.getInstance();
                serviceRef = db.collection("services");
                query = serviceRef.whereGreaterThan("name", "");
                services = new ArrayList<>();
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                service = document.toObject(ServiceInfo.class);
                                services.add(service);
                                System.out.println(service.getService_title());

                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "Task failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
*/
            }
        });


        //System.err.println(picURL);
       // System.err.println(email);
        System.err.println(currentUser.getPhotoUrl());
        System.out.println(currentUser.getPhoneNumber());

    }

    private void signOut() {
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

}

