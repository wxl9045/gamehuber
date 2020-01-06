package safe.com.gamehuber.mvp.home

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rank.*
import safe.com.gamehuber.R
import safe.com.gamehuber.R.id.re_rank
import safe.com.gamehuber.adapter.RankAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment

class RankFragment : BaseMvpFragment<RankPresenter>(){
    var rankAdapter :RankAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_rank

    private val labels by  lazy {
        listOf(
                "aaa","bbb","cccc","dddd","eeee","ffff"
        )
    }
    override fun initView() {
        re_rank.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rankAdapter = RankAdapter(labels)
            adapter = rankAdapter
        }
    }
}