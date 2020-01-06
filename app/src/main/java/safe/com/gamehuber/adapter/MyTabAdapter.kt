package com.ljb.mvp.kotlin.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import safe.com.gamehuber.mvp.model.bean.MyTabFragmentBean

class MyTabAdapter(fm: FragmentManager?, private val mData: Array<MyTabFragmentBean>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = mData[position].fragment

    override fun getCount() = mData.size

    override fun getPageTitle(position: Int) = mData[position].title
}