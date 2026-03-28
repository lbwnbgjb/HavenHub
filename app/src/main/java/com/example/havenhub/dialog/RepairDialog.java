package com.example.havenhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.havenhub.R;
import com.example.havenhub.database.Repair;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.utils.DatabaseAsyncTask;

public class RepairDialog extends Dialog {

    private EditText etStudentId, etRoomNumber, etDescription;
    private Spinner spStatus;
    private Button btnSave, btnCancel;
    private Repair repair;
    private OnRepairSavedListener listener;
    private SQlite db;

    public interface OnRepairSavedListener {
        void onRepairSaved();
    }

    public RepairDialog(Context context, Repair repair, OnRepairSavedListener listener) {
        super(context);
        this.repair = repair;
        this.listener = listener;
        db = new SQlite(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_repair);

        etStudentId = findViewById(R.id.et_student_id);
        etRoomNumber = findViewById(R.id.et_room_number);
        etDescription = findViewById(R.id.et_description);
        spStatus = findViewById(R.id.sp_status);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.repair_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);

        if (repair != null) {
            etStudentId.setText(repair.getStudentId());
            etRoomNumber.setText(repair.getDormitoryId());
            etDescription.setText(repair.getDescription());
            spStatus.setSelection(getStatusPosition(repair.getStatus()));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRepair();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int getStatusPosition(String status) {
        String[] statusArray = getContext().getResources().getStringArray(R.array.repair_status);
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i].equals(status)) {
                return i;
            }
        }
        return 0;
    }

    private void saveRepair() {
        String studentId = etStudentId.getText().toString().trim();
        String roomNumber = etRoomNumber.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String status = spStatus.getSelectedItem().toString();

        if (studentId.isEmpty() || roomNumber.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (repair == null) {
                    db.addRepair(studentId, roomNumber, "", "", description, status);
                } else {
                    db.updateRepair(repair.getId(), status, "", "", "");
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onRepairSaved();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}