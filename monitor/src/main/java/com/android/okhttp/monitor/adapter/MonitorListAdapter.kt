package com.android.okhttp.monitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.okhttp.monitor.R
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.utils.TIME_LONG
import com.android.okhttp.monitor.utils.formatData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_monitor.*

class MonitorListAdapter : RecyclerView.Adapter<MonitorListAdapter.MonitorListHolder>() {

    var itemClick: ((MonitorData) -> Unit)? = null

    private var monitorList: MutableList<MonitorData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonitorListHolder {
        return MonitorListHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_monitor,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int = monitorList.size

    override fun onBindViewHolder(holder: MonitorListHolder, position: Int) {
        holder.bindData(monitorList[position], itemClick)
    }

    fun setData(datas: MutableList<MonitorData>?) {
        this.monitorList = datas ?: mutableListOf()
        notifyDataSetChanged()
    }

    class MonitorListHolder(override var containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        var itemClick: ((MonitorData) -> Unit)? = null
        var monitorData: MonitorData? = null

        init {
            containerView.setOnClickListener {
                itemClick?.invoke(
                        monitorData ?: return@setOnClickListener
                )
            }
        }

        fun bindData(monitorData: MonitorData, itemClick: ((MonitorData) -> Unit)?) {
            this.monitorData = monitorData
            this.itemClick = itemClick

            tv_host.text = monitorData.host
            tv_path.text = monitorData.path
            tv_request_date.text = monitorData.requestDate.formatData(TIME_LONG)
            tv_duration.visibility = if (monitorData.duration == null) View.GONE else View.VISIBLE
            tv_duration.text = "${monitorData.duration}ms"
            tv_code.text = monitorData.responseCode.toString()
            tv_method.text = monitorData.method

            tv_path.setTextColor(getColor(monitorData.responseCode))
            tv_code.setTextColor(getColor(monitorData.responseCode))
        }

        private fun getColor(code: Int): Int {
            when (code) {
                200 -> return ContextCompat.getColor(
                        containerView.context,
                        R.color.monitor_status_success
                )
                300 -> return ContextCompat.getColor(
                        containerView.context,
                        R.color.monitor_status_300
                )
                400 -> return ContextCompat.getColor(
                        containerView.context,
                        R.color.monitor_status_400
                )
                500 -> return ContextCompat.getColor(
                        containerView.context,
                        R.color.monitor_status_500
                )
                else -> return ContextCompat.getColor(
                        containerView.context,
                        R.color.monitor_status_error
                )
            }
        }

    }

}