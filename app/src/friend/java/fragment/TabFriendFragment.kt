package fragment

import adapter.FriendAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bean.FeedBean
import bean.FeedBeanList
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.demo.swt.mystudyappshop.R
import com.demo.swt.mystudyappshop.Wight.RecyclerLinearDivider
import com.demo.swt.mystudyappshop.retrofit.RetrofitManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.search.*
import java.util.*

/**
 * Created by pc on 2016/11/29.
 */

class TabFriendFragment : androidx.fragment.app.Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(): TabFriendFragment {

            val args = Bundle()

            val fragment = TabFriendFragment()
            fragment.arguments = args
            return fragment
        }

        private const val STATE_NORMAL = 0
        private const val STATE_REFRESH = 1
        private const val STATE_LOADMORE = 2
    }

    private var feedList: List<FeedBean>? = ArrayList()
    private var moreList: List<FeedBean>? = ArrayList()
    private var myAdapter: FriendAdapter? = null
    private var nt = ""
    private var state = STATE_NORMAL
    private var feedbeanlist: FeedBeanList? = null
    private var mHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivAd.visibility = View.VISIBLE
        requestRecyclerView("")
        initRecyclerView()
        initRefresh()
    }

    private fun initRecyclerView() {
        myAdapter = FriendAdapter()
        home_recycle.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        home_recycle.addItemDecoration(RecyclerLinearDivider(activity, androidx.recyclerview.widget.RecyclerView.VERTICAL, 5, resources.getColor(R.color.gray1)))
        home_recycle.adapter = myAdapter
        home_recycle.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            var offset: Float = activity!!.resources.getDimensionPixelSize(R.dimen.q80).toFloat()
            var hide: ObjectAnimator? = null

            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mHandler.removeCallbacksAndMessages(null)
                if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE) {
                    mHandler.postDelayed({ showAnim(0f) }, 1000)
                } else if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING) {
                    showAnim(offset)
                }
            }

            private fun showAnim(offsetX: Float) {
                if (hide != null) {
                    hide?.cancel()
                }
                hide = ObjectAnimator.ofFloat(ivAd, "translationX", offsetX).setDuration(200)
                hide?.start()
            }
        })
    }


    private fun initRefresh() {
        refresh.setLoadMore(true)
        refresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
            override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                state = STATE_REFRESH
                requestRecyclerView("")
            }

            override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
                super.onRefreshLoadMore(materialRefreshLayout)
                state = STATE_LOADMORE
                nt = feedbeanlist?.data?.nt ?: ""
                requestRecyclerView(nt)
            }
        })
    }


    //请求recyclerview的数据
    private fun requestRecyclerView(nt: String) {
        RetrofitManager.getInstance().getFriend(nt).subscribe(object : Observer<FeedBeanList> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(response: FeedBeanList) {
                if (response.data != null) {
                    feedList = response.data.list
                    moreList = response.data.list
                    feedbeanlist = response
                    initData()
                    progress.visibility = View.GONE
                }
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        })
    }


    //设置recyclerview的数据和基本布局
    private fun initData() {
        when (state) {
            STATE_NORMAL -> myAdapter?.addData(feedList)
            STATE_LOADMORE -> {
                myAdapter?.addData(feedList)
                home_recycle.scrollToPosition(myAdapter?.getData()?.size ?: 0)
                refresh.finishRefreshLoadMore()
            }

            STATE_REFRESH -> {
                myAdapter?.setData(moreList)
                refresh.finishRefresh()
            }
        }
    }
}
