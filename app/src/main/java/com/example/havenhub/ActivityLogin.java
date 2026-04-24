package com.example.havenhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.havenhub.database.SQlite;
//import com.example.havenhub.database.User;
import com.example.havenhub.nettest.request.ApiClient;
import com.example.havenhub.nettest.request.LoginRequest;
import com.example.havenhub.nettest.request.LoginResponse;
import com.example.havenhub.nettest.request.Student;
import com.example.havenhub.utils.PasswordUtils;
import com.example.havenhub.utils.DatabaseAsyncTask;
import com.example.havenhub.xuexi.StudyActivity;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    private EditText Loginusername;
    private EditText Loginpassword;
    private MMKV mmkv;
    //private SQlite mSQlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mmkv=MMKV.defaultMMKV();
        Loginusername=findViewById(R.id.editText);
        Loginpassword=findViewById(R.id.editTextTextPassword);
       // mSQlite=new SQlite(ActivityLogin.this);
        Loginusername.setText(mmkv.decodeString("username"));
        //点击注册
        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(intent);

            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityLogin.this, StudyActivity.class);
                startActivity(intent);
            }
        });




        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                String username=Loginusername.getText().toString().trim();
                String password=Loginpassword.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(ActivityLogin.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    //final String encryptedPassword = PasswordUtils.encryptPassword(password);

                    login(username,password);

                    // 使用异步任务执行数据库操作
//                    new DatabaseAsyncTask<Void, Void, Boolean>() {
//                        @Override
//                        protected Boolean doInBackground(Void... voids) {
//                            try {
//                                ArrayList<User> data=mSQlite.getAllDATA();
//                                boolean user=false;
//                                for(int i=0;i<data.size();i++){
//                                    User userdata=data.get(i);
//                                    if(username.equals(userdata.getUsername())&&encryptedPassword.equals(userdata.getPassword())){
//                                        user=true;
//                                        break;
//                                    }else {
//                                        user=false;
//                                    }
//                                }
//                                return user;
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return false;
//                            }
//                        }
//
//                        @Override
//                        protected void onPostExecute(Boolean result) {
//                            // 不要调用super.onPostExecute(result)，因为父类会尝试调用null的mListener
//                            if(result) {
//                                Toast.makeText(ActivityLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
//                                Intent intent2=new Intent(ActivityLogin.this, MainPageActivity.class);
//                                intent2.putExtra("username",username);
//                                intent2.putExtra("password",password);
//                                startActivity(intent2);
//                                finish();
//                            }else Toast.makeText(ActivityLogin.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }.execute();
                }

            }
        });

    }

    private void login(String username, String password) {

        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<LoginResponse> call = ApiClient.getApiService().login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {

                    LoginResponse loginResponse = response.body();

                    if (loginResponse != null) {
                        if(loginResponse.isSuccess()) {

                            String realname = loginResponse.getName();
                            String studentId = loginResponse.getStudentId();


                            Toast.makeText(ActivityLogin.this, "登录成功", Toast.LENGTH_SHORT).show();

                            Intent intent2 = new Intent(ActivityLogin.this, MainPageActivity.class);

                            mmkv.encode("username", studentId);
                            mmkv.encode("realname", realname);
                            startActivity(intent2);

                            finish();

                        }else {Toast.makeText(ActivityLogin.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();}

                    }
                }else {Toast.makeText(ActivityLogin.this,"服务器返回为空"+response.code(), Toast.LENGTH_SHORT).show();}

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });



    }



}