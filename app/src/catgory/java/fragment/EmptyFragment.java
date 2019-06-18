package fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.Adapter.CommonAdapter;
import com.demo.swt.mystudyappshop.Adapter.ViewHolder;
import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 3/4/18
 */
public class EmptyFragment extends Fragment {
    List<String> stringList = new ArrayList<>();
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.empty_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.empty_rv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        for (int i = 0; i < 30; i++) {
            stringList.add("你好" + i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.empty_rv_item, stringList) {
            @Override
            public void convert(ViewHolder holder, String s) {
                TextView textView = holder.getView(R.id.empty_tv);
                textView.setText(s);
            }

        });

    }
}
