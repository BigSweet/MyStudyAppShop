package activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.swt.mystudyappshop.R
import kotlinx.android.synthetic.main.content_keyboard.*
import kotlinx.android.synthetic.main.cst_keyboard_activity.*
import widght.KeyBoardEditText


/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 2/4/18
 */


class CstKeyBoardActivity : AppCompatActivity() {

    // 滚动距离
    var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cst_keyboard_activity)


        ed_main.setKeyboardType(layout_main, view_keyboard!!, true)
        ed_main.listener = object : KeyBoardEditText.OnKeyboardStateChangeListener {
            override fun show() {
                ed_main.post {
                    val rect = IntArray(2)
                    ed_main.getLocationOnScreen(rect)
                    val y = rect[1]

                    view_keyboard?.getLocationOnScreen(rect)
                    val keyboardY = rect[1]

                    height = y - (keyboardY - ed_main.measuredHeight)
                    layout_root.scrollBy(0, height)
                }

            }

            override fun hide() {
                layout_root.scrollBy(0, -height)
            }
        }
    }
}