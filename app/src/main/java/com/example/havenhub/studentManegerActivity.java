package com.example.havenhub;

import android.annotation.SuppressLint;
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

import com.example.havenhub.adapter.StudentAdapter;
import com.example.havenhub.database.SQlite;
import com.example.havenhub.database.Student;
import com.example.havenhub.dialog.StudentDialog;
import com.example.havenhub.utils.DatabaseAsyncTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class studentManegerActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private ArrayList<Student> mStudentList;
    private SQlite mSQlite;
    private FloatingActionButton mFabAdd;
    private TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_maneger);
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

        mRecyclerView = findViewById(R.id.recyclerView_students);
        mFabAdd = findViewById(R.id.fab_add);
        mEmptyText = findViewById(R.id.empty_text);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentList = new ArrayList<>();
        mAdapter = new StudentAdapter(this, mStudentList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mSQlite = new SQlite(this);
        loadStudents();
    }

    private void initListeners() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddStudentDialog();
            }
        });
    }

    private void loadStudents() {
        new DatabaseAsyncTask<Void, Void, ArrayList<Student>>() {
            @Override
            protected ArrayList<Student> doInBackground(Void... voids) {
                return mSQlite.getAllStudents();
            }

            @Override
            protected void onPostExecute(ArrayList<Student> students) {
                super.onPostExecute(students);
                mStudentList.clear();
                mStudentList.addAll(students);
                mAdapter.notifyDataSetChanged();
                updateEmptyState();
            }
        }.execute();
    }

    private void showAddStudentDialog() {
        StudentDialog dialog = new StudentDialog(this, new StudentDialog.OnStudentSaveListener() {
            @Override
            public void onSave(Student student) {
                addStudent(student);
            }
        });
        dialog.show();
    }

    private void addStudent(Student student) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.addStudent(student.getStudentId(), student.getName(), student.getGender(), 
                            student.getAge(), student.getDepartment(), student.getMajor(), 
                            student.getDormitory(), student.getPhone(), student.getEmail());
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
                    Toast.makeText(studentManegerActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    loadStudents();
                } else {
                    Toast.makeText(studentManegerActivity.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showEditStudentDialog(Student student) {
        StudentDialog dialog = new StudentDialog(this, student, new StudentDialog.OnStudentSaveListener() {
            @Override
            public void onSave(Student updatedStudent) {
                updateStudent(updatedStudent);
            }
        });
        dialog.show();
    }

    private void updateStudent(Student student) {
        new DatabaseAsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mSQlite.updateStudent(student.getStudentId(), student.getName(), student.getGender(), 
                            student.getAge(), student.getDepartment(), student.getMajor(), 
                            student.getDormitory(), student.getPhone(), student.getEmail());
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
                    Toast.makeText(studentManegerActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    loadStudents();
                } else {
                    Toast.makeText(studentManegerActivity.this, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void deleteStudent(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除学生 " + student.getName() + " 吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    new DatabaseAsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                mSQlite.deleteStudent(student.getStudentId());
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
                                Toast.makeText(studentManegerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                loadStudents();
                            } else {
                                Toast.makeText(studentManegerActivity.this, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void updateEmptyState() {
        if (mStudentList.isEmpty()) {
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
    public void onEditClick(Student student) {
        showEditStudentDialog(student);
    }

    @Override
    public void onDeleteClick(Student student) {
        deleteStudent(student);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSQlite != null) {
            mSQlite.close();
        }
    }
}