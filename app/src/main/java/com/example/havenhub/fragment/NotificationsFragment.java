package com.example.havenhub.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.havenhub.R;
import com.example.havenhub.nettest.NetTest1;


public class NotificationsFragment extends Fragment {



private View rootViews;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootViews=inflater.inflate(R.layout.fragment_notifications, container, false);

        Button btGet=rootViews.findViewById(R.id.bt_get);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireContext(),NetTest1.class);
                startActivity(intent);
            }
        });



        return rootViews;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}