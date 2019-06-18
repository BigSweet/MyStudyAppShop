package activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/2
 */
public class CstTouchViewActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cst_touch_activity);
    }

    public void opentouch(View view) {
        Intent intent = new Intent(CstTouchViewActivity.this, CstTouchViewScondActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.cst_bottom_in,0);
    }
}
