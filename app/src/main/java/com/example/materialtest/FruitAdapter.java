package com.example.materialtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;
    private Context mContext;
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fruit_image);
            textView = itemView.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        //在这里添加点击事件
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = fruitList.get(position);//获取水果子项
                Intent intent = new Intent(mContext, FruitActivity.class);
                //传递数据
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.textView.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.imageView);
        /*
        * Glide的用法：
        * 调用Glide.with()方法并传入一个Context、Activity或者Fragment参数
        * 然后调用load()方法去加载图片，可以是一个URL地址，也可以是一个本地路径，或者是一个资源id
        * 最后调用into()方法将图片设置到某个ImageView中就可以了。
        * */
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }
}
