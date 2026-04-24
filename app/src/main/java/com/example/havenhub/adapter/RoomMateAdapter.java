package com.example.havenhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.R;
import com.example.havenhub.model.RoomMate;  // 注意：是 model 不是 modle

import java.util.List;

public class RoomMateAdapter extends RecyclerView.Adapter<RoomMateAdapter.ViewHolder> {

    private List<RoomMate> roomMateList;  // 建议改成 roomMateList
    private Context context;
    private OnItemClickListener listener;  // 添加这个成员变量

    public interface OnItemClickListener {
        void onItemClick(RoomMate roomMate, int position);
        void onBtnEditClick(RoomMate roomMate, int position);
        void onBtnDeleteClick(RoomMate roomMate, int position);
    }

    public RoomMateAdapter(Context context, List<RoomMate> roomMateList, OnItemClickListener listener) {
        this.context = context;
        this.roomMateList = roomMateList;
        this.listener = listener;  // 保存监听器
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.roommate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 1. 获取当前位置的数据
        RoomMate roomMate = roomMateList.get(position);

        // 2. 设置数据到视图
        holder.tvName.setText(roomMate.getName());
        holder.tvStudentId.setText("学号:"+roomMate.getStudentId());
        holder.tvAge.setText("年龄: " + roomMate.getAge());
        holder.tvZhiwei.setText("职位: " + roomMate.getZiZe());

        // 3. 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(roomMate, position);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBtnEditClick(roomMate, position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBtnDeleteClick(roomMate, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        // 返回实际数据数量
        return roomMateList == null ? 0 : roomMateList.size();
    }

    // 添加一个更新数据的方法
    public void updateData(List<RoomMate> newList) {
        this.roomMateList = newList;
        notifyDataSetChanged();
    }

    // ViewHolder 类 - 必须定义为 public static
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 声明布局中的所有视图
        TextView tvName;
        TextView tvStudentId;
        TextView tvZhiwei;
        TextView tvAge;
        TextView tvRemark;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 初始化所有视图
            tvName = itemView.findViewById(R.id.text_student_name);
            tvStudentId = itemView.findViewById(R.id.text_student_id);
            tvZhiwei = itemView.findViewById(R.id.tv_zhiwei);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvRemark=itemView.findViewById(R.id.tv_remark);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}