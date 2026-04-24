package com.example.havenhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.adapter.RoomMateAdapter;
import com.example.havenhub.model.RoomMate;

import java.util.ArrayList;
import java.util.List;

public class dataManegerActivity extends AppCompatActivity implements RoomMateAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private RoomMateAdapter adapter;
    private List<RoomMate> roomMateList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_maneger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();


        adapter=new RoomMateAdapter(this,roomMateList,this);
        recyclerView.setAdapter(adapter);


        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleDialog();
                adapter.updateData(roomMateList);
            }
        });

    }


    private void initData(){
        roomMateList.add(new RoomMate("张三","20210000001","寝室长",20));
    }


    private void showSimpleDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加新室友");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_room_mate, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etAge = dialogView.findViewById(R.id.et_age);
        EditText etStudentId = dialogView.findViewById(R.id.et_student_id);
        EditText etZhiwei = dialogView.findViewById(R.id.et_zhiwei);

        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    String name = etName.getText().toString();
                    String studentId = etStudentId.getText().toString();
                    String ziZe = etZhiwei.getText().toString();
                    String ageStr = etAge.getText().toString();

                    if(name.isEmpty()||studentId.isEmpty()||ziZe.isEmpty()||ageStr.isEmpty()){
                        Toast.makeText(dataManegerActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                        return;
                    }

                try {
                    int age = Integer.parseInt(ageStr);

                    // 创建新室友对象
                    RoomMate newRoomMate = new RoomMate(name, studentId, ziZe, age);
                    roomMateList.add(newRoomMate);

                    adapter.notifyItemInserted(roomMateList.size() - 1);

                    Toast.makeText(dataManegerActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(dataManegerActivity.this, "年龄必须为数字", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 6. 创建并显示弹窗
            AlertDialog dialog = builder.create();
            dialog.show();
    }





    @Override
    public void onItemClick(RoomMate roomMate, int position) {


    }
    @Override
    public void onBtnEditClick(RoomMate roomMate, int position){



    }

    @Override
    public void onBtnDeleteClick(RoomMate roomMate, int position){



    }


}