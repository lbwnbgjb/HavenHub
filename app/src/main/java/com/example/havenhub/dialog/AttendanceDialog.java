package com.example.havenhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.havenhub.R;
import com.example.havenhub.database.Attendance;

public class AttendanceDialog extends Dialog {
    private EditText etStudentId, etDate, etRemark;
    private Spinner spStatus;
    private Button btnSave, btnCancel;
    private OnAttendanceSaveListener mListener;
    private Attendance mAttendance;
    private boolean isEditMode;

    public interface OnAttendanceSaveListener {
        void onSave(Attendance attendance);
    }

    public AttendanceDialog(@NonNull Context context, OnAttendanceSaveListener listener) {
        super(context);
        mListener = listener;
        isEditMode = false;
    }

    public AttendanceDialog(@NonNull Context context, Attendance attendance, OnAttendanceSaveListener listener) {
        super(context);
        mAttendance = attendance;
        mListener = listener;
        isEditMode = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_attendance, null);
        setContentView(view);

        initViews(view);
        initData();
        initListeners();
    }

    private void initViews(View view) {
        etStudentId = view.findViewById(R.id.et_student_id);
        etDate = view.findViewById(R.id.et_date);
        spStatus = view.findViewById(R.id.sp_status);
        etRemark = view.findViewById(R.id.et_remark);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void initData() {
        if (mAttendance != null) {
            etStudentId.setText(mAttendance.getStudentId());
            etStudentId.setEnabled(false); // 学号不可编辑
            etDate.setText(mAttendance.getDate());
            etDate.setEnabled(false); // 日期不可编辑
            
            // 设置状态选择
            String status = mAttendance.getStatus();
            if ("出勤".equals(status)) {
                spStatus.setSelection(0);
            } else if ("缺勤".equals(status)) {
                spStatus.setSelection(1);
            } else if ("迟到".equals(status)) {
                spStatus.setSelection(2);
            } else if ("早退".equals(status)) {
                spStatus.setSelection(3);
            }
            
            etRemark.setText(mAttendance.getRemark());
        }
    }

    private void initListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = etStudentId.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String status = spStatus.getSelectedItem().toString();
                String remark = etRemark.getText().toString().trim();

                if (TextUtils.isEmpty(studentId) || TextUtils.isEmpty(date)) {
                    Toast.makeText(getContext(), "学号和日期不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Attendance attendance;
                if (isEditMode && mAttendance != null) {
                    attendance = new Attendance(mAttendance.getId(), studentId, date, status, remark);
                } else {
                    attendance = new Attendance(0, studentId, date, status, remark);
                }
                mListener.onSave(attendance);
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}