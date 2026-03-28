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
import com.example.havenhub.database.Repair;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {
    private Context mContext;
    private List<Repair> mRepairList;
    private OnRepairClickListener mListener;

    public interface OnRepairClickListener {
        void onEditClick(Repair repair);
        void onDeleteClick(Repair repair);
    }

    public RepairAdapter(Context context, List<Repair> repairList, OnRepairClickListener listener) {
        mContext = context;
        mRepairList = repairList;
        mListener = listener;
    }

    @NonNull
    @Override
    public RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_repair, parent, false);
        return new RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairViewHolder holder, int position) {
        Repair repair = mRepairList.get(position);
        holder.textStudentId.setText("学号: " + repair.getStudentId());
        holder.textStudentName.setText("姓名: " + (repair.getStudentName() != null ? repair.getStudentName() : "未知"));
        holder.textDormitoryId.setText("宿舍: " + repair.getDormitoryId());
        holder.textDate.setText("日期: " + repair.getDate());
        holder.textType.setText("类型: " + repair.getType());
        holder.textStatus.setText("状态: " + repair.getStatus());
        holder.textHandler.setText("处理人: " + (repair.getHandler() != null ? repair.getHandler() : "未处理"));
        holder.textHandleDate.setText("处理日期: " + (repair.getHandleDate() != null ? repair.getHandleDate() : "未处理"));
        holder.textDescriptionContent.setText(repair.getDescription());
        holder.textRemarkContent.setText(repair.getRemark() != null ? repair.getRemark() : "无");

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(repair);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteClick(repair);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepairList.size();
    }

    public void setRepairList(List<Repair> repairList) {
        mRepairList = repairList;
        notifyDataSetChanged();
    }

    public static class RepairViewHolder extends RecyclerView.ViewHolder {
        TextView textStudentId, textStudentName, textDormitoryId, textDate, textType, textStatus, textHandler, textHandleDate, textDescriptionContent, textRemarkContent;
        Button btnEdit, btnDelete;

        public RepairViewHolder(@NonNull View itemView) {
            super(itemView);
            textStudentId = itemView.findViewById(R.id.text_student_id);
            textStudentName = itemView.findViewById(R.id.text_student_name);
            textDormitoryId = itemView.findViewById(R.id.text_dormitory_id);
            textDate = itemView.findViewById(R.id.text_date);
            textType = itemView.findViewById(R.id.text_type);
            textStatus = itemView.findViewById(R.id.text_status);
            textHandler = itemView.findViewById(R.id.text_handler);
            textHandleDate = itemView.findViewById(R.id.text_handle_date);
            textDescriptionContent = itemView.findViewById(R.id.text_description_content);
            textRemarkContent = itemView.findViewById(R.id.text_remark_content);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}