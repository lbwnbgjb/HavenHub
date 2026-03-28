package com.example.havenhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.havenhub.R;
import com.example.havenhub.database.Student;

public class StudentDialog extends Dialog {
    private EditText etStudentId, etName, etAge, etDepartment, etMajor, etDormitory, etPhone, etEmail;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnSave, btnCancel;
    private OnStudentSaveListener mListener;
    private Student mStudent;

    public interface OnStudentSaveListener {
        void onSave(Student student);
    }

    public StudentDialog(@NonNull Context context, OnStudentSaveListener listener) {
        super(context);
        mListener = listener;
    }

    public StudentDialog(@NonNull Context context, Student student, OnStudentSaveListener listener) {
        super(context);
        mStudent = student;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_student, null);
        setContentView(view);

        initViews(view);
        initData();
        initListeners();
    }

    private void initViews(View view) {
        etStudentId = view.findViewById(R.id.et_student_id);
        etName = view.findViewById(R.id.et_name);
        rgGender = view.findViewById(R.id.rg_gender);
        rbMale = view.findViewById(R.id.rb_male);
        rbFemale = view.findViewById(R.id.rb_female);
        etAge = view.findViewById(R.id.et_age);
        etDepartment = view.findViewById(R.id.et_department);
        etMajor = view.findViewById(R.id.et_major);
        etDormitory = view.findViewById(R.id.et_dormitory);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void initData() {
        if (mStudent != null) {
            etStudentId.setText(mStudent.getStudentId());
            etStudentId.setEnabled(false); // 学号不可编辑
            etName.setText(mStudent.getName());
            if ("男".equals(mStudent.getGender())) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }
            etAge.setText(String.valueOf(mStudent.getAge()));
            etDepartment.setText(mStudent.getDepartment());
            etMajor.setText(mStudent.getMajor());
            etDormitory.setText(mStudent.getDormitory());
            etPhone.setText(mStudent.getPhone());
            etEmail.setText(mStudent.getEmail());
        }
    }

    private void initListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = etStudentId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String gender = rbMale.isChecked() ? "男" : "女";
                String ageStr = etAge.getText().toString().trim();
                String department = etDepartment.getText().toString().trim();
                String major = etMajor.getText().toString().trim();
                String dormitory = etDormitory.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(studentId) || TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr)) {
                    Toast.makeText(getContext(), "学号、姓名和年龄不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageStr);
                Student student = new Student(studentId, name, gender, age, department, major, dormitory, phone, email);
                mListener.onSave(student);
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