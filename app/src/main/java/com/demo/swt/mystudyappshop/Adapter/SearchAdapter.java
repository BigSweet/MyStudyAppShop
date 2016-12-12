package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Wight.CstImage;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.demo.swt.mystudyappshop.Wight.WordWrapView;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.demo.swt.mystudyappshop.bean.PostInfoBean;
import com.demo.swt.mystudyappshop.bean.UpsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/12/7.
 */

public class SearchAdapter extends BaseAdapter<FeedBean, BaseHolder> {
    private Context context;
    private List<UpsBean> uplist;
    private List<TextView> textlist = new ArrayList<>();
    private PostInfoBean postInfoBean;
    private List<String> imglist;

    public SearchAdapter(Context context, List<FeedBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void bindata(BaseHolder holder, FeedBean feedBean, int position) {
        if (feedBean.getFeed_type() == 105) {
            LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            wuyuitem.setLayoutParams(params);
            if (feedBean.getPost() != null) {
                postInfoBean = feedBean.getPost();
                if (postInfoBean.getContent() != null) {
                    setText((TextView) holder.getView(R.id.wuyu_text), feedBean.getPost().getContent());
                }

                if (postInfoBean.getImages().size() > 0 && postInfoBean.getImages() != null) {
                    CstImage cstImage = (CstImage) holder.getView(R.id.cstimage);
                    imglist = postInfoBean.getImages();
                    cstImage.setImgs(imglist);

                    cstImage.setOnChildItemClickListener(new CstImage.OnChlidItemClickListener() {
                        @Override
                        public void onClick(int position, List<String> imgs) {
                            Intent intent = new Intent(context, BigImageActivity.class);
                            Bundle bundle = new Bundle();
                            if (imgs.size() > 0) {
                                bundle.putStringArrayList("tulist", (ArrayList<String>) imgs);
                                bundle.putInt("pos", position);
                            }
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
            }
            setText((TextView) holder.getView(R.id.name), feedBean.getUser().getName());
            setText((TextView) holder.getView(R.id.school), feedBean.getUser().getEntity_name());

            WordWrapView wordWrapView = (WordWrapView) holder.getView(R.id.wordview);
            RecyclerView comment = (RecyclerView) holder.getView(R.id.commentzan);

            textlist.clear();
            wordWrapView.removeAllViews();
            if (feedBean.getUps().size() > 0) {
                uplist = feedBean.getUps();
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.mipmap.friend_icon_zan);
                wordWrapView.addView(imageView);
                for (int i = 0; i < uplist.size(); i++) {
                    TextView textView = new TextView(context);
                    textView.setTextColor(Color.parseColor("#396b9c"));
                    textlist.add(textView);
                    wordWrapView.addView(textView);
                    NoNullUtils.setText(textView, uplist.get(i).getName() + "。");
                    wordWrapView.setVisibility(View.VISIBLE);
                }
            }

       /*     if (feedBean.getUps().size() > 0) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.mipmap.friend_icon_zan);
                wordWrapView.addView(imageView);
                uplist = feedBean.getUps();
                if (textlist.size() == 0) {
                    for (int i = 0; i < uplist.size(); i++) {
                        TextView textView = new TextView(context);
                        textView.setTextColor(Color.parseColor("#396b9c"));
                        textlist.add(textView);
                        wordWrapView.addView(textView);
                        wordWrapView.setVisibility(View.VISIBLE);
                    }
                }
                if (textlist.size() > 0 || textlist.size() < uplist.size()) {
                    for (int i = textlist.size(); i < uplist.size(); i++) {
                        TextView textView = new TextView(context);
                        textlist.add(textView);
                        wordWrapView.addView(textView);
                        wordWrapView.setVisibility(View.VISIBLE);
                    }
                }
            *//*    if (textlist.size() > uplist.size() || textlist.size() == uplist.size()) {
                    for (int i = uplist.size(); i < textlist.size(); i++) {
                        NoNullUtils.setVisible(textlist.get(i), false);
                        wordWrapView.setVisibility(View.VISIBLE);
                    }
                    wordWrapView.setVisibility(View.VISIBLE);
                }*//*
                for (int i = 0; i < uplist.size(); i++) {
                    NoNullUtils.setText(textlist.get(i), uplist.get(i).getName() + "。");
                }
                wordWrapView.setVisibility(View.VISIBLE);
            } else {
                wordWrapView.setVisibility(View.GONE);
            }*/

            if (feedBean.getComments() != null) {
                CommentAdapter adapter = new CommentAdapter(context, feedBean.getComments(), R.layout.comment_item);
                comment.setVisibility(View.VISIBLE);
                comment.setAdapter(adapter);
                comment.setLayoutManager(new LinearLayoutManager(context));
                NoNullUtils.setVisible(comment, true);
            } else {
                NoNullUtils.setVisible(comment, false);
            }
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.logo);
            simpleDraweeView.setImageURI(feedBean.getUser().getAvatar());

        } else {
            LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
            params.height = 1;
            wuyuitem.setLayoutParams(params);
        }
    }
}

