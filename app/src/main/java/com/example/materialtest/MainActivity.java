package com.example.materialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Fruit[] fruits = new Fruit[]{new Fruit("Banana", R.drawable.img_1),
            new Fruit("Orange", R.drawable.img_2),
            new Fruit("Watermelon", R.drawable.img_3),
            new Fruit("Pear", R.drawable.img_4),
            new Fruit("Grape", R.drawable.img_5)};
    private List<Fruit> list = new ArrayList<>();
    private FruitAdapter fruitAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);//使用Toolbar代替ActionBar的外观和功能
        //如果不设置这句的话，Toolbar无法与原来的ActionBar产生关联

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.twotone_notes_24);//设置导航按钮图标，默认是返回的图标
        }

        //获取NavigationView的实例
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);//设置菜单的默认选中项
        //对菜单项设置监听 当用户点击了任意菜单项时，就会回调到这个onNavigationItemSelected()方法
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();// 关闭所有滑动菜单
                return true;
            }
        });

        //悬浮按钮：点击事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
//            Toast.makeText(this, "FAB clicked.", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "Data delete", Snackbar.LENGTH_SHORT)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        });

        initFruit();//随机初始化数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);//网格布局
        recyclerView.setLayoutManager(layoutManager);
        fruitAdapter = new FruitAdapter(list);
        recyclerView.setAdapter(fruitAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });

    }

    private void refreshFruit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        fruitAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruit() {
        list.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            list.add(fruits[random.nextInt(fruits.length)]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.backup) {
            Toast.makeText(this, "Backup", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}