package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bean.FeedBean
import com.bumptech.glide.Glide
import com.demo.swt.mystudyappshop.Activity.BigImageActivity
import com.demo.swt.mystudyappshop.Adapter.CommentAdapter
import com.demo.swt.mystudyappshop.Adapter.UpsAdapter
import com.demo.swt.mystudyappshop.R
import com.demo.swt.mystudyappshop.Util.DetailTimeUtil
import com.demo.swt.mystudyappshop.Wight.NoNullUtils
import com.demo.swt.mystudyappshop.databinding.WuyuBinding
import com.demo.swt.mystudyappshop.retrofit.HeartData
import com.jaeger.ninegridimageview.NineGridImageViewAdapter
import com.spero.vision.ktx.inflateLayout
import com.umeng.socialize.utils.DeviceConfig.context
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.wuyu.*
import java.util.*


/**
 * Created by pc on 2016/12/7.
 */

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendHolder>() {
    private var mList: MutableList<HeartData> = mutableListOf()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(p0.context), R.layout.wuyu, p0, false)
        return FriendHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: FriendHolder, p1: Int) {
        val binding = DataBindingUtil.getBinding<WuyuBinding>(p0.itemView)
        binding?.friend = mList[p1]
        binding?.executePendingBindings()
    }

    fun setData(list: List<HeartData>?) {
        if (list == null) return
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(list: List<HeartData>?) {
        if (list == null) return
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(): List<HeartData> {
        return mList
    }

    class FriendHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

}

