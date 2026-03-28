package com.example.havenhub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.adapter.DormitoryAdapter;
import com.example.havenhub.database.Dormitory;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.dialog.DormitoryDialog;
import com.example.havenhub.utils.DatabaseAsyncTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class suSeManegerActivity extends AppCompatActivity implements DormitoryAdapter.OnDormitoryClickListener {

    private RecyclerView mRecyclerView;
    private DormitoryAdapter mAdapter;
    private ArrayList<Dormitory> mDormitoryList;
    private SQlite mSQlite;
    private FloatingActionButton mFabAdd;
    private TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_su_se_maneger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initData();
        initListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = findViewById(R.id.recyclerView_dormitories);
        mFabAdd = findViewById(R.id.fab_add);
        mEmptyText = findViewById(R.id.empty_text);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDormitoryList = new ArrayList<>();
        mAdapter = new DormitoryAdapter(this, mDormitoryList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mSQlite = new SQlite(this);
        loadDormitories();
    }

    private void initListeners() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDormitoryDialog();
            }
        });
    }

    private void loadDormitories() {
        new DatabaseAsyncTask<Void, Void, ArrayList<Dormitory>>() {
            @Override
            protected ArrayList<Dormitory> doInBackground(Void... voids) {
                return mSQlite.getAllDormitories();
            }

            @Override
            protected void onPostExecute(ArrayList<Dormitory> dormitories) {
                super.onPostExecute(dormitories);
                mDormitoryList.clear();
                mDormitoryList.addAll(dormitories);
                mAdapter.notifyDataSetChanged();
                updateEmptyState();
            }
        }.execute();
    }

    private void showAddDormitoryDialog() {
        DormitoryDialog dialog = new DormitoryDialog(this, new DormitoryDialog.OnDormitorySaveListener() {
            @Override
            public void onSave(Dormitory dormitory) {
                addDormitory(dormitory);
            }
        });
        dialog.show();
    }

    private void addDormitory(Dormitory dormitory) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.addDormitory(dormitory.getDormitoryId(), dormitory.getBuilding(), dormitory.getFloor(),
                            dormitory.getRoomNumber(), dormitory.getBedCount(), dormitory.getOccupiedCount(),
                            dormitory.getStatus(), dormitory.getDescription());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Toast.makeText(suSeManegerActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    loadDormitories();
                } else {
                    Toast.makeText(suSeManegerActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showEditDormitoryDialog(Dormitory dormitory) {
        DormitoryDialog dialog = new DormitoryDialog(this, dormitory, new DormitoryDialog.OnDormitorySaveListener() {
            @Override
            public void onSave(Dormitory updatedDormitory) {
                updateDormitory(updatedDormitory);
            }
        });
        dialog.show();
    }

    private void updateDormitory(Dormitory dormitory) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.updateDormitory(dormitory.getDormitoryId(), dormitory.getBuilding(), dormitory.getFloor(),
                            dormitory.getRoomNumber(), dormitory.getBedCount(), dormitory.getOccupiedCount(),
                            dormitory.getStatus(), dormitory.getDescription());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    Toast.makeText(suSeManegerActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    loadDormitories();
                } else {
                    Toast.makeText(suSeManegerActivity.this, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void deleteDormitory(Dormitory dormitory) {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除宿舍 " + dormitory.getDormitoryId() + " 吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    new DatabaseAsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                mSQlite.deleteDormitory(dormitory.getDormitoryId());
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean success) {
                            super.onPostExecute(success);
                            if (success) {
                                Toast.makeText(suSeManegerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                loadDormitories();
                            } else {
                                Toast.makeText(suSeManegerActivity.this, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void updateEmptyState() {
        if (mDormitoryList.isEmpty()) {
            mEmptyText.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEditClick(Dormitory dormitory) {
        showEditDormitoryDialog(dormitory);
    }

    @Override
    public void onDeleteClick(Dormitory dormitory) {
        deleteDormitory(dormitory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSQlite != null) {
            mSQlite.close();
        }
    }
}