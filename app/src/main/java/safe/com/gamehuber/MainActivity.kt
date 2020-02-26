package safe.com.gamehuber

import android.content.Intent
import android.provider.MediaStore
import android.support.v4.app.FragmentTransaction
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.imydao.jiangbei.sp.DelegatesSP
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_CODE_MAIN
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_VIDEO_CODE
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseActivity
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.mvp.page.LoginActivity
import safe.com.gamehuber.mvp.page.SendVideoActivity
import safe.com.gamehuber.mvp.page.fragment.ForumFragment
import safe.com.gamehuber.mvp.page.fragment.HomeFragment
import safe.com.gamehuber.mvp.page.fragment.MeFragment
import safe.com.gamehuber.mvp.page.fragment.RankFragment


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
                    startActivityForResult<LoginActivity>(
                            REQUEST_CODE_MAIN, "code" to REQUEST_CODE_MAIN
                    )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (resultCode == REQUEST_CODE_MAIN).yes {
            bottom_bar.selectTab(0)
        }
        (requestCode == REQUEST_VIDEO_CODE).yes {
            (resultCode == RESULT_OK).yes {
                val uri = data?.data
                val cr = contentResolver
                val cursor = cr?.query(uri, null, null, null, null)
                (cursor?.moveToFirst())?.yes {
                    //视频路径
                    val videoPath = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)?.let { cursor?.getString(it) }
                    //缩略图路径
                    val imagePath = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)?.let { cursor?.getString(it) }
                    startActivity<SendVideoActivity>("videoPath" to videoPath, "imagePath" to imagePath)
                }
            }
        }
    }
}
