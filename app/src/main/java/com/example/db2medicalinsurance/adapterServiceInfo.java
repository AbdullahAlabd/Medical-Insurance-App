package com.example.db2medicalinsurance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterServiceInfo extends RecyclerView.Adapter<adapterServiceInfo.ServiceViewHolder> {
    private Context mContext;
    private List<ServiceInfo> mserviceProviderInfo;
    public adapterServiceInfo(Context mContext, List<ServiceInfo> mserviceProviderInfo) {
        this.mContext = mContext;
        this.mserviceProviderInfo = mserviceProviderInfo;
    }
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.service_providerinfo, parent, false);
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder holder, int position) {
        ServiceInfo serviceProviderInfo  = mserviceProviderInfo.get(position);
        holder.textViewTitle.setText(serviceProviderInfo.getName());
        //holder.textViewDesc.setText(serviceProviderInfo.getStart_time().toString());
        holder.textViewDesc.setText(serviceProviderInfo.getDescription());
        holder.textViewName.setText(serviceProviderInfo.getProvider_name());

        final String docmentId = serviceProviderInfo.getDocumentId();
        final String title = serviceProviderInfo.getName();
        final String description = serviceProviderInfo.getDescription();
        final String start_time = serviceProviderInfo.getStart_time().toString();
        final String end_time = serviceProviderInfo.getEnd_time().toString();
        final String Provider_Id = serviceProviderInfo.getProvider_id();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageReference = storageRef.child("images/myOwl.png");

        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                System.out.println(uri.toString());
                Picasso.get()
                        .load(uri.toString())
                        .into(holder.imageViewLogo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                intent.putExtra("documentId", docmentId);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("start_time", start_time);
                intent.putExtra("end_tiem", end_time);
                //Toast.makeText(mContext, docmentId + " " + title + " " + description + " " + start_time + " " + end_time, Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

        holder.textViewDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                intent.putExtra("documentId", docmentId);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("start_time", start_time);
                intent.putExtra("end_tiem", end_time);
                //Toast.makeText(mContext, docmentId + " " + title + " " + description + " " + start_time + " " + end_time, Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

        holder.imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProviderInfoActivity.class);
                intent.putExtra("Provider_Id", Provider_Id);
                mContext.startActivity(intent);
            }
        });
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProviderInfoActivity.class);
                intent.putExtra("Provider_Id", Provider_Id);
                mContext.startActivity(intent);
            }
        });
        /*Picasso.get()
                .load("https://i.ytimg.com/vi/zwR44n54-bk/maxresdefault.jpg")
                .into(holder.imageViewLogo);*/

    }

    @Override
    public int getItemCount() {
        return mserviceProviderInfo.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDesc;
        public TextView textViewName;
        public ImageView imageViewLogo;
        public CardView cardView;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.mService_Title);
            textViewDesc = itemView.findViewById(R.id.mService_Desc);
            textViewName = itemView.findViewById(R.id.mProvider_Name);
            imageViewLogo = itemView.findViewById(R.id.mService_Logo);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}
