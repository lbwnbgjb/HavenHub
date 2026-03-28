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
import com.example.havenhub.database.Attendance;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    private Context mContext;
    private List<Attendance> mAttendanceList;
    private OnAttendanceClickListener mListener;

    public interface OnAttendanceClickListener {
        void onEditClick(Attendance attendance);
        void onDeleteClick(Attendance attendance);
    }

    public AttendanceAdapter(Context context, List<Attendance> attendanceList, OnAttendanceClickListener listener) {
        mContext = context;
        mAttendanceList = attendanceList;
        mListener = listener;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_attendance, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        Attendance attendance = mAttendanceList.get(position);
        holder.textStudentId.setText("学号: " + attendance.getStudentId());
        holder.textStudentName.setText("姓名: " + (attendance.getStudentName() != null ? attendance.getStudentName() : "未知"));
        holder.textDate.setText("日期: " + attendance.getDate());
        holder.textStatus.setText("状态: " + attendance.getStatus());
        holder.textRemark.setText("备注: " + (attendance.getRemark() != null ? attendance.getRemark() : "无"));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(attendance);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteClick(attendance);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAttendanceList.size();
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        mAttendanceList = attendanceList;
        notifyDataSetChanged();
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        TextView textStudentId, textStudentName, textDate, textStatus, textRemark;
        Button btnEdit, btnDelete;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            textStudentId = itemView.findViewById(R.id.text_student_id);
            textStudentName = itemView.findViewById(R.id.text_student_name);
            textDate = itemView.findViewById(R.id.text_date);
            textStatus = itemView.findViewById(R.id.text_status);
            textRemark = itemView.findViewById(R.id.text_remark);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}