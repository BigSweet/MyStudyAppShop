package activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.swt.mystudyappshop.R
import kotlinx.android.synthetic.main.pop_windows.*
import widght.CstPopWindows


/**
 * introduce：这里写介绍
 * createBy：sunwentao
 * email：wentao.sun@yintech.cn
 * time: 26/3/2019
 */
class PopWindowsActivity : AppCompatActivity() {
    private var sharePopWindows: CstPopWindows? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_windows)
    }

    fun lefttop(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showLeftTop(pop_tv)
    }

    fun righttop(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showRightTop(pop_tv)
    }
    fun rightbottom(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showRightBottom(pop_tv)
    }

    fun leftbottom(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showLeftBottom(pop_tv)
    }

    fun asdownleftbottom(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showAsDownLeftBottom(pop_tv)
    }

    fun asdownlefttop(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showAsTopLeftTop(pop_tv)
    }
    fun asdownrightbottom(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showAsDownRightBottom(pop_tv)
    }
    fun asdownrighttop(view: View) {
        if (sharePopWindows == null) {
            sharePopWindows = CstPopWindows(this)
        }
        sharePopWindows?.showAsTopRightTop(pop_tv)
    }
}