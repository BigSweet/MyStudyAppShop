package com.android.okhttp.monitor.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.okhttp.monitor.R
import com.android.okhttp.monitor.adapter.MonitorPagerAdapter
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.utils.formatBody
import kotlinx.android.synthetic.main.activity_monitor_detail.*


class MonitorDetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_MONITOR_DATA = "monitor_data"
        fun buildIntent(context: Context, monitorData: MonitorData): Intent {
            return Intent(context, MonitorDetailActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putParcelable(KEY_MONITOR_DATA, monitorData)
                this.putExtras(bundle)
            }
        }
    }

    private var monitorData: MonitorData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor_detail)
        monitorData = intent.getParcelableExtra(KEY_MONITOR_DATA)
        initView()
    }

    private fun initView() {
        iv_back.setOnClickListener { finish() }
        iv_share.setOnClickListener { share() }
        tv_title.text = monitorData?.path
        val fragmentPagerAdapter = MonitorPagerAdapter(supportFragmentManager)
        fragmentPagerAdapter.addFragment(MonitorRequestFragment.newInstance(monitorData), "request")
        fragmentPagerAdapter.addFragment(MonitorResponseFragment.newInstance(monitorData), "response")
        viewPager.adapter = fragmentPagerAdapter
        tab_layout.setupWithViewPager(viewPager)
    }

    private fun share() {
        val shareString = "url = ${monitorData?.url} \n method = ${monitorData?.method} \n header = ${monitorData?.requestHeaders} \n requestBody = ${formatBody(monitorData?.requestBody
                ?: "", monitorData?.requestContentType)} \n responseBody = ${formatBody(monitorData?.responseBody
                ?: "", monitorData?.responseContentType)}"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString)
        startActivity(Intent.createChooser(shareIntent, "分享抓包数据"))
    }
}