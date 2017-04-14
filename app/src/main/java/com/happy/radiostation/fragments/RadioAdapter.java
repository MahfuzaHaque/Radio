package com.happy.radiostation.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.radiostation.R;
import com.happy.radiostation.data.RadioData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> {

    private ArrayList<RadioData> datas;
    private RadioData playData;
    private Boolean isPlay = false;

    public RadioAdapter() {
        datas = new ArrayList<>();
    }

    public void setDatas(ArrayList<RadioData> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void setPlayItem(RadioData playData, boolean isPlay) {
        this.playData = playData;
        this.isPlay = isPlay;
        notifyDataSetChanged();
    }

    public RadioData getItem(int position) {
        return datas.get(position);
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_radio, parent, false);
        RadioViewHolder viewHolder = new RadioViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RadioViewHolder holder, int position) {
        RadioData data = datas.get(position);

        holder.nameTextView.setText(data.getName());
        holder.typeTextView.setText(data.getType());

        if (isPlay && compareData(data)) {
            holder.playImageView.setVisibility(View.VISIBLE);

        } else {
            holder.playImageView.setVisibility(View.GONE);
        }

        if (data.isFavourite()) {
            holder.favImageView.setVisibility(View.VISIBLE);
        } else {
            holder.favImageView.setVisibility(View.GONE);
        }
    }

    private boolean compareData(RadioData data) {
        if (playData != null) {
            if (playData.getName().equals(data.getName()) && playData.getType().equals(data.getType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RadioViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_text_view)
        TextView nameTextView;
        @BindView(R.id.playImageView)
        ImageView playImageView;
        @BindView(R.id.favImageView)
        ImageView favImageView;
        @BindView(R.id.type_text_view)
        TextView typeTextView;

        public RadioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
