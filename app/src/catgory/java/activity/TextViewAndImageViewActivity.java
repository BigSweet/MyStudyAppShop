package activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.CustomImageSpan;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2017/6/19
 */
public class TextViewAndImageViewActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_iv_activity);
        init();
    }

    private void init() {

        TextView tv_test = (TextView) findViewById(R.id.tv_test);
        SpannableString spannableString = new SpannableString("点击 按钮 有惊喜  ");

        //调用自定义的imageSpan,实现文字与图片的横向居中对齐
        CustomImageSpan imageSpan = new CustomImageSpan(this, R.mipmap.ic_launcher, 2);

        //setSpan插入内容的时候，起始位置不替换，会替换起始位置到终止位置间的内容，含终止位置。
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE模式用来控制是否同步设置新插入的内容与start/end 位置的字体样式，此处没设置具体字体，所以可以随意设置
//        spannableString.setSpan(imageSpan, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(imageSpan, spannableString.length()-1, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_test.setText(spannableString);
    }
}
