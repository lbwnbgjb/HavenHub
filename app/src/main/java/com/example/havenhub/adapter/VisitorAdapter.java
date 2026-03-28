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
import com.example.havenhub.database.Visitor;

import java.util.List;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {

    private Context context;
    private List<Visitor> visitorList;
    private OnVisitorClickListener listener;

    public interface OnVisitorClickListener {
        void onEdit(Visitor visitor);
        void onDelete(int id);
    }

    public VisitorAdapter(Context context, List<Visitor> visitorList, OnVisitorClickListener listener) {
        this.context = context;
        this.visitorList = visitorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_visitor, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor visitor = visitorList.get(position);
        holder.tvName.setText(visitor.getName());
        holder.tvIdCard.setText(visitor.getIdCard());
        holder.tvPhone.setText(visitor.getPhone());
        holder.tvVisitDate.setText(visitor.getVisitDate());
        holder.tvVisitTime.setText(visitor.getVisitTime());
        holder.tvVisitReason.setText(visitor.getVisitReason());
        holder.tvRoomNumber.setText(visitor.getRoomNumber());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEdit(visitor);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDelete(visitor.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvIdCard, tvPhone, tvVisitDate, tvVisitTime, tvVisitReason, tvRoomNumber;
        Button btnEdit, btnDelete;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvIdCard = itemView.findViewById(R.id.tv_id_card);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvVisitDate = itemView.findViewById(R.id.tv_visit_date);
            tvVisitTime = itemView.findViewById(R.id.tv_visit_time);
            tvVisitReason = itemView.findViewById(R.id.tv_visit_reason);
            tvRoomNumber = itemView.findViewById(R.id.tv_room_number);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}