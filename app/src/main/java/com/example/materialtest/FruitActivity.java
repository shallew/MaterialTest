package com.example.materialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {
    //key
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();//获取到启动该活动的Intent，读取数据
        String fruitName =  intent.getStringExtra(FRUIT_NAME);//读取水果的名字
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);//读取水果的图片id，默认值为0
//      androidx.appcompat.widget.Toolbar 包下的Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView fruitImage = (ImageView) findViewById(R.id.fruit_image_view);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView fruitText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);//Toolbar代替ActionBar
        //androidx.appcompat.app.ActionBar 包下的ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置显示导航按钮， 启用HomeAsUp按钮
            //HomeAsUp按钮默图标是一个返回箭头
        }
        collapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImage);//加载图片
        String fruitContent = generateFruitContent(fruitName);//模拟生成水果内容详情
        fruitText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //设置标题栏上HomeAsUp按钮的点击事件 --退出
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}