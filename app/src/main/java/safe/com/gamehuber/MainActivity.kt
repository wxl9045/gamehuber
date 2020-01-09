package safe.com.gamehuber

import android.support.v4.app.FragmentTransaction
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.activity_main.*
import safe.com.gamehuber.mvp.base.impl.BaseActivity
import safe.com.gamehuber.mvp.page.fragment.ForumFragment
import safe.com.gamehuber.mvp.page.fragment.HomeFragment
import safe.com.gamehuber.mvp.page.fragment.MeFragment
import safe.com.gamehuber.mvp.page.fragment.RankFragment


class MainActivity : BaseActivity(),BottomNavigationBar.OnTabSelectedListener{
    private var homeFragment: HomeFragment? = null
    private var rankFragment: RankFragment? = null
    private var forumFragment: ForumFragment? = null
    private var meFragment: MeFragment? = null
    override fun isImmersionBarImage(): Boolean = true
    override fun getLayoutId(): Int = R.layout.activity_main
    override fun initView() {
        with(bottom_bar){
            //设置显示模式,必须在初始化完成之前设置
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            setMode(BottomNavigationBar.MODE_FIXED)
            addItem(BottomNavigationItem(R.mipmap.icon_mine,R.string.me).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_forum,R.string.forum).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_rank,R.string.rank).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_home,R.string.home).setActiveColorResource(R.color.colorAccent))
            setFirstSelectedPosition(3)// 默认选中 的item
            initialise()
        }
        bottom_bar.setTabSelectedListener(this)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        HomeFragment()?.let {
            homeFragment = it
            transaction.add(R.id.fl,it)
        }
        transaction.commit()
    }

    override fun onTabSelected(position: Int) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        hideFragments(transaction)
        when (position){
            0 -> {
                meFragment?.let {
                    transaction.show(it).commit()
                }?: MeFragment()?.let {
                    meFragment = it
                    transaction.add(R.id.fl,it).commit()
                }
            }
            1 -> {
                forumFragment?.let {
                    transaction.show(it).commit()
                }?:ForumFragment()?.let {
                    forumFragment = it
                    transaction.add(R.id.fl,it).commit()
                }
            }
            2 -> {
                rankFragment?.let {
                    transaction.show(it).commit()
                }?:RankFragment()?.let {
                    rankFragment = it
                    transaction.add(R.id.fl,it).commit()
                }
            }
            else -> {
                homeFragment?.let {
                    transaction.show(it).commit()
                }
            }
        }

    }

    /**
     * 先隐藏所有的Fragment 再显示选中的
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        rankFragment?.let { transaction.hide(it) }
        forumFragment?.let { transaction.hide(it) }
        meFragment?.let { transaction.hide(it) }
    }

    override fun onTabUnselected(position: Int) {}

    override fun onTabReselected(position: Int) {}
}
