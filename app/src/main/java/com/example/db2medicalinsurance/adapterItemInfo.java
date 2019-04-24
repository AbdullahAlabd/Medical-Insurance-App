package com.example.db2medicalinsurance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.db2medicalinsurance.R;

import java.util.List;

public class adapterItemInfo extends RecyclerView.Adapter<adapterItemInfo.ItemViewHolder> {
    private Context context;
    private List<ItemInfo> itemInfos;
    public adapterItemInfo (Context context, List<ItemInfo> itemInfos) {
        this.context = context;
        this.itemInfos = itemInfos;
    }
    @NonNull
    @Override
    public adapterItemInfo.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_detail, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterItemInfo.ItemViewHolder holder, int position) {
        ItemInfo itemInfo = itemInfos.get(position);
        holder.textViewName.setText(itemInfo.getName());
        holder.textViewDesc.setText(itemInfo.getDescription());
        holder.textViewPrice.setText(itemInfo.getPrice()+"");
        holder.textViewDiscount.setText(itemInfo.getDiscount()+"");
        String start_time = itemInfo.getStart_time()+"";
        String end_time = itemInfo.getEnd_time()+"";
        holder.textViewStart_Time.setText(start_time);
        holder.textViewEnd_Time.setText(end_time);
    }

    @Override
    public int getItemCount() {
        return itemInfos.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewDesc, textViewPrice, textViewDiscount, textViewStart_Time, textViewEnd_Time;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.mService_Name);
            textViewDesc = itemView.findViewById(R.id.mService_Des);
            textViewPrice = itemView.findViewById(R.id.mService_Price);
            textViewDiscount = itemView.findViewById(R.id.mService_Discount);
            textViewStart_Time = itemView.findViewById(R.id.mStart_Time);
            textViewEnd_Time = itemView.findViewById(R.id.mEnd_Time);
        }
    }
}
