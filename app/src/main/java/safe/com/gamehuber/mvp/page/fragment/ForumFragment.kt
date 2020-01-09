package safe.com.gamehuber.mvp.page.fragment

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.home.RankPresenter

class ForumFragment : BaseMvpFragment<RankPresenter>(){
    override fun getLayoutId(): Int = R.layout.fragment_home
    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()


    override fun initView() {
        tabList.add("Follow")
        tabList.add("Community")
        fragments.add(FollowFragment())
        fragments.add(CommunityFragment())

        viewpager.apply {
            adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
//            TabLayoutHelper.setUpIndicatorWidth(tablayout)
        }
    }
}