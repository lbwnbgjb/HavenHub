package com.example.havenhub.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.havenhub.ActivityLogin;
import com.example.havenhub.EditPasswordActivity;
import com.example.havenhub.R;
import com.example.havenhub.setingActivity;
import com.tencent.mmkv.MMKV;


public class MeFragment extends Fragment {

    private View rootViews;
    TextView username;
    TextView name;
    private MMKV mmkv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootViews = inflater.inflate(R.layout.fragment_me, container, false);
        mmkv=MMKV.defaultMMKV();
        username=rootViews.findViewById(R.id.username);
        name=rootViews.findViewById(R.id.name);
        username.setText(mmkv.decodeString("username"));
        name.setText(mmkv.decodeString("realname"));
        return rootViews;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootViews.findViewById(R.id.textView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), setingActivity.class);
                startActivity(intent);
            }
        });
        rootViews.findViewById(R.id.imageView11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), setingActivity.class);
                startActivity(intent);
            }
        });

        rootViews.findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditPasswordActivity.class);
                intent.putExtra("username",(username.getText().toString()));
                startActivity(intent);
            }
        });

        rootViews.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new AlertDialog.Builder(getContext()).setTitle("温馨提醒").setMessage("确定要退出登陆吗").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intent=new Intent(getActivity(), ActivityLogin.class);
                       startActivity(intent);
                       getActivity().finish();
                       Toast.makeText(getContext(), "已清除用户数据", Toast.LENGTH_SHORT).show();
                   }
               }).show();

            }
        });
    }


}