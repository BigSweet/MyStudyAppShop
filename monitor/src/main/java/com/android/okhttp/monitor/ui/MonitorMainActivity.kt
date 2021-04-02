package com.android.okhttp.monitor.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.okhttp.monitor.MonitorHelper
import com.android.okhttp.monitor.R
import com.android.okhttp.monitor.adapter.MonitorListAdapter
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.utils.getPhoneWifiIpAddress
import kotlinx.android.synthetic.main.activity_monitor_main.*

class MonitorMainActivity : AppCompatActivity() {

    private var adapter: MonitorListAdapter? = null

    private var handle: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor_main)
        initView()
        initRv()
//        mockData()
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        handle.removeCallbacksAndMessages(null)
    }

    private fun initView() {
        tv_title.text = getString(R.string.monitor_app_name)
        swipe_refresh.setOnRefreshListener {
            handle.postDelayed({
                swipe_refresh.isRefreshing = false
                setData()
            }, 1000)
        }

        tv_clean.setOnClickListener {
            MonitorHelper.deleteAll()
            setData()
        }
        getPhoneWifiIpAddress(this)?.let {
            tv_wifi_address.visibility = View.VISIBLE
            tv_wifi_address.text = "局域网内可访问：$it:8080/index"
        }
    }

    private fun setData() {
        val list = MonitorHelper.getMonitorDataList()
        adapter?.setData(list)
    }

    private fun initRv() {
        adapter = MonitorListAdapter()
        adapter?.itemClick = { gotoMonitorDetail(it) }
        rv_monitor.layoutManager = LinearLayoutManager(this)
        rv_monitor.adapter = adapter
    }

    private fun gotoMonitorDetail(monitorData: MonitorData) {
        startActivity(MonitorDetailActivity.buildIntent(this, monitorData))
    }

    private fun mockData() {
        val data = mutableListOf<MonitorData>()
        repeat(20) {
            data.add(
                    MonitorData()
            )
        }
        adapter?.setData(data)
    }
}