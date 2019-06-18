package com.demo.swt.mystudyappshop.Activity;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.bean.ChatMessage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/14
 */

public class ChatMegAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;

    public ChatMegAdapter(Context context, List<ChatMessage> datas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.item_from_msg, parent, false);
            LeftViewHolder leftViewHolder = new LeftViewHolder(view);
            return leftViewHolder;
        } else {
            view = mInflater.inflate(R.layout.item_to_msg, parent, false);
            RightViewHolder rightViewHolder = new RightViewHolder(view);
            return rightViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ChatMessage chatMessage = mDatas.get(position);
        if (getItemViewType(position) == 0) {
            LeftViewHolder leftViewHolder = (LeftViewHolder) holder;
            leftViewHolder.mDate.setText(df.format(chatMessage.getDate()));
            leftViewHolder.mMsg.setText(chatMessage.getMsg());
            leftViewHolder.mUrl.setImageURI(chatMessage.getUrl());
        } else {
            RightViewHolder rightViewHolder = (RightViewHolder) holder;
            rightViewHolder.mDate.setText(df.format(chatMessage.getDate()));
            rightViewHolder.mMsg.setText(chatMessage.getMsg());
        }
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class LeftViewHolder extends RecyclerView.ViewHolder {

        private TextView mDate;
        private TextView mMsg;
        private SimpleDraweeView mUrl;

        public LeftViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView
                    .findViewById(R.id.id_form_msg_date);
            mMsg = (TextView) itemView
                    .findViewById(R.id.id_from_msg_info);
            mUrl = (SimpleDraweeView) itemView.findViewById(R.id.id_from_msg_url);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView mMsg;

        public RightViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView
                    .findViewById(R.id.id_to_msg_date);
            mMsg = (TextView) itemView
                    .findViewById(R.id.id_to_msg_info);
        }
    }
}
