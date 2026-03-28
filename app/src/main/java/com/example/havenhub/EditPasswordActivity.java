package com.example.havenhub;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.havenhub.database.SQlite;
import com.example.havenhub.utils.PasswordUtils;
import com.example.havenhub.utils.DatabaseAsyncTask;

public class EditPasswordActivity extends AppCompatActivity {
    private String username;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText newName;

    private SQlite mSQlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        oldPassword=findViewById(R.id.oldPassword);
        newPassword=findViewById(R.id.newPassword);
        newName=findViewById(R.id.newName);

        mSQlite=new SQlite(EditPasswordActivity.this);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditPasswordActivity.this)
                        .setTitle("提醒")
                        .setMessage("确定保存吗")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String strOldPassword = oldPassword.getText().toString().trim();
                                final String strNewPassword = newPassword.getText().toString().trim();
                                final String strNewName = newName.getText().toString().trim();
                                final String finalUsername = username;

                                // 1. 检查旧密码是否为空
                                if (TextUtils.isEmpty(strOldPassword)) {
                                    Toast.makeText(EditPasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // 使用异步任务执行数据库操作
                                new DatabaseAsyncTask<Void, Void, Boolean>() {
                                    @Override
                                    protected Boolean doInBackground(Void... voids) {
                                        // 验证旧密码
                                        String correctOldPassword = getCurrentPassword(finalUsername);
                                        String encryptedOldPassword = PasswordUtils.encryptPassword(strOldPassword);
                                        if (!encryptedOldPassword.equals(correctOldPassword)) {
                                            return false;
                                        }

                                        // 执行更新操作
                                        boolean updated = false;
                                        try {
                                            if (!TextUtils.isEmpty(strNewPassword)) {
                                                String encryptedNewPassword = PasswordUtils.encryptPassword(strNewPassword);
                                                mSQlite.updatePassword(finalUsername, encryptedNewPassword);
                                                updated = true;
                                            }

                                            if (!TextUtils.isEmpty(strNewName)) {
                                                mSQlite.updateName(finalUsername, strNewName);
                                                updated = true;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            return false;
                                        }
                                        return updated;
                                    }
                                    
                                    @Override
                                    protected void onPostExecute(Boolean result) {
                                        super.onPostExecute(result);
                                        if (!result) {
                                            Toast.makeText(EditPasswordActivity.this, "旧密码错误或更新失败，请重试", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        if (TextUtils.isEmpty(strNewPassword) && TextUtils.isEmpty(strNewName)) {
                                            Toast.makeText(EditPasswordActivity.this, "请输入新密码或姓名", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String message = (!TextUtils.isEmpty(strNewPassword) && !TextUtils.isEmpty(strNewName))
                                                    ? "已修改密码和姓名,请重新登录"
                                                    : (!TextUtils.isEmpty(strNewPassword)
                                                    ? "已修改密码,请重新登录"
                                                    : "已修改姓名,请重新登录");

                                            Toast.makeText(EditPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(EditPasswordActivity.this, ActivityLogin.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }.execute();
                            }
                        }).show();
            }
        });



    }

    @SuppressLint("Range")
    private String getCurrentPassword(String username) {
        // 关键修复5：正确处理数据库查询
        try (Cursor cursor = mSQlite.getUserByUsername(username)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("password"));
            } else {
                // 用户不存在或数据库错误
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}