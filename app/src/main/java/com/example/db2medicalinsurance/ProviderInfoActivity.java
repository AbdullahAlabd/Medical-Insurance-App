package com.example.db2medicalinsurance;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db2medicalinsurance.MainActivity;
import com.example.db2medicalinsurance.ServiceDetailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.example.db2medicalinsurance.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProviderInfoActivity extends FragmentActivity implements OnMapReadyCallback {
    private String Provider_Id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recycler_view;
    private adapterServiceInfo adapterServiceInfo;
    private List<ServiceInfo> mserviceProviderInfos;
    private TextView mProvider_Name, mSpecialization, mOwner, mPhone_Number, mCountry, mCity, mStreet;
    private String Name, Specialization, Owner, Phone_Number, Country, City, Street;
    private Location location;
    private DocumentReference ProviderRef;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firestoreDB;
    private CollectionReference collectionReference;
    private GoogleMap mMap;
    private String userId;
    private String userPath;
    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath();
    private double lon, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_info);
        Intent in = getIntent();

        Provider_Id = in.getStringExtra("Provider_Id");

        mProvider_Name = findViewById(R.id.mProvider_Name);
        mSpecialization = findViewById(R.id.mSpecialization);
        mOwner = findViewById(R.id.mOwner);
        mPhone_Number = findViewById(R.id.mPhone_Number);
        mCountry = findViewById(R.id.mCountry);
        mCity = findViewById(R.id.mCity);
        mStreet = findViewById(R.id.mStreet);

        mserviceProviderInfos = new ArrayList<>();

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        load_informatio_provider(Provider_Id);
        load_service_provider();

    }

    public void load_informatio_provider(String Id) {
        ProviderRef = db.collection("providers").document(Id);
        ProviderRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ProviderInfo providerInfoInformation = documentSnapshot.toObject(ProviderInfo.class);
                        Specialization = providerInfoInformation.getType();
                        Owner = providerInfoInformation.getOwner();
                        Phone_Number = providerInfoInformation.getPhone();
                        Country = providerInfoInformation.getAddress().get("country");;
                        City = providerInfoInformation.getAddress().get("city");
                        Name = providerInfoInformation.getName();
                        Street = providerInfoInformation.getAddress().get("streat");
                        GeoPoint location = providerInfoInformation.getLocation();
                        lat = location.getLatitude();
                        lon = location.getLongitude();


                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(ProviderInfoActivity.this);

                        mProvider_Name.setText(Name);
                        mSpecialization.setText(Specialization);
                        mOwner.setText(Owner);
                        mPhone_Number.setText(Phone_Number);
                        mCity.setText(City);
                        mStreet.setText(Street);
                        mCountry.setText(Country);

                    }
                });
    }

    public void load_service_provider() {
        collectionReference = db.collection("services");
        collectionReference.whereEqualTo("provider_id" , "1RvrRsxVzYg6XFadEiLo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(task.isSuccessful()) {
                                //String documentId =    document.getId();
                                ServiceInfo serviceInfo = document.toObject(ServiceInfo.class);
                                mserviceProviderInfos.add(serviceInfo);
                                adapterServiceInfo = new adapterServiceInfo(ProviderInfoActivity.this, mserviceProviderInfos);
                                recycler_view.setAdapter(adapterServiceInfo);
                                serviceInfo.setDocumentId(document.getId());
                            } else {
                                Toast.makeText(ProviderInfoActivity.this, "Error getting documents", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        adapterServiceInfo = new adapterServiceInfo(ProviderInfoActivity.this, mserviceProviderInfos);
        recycler_view.setAdapter(adapterServiceInfo);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng position = new LatLng(lat, lon);
        MarkerOptions options = new MarkerOptions();

        options.position(position);
        options.title("amr");
        mMap.addMarker(options);
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(4.0f));
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLngZoom(position, (float)17);
        mMap.moveCamera(updatePosition);
    }
}
