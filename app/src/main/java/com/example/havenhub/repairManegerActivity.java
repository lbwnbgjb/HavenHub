package com.example.havenhub;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.adapter.RepairAdapter;
import com.example.havenhub.database.Repair;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.dialog.RepairDialog;
import com.example.havenhub.utils.DatabaseAsyncTask;

import java.util.ArrayList;

public class repairManegerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RepairAdapter adapter;
    private SQlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_repair_maneger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQlite(this);
        recyclerView = findViewById(R.id.recyclerView_repair);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RepairDialog(repairManegerActivity.this, null, new RepairDialog.OnRepairSavedListener() {
                    @Override
                    public void onRepairSaved() {
                        loadRepairData();
                    }
                }).show();
            }
        });

        loadRepairData();
    }

    private void loadRepairData() {
        new DatabaseAsyncTask<Void, Void, ArrayList<Repair>>() {
            @Override
            protected ArrayList<Repair> doInBackground(Void... voids) {
                return db.getAllRepair();
            }

            @Override
            protected void onPostExecute(ArrayList<Repair> repairs) {
                super.onPostExecute(repairs);
                adapter = new RepairAdapter(repairManegerActivity.this, repairs, new RepairAdapter.OnRepairClickListener() {
                    @Override
                    public void onEditClick(Repair repair) {
                        new RepairDialog(repairManegerActivity.this, repair, new RepairDialog.OnRepairSavedListener() {
                            @Override
                            public void onRepairSaved() {
                                loadRepairData();
                            }
                        }).show();
                    }

                    @Override
                    public void onDeleteClick(Repair repair) {
                        new DatabaseAsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                db.deleteRepair(repair.getId());
                                return true;
                            }

                            @Override
                            protected void onPostExecute(Boolean success) {
                                super.onPostExecute(success);
                                if (success) {
                                    Toast.makeText(repairManegerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    loadRepairData();
                                } else {
                                    Toast.makeText(repairManegerActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute();
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }
}