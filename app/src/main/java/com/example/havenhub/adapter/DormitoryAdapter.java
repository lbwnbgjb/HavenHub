package com.example.havenhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.R;
import com.example.havenhub.database.Dormitory;

import java.util.List;

public class DormitoryAdapter extends RecyclerView.Adapter<DormitoryAdapter.DormitoryViewHolder> {
    private Context mContext;
    private List<Dormitory> mDormitoryList;
    private OnDormitoryClickListener mListener;

    public interface OnDormitoryClickListener {
        void onEditClick(Dormitory dormitory);
        void onDeleteClick(Dormitory dormitory);
    }

    public DormitoryAdapter(Context context, List<Dormitory> dormitoryList, OnDormitoryClickListener listener) {
        mContext = context;
        mDormitoryList = dormitoryList;
        mListener = listener;
    }

    @NonNull
    @Override
    public DormitoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dormitory, parent, false);
        return new DormitoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DormitoryViewHolder holder, int position) {
        Dormitory dormitory = mDormitoryList.get(position);
        holder.textDormitoryId.setText("宿舍号: " + dormitory.getDormitoryId());
        holder.textBuilding.setText("楼栋: " + dormitory.getBuilding());
        holder.textFloor.setText("楼层: " + dormitory.getFloor());
        holder.textRoomNumber.setText("房间号: " + dormitory.getRoomNumber());
        holder.textBedCount.setText("床位总数: " + dormitory.getBedCount());
        holder.textOccupiedCount.setText("已占用: " + dormitory.getOccupiedCount());
        holder.textStatus.setText("状态: " + dormitory.getStatus());
        holder.textDescription.setText("描述: " + (dormitory.getDescription() != null ? dormitory.getDescription() : "无"));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(dormitory);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteClick(dormitory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDormitoryList.size();
    }

    public void setDormitoryList(List<Dormitory> dormitoryList) {
        mDormitoryList = dormitoryList;
        notifyDataSetChanged();
    }

    public static class DormitoryViewHolder extends RecyclerView.ViewHolder {
        TextView textDormitoryId, textBuilding, textFloor, textRoomNumber, textBedCount, textOccupiedCount, textStatus, textDescription;
        Button btnEdit, btnDelete;

        public DormitoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textDormitoryId = itemView.findViewById(R.id.text_dormitory_id);
            textBuilding = itemView.findViewById(R.id.text_building);
            textFloor = itemView.findViewById(R.id.text_floor);
            textRoomNumber = itemView.findViewById(R.id.text_room_number);
            textBedCount = itemView.findViewById(R.id.text_bed_count);
            textOccupiedCount = itemView.findViewById(R.id.text_occupied_count);
            textStatus = itemView.findViewById(R.id.text_status);
            textDescription = itemView.findViewById(R.id.text_description);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}