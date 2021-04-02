package com.android.okhttp.monitor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class MonitorPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()

    private val fragmentTitleList = ArrayList<String>()

    internal fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

}