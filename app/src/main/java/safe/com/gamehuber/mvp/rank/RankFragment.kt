package safe.com.gamehuber.mvp.home

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment

class RankFragment : BaseMvpFragment<RankPresenter>(){
    override fun getLayoutId(): Int = R.layout.fragment_home
    private val fragments = ArrayList<Fragment>()

    private val tabList by  lazy {
        listOf(
                "aaa",
                "bbb",
                "ccc",
                "ddd"
        )
    }

    override fun initView() {
        for (element in tabList){
            fragments.add(RankChildFragment())
        }
        viewpager.apply {
            adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
//            TabLayoutHelper.setUpIndicatorWidth(tablayout)
        }
    }
}