package safe.com.gamehuber.mvp.page.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rank.*
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.RankAdapter
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.home.RankPresenter
import safe.com.gamehuber.mvp.model.bean.RankListBean

class RankListFragment : BaseMvpFragment<RankPresenter>() {
    var rankAdapter: RankAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_rank
    private var type = 0
    private var typeId = ""
    private var page = 1
    private var isRefresh = false
    private var isLoadmore = false
    private var rankListBeans: ArrayList<RankListBean>? = ArrayList()

    companion object {
        fun getInstance(type: Int, typeId: String): RankListFragment {
            val fragment = RankListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.type = type
            fragment.typeId = typeId
            return fragment
        }
    }

    override fun initView() {
        re_rank.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rankAdapter = rankListBeans?.let { RankAdapter(it) }
            adapter = rankAdapter
        }
        initRefresh()
        presenter.getRankList(1,this.type,this.typeId)
    }

    private fun initRefresh() {
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            isLoadmore = false
            page = 1
            presenter.getRankList(page,this.type,this.typeId)
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_translucent, R.color.color_title_bg)
        mRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            isLoadmore = true
            page++
            presenter.getRankList(page,this.type,this.typeId)
        }
    }

    fun getRankList(rankListBeans: List<RankListBean>) {
        isRefresh.yes { this.rankListBeans?.clear() }
        this.rankListBeans?.addAll(rankListBeans)
        rankAdapter?.notifyDataSetChanged()
    }

    fun missRefresh() {
        isRefresh.yes {
            mRefreshLayout.finishRefresh()
        }
        isLoadmore.yes {
            mRefreshLayout.finishLoadmore()
        }
    }
}