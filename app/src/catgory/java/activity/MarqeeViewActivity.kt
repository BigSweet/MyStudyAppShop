package activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
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
        lottieAnimationView.setAnimation("data.json")
        lottieAnimationView.playAnimation()

        LottieCompositionFactory.fromAsset(this,"data.json","11")
    }
}