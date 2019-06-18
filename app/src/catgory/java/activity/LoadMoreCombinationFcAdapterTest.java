package activity;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rjhy on 15-3-6.
 */
public class LoadMoreCombinationFcAdapterTest extends RecyclerView.Adapter {

    private List<String> data = new ArrayList<>();
    private Context context;

    public LoadMoreCombinationFcAdapterTest(Context context) {
        this.context = context;
    }

    public void addAll(List<String> list) {
        data.addAll(list);
        notifyItemRangeInserted(data.size() - list.size(), list.size());
    }

    public void reset(List<String> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MainViewHolder)holder).textView.setText(data.get(position));
    }

    static class MainViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MainViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tv_text);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
