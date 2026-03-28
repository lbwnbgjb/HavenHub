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
import com.example.havenhub.database.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context mContext;
    private List<Student> mStudentList;
    private OnStudentClickListener mListener;

    public interface OnStudentClickListener {
        void onEditClick(Student student);
        void onDeleteClick(Student student);
    }

    public StudentAdapter(Context context, List<Student> studentList, OnStudentClickListener listener) {
        mContext = context;
        mStudentList = studentList;
        mListener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.textStudentId.setText("学号: " + student.getStudentId());
        holder.textName.setText("姓名: " + student.getName());
        holder.textGender.setText("性别: " + student.getGender());
        holder.textAge.setText("年龄: " + student.getAge());
        holder.textDepartment.setText("系别: " + student.getDepartment());
        holder.textMajor.setText("专业: " + student.getMajor());
        holder.textDormitory.setText("宿舍: " + student.getDormitory());
        holder.textPhone.setText("电话: " + student.getPhone());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(student);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteClick(student);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public void setStudentList(List<Student> studentList) {
        mStudentList = studentList;
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView textStudentId, textName, textGender, textAge, textDepartment, textMajor, textDormitory, textPhone;
        Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textStudentId = itemView.findViewById(R.id.text_student_id);
            textName = itemView.findViewById(R.id.text_name);
            textGender = itemView.findViewById(R.id.text_gender);
            textAge = itemView.findViewById(R.id.text_age);
            textDepartment = itemView.findViewById(R.id.text_department);
            textMajor = itemView.findViewById(R.id.text_major);
            textDormitory = itemView.findViewById(R.id.text_dormitory);
            textPhone = itemView.findViewById(R.id.text_phone);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}