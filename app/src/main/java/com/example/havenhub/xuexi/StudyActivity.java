package com.example.havenhub.xuexi;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.R;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity implements SimpleAdapter.OnItemClickListener {

    private Toolbar toolbar;
    private SimpleAdapter adapter;
    private List<Person> personList=new ArrayList<>();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_study);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // 4. 设置返回点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前Activity
                finish();

                // 或者使用动画
                // onBackPressed();
            }
        });
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new SimpleAdapter(this,personList,this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson(personList, StudyActivity.this);
            }
        });


    }



    @Override
    public void onItemClick(Person person, int position) {

    }

    public void addPerson(List<Person> personList, Context  context){

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_person, null);
        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etAge = dialogView.findViewById(R.id.et_age);
        EditText etMiaoShu = dialogView.findViewById(R.id.et_miaoshu);
        Button btnAdd = dialogView.findViewById(R.id.btn_add);


        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("添加人员").setView(dialogView)
                        .setCancelable(true).create();


        dialog.show();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String miaoShu = etMiaoShu.getText().toString();

                if(name.isEmpty()||age.isEmpty()||miaoShu.isEmpty()){
                    Toast.makeText(context,"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else{
                    Person person = new Person(name, Integer.parseInt(age), miaoShu);
                    personList.add(person);
                    adapter.notifyItemInserted(personList.size()-1);
                    Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });




    }



}