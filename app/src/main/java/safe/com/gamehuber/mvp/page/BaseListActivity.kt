package safe.com.gamehuber.mvp.page

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_follow.*
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.base.impl.BasePresenter

/**
 * 继承该类实现下拉刷新 下拉加载功能
 */
abstract class BaseListActivity<out P : BasePresenter<BaseMvpActivity<P>>> : BaseMvpActivity<P>() {
    var isRefresh = false
    var isLoadmore = false
    var page = 1

    override fun initView() {
        initRefresh()
        onInit()
    }

    private fun initRefresh() {
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            isLoadmore = false
            page = 1
            onRefresh()
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_translucent, R.color.color_title_bg)
        mRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            isLoadmore = true
            page++
            onLoadmore(page)
        }
    }

    fun missRefresh() {
        isRefresh.yes {
            mRefreshLayout.finishRefresh()
        }
        isLoadmore.yes {
            mRefreshLayout.finishLoadmore()
        }
    }

    val linearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    abstract fun onRefresh()
    abstract fun onLoadmore(page: Int)
    abstract fun onInit()
}