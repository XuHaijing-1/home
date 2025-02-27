package com.example.diandian;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ChannelRvAdapter extends RecyclerView.Adapter<ChannelRvAdapter.ChannelRowHolder> {

    private ChannelLab lab=ChannelLab.getInstance();
    private ChannelClickListener listener;
    private Context context;

    public ChannelRvAdapter(Context context, ChannelClickListener listener){
        this.context=context;
        this.listener=listener;
    }
    /**
     * 当需要新的一行，此方法负责创建一行对应的对象，既ChannelRowHolder对象
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ChannelRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView= LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row,parent,false);
        ChannelRowHolder holder =new ChannelRowHolder(rowView);
        return holder;
    }

    /**
     * 用于确定列表有几行（既多少ChannRowHolder对象）
     * @return
     */
    @Override
    public int getItemCount() {
        return lab.getSize();
    }

    /**
     * 用于确定每一行的内容是什么，及填充行中各个视图内容
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ChannelRowHolder holder, int position) {
        Channel c=lab.getChannel(position);
        holder.bind(c);
    }

    //自定义新接口
    public interface ChannelClickListener{
        public void onChannelClick(int position);
    }

    /**
     * 单行布局对应的java控制类
     */
    public class ChannelRowHolder extends RecyclerView.ViewHolder{
        private TextView title; //频道标题 实例对象
        private TextView quality;//清晰度
        private ImageView cover;
        private TextView like;//关注度

        public ChannelRowHolder(@NonNull View row) {
            super(row);
            this.title=row.findViewById(R.id.channel_title);
            this.quality=row.findViewById(R.id.channel_quality);
            this.cover=row.findViewById(R.id.channel_cover);
            this.like=row.findViewById(R.id.thumbup_count);
            row.setOnClickListener((v)->{
                int position= getLayoutPosition();
                Log.d("DianDian", position+"行被点击啦！");
                    listener.onChannelClick(position);
            });
        }

        /**
         * 自定义方法，用于向内部的title提供数据
         * @param // title
         */
        public void bind(Channel c){
            this.title.setText(c.getTitle());
            this.quality.setText(c.getQuality());
            this.like.setText(c.getLike()+"");
            //从网络获取图片
            //this.cover.setImageResource(c.getCover());
            Log.d("Diandian","频道"+c.getTitle()+"准备从网络加载图片"+c.getCover());
            Glide.with(context)
                    .load(c.getCover())
                    .into(this.cover);
        }
    }

//    public interface ChannelClickListener{
//        public void onChannelClick();
}
