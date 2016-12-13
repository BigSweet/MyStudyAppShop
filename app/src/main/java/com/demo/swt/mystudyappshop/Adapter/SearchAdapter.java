package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DetailTimeUtil;
import com.demo.swt.mystudyappshop.Wight.CstImage;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
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

            RecyclerView comment = (RecyclerView) holder.getView(R.id.commentzan);
            TextView textView = (TextView) holder.getView(R.id.zan);
            LinearLayout zanlayout = (LinearLayout) holder.getView(R.id.layoutzan);
            StringBuffer stringBuffer = new StringBuffer();
            uplist = feedBean.getUps();
            if (feedBean.getUps().size() > 0) {
                for (int i = 0; i < uplist.size(); i++) {
                    stringBuffer.append(uplist.get(i).getName() + "。");
                }
                NoNullUtils.setText(textView, stringBuffer);
            } else {
                NoNullUtils.setVisible(zanlayout, false);
            }


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

            setText((TextView) holder.getView(R.id.displaytime), DetailTimeUtil.getTimeRange(feedBean.getDisplay_time()));
        } else {
            LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
            params.height = 1;
            wuyuitem.setLayoutParams(params);
        }
    }
}

