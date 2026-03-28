package com.example.havenhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.havenhub.database.SQlite;
import com.example.havenhub.fragment.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity {
    private HomeFragment mHomeFragment;
    private DashboardFragment mDashboardFragment;
    private NotificationsFragment mNotificationsFragment;
    private MeFragment mMeFragment;

    private String username;
    private SQlite mSqlite;

    public String name;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // 恢复数据（如果Activity被重建）
        if (savedInstanceState != null) {
            username = savedInstanceState.getString("username", "");
        } else {
            // 从Intent获取数据
            Intent intent = getIntent();
            if (intent != null) {
                username = intent.getStringExtra("username");
            }
        }

        // 初始化数据库
        mSqlite = new SQlite(MainPageActivity.this);

        // 初始化控件
        mBottomNavigationView = findViewById(R.id.bottomNav);

        // 设置点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment(0);
            } else if (item.getItemId() == R.id.navigation_dashboard) {
                selectedFragment(1);
            } else if (item.getItemId() == R.id.navigation_notifications) {
                selectedFragment(2);
            } else if (item.getItemId() == R.id.navigation_me) {
                selectedFragment(3);
            }
            return true;
        });

        // 默认选中首页
        selectedFragment(0);
    }

    private void selectedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);

        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    Bundle homeBundle = new Bundle();
                    try (Cursor cursor = mSqlite.getUserByUsername(username)) {
                        if (cursor != null && cursor.moveToFirst()) {
                            @SuppressLint("Range")
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            homeBundle.putString("name", name);
                            this.name=name;
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "加载用户信息失败", Toast.LENGTH_SHORT).show();
                        Log.e("MainPage", "加载用户信息失败", e);
                    }
                    mHomeFragment.setArguments(homeBundle);
                    fragmentTransaction.add(R.id.fragment_container, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case 1:
                if (mDashboardFragment == null) {
                    mDashboardFragment = new DashboardFragment();
                    fragmentTransaction.add(R.id.fragment_container, mDashboardFragment);
                } else {
                    fragmentTransaction.show(mDashboardFragment);
                }
                break;
            case 2:
                if (mNotificationsFragment == null) {
                    mNotificationsFragment = new NotificationsFragment();
                    fragmentTransaction.add(R.id.fragment_container, mNotificationsFragment);
                } else {
                    fragmentTransaction.show(mNotificationsFragment);
                }
                break;
            case 3:
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    Bundle meBundle = new Bundle();
                    meBundle.putString("username", username);
                    meBundle.putString("name", name);
                    mMeFragment.setArguments(meBundle);
                    fragmentTransaction.add(R.id.fragment_container, mMeFragment);
                } else {
                    fragmentTransaction.show(mMeFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mDashboardFragment != null) {
            fragmentTransaction.hide(mDashboardFragment);
        }
        if (mNotificationsFragment != null) {
            fragmentTransaction.hide(mNotificationsFragment);
        }
        if (mMeFragment != null) {
            fragmentTransaction.hide(mMeFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", username);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        username = savedInstanceState.getString("username", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 释放资源
        mHomeFragment = null;
        mDashboardFragment = null;
        mNotificationsFragment = null;
        mMeFragment = null;

        if (mSqlite != null) {
            mSqlite.close();
        }
    }
}