package com.demo.swt.mystudyappshop

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.swt.mystudyappshop.Wight.HeadListener
import com.demo.swt.mystudyappshop.Wight.SmartRefreshHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.TwoLevelHeader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_pull_down.*
import kotlinx.android.synthetic.main.pull_down_item.*


class PullDownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_down)
        var list = arrayListOf<String>()
        for (i in 0..20) {
            list.add("test" + i)
        }
        var adapter = object : RecyclerView.Adapter<TestViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
                return TestViewHolder(LayoutInflater.from(baseContext).inflate(R.layout.pull_down_item, null))
            }

            override fun getItemCount(): Int {
                return list.size
            }

            override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
                holder.bindData(list[position])
            }
        }
        test.adapter = adapter
        test.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()

        val head = SmartRefreshHeader(baseContext)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            head
        }
        smart_refresh.setOnRefreshListener {

        }
        smart_refresh.upListener = {
            head.actionUp()
        }
        head.listener = object : HeadListener {
            override fun twoListener() {
                Toast.makeText(application, "打开二楼", Toast.LENGTH_SHORT).show()
            }

            override fun expand() {
                smart_refresh.finishRefresh()
            }

            override fun open() {
//                smart_refresh.finishRefresh()
//                smart_refresh.autoRefresh(0, 400, 1f, true)
            }
        }
        smart_refresh.autoRefresh(0, 50, 1f, true)

        Handler().postDelayed({
//                smart_refresh.finishRefresh()
        }, 3000)
    }

    class TestViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindData(s: String) {
            pull_down_tv.text = s
        }
    }
}