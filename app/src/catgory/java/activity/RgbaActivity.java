package activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.widget.ImageView;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.Util.ImageHelper;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/17
 */

public class RgbaActivity extends FragmentActivity {
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private Bitmap mBitmap1,mBitmap2,mBitmap3,mBitmap4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rgba);
        imageView1 = (ImageView) findViewById(R.id.imageview1);
        imageView2 = (ImageView) findViewById(R.id.imageview2);
        imageView3 = (ImageView) findViewById(R.id.imageview3);
        imageView4 = (ImageView) findViewById(R.id.imageview4);
         mBitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.psb);
         mBitmap2 = ImageHelper.handleImageNegative(mBitmap1);
        mBitmap3 = ImageHelper.handleImagePixelsOldPhoto(mBitmap1);
        mBitmap4 = ImageHelper.handleImagePixelsRelief(mBitmap1);
        imageView2.setImageBitmap(mBitmap2);
        imageView3.setImageBitmap(mBitmap3);
        imageView4.setImageBitmap(mBitmap4);
    }
}
