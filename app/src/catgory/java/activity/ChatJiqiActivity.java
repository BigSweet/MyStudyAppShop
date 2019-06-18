package activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.Activity.ChatMegAdapter;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ChatUtil;
import com.demo.swt.mystudyappshop.bean.ChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/14
 */

public class ChatJiqiActivity extends FragmentActivity {
//    private ListView mMsgs;
    private RecyclerView msgs;
//    private ChatMessageAdapter mAdapter;
    private ChatMegAdapter mAdapter;
    private List<ChatMessage> mDatas;

    private EditText mInputMsg;
    private Button mSendMsg;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 等待接收，子线程完成数据的返回
            ChatMessage fromMessge = (ChatMessage) msg.obj;
            mDatas.add(fromMessge);
            mAdapter.notifyDataSetChanged();
//            mMsgs.setSelection(mDatas.size() - 1);
            msgs.scrollToPosition(mDatas.size() - 1);
        }

        ;

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiqiren);
        initView();
        initDatas();
        // 初始化事件
        initListener();
    }


    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(ChatJiqiActivity.this, "发送消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();
//                mMsgs.setSelection(mDatas.size() - 1);
                msgs.scrollToPosition(mDatas.size() - 1);
                mInputMsg.setText("");

                new Thread() {
                    public void run() {
                        ChatMessage fromMessage = ChatUtil.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);
                    }

                    ;
                }.start();

            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好 我是颜大傻 为你服务", ChatMessage.Type.INCOMING, new Date()));
//        mAdapter = new ChatMessageAdapter(this, mDatas);
        mAdapter = new ChatMegAdapter(this, mDatas);
        msgs.setLayoutManager(new LinearLayoutManager(this));
        msgs.setAdapter(mAdapter);
//        mMsgs.setAdapter(mAdapter);
    }

    private void initView() {
//        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        msgs = (RecyclerView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_input_msg);
        mSendMsg = (Button) findViewById(R.id.id_send_msg);
    }
}
