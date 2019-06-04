package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.swt.mystudyappshop.Activity.BigImageActivity;
import com.demo.swt.mystudyappshop.Adapter.BaseAdapter;
import com.demo.swt.mystudyappshop.Adapter.CommentAdapter;
import com.demo.swt.mystudyappshop.Adapter.UpsAdapter;
import com.demo.swt.mystudyappshop.Holder.BaseHolder;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DetailTimeUtil;
import com.demo.swt.mystudyappshop.Wight.NoNullUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import bean.FeedBean;


/**
 * Created by pc on 2016/12/7.
 */

public class SearchAdapter extends BaseAdapter<FeedBean, BaseHolder> {
    private Context context;
    private List<FeedBean.UpsBean> uplist;
    private List<TextView> textlist = new ArrayList<>();
    private List<String> imglist;


    public SearchAdapter(Context context, List<FeedBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
        this.context = context;
    }

    @Override
    public void bindata(BaseHolder holder, FeedBean feedBean, int position) {
        if (feedBean.getFeedType() == 1) {
            LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            wuyuitem.setLayoutParams(params);
            if (feedBean.getContent() != null) {
                setText((TextView) holder.getView(R.id.wuyu_text), feedBean.getContent());
                NineGridImageView nineGridView = (NineGridImageView) holder.getView(R.id.cstimage);
                if (feedBean.getImageList() != null && feedBean.getImageList().size() > 0) {
                    imglist = feedBean.getImageList();
                    NineGridImageViewAdapter nineGridImageViewAdapter = new NineGridImageViewAdapter<String>() {
                        @Override
                        protected void onDisplayImage(Context context, ImageView imageView, String s) {
                            Glide.with(context).load(s).into(imageView);

                        }

                        @Override
                        protected void onItemImageClick(Context context, int index, List list) {
                            Intent intent = new Intent(context, BigImageActivity.class);
                            Bundle bundle = new Bundle();
                            if (list.size() > 0) {
                                bundle.putStringArrayList("tulist", (ArrayList<String>) list);
                                bundle.putInt("pos", index);
                            }
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    };
                    nineGridView.setAdapter(nineGridImageViewAdapter);
                    nineGridView.setImagesData(imglist);
                } else {
                    NoNullUtils.setVisible(nineGridView, false);
                }
            }
            setText((TextView) holder.getView(R.id.name), feedBean.getUser().getName());
            setText((TextView) holder.getView(R.id.school), feedBean.getUser().getEntityName());
            if (feedBean.getUps() != null) {
                setText((TextView) holder.getView(R.id.ups_size_tv), feedBean.getUps().size() + "人喜欢");
            }
            RecyclerView uoRv = (RecyclerView) holder.getView(R.id.ups_rv);

            RecyclerView comment = (RecyclerView) holder.getView(R.id.commentzan);
            uplist = feedBean.getUps();
            if (uplist != null && uplist.size() > 0) {
                UpsAdapter adapter = new UpsAdapter(context, uplist, R.layout.up_rv_item);
                NoNullUtils.setVisible(uoRv, true);
                uoRv.setAdapter(adapter);
                uoRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            } else {
                NoNullUtils.setVisible(uoRv, false);
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
            simpleDraweeView.setImageURI(feedBean.getUser().getAvatar() + "?x-oss-process=image/resize,h_200");
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            setText((TextView) holder.getView(R.id.displaytime), DetailTimeUtil.getTimeRange(feedBean.getCreateTime()));
        } else {
            LinearLayout wuyuitem = (LinearLayout) holder.getView(R.id.wuyuitemlayout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) wuyuitem.getLayoutParams();
            params.height = 1;
            wuyuitem.setLayoutParams(params);
        }
    }
}

