package com.example.havenhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.havenhub.database.SQlite;
import com.example.havenhub.utils.PasswordUtils;
import com.example.havenhub.utils.DatabaseAsyncTask;

public class ActivityRegister extends AppCompatActivity {

    private EditText Register_username;
    private EditText Register_password;
    private EditText Register_name;
    SQlite mSQlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Register_username=findViewById(R.id.register_username);
        Register_password=findViewById(R.id.register_password);
        Register_name= findViewById(R.id.register_name);
        mSQlite=new SQlite(ActivityRegister.this);
        //回退到登录界面
        findViewById(R.id.textReturnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.backTologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //注册按钮
        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=Register_username.getText().toString().trim();
                String password=Register_password.getText().toString().trim();
                String name=Register_name.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(name)) {
                    Toast.makeText(ActivityRegister.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                }else {
                    final String encryptedPassword = PasswordUtils.encryptPassword(password);
                    final String finalUsername = username;
                    final String finalName = name;

                    // 使用异步任务执行数据库操作
                    new DatabaseAsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                mSQlite.add(finalUsername, encryptedPassword, finalName);
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean result) {
                            super.onPostExecute(result);
                            if(result) {
                                Toast.makeText(ActivityRegister.this, "注册完成请前往登录", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityRegister.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();
                }

            }

        });



    }
}