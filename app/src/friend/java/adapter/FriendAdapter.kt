package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import bean.FeedBean
import com.bumptech.glide.Glide
import com.demo.swt.mystudyappshop.Activity.BigImageActivity
import com.demo.swt.mystudyappshop.Adapter.CommentAdapter
import com.demo.swt.mystudyappshop.Adapter.UpsAdapter
import com.demo.swt.mystudyappshop.R
import com.demo.swt.mystudyappshop.Util.DetailTimeUtil
import com.demo.swt.mystudyappshop.Wight.NoNullUtils
import com.jaeger.ninegridimageview.NineGridImageViewAdapter
import com.spero.vision.ktx.inflateLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.wuyu.*
import java.util.*


/**
 * Created by pc on 2016/12/7.
 */

class FriendAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<FriendAdapter.FriendHolder>() {
    private var mList: MutableList<FeedBean> = mutableListOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendHolder {
        return FriendHolder(p0.inflateLayout(R.layout.wuyu))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: FriendHolder, p1: Int) {
        p0.binData(mList[p1])
    }

    fun setData(list: List<FeedBean>?) {
        if (list == null) return
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<FeedBean>?) {
        if (list == null) return
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(): List<FeedBean> {
        return mList
    }

    class FriendHolder(override var containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun binData(feedBean: FeedBean) {
            if (feedBean.feedType == 1) {
                setParams(LinearLayout.LayoutParams.WRAP_CONTENT)
                setContent(feedBean)
                setText(feedBean)
                setUpList(feedBean)
                setComment(feedBean)
            } else {
                setParams(1)
            }
        }

        private fun setText(feedBean: FeedBean) {
            name.text = feedBean.user.name
            school.text = feedBean.user.entityName
            ups_size_tv.text = feedBean?.ups?.size.toString() + "人喜欢"
            displaytime.text = DetailTimeUtil.getTimeRange(feedBean.createTime)
            logo.setImageURI(feedBean.user.avatar + "?x-oss-process=image/resize,h_200")
            logo.scaleType = ImageView.ScaleType.CENTER_CROP
        }

        private fun setParams(height: Int) {
            val params = wuyuitemlayout.layoutParams as LinearLayout.LayoutParams
            params.height = height
            wuyuitemlayout.layoutParams = params
        }

        private fun setUpList(feedBean: FeedBean) {
            var uplist = feedBean.ups
            if (uplist != null && uplist!!.size > 0) {
                val adapter = UpsAdapter(containerView.context, uplist, R.layout.up_rv_item)
                NoNullUtils.setVisible(ups_rv, true)
                ups_rv.adapter = adapter
                ups_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(containerView.context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
            } else {
                NoNullUtils.setVisible(ups_rv, false)
            }

        }

        private fun setComment(feedBean: FeedBean) {
            if (feedBean.comments != null) {
                val adapter = CommentAdapter(containerView.context, feedBean.comments, R.layout.comment_item)
                commentzan.visibility = View.VISIBLE
                commentzan.adapter = adapter
                commentzan.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(containerView.context)
                NoNullUtils.setVisible(commentzan, true)
            } else {
                NoNullUtils.setVisible(commentzan, false)
            }
        }

        private fun setContent(feedBean: FeedBean) {
            var imglist: List<String>? = null
            if (feedBean.content != null) {
                wuyu_text.text = feedBean.content
                if (feedBean.imageList != null && feedBean.imageList.size > 0) {
                    imglist = feedBean.imageList
                    val nineGridImageViewAdapter = object : NineGridImageViewAdapter<String>() {
                        override fun onDisplayImage(context: Context, imageView: ImageView, s: String) {
                            Glide.with(context).load(s).into(imageView)

                        }
                        override fun onItemImageClick(context: Context?, index: Int, list: MutableList<String>) {
                            super.onItemImageClick(context, index, list)
                            val intent = Intent(context, BigImageActivity::class.java)
                            val bundle = Bundle()
                            if (list.size > 0) {
                                bundle.putStringArrayList("tulist", list as ArrayList<String>)
                                bundle.putInt("pos", index)
                            }
                            intent.putExtras(bundle)
                            context!!.startActivity(intent)
                        }
                    }
                    cstimage.setAdapter(nineGridImageViewAdapter)
                    cstimage.setImagesData(imglist)
                } else {
                    NoNullUtils.setVisible(cstimage, false)
                }
            }

        }

    }


}

