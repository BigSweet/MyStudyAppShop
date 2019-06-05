package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.demo.swt.mystudyappshop.Wight.NoNullUtils.setText
import com.jaeger.ninegridimageview.NineGridImageViewAdapter
import com.spero.vision.ktx.inflateLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.wuyu.*
import java.util.*


/**
 * Created by pc on 2016/12/7.
 */

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {
    private var mList: MutableList<FeedBean> = mutableListOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendHolder {
        return FriendHolder(p0.inflateLayout(R.layout.wuyu))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: FriendHolder, p1: Int) {
        p0.binData(mList[p1], p1)
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

    class FriendHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun binData(feedBean: FeedBean, position: Int) {
            if (feedBean.feedType == 1) {
                var imglist: List<String>? = null

                val params = wuyuitemlayout.layoutParams as LinearLayout.LayoutParams
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                wuyuitemlayout.layoutParams = params
                if (feedBean.content != null) {
                    setText(wuyu_text, feedBean.content)

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
                setText(name, feedBean.user.name)
                setText(school, feedBean.user.entityName)
                if (feedBean.ups != null) {
                    setText(ups_size_tv, feedBean.ups.size.toString() + "人喜欢")
                }

                var uplist = feedBean.ups
                if (uplist != null && uplist!!.size > 0) {
                    val adapter = UpsAdapter(containerView.context, uplist, R.layout.up_rv_item)
                    NoNullUtils.setVisible(ups_rv, true)
                    ups_rv.adapter = adapter
                    ups_rv.layoutManager = LinearLayoutManager(containerView.context, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    NoNullUtils.setVisible(ups_rv, false)
                }

                if (feedBean.comments != null) {
                    val adapter = CommentAdapter(containerView.context, feedBean.comments, R.layout.comment_item)
                    commentzan.visibility = View.VISIBLE
                    commentzan.adapter = adapter
                    commentzan.layoutManager = LinearLayoutManager(containerView.context)
                    NoNullUtils.setVisible(commentzan, true)
                } else {
                    NoNullUtils.setVisible(commentzan, false)
                }
                logo.setImageURI(feedBean.user.avatar + "?x-oss-process=image/resize,h_200")
                logo.scaleType = ImageView.ScaleType.CENTER_CROP
                setText(displaytime, DetailTimeUtil.getTimeRange(feedBean.createTime))
            } else {
                val params = wuyuitemlayout.layoutParams as LinearLayout.LayoutParams
                params.height = 1
                wuyuitemlayout.layoutParams = params
            }
        }

    }


}

