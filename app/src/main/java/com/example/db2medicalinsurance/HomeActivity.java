package com.example.db2medicalinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;


public class HomeActivity extends AppCompatActivity {
    TextView nameView, emailView;
    Button logout, searchBtn;
    Spinner spinnerSrcCat;
    EditText searchEditText;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView picView;


    ///firestore
    private FirebaseFirestore db;
    private CollectionReference serviceRef;
    private ServiceInfo service;
    private Query query;
    ////fire
    private ArrayList<ServiceInfo> services;

    //// adapter's
    private adapterServiceInfo adapterServiceInfo;
    private RecyclerView searchResultsRecycler;
    ////

    ///algolia

    Client client = new Client("GUZHO6MLRL", "e3a143c6f7299a087f164ced6bb6d3a4");
    Index index = client.getIndex("services");

    ////
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
        searchEditText = findViewById(R.id.searchEditText);

        searchResultsRecycler = findViewById(R.id.searchResultsRecycler);
        searchResultsRecycler.setHasFixedSize(true);
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));

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


        /// load cards from firestore
        db = FirebaseFirestore.getInstance();
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

                        //Toast.makeText(HomeActivity.this, service.getName(), LENGTH_LONG).show();

                        services.add(service);
                        adapterServiceInfo = new adapterServiceInfo(HomeActivity.this, services);
                        searchResultsRecycler.setAdapter(adapterServiceInfo);
                        //serviceInfo.setDocumentId(document.getId());
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Task failed", LENGTH_LONG).show();
                }
            }
        });

        //// finish loading cards
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();

            }
        });


        // push data to algolia: https://www.algolia.com/doc/guides/getting-started/quick-start/tutorials/quick-start-with-the-api-client/android/

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////// algolia's:

                com.algolia.search.saas.Query aQuery = new com.algolia.search.saas.Query(searchEditText.getText().toString())
                        .setAttributesToRetrieve("serviceID")
                        .setHitsPerPage(5);

                index.searchAsync(aQuery, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        try {
                            services.clear();
                            JSONArray hits = content.getJSONArray("hits");
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < hits.length(); i++) {
                                JSONObject jsonObject = hits.getJSONObject(i);
                                String service_id = jsonObject.getString("serviceID");
                                db.collection("services").document(service_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()) {
                                            service = documentSnapshot.toObject(ServiceInfo.class);
                                            services.add(service);
                                            adapterServiceInfo = new adapterServiceInfo(HomeActivity.this, services);
                                            searchResultsRecycler.setAdapter(adapterServiceInfo);
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        //System.err.println(picURL);
       // System.err.println(email);
        //System.err.println(currentUser.getPhotoUrl());
        //System.out.println(currentUser.getPhoneNumber());

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

