package com.example.transporteationtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporteationtask.R;
import com.example.transporteationtask.local.Constants;
import com.example.transporteationtask.models.Transport;

import java.util.ArrayList;
import java.util.List;

public class TransportationsAdapter extends RecyclerView.Adapter<TransportationViewHolder> {

    private final Context context;
    private final List<Transport> transportsList = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    OnItemClickListener mOnClickListener;

    public TransportationsAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransportationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransportationViewHolder(context, layoutInflater.inflate(R.layout.rv_item_transportation,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransportationViewHolder holder, int position) {
        Transport transport = transportsList.get(position);

        holder.setData(transport, context, position);
        holder.setOnClickListener(new TransportationViewHolder.OnClickListener() {
            @Override
            public void onItemClick(int position, View view, Transport transport) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(position, view, transport, holder);
                }
            }
        });
    }

    public int getItemCount() {
        return transportsList.size();
    }

    public void clearAll() {
        transportsList.clear();
        notifyDataSetChanged();
    }

    public void addAllItems(List<Transport> transports) {
        transportsList.addAll(transports);
        notifyDataSetChanged();
    }

    public Transport getItem(int position) {
        return transportsList.get(position);
    }

    public void onBuildingStarted(int position) {
        transportsList.get(position).setState(Constants.STATUS_BUILDING);
        notifyItemChanged(position);
    }

    public void onBuildingCompleted(int position) {
        transportsList.get(position).setState(Constants.STATUS_ACTIVE);
        notifyItemChanged(position);
    }

    public void onTick() {
        notifyDataSetChanged();
    }

    public void onTick(int position) {
        notifyItemChanged(position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view, Transport transport, RecyclerView.ViewHolder holder);
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        mOnClickListener = onItemClickListener;
    }
}
