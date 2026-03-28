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
import com.example.havenhub.database.Dormitory;

public class DormitoryDialog extends Dialog {
    private EditText etDormitoryId, etBuilding, etFloor, etRoomNumber, etBedCount, etOccupiedCount, etDescription;
    private Spinner spStatus;
    private Button btnSave, btnCancel;
    private OnDormitorySaveListener mListener;
    private Dormitory mDormitory;

    public interface OnDormitorySaveListener {
        void onSave(Dormitory dormitory);
    }

    public DormitoryDialog(@NonNull Context context, OnDormitorySaveListener listener) {
        super(context);
        mListener = listener;
    }

    public DormitoryDialog(@NonNull Context context, Dormitory dormitory, OnDormitorySaveListener listener) {
        super(context);
        mDormitory = dormitory;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dormitory, null);
        setContentView(view);

        initViews(view);
        initData();
        initListeners();
    }

    private void initViews(View view) {
        etDormitoryId = view.findViewById(R.id.et_dormitory_id);
        etBuilding = view.findViewById(R.id.et_building);
        etFloor = view.findViewById(R.id.et_floor);
        etRoomNumber = view.findViewById(R.id.et_room_number);
        etBedCount = view.findViewById(R.id.et_bed_count);
        etOccupiedCount = view.findViewById(R.id.et_occupied_count);
        spStatus = view.findViewById(R.id.sp_status);
        etDescription = view.findViewById(R.id.et_description);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    private void initData() {
        if (mDormitory != null) {
            etDormitoryId.setText(mDormitory.getDormitoryId());
            etDormitoryId.setEnabled(false); // 宿舍号不可编辑
            etBuilding.setText(mDormitory.getBuilding());
            etFloor.setText(String.valueOf(mDormitory.getFloor()));
            etRoomNumber.setText(mDormitory.getRoomNumber());
            etBedCount.setText(String.valueOf(mDormitory.getBedCount()));
            etOccupiedCount.setText(String.valueOf(mDormitory.getOccupiedCount()));
            
            // 设置状态选择
            String status = mDormitory.getStatus();
            if ("空闲".equals(status)) {
                spStatus.setSelection(0);
            } else if ("已分配".equals(status)) {
                spStatus.setSelection(1);
            } else if ("维修中".equals(status)) {
                spStatus.setSelection(2);
            }
            
            etDescription.setText(mDormitory.getDescription());
        }
    }

    private void initListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dormitoryId = etDormitoryId.getText().toString().trim();
                String building = etBuilding.getText().toString().trim();
                String floorStr = etFloor.getText().toString().trim();
                String roomNumber = etRoomNumber.getText().toString().trim();
                String bedCountStr = etBedCount.getText().toString().trim();
                String occupiedCountStr = etOccupiedCount.getText().toString().trim();
                String status = spStatus.getSelectedItem().toString();
                String description = etDescription.getText().toString().trim();

                if (TextUtils.isEmpty(dormitoryId) || TextUtils.isEmpty(building) || TextUtils.isEmpty(floorStr) ||
                        TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(bedCountStr) || TextUtils.isEmpty(occupiedCountStr)) {
                    Toast.makeText(getContext(), "请填写所有必填项", Toast.LENGTH_SHORT).show();
                    return;
                }

                int floor = Integer.parseInt(floorStr);
                int bedCount = Integer.parseInt(bedCountStr);
                int occupiedCount = Integer.parseInt(occupiedCountStr);

                Dormitory dormitory = new Dormitory(dormitoryId, building, floor, roomNumber, bedCount, occupiedCount, status, description);
                mListener.onSave(dormitory);
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