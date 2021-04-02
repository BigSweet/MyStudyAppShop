package com.android.okhttp.monitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.okhttp.monitor.R
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.ui.MonitorDetailActivity.Companion.KEY_MONITOR_DATA
import com.android.okhttp.monitor.utils.TIME_LONG
import com.android.okhttp.monitor.utils.formatBody
import com.android.okhttp.monitor.utils.formatData
import kotlinx.android.synthetic.main.fragment_monitor_response.*

class MonitorResponseFragment : Fragment() {

    companion object {
        fun newInstance(monitorData: MonitorData?): MonitorResponseFragment {
            return MonitorResponseFragment().apply {
                arguments = Bundle().apply { putParcelable(KEY_MONITOR_DATA, monitorData) }
            }
        }
    }

    private var monitorData: MonitorData? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monitor_response, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monitorData = arguments?.getParcelable(KEY_MONITOR_DATA)
        initData()
    }

    private fun initData() {
        tv_url.text = monitorData?.url
        tv_method.text = monitorData?.method
        tv_code.text = monitorData?.responseCode.toString()
        tv_response_date.text = monitorData?.responseDate?.formatData(TIME_LONG)
        tv_response_body.text = formatBody(monitorData?.responseBody
                ?: return, monitorData?.responseContentType)
    }
}