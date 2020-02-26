package safe.com.gamehuber.mvp.page.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.flyco.tablayout.SlidingTabLayout
import kotlinx.android.synthetic.main.fragment_child_rank.*
import safe.com.gamehuber.R
import safe.com.gamehuber.R.id.tablayout
import safe.com.gamehuber.R.id.viewpager
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.home.RankChildPresenter
import safe.com.gamehuber.mvp.model.bean.TopicTypeBean

class RankChildFragment : BaseMvpFragment<RankChildPresenter>() {
    override fun getLayoutId(): Int = R.layout.fragment_child_rank
    private val fragments = ArrayList<Fragment>()
    private var tb: SlidingTabLayout? = null
    private var tabList = ArrayList<String>()
    private var type = 0

    companion object {
        fun getInstance(type: Int): RankChildFragment {
            val fragment = RankChildFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.type = type
            return fragment
        }
    }

    override fun initView() {
//        val decorView = activity?.window?.decorView
//        tb = ViewFindUtils.find(decorView, R.id.slid_tab)
        presenter.getTopicType()
    }

    fun showTopicType(topicTypeBeans: List<TopicTypeBean>) {
        tabList.clear()
        fragments.clear()
        for ((index, bean) in topicTypeBeans.withIndex()) {
            tabList.add(bean.name)
            fragments.add(RankListFragment.getInstance(this.type, topicTypeBeans?.get(index).id))
        }
        tabList.add("All")
        fragments.add(RankListFragment.getInstance(this.type, ""))
        viewpager.apply {
            adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
//            TabLayoutHelper.setUpIndicatorWidth(tablayout)
        }
//        tablayout.viewpager =tablayout
    }
}