package safe.com.gamehuber.mvp.page

import android.annotation.TargetApi
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_game_detail.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseFragmentAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.page.fragment.RankChildFragment
import safe.com.gamehuber.mvp.presenter.LoginPresenter




class GameDetailActivity : BaseMvpActivity<LoginPresenter>() {

    override fun getLayoutId(): Int = R.layout.activity_game_detail
    override fun isImmersionBarImage(): Boolean = true
    private var transition: Transition? = null
    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()
    //http://img.kaiyanapp.com/ff0f6d0ad5f4b6211a3f746aaaffd916.jpeg?imageMogr2/quality/60/format/jpg
    override fun initView() {
        initPageView()
        Glide.with(this)
                .load(R.mipmap.bg_one)
                .into(imageView)
        Glide.with(this)
                .load("http://img.kaiyanapp.com/f9eae3e0321fa1e99a7b38641b5536a2.jpeg?imageMogr2/quality/60/format/jpg")
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_icon)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(iv_icon, VideoDetailActivity.IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
        }
        val screenW = resources.displayMetrics.widthPixels
        val toolbarHeight = resources.getDimension(R.dimen.toolbar_height)
        val initHeight = resources.getDimension(R.dimen.subscription_head)
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val scale = 1.0f - -verticalOffset / (initHeight - toolbarHeight + 450)
            val hiddenScale = 1.0f - -verticalOffset / (initHeight - toolbarHeight - 250)
            Log.d("aaa", "initHeight === " + initHeight)
            Log.d("aaa", "toolbarHeight === " + toolbarHeight)

            iv_icon.scaleX = hiddenScale
            iv_icon.scaleY = hiddenScale

            tv_game_name.translationY = 0.63f * verticalOffset
            tv_game_name.translationX = 0.30f * verticalOffset

            ll_score.scaleX = hiddenScale
            ll_score.scaleY = hiddenScale
            ll_playres.scaleX = hiddenScale
            ll_playres.scaleY = hiddenScale
            ll_posts.scaleX = hiddenScale
            ll_posts.scaleY = hiddenScale

        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                Logger.d("onTransitionEnd()------")
                transition?.removeListener(this)
            }

        })
    }

    private fun initPageView() {
        tabList.add("Introduction")
        tabList.add("Community")
        fragments.add(RankChildFragment())
        fragments.add(RankChildFragment())
        viewpager.apply {
            adapter = BaseFragmentAdapter(supportFragmentManager, fragments, tabList)
            tablayout.setupWithViewPager(this)
//            TabLayoutHelper.setUpIndicatorWidth(tablayout)
        }
    }
}