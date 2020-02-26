package com.imydao.jiangbei.sp

import android.content.Context
import com.google.gson.Gson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty



/**
 * 存储单个bean对象
 * @param spName Preferences key
 * @param name putString key
 */
class BaseBeanSP<T>(var ctx : Context,var spName :String, var name : String,var clazz : Class<T>) : ReadWriteProperty<Any?,T?> {


    private val mBaseBeanSP by lazy {
        ctx.getSharedPreferences(spName,Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val gs = Gson()
        return gs.fromJson(mBaseBeanSP.getString(name,""),clazz)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        val gs = Gson()
        val jsonStr = gs.toJson(value) //将对象转换成JsonString
        mBaseBeanSP.edit().putString(name,jsonStr).commit()
    }

}