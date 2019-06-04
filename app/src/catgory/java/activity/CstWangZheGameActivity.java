package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.CstWZRYLevelProgress;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/5
 */
public class CstWangZheGameActivity extends FragmentActivity implements View.OnClickListener {

    CstWZRYLevelProgress myProgressBar;
    Button button1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cst_wangzhe_activity);
        myProgressBar = findViewById(R.id.progress_bar);
        myProgressBar.setLevels(4);
        String[] texts = {"倔强青铜", "持续白银", "荣耀黄金", "尊贵铂金"};
        myProgressBar.setLevelTexts(texts);
        myProgressBar.setCurrentLevel(1);
        myProgressBar.setAnimInterval(10);


         button1 = findViewById(R.id.level1);
        button1.setOnClickListener(this);
        Button button2 = findViewById(R.id.level2);
        button2.setOnClickListener(this);
        Button button3 = findViewById(R.id.level3);
        button3.setOnClickListener(this);
        Button button4 = findViewById(R.id.level4);
        button4.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.level1:
                myProgressBar.setCurrentLevel(1);
                myProgressBar.setAnimInterval(10);
                break;
            case R.id.level2:
                myProgressBar.setCurrentLevel(2);
                myProgressBar.setAnimInterval(10);
                break;
            case R.id.level3:
                myProgressBar.setCurrentLevel(3);
                myProgressBar.setAnimInterval(10);
                break;
            case R.id.level4:
                myProgressBar.setCurrentLevel(4);
                myProgressBar.setAnimInterval(10);
                break;
        }
    }
}
