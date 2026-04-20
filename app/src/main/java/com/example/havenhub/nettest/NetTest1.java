package com.example.havenhub.nettest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.havenhub.R;
import com.example.havenhub.nettest.request.ApiClient;
import com.example.havenhub.nettest.request.ServiceAPI;
import com.example.havenhub.nettest.request.Student;
import com.tencent.mmkv.MMKV;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetTest1 extends AppCompatActivity {


    private TextView name;
    private TextView studentId;
    private TextView gender;
    private static final String BASE_URL="http://192.168.10.182:8080/";
    private MMKV mmkv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_net_test1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        name=findViewById(R.id.name);
        studentId=findViewById(R.id.numer);
        gender=findViewById(R.id.gender);

        mmkv=MMKV.defaultMMKV();

//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ServiceAPI serviceAPI=retrofit.create(ServiceAPI.class);
//        Call<Student> call=serviceAPI.getStudentInfo();

        Student student=new Student();
        student.setStudentId(mmkv.decodeString("username"));

        Call<Student> call= ApiClient.getApiService().getStudentInfo(student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    Student responseStudent=response.body();
                    name.setText(responseStudent.getName());
                    studentId.setText(responseStudent.getStudentId());
                    Toast.makeText(NetTest1.this, "请求成功"+responseStudent.getGender(), Toast.LENGTH_SHORT).show();
                    gender.setText(responseStudent.getGender());

                }else {
                    Toast.makeText(NetTest1.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(NetTest1.this, "网络错误", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });





    }
}