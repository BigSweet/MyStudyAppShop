package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/1/18
 */

public class QrCodeActivity extends FragmentActivity {
    private TextView textView;
    private static final int FROM_CAP = 1;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        textView = (TextView) findViewById(R.id.scanresult);
        editText = (EditText) findViewById(R.id.edit_text);
        imageView = (ImageView) findViewById(R.id.resultimg);
        findViewById(R.id.scanbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(QrCodeActivity.this, CaptureActivity.class), FROM_CAP);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            textView.setText(result);
        }
    }

    public void createqr(View view) {
        Bitmap bitmap = EncodingUtils.createQRCode(editText.getText().toString(), 600, 600, BitmapFactory.decodeResource(getResources(), R.mipmap.psb));
        imageView.setImageBitmap(bitmap);
    }
}
