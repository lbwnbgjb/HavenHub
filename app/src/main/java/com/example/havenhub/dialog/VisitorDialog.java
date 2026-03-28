package com.example.havenhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.havenhub.R;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.database.Visitor;
import com.example.havenhub.utils.DatabaseAsyncTask;

public class VisitorDialog extends Dialog {

    private EditText etName, etIdCard, etPhone, etVisitDate, etVisitTime, etVisitReason, etRoomNumber;
    private Button btnSave, btnCancel;
    private Visitor visitor;
    private OnVisitorSavedListener listener;
    private SQlite db;

    public interface OnVisitorSavedListener {
        void onVisitorSaved();
    }

    public VisitorDialog(Context context, Visitor visitor, OnVisitorSavedListener listener) {
        super(context);
        this.visitor = visitor;
        this.listener = listener;
        db = new SQlite(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_visitor);

        etName = findViewById(R.id.et_name);
        etIdCard = findViewById(R.id.et_id_card);
        etPhone = findViewById(R.id.et_phone);
        etVisitDate = findViewById(R.id.et_visit_date);
        etVisitTime = findViewById(R.id.et_visit_time);
        etVisitReason = findViewById(R.id.et_visit_reason);
        etRoomNumber = findViewById(R.id.et_room_number);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        if (visitor != null) {
            etName.setText(visitor.getName());
            etIdCard.setText(visitor.getIdCard());
            etPhone.setText(visitor.getPhone());
            etVisitDate.setText(visitor.getVisitDate());
            etVisitTime.setText(visitor.getVisitTime());
            etVisitReason.setText(visitor.getVisitReason());
            etRoomNumber.setText(visitor.getRoomNumber());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVisitor();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void saveVisitor() {
        String name = etName.getText().toString().trim();
        String idCard = etIdCard.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String visitDate = etVisitDate.getText().toString().trim();
        String visitTime = etVisitTime.getText().toString().trim();
        String visitReason = etVisitReason.getText().toString().trim();
        String roomNumber = etRoomNumber.getText().toString().trim();

        if (name.isEmpty() || idCard.isEmpty() || phone.isEmpty() || visitDate.isEmpty() || visitTime.isEmpty() || visitReason.isEmpty() || roomNumber.isEmpty()) {
            Toast.makeText(getContext(), "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        final Visitor newVisitor = new Visitor();
        newVisitor.setName(name);
        newVisitor.setIdCard(idCard);
        newVisitor.setPhone(phone);
        newVisitor.setVisitDate(visitDate);
        newVisitor.setVisitTime(visitTime);
        newVisitor.setVisitReason(visitReason);
        newVisitor.setRoomNumber(roomNumber);

        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (visitor == null) {
                    db.addVisitor(newVisitor.getName(), newVisitor.getIdCard(), newVisitor.getPhone(),
                            newVisitor.getVisitDate(), newVisitor.getVisitTime(), newVisitor.getVisitReason(), newVisitor.getRoomNumber());
                    return true;
                } else {
                    newVisitor.setId(visitor.getId());
                    db.updateVisitor(newVisitor.getId(), newVisitor.getName(), newVisitor.getIdCard(), newVisitor.getPhone(),
                            newVisitor.getVisitDate(), newVisitor.getVisitTime(), newVisitor.getVisitReason(), newVisitor.getRoomNumber());
                    return true;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onVisitorSaved();
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}