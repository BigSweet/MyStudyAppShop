package com.demo.swt.mystudyappshop.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageUtils;
import com.demo.swt.mystudyappshop.bean.FeedBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 2016/12/2.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int LEFT_TYPE = 1000;
    private static int RIGHT_TYPE = 2000;
    private List<FeedBean> mlist;
    private LayoutInflater inflater;
    private Context mcontext;

    public MyAdapter(Context context, List<FeedBean> list) {
        this.mcontext = context;
        this.mlist = list;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

     /*   if (viewType == LEFT_TYPE) {

            return new MyLeftHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.toast, parent, false));
        }*/
        //   return new MyRightHolder(inflater.inflate(R.layout.home_rv_right_item, parent, false));
        return new wuyuHolder(inflater.inflate(R.layout.wuyu, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        wuyuHolder wuyuHolder = (MyAdapter.wuyuHolder) holder;
        wuyuHolder.textView.setText(mlist.get(position).getDisplay_time());
       /* if (getItemViewType(position) == LEFT_TYPE) {
            MyLeftHolder myLeftHolder = (MyLeftHolder) holder;
            //myLeftHolder.big.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(0)));
            myLeftHolder.bottom.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(1)));
            myLeftHolder.top.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(2)));
            //Picasso.with(mcontext)

        } else if (getItemViewType(position) == RIGHT_TYPE) {
            MyRightHolder myRightHolder = (MyRightHolder) holder;
            myRightHolder.big.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(0)));
            myRightHolder.bottom.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(1)));
            myRightHolder.top.setImageBitmap(ImageUtils.getBitMapFromUri(mlist.get(position).getPost().getImages().get(2)));
        }*/
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

   /* class MyLeftHolder extends RecyclerView.ViewHolder {
         ImageView top, bottom, big;

        public MyLeftHolder(View itemView) {
            super(itemView);
            top = (ImageView) itemView.findViewById(R.id.imageview_top);
            bottom = (ImageView) itemView.findViewById(R.id.imageview_big);
            big = (ImageView) itemView.findViewById(R.id.imageviwe_bottom);
        }
    }

    class MyRightHolder extends RecyclerView.ViewHolder {
        ImageView top, bottom, big;

        public MyRightHolder(View itemView) {
            super(itemView);
            top = (ImageView) itemView.findViewById(R.id.imageview_right_top);
            bottom = (ImageView) itemView.findViewById(R.id.imageview_right_big);
            big = (ImageView) itemView.findViewById(R.id.imageview_right_bottom);
        }
    }*/

    class wuyuHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public wuyuHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.wuyu_text);
        }
    }


   /* @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return LEFT_TYPE;
        } else return RIGHT_TYPE;
    }*/
}
