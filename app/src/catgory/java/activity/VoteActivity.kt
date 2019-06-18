package activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.demo.swt.mystudyappshop.R
import kotlinx.android.synthetic.main.activity_vote.*

class VoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        toupiao.post {
            toupiao.setSupportWeight(10f,10f)
            toupiao.setSupportColor(ContextCompat.getColor(this, R.color.colorAccent))
            toupiao.setOppColor(ContextCompat.getColor(this, R.color.colorPrimary))
            toupiao.setAnimDuration(500)
            toupiao.startAnimation()
        }

    }

    var numSupport = 1f
    var num = 20f

    fun setred(view: View) {
        numSupport++
        toupiao.setSupportWeight(numSupport,4f)
    }

    fun setblue(view: View) {
        num--
        toupiao.setSupportWeight(num,4f)
    }
}
