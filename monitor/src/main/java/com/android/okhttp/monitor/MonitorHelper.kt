package com.android.okhttp.monitor

import android.content.Context
import com.android.okhttp.monitor.db.DaoMaster
import com.android.okhttp.monitor.db.DaoMaster.DevOpenHelper
import com.android.okhttp.monitor.db.DaoSession
import com.android.okhttp.monitor.db.MonitorData
import com.android.okhttp.monitor.db.MonitorDataDao
import com.android.okhttp.monitor.service.MonitorPCService


object MonitorHelper {

    var daoSession: DaoSession? = null

    var context: Context? = null

    fun init(context: Context, dbName: String) {
        this.context = context
        initMonitorDataDao(dbName)
        MonitorPCService(context).start()
    }

    private fun initMonitorDataDao(dbName: String) {
        if (daoSession == null) {
            val helper = DevOpenHelper(context, "${dbName}.db")
            daoSession = DaoMaster(helper.writableDb).newSession()
        }
    }

    private fun getMonitorDataDao(): MonitorDataDao? {
        return daoSession?.monitorDataDao
    }

    fun insert(monitorData: MonitorData) {
        getMonitorDataDao()?.insert(monitorData)
    }

    fun update(monitorData: MonitorData) {
        getMonitorDataDao()?.update(monitorData)
    }

    fun deleteAll() {
        getMonitorDataDao()?.deleteAll()
    }

    fun getMonitorDataList(limit: Int = 50): MutableList<MonitorData> {
        val monitorDatas = getMonitorDataDao()?.queryBuilder()?.orderDesc(MonitorDataDao.Properties.Id)?.limit(limit)?.build()?.list()
        return monitorDatas ?: mutableListOf()
    }
}