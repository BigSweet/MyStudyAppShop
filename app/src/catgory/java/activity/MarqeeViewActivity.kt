package activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.demo.swt.mystudyappshop.R
import kotlinx.android.synthetic.main.marqee_view_activity.*


/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 24/4/18
 */

class MarqeeViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.marqee_view_activity)
        mr_tv.setContent("窥天意竭心力，皆为吾主……")

    }
}