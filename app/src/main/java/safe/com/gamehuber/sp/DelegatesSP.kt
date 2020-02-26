package com.imydao.jiangbei.sp

import android.content.Context
import safe.com.gamehuber.mvp.model.bean.LoginBean

/**
 *  sp 代理类
 */
object DelegatesSP{
    /**
     *  保存 获取login返回的用户信息
     */
    fun userInfoSP(cxt: Context)  =
            BaseBeanSP(ctx = cxt, spName = "USER_INFO", name = "userInfo",clazz = LoginBean::class.java)
}