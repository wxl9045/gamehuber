package safe.com.gamehuber.mvp.page.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_community.*
import safe.com.gamehuber.R
import safe.com.gamehuber.R.id.*
import safe.com.gamehuber.adapter.CommunityAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.home.RankPresenter
import safe.com.gamehuber.mvp.model.bean.HomeGameBean

class CommunityFragment : BaseMvpFragment<RankPresenter>(){
    override fun getLayoutId(): Int = R.layout.fragment_community
    private var myAdapter : CommunityAdapter? = null
    private val homeGameBeans: ArrayList<HomeGameBean> = ArrayList()
    override fun initView() {
        initCommunityAdapter()
    }

    private fun initCommunityAdapter() {
        re_label_one.apply {
            var linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            layoutManager = linearLayoutManager
            myAdapter = CommunityAdapter(homeGameBeans)
            adapter = myAdapter
        }
        re_label_two.apply {
            var linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            layoutManager = linearLayoutManager
            myAdapter = CommunityAdapter(homeGameBeans)
            adapter = myAdapter
        }
        re_label_three.apply {
            var linearLayoutManager = GridLayoutManager(activity, 4)
            layoutManager = linearLayoutManager
            myAdapter = CommunityAdapter(homeGameBeans)
            adapter = myAdapter
        }
    }

}