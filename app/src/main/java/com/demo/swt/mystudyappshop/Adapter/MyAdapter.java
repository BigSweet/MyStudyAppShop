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
import com.demo.swt.mystudyappshop.Wight.CstImage;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.Wight.SwtToast;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.PostInfoBean;
import com.demo.swt.mystudyappshop.bean.UserBean;
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
    private UserBean userBean;

    public MyAdapter(Context context, List<FeedBean> list) {
        this.mcontext = context;
        this.mlist = list;
        inflater = LayoutInflater.from(mcontext);
    }


    public void addData(int position, List<FeedBean> morelist) {
        mlist.addAll(morelist);
        notifyItemChanged(position, mlist.size());

    }


    public void addData(List<FeedBean> morelist) {
        mlist.clear();
        mlist.addAll(morelist);
        notifyItemChanged(0, mlist.size());

    }

    public List<FeedBean> getdata() {
        return mlist;
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
        userBean = mlist.get(position).getUser();
        NoNullUtils.setText(wuyuHolder.name, userBean.getName());
        NoNullUtils.setText(wuyuHolder.school, userBean.getEntity_name());
        wuyuHolder.logo.setImageURI(userBean.getAvatar());
        imagelist = postInfoBean.getImages();
        wuyuHolder.cstImage.setImgs(imagelist);
        wuyuHolder.setOnItemClickListener(position, mlist.get(position), mItemClickListener);
        if (imagelist.size() > 0) {
            requestImage(wuyuHolder);
        }
        wuyuHolder.cstImage.setOnChildItemClickListener(new CstImage.OnChlidItemClickListener() {
            @Override
            public void onClick(int position, List<String> imgs) {
                SwtToast.show("照片被点击了" + position);
                Intent intent = new Intent(mcontext, BigImageActivity.class);
                Bundle bundle = new Bundle();
                if (imagelist.size() > 0) {
                    bundle.putStringArrayList("tulist", (ArrayList<String>) imagelist);
                }
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });


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
        TextView textView, name, school;
        SimpleDraweeView draweeView, logo;
        CstImage cstImage;

        public wuyuHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.wuyu_text);
            name = (TextView) itemView.findViewById(R.id.name);
            school = (TextView) itemView.findViewById(R.id.school);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.image_one);
            logo = (SimpleDraweeView) itemView.findViewById(R.id.logo);
            cstImage = (CstImage) itemView.findViewById(R.id.cstimage);

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
