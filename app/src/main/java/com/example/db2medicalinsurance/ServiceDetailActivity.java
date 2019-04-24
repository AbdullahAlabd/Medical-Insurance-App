package com.example.db2medicalinsurance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db2medicalinsurance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailActivity extends AppCompatActivity {
    private TextView mService_Title, mService_Desc, mServiceStart_time, mServiceEnd_time;
    private ImageView mHostibtal_Logo;
    private RecyclerView recycler_view1;
    private adapterItemInfo adapterItemInfo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference itemRef;
    private List<ItemInfo> itemInfos;
    private Spinner mSpinner_Duration;
    private Button mBtn_Subscripe;
    private CollectionReference itemRef;
    private String Spinner_Value;
    private String Service_Name, Service_Desc, provider_id, service_id, ServiceStrart_Time, ServiceEnd_Time;
    private Timestamp start_time, end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        mService_Title = findViewById(R.id.mSer_Title);
        mService_Desc = findViewById(R.id.mSer_Desc_);
        mHostibtal_Logo = findViewById(R.id.mHostibtal_Logo);
        mServiceStart_time = findViewById(R.id.mSerStart_Time);
        mServiceEnd_time = findViewById(R.id.mSerEnd_Time);

        itemInfos = new ArrayList<>();

        Intent in = getIntent();
        Service_Name = in.getStringExtra("title");
        Service_Desc = in.getStringExtra("description");
        service_id   = in.getStringExtra("documentId");
        ServiceStrart_Time = in.getStringExtra("start_time");
        ServiceEnd_Time = in.getStringExtra("end_tiem");
        //start_time   = in.get
        Toast.makeText(ServiceDetailActivity.this, Service_Desc + "\n" + Service_Name, Toast.LENGTH_SHORT).show();

        load_sevice_activity();
        recycler_view1 = findViewById(R.id.recycler_view1);
        recycler_view1.setHasFixedSize(true);
        recycler_view1.setLayoutManager(new LinearLayoutManager(this));
        load_service_detail();




    }
    public void load_service_detail() {
        itemRef = db.collection("services").document(service_id).collection("items");
        itemRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(task.isSuccessful()) {
                                ItemInfo itemInfo = document.toObject(ItemInfo.class);
                                itemInfos.add(itemInfo);
                                adapterItemInfo = new adapterItemInfo(ServiceDetailActivity.this, itemInfos);
                                recycler_view1.setAdapter(adapterItemInfo);
                            } else {
                                Toast.makeText(ServiceDetailActivity.this, "Error getting documents", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void load_sevice_activity() {
        mService_Title.setText(Service_Name);
        mService_Desc.setText(Service_Desc);
        mServiceStart_time.setText(ServiceStrart_Time);
        mServiceEnd_time.setText(ServiceEnd_Time);
        System.out.println(ServiceStrart_Time);
        System.out.println(ServiceEnd_Time);
    }
}
