package com.example.havenhub.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.havenhub.R;
import com.example.havenhub.*;
import com.example.havenhub.database.SQlite;
import com.tencent.mmkv.MMKV;


public class HomeFragment extends Fragment {

    private View rootViews;
    //Bundle bundle;
    MMKV mmkv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootViews=inflater.inflate(R.layout.fragment_home,container,false);
      //  bundle=this.getArguments();
        TextView realName=rootViews.findViewById(R.id.realName);
        mmkv=MMKV.defaultMMKV();
        realName.setText((mmkv.decodeString("realname")));


        return rootViews;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. 获取视图引用
        rootViews.findViewById(R.id.studentManeger).setOnClickListener(new View.OnClickListener() {
        // 2. 设置点击监听器
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), studentManegerActivity.class);
                startActivity(intent);
            }
        });
        rootViews.findViewById(R.id.suseManeger).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(), suSeManegerActivity.class);
            startActivity(intent);
        }
    });
        rootViews.findViewById(R.id.kaoqinManeger).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(),kaoQinManegerActivity.class);
            startActivity(intent);
        }
    });
        rootViews.findViewById(R.id.repairManeger).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(),repairManegerActivity.class);
            startActivity(intent);
        }
    });
        rootViews.findViewById(R.id.visitorManeger).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(),visitorManegerActivity.class);
            startActivity(intent);
        }
    });
        rootViews.findViewById(R.id.suJuBaoBiao).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(),dataManegerActivity.class);
            startActivity(intent);
        }
    });
        rootViews.findViewById(R.id.headerCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

}