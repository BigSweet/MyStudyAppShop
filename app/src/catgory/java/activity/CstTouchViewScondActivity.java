package activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.demo.swt.mystudyappshop.Activity.DraggableListener;
import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.DraggableScrollView;
import com.demo.swt.mystudyappshop.Util.DraggableView;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/2
 */
public class CstTouchViewScondActivity extends FragmentActivity implements DraggableListener,DraggableScrollView.ScrollListener{
    DraggableView dragView;
    DraggableScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cst_touch_activity2);
        dragView = findViewById(R.id.draggable);
        dragView.setDraggableListener(this);
        scrollView = findViewById(R.id.scroll_view);
        scrollView.setOnScrollListener(this);
    }

    @Override
    public void onClosedToBottom() {
        Toast.makeText(this, "关闭页面", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        }, 500);

    }

    /**
     * 取消Activity关闭动画
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, 0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClosedToLeft() {
        Toast.makeText(CstTouchViewScondActivity.this, "向左切换，加载下一个视频...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dragView.show();
                Toast.makeText(CstTouchViewScondActivity.this, "视频数据加载完成", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }

    @Override
    public void onClosedToRight() {
        Toast.makeText(CstTouchViewScondActivity.this, "向右切换，加载上一个视频...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                dragView.show();
                Toast.makeText(CstTouchViewScondActivity.this, "视频数据加载完成", Toast.LENGTH_SHORT).show();
            }
        }, 500);
    }

    @Override
    public void onBackgroundChanged(int top) {
        int newAlpha = 255 - (int) (255 * ((float) top / (float) dragView.getRootView().getHeight()));

        if (newAlpha == 255) {
//            dragView.setBackgroundResource(R.drawable.bg_gauss_blur);
        } else {
//            dragView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground));
        }

        dragView.getBackground().setAlpha(newAlpha);
        if (newAlpha < 216) { //达到子控件缩放最小值，原大小的0.85倍
            scrollView.setScaleX(0.85f);
            scrollView.setScaleY(0.85f);
        } else {// newAlpha >= 204 平滑缩放
            scrollView.setScaleX(1 - (255.0f - (float) newAlpha) / 255);
            scrollView.setScaleY(1 - (255.0f - (float) newAlpha) / 255);
        }
    }

    @Override
    public void isOnTop(boolean isTop) {
        dragView.setScrollToTop(isTop);
    }

    @Override
    public void onScrollChanged(int tY) {

    }

    @Override
    public boolean isForbidden() {
        return false;
    }
}
