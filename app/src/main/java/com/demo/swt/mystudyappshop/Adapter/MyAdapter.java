package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageUtils;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.PostInfoBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/2.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedBean> mlist;
    private LayoutInflater inflater;
    private Context mcontext;
    private List<String> imagelist = new ArrayList<>();
    private PostInfoBean postInfoBean;

    public MyAdapter(Context context, List<FeedBean> list) {
        this.mcontext = context;
        this.mlist = list;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new wuyuHolder(inflater.inflate(R.layout.wuyu, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        wuyuHolder wuyuHolder = (MyAdapter.wuyuHolder) holder;
        wuyuHolder.textView.setText(mlist.get(position).getPost().getContent());
        postInfoBean = mlist.get(position).getPost();
        imagelist = postInfoBean.getImages();
        if (imagelist.size() > 0) {
            Picasso.with(mcontext).load(imagelist.get(0)).into(wuyuHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class wuyuHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public wuyuHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.wuyu_text);
            imageView = (ImageView) itemView.findViewById(R.id.image_one);
        }
    }

}
