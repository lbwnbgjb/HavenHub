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

import com.example.havenhub.adapter.VisitorAdapter;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.database.Visitor;
import com.example.havenhub.dialog.VisitorDialog;
import com.example.havenhub.utils.DatabaseAsyncTask;

import java.util.ArrayList;

public class visitorManegerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VisitorAdapter adapter;
    private SQlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visitor_maneger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQlite(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VisitorDialog(visitorManegerActivity.this, null, new VisitorDialog.OnVisitorSavedListener() {
                    @Override
                    public void onVisitorSaved() {
                        loadVisitorData();
                    }
                }).show();
            }
        });

        loadVisitorData();
    }

    private void loadVisitorData() {
        new DatabaseAsyncTask<Void, Void, ArrayList<Visitor>>() {
            @Override
            protected ArrayList<Visitor> doInBackground(Void... voids) {
                return db.getAllVisitors();
            }

            @Override
            protected void onPostExecute(ArrayList<Visitor> visitors) {
                super.onPostExecute(visitors);
                adapter = new VisitorAdapter(visitorManegerActivity.this, visitors, new VisitorAdapter.OnVisitorClickListener() {
                    @Override
                    public void onEdit(Visitor visitor) {
                        new VisitorDialog(visitorManegerActivity.this, visitor, new VisitorDialog.OnVisitorSavedListener() {
                            @Override
                            public void onVisitorSaved() {
                                loadVisitorData();
                            }
                        }).show();
                    }

                    @Override
                    public void onDelete(int id) {
                        new DatabaseAsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                db.deleteVisitor(id);
                                return true;
                            }

                            @Override
                            protected void onPostExecute(Boolean success) {
                                super.onPostExecute(success);
                                if (success) {
                                    Toast.makeText(visitorManegerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    loadVisitorData();
                                } else {
                                    Toast.makeText(visitorManegerActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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