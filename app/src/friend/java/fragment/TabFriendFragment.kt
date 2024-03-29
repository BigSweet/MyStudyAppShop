package fragment

import adapter.FriendAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import bean.FeedBean
import bean.FeedBeanList
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener
import com.demo.swt.mystudyappshop.R
import com.demo.swt.mystudyappshop.Wight.RecyclerLinearDivider
import com.demo.swt.mystudyappshop.retrofit.HeartData
import com.demo.swt.mystudyappshop.retrofit.RetrofitManager
import io.reactivex.disposables.Disposable
import ioToMain
import kotlinx.android.synthetic.main.search.*

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

    private var feedList: List<HeartData>? = ArrayList()
    private var moreList: List<HeartData>? = ArrayList()
    private var myAdapter: FriendAdapter? = null
    private var nt = ""
    private var state = STATE_NORMAL
    private var mHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivAd.visibility = View.VISIBLE
        requestRecyclerView(page)
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


    var page = 0
    private fun initRefresh() {
        refresh.setLoadMore(true)
        refresh.setMaterialRefreshListener(object : MaterialRefreshListener() {
            override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout) {
                state = STATE_REFRESH
                page = 0
                requestRecyclerView(page)
            }

            override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
                super.onRefreshLoadMore(materialRefreshLayout)
                state = STATE_LOADMORE
                requestRecyclerView(++page)
            }
        })
        viewModel.data.observe(this, Observer {
            if (it.data != null) {
//                feedList = it.data.list
//                moreList = it.data.list
                initData()
                progress.visibility = View.GONE
            }
        })
    }

    val viewModel by lazy { ViewModelProviders.of(this).get(FriendViewModel::class.java) }


    //请求recyclerview的数据
    private fun requestRecyclerView(page: Int) {
//        viewModel.getFriend("sjasDIv1so/EtjcEnuJiMCZ9Hg8YSv6TTMjcD3Wb2BFmLAOuTGnPPeFDPCYM2+oIMOERmRD8o4p6\n" +
//                "Es7ykySnBgEq4NGyjSk3lDdTp2mEBUnHTgxXNuJFB1u4g4MMWlOkkrROB+GkzLLtM/e5m38NP4z7\n" +
//                "YEISOyj9uZk56SiTEHCAVEV37o3mBON33ConeoxH/8zf+LC4zcPdcGVxyR3xPxAwK0APdqOyN9ls\n" +
//                "s4iN4ohlL9BW/YpE5yMLHuDWaySYaU/of7Plpqespux/K1Kcjytcr67pIC8p+aOTvxlSMNh/Hajx\n" +
//                "BXUujSvHELhHcPL6spJWfhVR9v5FZVAS/jdsToSAwUrcIvA7cUvpIyY4sHc26OmfkYYS8enINcNV\n" +
//                "KdR3UTCgbLCMjxj4Z6Ax04b5i330rwbhEYCCHzRsjRHMxno=")
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
