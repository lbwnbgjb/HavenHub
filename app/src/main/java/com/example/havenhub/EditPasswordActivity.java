package com.example.havenhub;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.util.Log;
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
import com.example.havenhub.nettest.request.ApiClient;
import com.example.havenhub.nettest.request.StudentUpdateRequest;
import com.example.havenhub.nettest.request.StudentUpdateResponse;
import com.example.havenhub.utils.PasswordUtils;
import com.example.havenhub.utils.DatabaseAsyncTask;
import com.tencent.mmkv.MMKV;

public class EditPasswordActivity extends AppCompatActivity {
    private String username;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText newName;
    private MMKV mmkv;
    //private SQlite mSQlite;

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

        mmkv=MMKV.defaultMMKV();
        username=mmkv.decodeString("username");

        oldPassword=findViewById(R.id.oldPassword);
        newPassword=findViewById(R.id.newPassword);
        newName=findViewById(R.id.newName);

        //mSQlite=new SQlite(EditPasswordActivity.this);

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

                                editPassword(finalUsername,strOldPassword,strNewPassword,strNewName);

                                // 使用异步任务执行数据库操作
//                                new DatabaseAsyncTask<Void, Void, Boolean>() {
//                                    @Override
//                                    protected Boolean doInBackground(Void... voids) {
//                                        // 验证旧密码
//                                        String correctOldPassword = getCurrentPassword(finalUsername);
//                                        String encryptedOldPassword = PasswordUtils.encryptPassword(strOldPassword);
//                                        if (!encryptedOldPassword.equals(correctOldPassword)) {
//                                            return false;
//                                        }
//
//                                        // 执行更新操作
//                                        boolean updated = false;
//                                        try {
//                                            if (!TextUtils.isEmpty(strNewPassword)) {
//                                                String encryptedNewPassword = PasswordUtils.encryptPassword(strNewPassword);
//                                                mSQlite.updatePassword(finalUsername, encryptedNewPassword);
//                                                updated = true;
//                                            }
//
//                                            if (!TextUtils.isEmpty(strNewName)) {
//                                                mSQlite.updateName(finalUsername, strNewName);
//                                                updated = true;
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                            return false;
//                                        }
//                                        return updated;
//                                    }
//
//                                    @Override
//                                    protected void onPostExecute(Boolean result) {
//                                        super.onPostExecute(result);
//                                        if (!result) {
//                                            Toast.makeText(EditPasswordActivity.this, "旧密码错误或更新失败，请重试", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                        if (TextUtils.isEmpty(strNewPassword) && TextUtils.isEmpty(strNewName)) {
//                                            Toast.makeText(EditPasswordActivity.this, "请输入新密码或姓名", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            String message = (!TextUtils.isEmpty(strNewPassword) && !TextUtils.isEmpty(strNewName))
//                                                    ? "已修改密码和姓名,请重新登录"
//                                                    : (!TextUtils.isEmpty(strNewPassword)
//                                                    ? "已修改密码,请重新登录"
//                                                    : "已修改姓名,请重新登录");
//
//                                            Toast.makeText(EditPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(EditPasswordActivity.this, ActivityLogin.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    }
//                                }.execute();
                            }
                        }).show();
            }
        });



    }


    public void editPassword(String studentId,String oldPassword,String newPassword,String newName) {

        StudentUpdateRequest request = new StudentUpdateRequest(studentId, oldPassword, newPassword,newName);

        Call<StudentUpdateResponse> call = ApiClient.getApiService().updateStudentPassword(request);

        call.enqueue(new Callback<StudentUpdateResponse>() {
            @Override
            public void onResponse(Call<StudentUpdateResponse> call, Response<StudentUpdateResponse> response) {

                android.util.Log.d("EditPassword", "=== onResponse 开始 ===");
                android.util.Log.d("EditPassword", "响应码: " + (response != null ? response.code() : "null"));

                if(response!=null&&response.isSuccessful()){
                    android.util.Log.d("EditPassword", "响应成功");
                    StudentUpdateResponse studentUpdateResponse = response.body();
                    android.util.Log.d("EditPassword", "响应体: " + studentUpdateResponse);

                    if(studentUpdateResponse != null&&studentUpdateResponse.getSuccess()){
                        Toast.makeText(EditPasswordActivity.this, studentUpdateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("StudentUpdateResponse", studentUpdateResponse.getMessage());

                        Intent intent = new Intent(EditPasswordActivity.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();



                    }else Toast.makeText(EditPasswordActivity.this, "密码修改失败:"+studentUpdateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else
                Toast.makeText(EditPasswordActivity.this, "服务器返回异常", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<StudentUpdateResponse> call, Throwable t) {
                Toast.makeText(EditPasswordActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    public void editPassword(String studentId, String oldPassword, String newPassword, String newName) {
//        StudentUpdateRequest request = new StudentUpdateRequest(studentId, oldPassword, newPassword, newName);
//
//        Call<StudentUpdateResponse> call = ApiClient.getApiService().updateStudentPassword(request);
//
//        call.enqueue(new Callback<StudentUpdateResponse>() {
//            @Override
//            public void onResponse(Call<StudentUpdateResponse> call, Response<StudentUpdateResponse> response) {
//                android.util.Log.d("EditPassword", "=== onResponse 开始 ===");
//                android.util.Log.d("EditPassword", "响应码: " + (response != null ? response.code() : "null"));
//
//                if(response != null && response.isSuccessful()){
//                    android.util.Log.d("EditPassword", "响应成功");
//                    StudentUpdateResponse studentUpdateResponse = response.body();
//                    android.util.Log.d("EditPassword", "响应体: " + studentUpdateResponse);
//
//                    // 关键修复：添加空值检查
//                    if(studentUpdateResponse != null){
//                        // 关键修复：安全地获取success值
//                        boolean success = studentUpdateResponse.getSuccess();
//                        android.util.Log.d("EditPassword", "success值: " + success);
//
//                        // 关键修复：使用Boolean.TRUE.equals()避免空指针
//                        if(success){
//                            android.util.Log.d("EditPassword", "进入成功分支");
//                            String message = studentUpdateResponse.getMessage();
//                            android.util.Log.d("EditPassword", "消息: " + message);
//
//                            Toast.makeText(EditPasswordActivity.this,
//                                    message != null ? message : "修改成功",
//                                    Toast.LENGTH_SHORT).show();
//                            Log.i("StudentUpdateResponse", message != null ? message : "修改成功");
//
//                            // 跳转到登录页面
//                            android.util.Log.d("EditPassword", "准备跳转");
//                            try {
//                                Intent intent = new Intent(EditPasswordActivity.this, ActivityLogin.class);
//                                // 清除任务栈，确保登录页是新的任务
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                startActivity(intent);
//                                android.util.Log.d("EditPassword", "跳转成功");
//
//                                // 给跳转一点时间
//                                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        finish();
//                                        android.util.Log.d("EditPassword", "finish完成");
//                                    }
//                                }, 300);
//
//                            } catch (Exception e) {
//                                android.util.Log.e("EditPassword", "跳转异常: " + e.getMessage(), e);
//                                Toast.makeText(EditPasswordActivity.this, "跳转失败", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else {
//                            android.util.Log.d("EditPassword", "进入失败分支");
//                            String errorMsg = "密码修改失败";
//                            if(studentUpdateResponse.getMessage() != null){
//                                errorMsg += ": " + studentUpdateResponse.getMessage();
//                            }
//                            Toast.makeText(EditPasswordActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        android.util.Log.d("EditPassword", "响应体为null");
//                        Toast.makeText(EditPasswordActivity.this, "服务器返回空数据", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    android.util.Log.d("EditPassword", "响应不成功");
//                    String errorMsg = "服务器返回异常";
//                    if(response != null){
//                        errorMsg += " (状态码: " + response.code() + ")";
//                    }
//                    Toast.makeText(EditPasswordActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
//                }
//                android.util.Log.d("EditPassword", "=== onResponse 结束 ===");
//            }
//
//            @Override
//            public void onFailure(Call<StudentUpdateResponse> call, Throwable t) {
//                android.util.Log.e("EditPassword", "网络请求失败", t);
//                Toast.makeText(EditPasswordActivity.this, "网络异常: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }




//    @SuppressLint("Range")
//    private String getCurrentPassword(String username) {
//        // 关键修复5：正确处理数据库查询
//        try (Cursor cursor = mSQlite.getUserByUsername(username)) {
//            if (cursor != null && cursor.moveToFirst()) {
//                return cursor.getString(cursor.getColumnIndex("password"));
//            } else {
//                // 用户不存在或数据库错误
//                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}