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

import com.example.havenhub.adapter.AttendanceAdapter;
import com.example.havenhub.database.Attendance;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.database.Student;
import com.example.havenhub.dialog.AttendanceDialog;
import com.example.havenhub.utils.DatabaseAsyncTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class kaoQinManegerActivity extends AppCompatActivity implements AttendanceAdapter.OnAttendanceClickListener {

    private RecyclerView mRecyclerView;
    private AttendanceAdapter mAdapter;
    private ArrayList<Attendance> mAttendanceList;
    private SQlite mSQlite;
    private FloatingActionButton mFabAdd;
    private TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kao_qin_maneger);
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

        mRecyclerView = findViewById(R.id.recyclerView_attendance);
        mFabAdd = findViewById(R.id.fab_add);
        mEmptyText = findViewById(R.id.empty_text);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAttendanceList = new ArrayList<>();
        mAdapter = new AttendanceAdapter(this, mAttendanceList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mSQlite = new SQlite(this);
        loadAttendance();
    }

    private void initListeners() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAttendanceDialog();
            }
        });
    }

    private void loadAttendance() {
        new DatabaseAsyncTask<Void, Void, ArrayList<Attendance>>() {
            @Override
            protected ArrayList<Attendance> doInBackground(Void... voids) {
                ArrayList<Attendance> attendanceList = mSQlite.getAllAttendance();
                // 为每个考勤记录添加学生姓名
                for (Attendance attendance : attendanceList) {
                    ArrayList<Student> students = mSQlite.getAllStudents();
                    for (Student student : students) {
                        if (student.getStudentId().equals(attendance.getStudentId())) {
                            attendance.setStudentName(student.getName());
                            break;
                        }
                    }
                }
                return attendanceList;
            }

            @Override
            protected void onPostExecute(ArrayList<Attendance> attendances) {
                super.onPostExecute(attendances);
                mAttendanceList.clear();
                mAttendanceList.addAll(attendances);
                mAdapter.notifyDataSetChanged();
                updateEmptyState();
            }
        }.execute();
    }

    private void showAddAttendanceDialog() {
        AttendanceDialog dialog = new AttendanceDialog(this, new AttendanceDialog.OnAttendanceSaveListener() {
            @Override
            public void onSave(Attendance attendance) {
                addAttendance(attendance);
            }
        });
        dialog.show();
    }

    private void addAttendance(Attendance attendance) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.addAttendance(attendance.getStudentId(), attendance.getDate(), attendance.getStatus(), attendance.getRemark());
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
                    Toast.makeText(kaoQinManegerActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    loadAttendance();
                } else {
                    Toast.makeText(kaoQinManegerActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showEditAttendanceDialog(Attendance attendance) {
        AttendanceDialog dialog = new AttendanceDialog(this, attendance, new AttendanceDialog.OnAttendanceSaveListener() {
            @Override
            public void onSave(Attendance updatedAttendance) {
                updateAttendance(updatedAttendance);
            }
        });
        dialog.show();
    }

    private void updateAttendance(Attendance attendance) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.updateAttendance(attendance.getId(), attendance.getStatus(), attendance.getRemark());
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
                    Toast.makeText(kaoQinManegerActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    loadAttendance();
                } else {
                    Toast.makeText(kaoQinManegerActivity.this, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void deleteAttendance(Attendance attendance) {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除该考勤记录吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    new DatabaseAsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                mSQlite.deleteAttendance(attendance.getId());
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
                                Toast.makeText(kaoQinManegerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                loadAttendance();
                            } else {
                                Toast.makeText(kaoQinManegerActivity.this, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void updateEmptyState() {
        if (mAttendanceList.isEmpty()) {
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
    public void onEditClick(Attendance attendance) {
        showEditAttendanceDialog(attendance);
    }

    @Override
    public void onDeleteClick(Attendance attendance) {
        deleteAttendance(attendance);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSQlite != null) {
            mSQlite.close();
        }
    }
}