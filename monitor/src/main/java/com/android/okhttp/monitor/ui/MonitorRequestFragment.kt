package com.android.okhttp.monitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.okhttp.monitor.R
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.utils.TIME_LONG
import com.android.okhttp.monitor.utils.formatBody
import com.android.okhttp.monitor.utils.formatData
import kotlinx.android.synthetic.main.fragment_monitor_request.*

class MonitorRequestFragment : Fragment() {

    companion object {
        fun newInstance(monitorData: MonitorData?): MonitorRequestFragment {
            return MonitorRequestFragment().apply {
                arguments = Bundle().apply { putParcelable(MonitorDetailActivity.KEY_MONITOR_DATA, monitorData) }
            }
        }
    }

    private var monitorData: MonitorData? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monitor_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monitorData = arguments?.getParcelable(MonitorDetailActivity.KEY_MONITOR_DATA)
        initData()
    }

    private fun initData() {
        tv_url.text = monitorData?.url
        tv_method.text = monitorData?.method
        tv_request_date.text = monitorData?.requestDate?.formatData(TIME_LONG)
        tv_header.text = monitorData?.requestHeaders
        if (monitorData?.requestBody.isNullOrBlank()) return
        tv_request_body.text = formatBody(monitorData?.requestBody
                ?: return, monitorData?.requestContentType)
    }
}