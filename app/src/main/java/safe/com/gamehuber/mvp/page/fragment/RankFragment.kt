package safe.com.gamehuber.mvp.page.fragment

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseFragment
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter

class RankFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    private val fragments = ArrayList<Fragment>()

    private val tabList by lazy {
        listOf(
                "Best Games",
                "New Games",
                "Hot Games"
        )
    }

    override fun initView() {
        for ((index, e) in tabList.withIndex()) {
            (index == 0).yes {
                fragments.add(RankChildFragment.getInstance(3))
            }.otherwise { fragments.add(RankChildFragment.getInstance(index + 1)) }
        }
        viewpager.apply {
            adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
//            TabLayoutHelper.setUpIndicatorWidth(tablayout)
        }
    }
}