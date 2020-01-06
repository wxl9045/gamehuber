package safe.com.gamehuber

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main2.*
import safe.com.gamehuber.mvp.base.impl.BaseActivity
import safe.com.gamehuber.mvp.home.ForumFragment
import safe.com.gamehuber.mvp.home.HomeFragment
import safe.com.gamehuber.mvp.home.MeFragment
import safe.com.gamehuber.mvp.home.RankFragment
import safe.com.gamehuber.mvp.model.bean.TabEntity
import java.util.*


class MainActivity2 : BaseActivity(){
    override fun getLayoutId(): Int = R.layout.activity_main2
    private var homeFragment: HomeFragment? = null
    private var rankFragment: RankFragment? = null
    private var forumFragment: ForumFragment? = null
    private var meFragment: MeFragment? = null

    private val mTitles = arrayOf("me", "forum", "rank", "home")
    //默认为0
    private var mIndex = 0
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.icon_mine, R.mipmap.icon_forum, R.mipmap.icon_rank, R.mipmap.icon_home)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.icon_mine, R.mipmap.icon_forum, R.mipmap.icon_rank, R.mipmap.icon_home)

    private val mTabEntities = ArrayList<CustomTabEntity>()

    override fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
    }
    override fun initView() {
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    //初始化底部菜单
    private fun initTab() {
        (0 until mTitles.size)
                .mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        //为Tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //切换Fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 // 首页
            ->  meFragment?.let {
                transaction.show(it)
            } ?: MeFragment().let {
                meFragment = it
                transaction.add(R.id.fl, it, "me")
            }
            1  //发现
            -> forumFragment?.let {
                transaction.show(it)
            } ?: ForumFragment().let {
                forumFragment = it
                transaction.add(R.id.fl, it, "forum") }
            2  //热门
            -> rankFragment?.let {
                transaction.show(it)
            } ?: RankFragment().let {
                rankFragment = it
                transaction.add(R.id.fl, it, "rank") }
            3 //我的
            -> homeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment().let {
                homeFragment = it
                transaction.add(R.id.fl, it, "home") }

            else -> {

            }
        }

        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        meFragment?.let { transaction.hide(it) }
        forumFragment?.let { transaction.hide(it) }
        rankFragment?.let { transaction.hide(it) }
        homeFragment?.let { transaction.hide(it) }
    }

}
