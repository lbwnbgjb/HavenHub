package com.example.havenhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.R;
import com.example.havenhub.modle.RoomMate;

import java.util.List;

public class RoomMateAdapter extends RecyclerView.Adapter<RoomMateAdapter.ViewHolder> {

    private List<RoomMate> contactList;
    private Context context;


public interface OnItemClickListener {
    void onItemClick(RoomMate contact, int position);
    void onCallClick(RoomMate contact, int position);
    void onFavoriteClick(RoomMate contact, int position);
}

public RoomMateAdapter(Context context, List<RoomMate> contactList, OnItemClickListener listener) {
    this.context = context;
    this.contactList = contactList;
}

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.roommate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomMateAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}