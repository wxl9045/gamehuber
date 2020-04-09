package safe.com.gamehuber

import android.support.v4.app.FragmentTransaction
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.imydao.jiangbei.sp.DelegatesSP
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_CODE_LOGIN_SUCCESS
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseActivity
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.mvp.page.LoginActivity
import safe.com.gamehuber.mvp.page.fragment.ForumFragment
import safe.com.gamehuber.mvp.page.fragment.HomeFragment
import safe.com.gamehuber.mvp.page.fragment.MeFragment
import safe.com.gamehuber.mvp.page.fragment.RankFragment

/**
 * 主页面
 * 注：别在此页面写onActivityResult 否则子fragment的onActivityResult 将不会被调用
 * 请使用eventBus
 */
class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {
    private var homeFragment: HomeFragment? = null
    private var rankFragment: RankFragment? = null
    private var forumFragment: ForumFragment? = null
    private var meFragment: MeFragment? = null
    var loginBean: LoginBean? by DelegatesSP.userInfoSP(this)
    override fun isImmersionBarImage(): Boolean = true
    override fun getLayoutId(): Int = R.layout.activity_main
    private var thisSelectedPosition = 3
    override fun initView() {
        EventBus.getDefault().register(this)
        with(bottom_bar) {
            //设置显示模式,必须在初始化完成之前设置
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            setMode(BottomNavigationBar.MODE_FIXED)
            addItem(BottomNavigationItem(R.mipmap.icon_mine, R.string.me).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_forum, R.string.forum).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_rank, R.string.rank).setActiveColorResource(R.color.colorAccent))
            addItem(BottomNavigationItem(R.mipmap.icon_home, R.string.home).setActiveColorResource(R.color.colorAccent))
            setFirstSelectedPosition(thisSelectedPosition)// 默认选中 的item
            initialise()
        }
        bottom_bar.setTabSelectedListener(this)
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        HomeFragment()?.let {
            homeFragment = it
            transaction.add(R.id.fl, it)
        }
        transaction.commitAllowingStateLoss()
    }

    override fun onTabSelected(position: Int) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> {
                (loginBean == null).yes {
                    EventBus.getDefault().post(ConstantsCode.REQUEST_CODE_MAIN)
                    startActivity<LoginActivity>()
                    return
                }
                thisSelectedPosition = position
                meFragment?.let {
                    transaction.show(it).commitAllowingStateLoss()
                } ?: MeFragment()?.let {
                    meFragment = it
                    transaction.add(R.id.fl, it).commitAllowingStateLoss()
                }
            }
            1 -> {
                thisSelectedPosition = position
                forumFragment?.let {
                    transaction.show(it).commitAllowingStateLoss()
                } ?: ForumFragment()?.let {
                    forumFragment = it
                    transaction.add(R.id.fl, it).commitAllowingStateLoss()
                }
            }
            2 -> {
                thisSelectedPosition = position
                rankFragment?.let {
                    transaction.show(it).commitAllowingStateLoss()
                } ?: RankFragment()?.let {
                    rankFragment = it
                    transaction.add(R.id.fl, it).commitAllowingStateLoss()
                }
            }
            else -> {
                thisSelectedPosition = position
                homeFragment?.let {
                    transaction.show(it).commitAllowingStateLoss()
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

    override fun onStart() {
        super.onStart()
        bottom_bar.selectTab(thisSelectedPosition)
    }
//

    @Subscribe
    fun getEventCode(code: Int) {
        //当用户未登陆时 其他页面点击跳转到登录页
        (code == REQUEST_CODE_LOGIN_SUCCESS).yes {
            bottom_bar.selectTab(0)
        }
    }
}
