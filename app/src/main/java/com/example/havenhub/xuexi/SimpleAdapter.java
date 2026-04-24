package com.example.havenhub.xuexi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.havenhub.R;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private List<Person> personList;
    private Context context;
    private OnItemClickListener listener;

    public SimpleAdapter(Context context, List<Person> personList, OnItemClickListener listener) {
        this.context = context;
        this.personList = personList;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Person person, int position);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Person person = personList.get(position);
        int currentPosition =  holder.getBindingAdapterPosition();

        holder.tvName.setText(person.getName());
        holder.tvAge.setText("年龄："+person.getAge());
        holder.tvMiaoShu.setText(person.getMiaoShu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(person, currentPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return personList == null ? 0 : personList.size();
    }


    public  static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvAge;
        TextView tvMiaoShu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvAge = itemView.findViewById(R.id.age);
            tvMiaoShu = itemView.findViewById(R.id.miaoShu);
        }
    }

}



