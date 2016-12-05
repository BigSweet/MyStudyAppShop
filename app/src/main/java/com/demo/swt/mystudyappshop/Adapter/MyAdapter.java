package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Interface.OnRecyclerViewItemClickListener;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageUtils;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.PostInfoBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/12/2.
 */

public class MyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedBean> mlist;
    private LayoutInflater inflater;
    private Context mcontext;
    private List<String> imagelist = new ArrayList<>();
    private PostInfoBean postInfoBean;
    private OnRecyclerViewItemClickListener<T> mItemClickListener;


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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        wuyuHolder wuyuHolder = (MyAdapter.wuyuHolder) holder;
        wuyuHolder.textView.setText(mlist.get(position).getPost().getContent());
        postInfoBean = mlist.get(position).getPost();
        imagelist = postInfoBean.getImages();
        wuyuHolder.setOnItemClickListener(position, mlist.get(position), mItemClickListener);
        if (imagelist.size() > 0) {
          /*  Uri uri = Uri.parse(imagelist.get(0));
            wuyuHolder.draweeView.setImageURI(uri);*/
            requestImage(wuyuHolder);
        /*    wuyuHolder.draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, BigImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tu", imagelist.get(0));
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                }
            });*/
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    private void requestImage(wuyuHolder wuyuHolder) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imagelist.get(0)))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(wuyuHolder.draweeView.getController())
                .build();
        wuyuHolder.draweeView.setController(controller);


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class wuyuHolder<T> extends RecyclerView.ViewHolder {
        TextView textView;
        SimpleDraweeView draweeView;

        public wuyuHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.wuyu_text);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.image_one);
        }

        public void setOnItemClickListener(final int position, final T t, final OnRecyclerViewItemClickListener<T> l) {
            if (null != itemView) {
                if (null != l) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            l.onClick(position, t);
                        }
                    });
                } else {
                    itemView.setOnClickListener(null);
                }
            }
        }


    }


}
