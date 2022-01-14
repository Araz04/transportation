package com.example.transporteationtask.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.transporteationtask.R;
import com.example.transporteationtask.local.Constants;
import com.example.transporteationtask.models.Transport;

public class TransportationViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout constraintLayout;
    TextView tvName;
    TextView tvPrice;
    TextView tvType;
    ImageView ivTransportImage;
    TextView tvPoints;
    ImageView ivArrow;
    OnClickListener mOnClickListener;
    private final Context context;

    TransportationViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    public void initView(View view) {
        constraintLayout = view.findViewById(R.id.container_rv_item_transportation);
        tvName = view.findViewById(R.id.tv_item_transportation_name);
        tvPrice = view.findViewById(R.id.tv_item_transportation_price);
        ivTransportImage = view.findViewById(R.id.iv_item_transportation_cover);
        tvType = view.findViewById(R.id.tv_item_transportation_type);
        tvPoints = view.findViewById(R.id.tv_item_transportation_point);
        ivArrow = view.findViewById(R.id.iv_item_transportation_arrow);
    }

    public void setData(Transport transport, Context context, int position) {
        Glide.with(context).
                load(transport.getImageUrl()).
                centerCrop().
                placeholder(R.drawable.image_taxi).
                into(ivTransportImage);

        if (transport.getName() != null) {
            tvName.setText(transport.getName());
        }

        if (transport.getPrice() != null) {
            tvPrice.setText(String.format(context.getString(R.string.usd), transport.getPrice()));
        }

        if (transport.getType() != null) {
            tvType.setText(transport.getType() );
        }

        if (transport.getType() != null) {
            tvPoints.setText(String.valueOf(transport.getPoints()));
        }

        switch (transport.getState()) {
            case Constants.STATUS_BUILDING:
                setBuildingStatus(transport);
                break;
            case Constants.STATUS_ACTIVE:
            default:
                setNormalStatus(transport);
        }

        initClickListeners(transport);
    }

    public void setNormalStatus(Transport transport){
        tvPrice.setText(String.format(context.getString(R.string.usd), transport.getPrice()));
        tvPrice.setTextColor(ContextCompat.getColor(context, R.color.text_color_gray_title));
        ivArrow.setVisibility(View.VISIBLE);
        tvPoints.setVisibility(View.VISIBLE);
        tvPoints.setText(String.valueOf(transport.getPoints()));
        ivTransportImage.setAlpha(1.0F);
        tvType.setText(transport.getType());
    }

    public void setBuildingStatus(Transport transport){
        tvPrice.setText(R.string.building);
        tvPrice.setTextColor(ContextCompat.getColor(context, R.color.text_color_red_dark));
        ivArrow.setVisibility(View.GONE);
        tvPoints.setVisibility(View.GONE);
        ivTransportImage.setAlpha(0.1F);
        long milliseconds = transport.getBuildingTime() - (System.currentTimeMillis() - transport.getCreatedAt());
        tvType.setText(millisecondsToText(milliseconds));
    }

    public void initClickListeners(Transport transport) {
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(getAdapterPosition(), constraintLayout, transport);
                }
            }
        });
    }

    private String millisecondsToText(long milliseconds){
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        return minutes + " min, " + seconds % 60 + " sec";
    }

    public interface OnClickListener {
        void onItemClick(int position, View view, Transport transport);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
