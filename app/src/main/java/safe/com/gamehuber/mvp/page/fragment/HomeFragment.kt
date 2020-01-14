package safe.com.gamehuber.mvp.page.fragment

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseFragment
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter

class HomeFragment : BaseFragment(){
    override fun getLayoutId(): Int = R.layout.fragment_home

    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()

    override fun initView() {
        tabList.add("Video")
        tabList.add("Game")
        fragments.add(VideoFragment())
        fragments.add(GameFragment())

        viewpager.apply {
            adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
        }
    }
}