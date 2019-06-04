package activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.demo.swt.mystudyappshop.R
import fragment.EmptyFragment
import kotlinx.android.synthetic.main.nes_scroll_activity.*


/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 3/4/18
 */

class NesScrollViewActivty : AppCompatActivity() {
    var fragmentlist = arrayListOf<Fragment>()
    private val mTitles = arrayOf("社团介绍", "活动", "讨论区")
    private var imgList: MutableList<Any>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nes_scroll_activity)
        initView()
        initHead()
    }

    private fun initHead() {
        nes_scroll!!.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val height = nes_scroll!!.height
                        if (height != 0) {
                            val layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT, height)
                            head!!.layoutParams = layoutParams
//                            scrollY = (choice_list_main_head.height + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30F, choice_list_main_head.context.resources.displayMetrics)).toInt()
//                            choice_list_main_fns.setCanScrollY(scrollY)
//                            choice_list_main_fns.setTouchNoScrollRange(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, choice_list_main_fns.context.resources.displayMetrics),
//                                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, choice_list_main_fns.context.resources.displayMetrics) + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, choice_list_main_fns.context.resources.displayMetrics))
//                            choice_list_main_fns.touchOffset(statusBarHeight)
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                nes_scroll!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            } else {
                                nes_scroll!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
                            }
                        }
                    }
                })
    }

    private fun initView() {

        for (i in 1..3) {
            var fragment = EmptyFragment()
            fragmentlist.add(fragment)
        }

        vp_club_main.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragmentlist.get(position)
            }

            override fun getCount(): Int {
                return fragmentlist.size
            }

            override fun getPageTitle(position: Int): CharSequence {
                return mTitles.get(position)
            }

        }
        vp_club_main.offscreenPageLimit=3
        tab_layout_club_main.setupWithViewPager(vp_club_main)
    }


}