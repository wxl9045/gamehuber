package safe.com.gamehuber.mvp.page

import android.annotation.TargetApi
import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_user_detail2.*
import kotlinx.android.synthetic.main.fragment_me.app_bar
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.home.VideoDetailActivity
import safe.com.gamehuber.mvp.login.LoginPresenter


class UserDetail2Activity : BaseMvpActivity<LoginPresenter>(){
    private var mSelfHeight = 0f//用以判断是否得到正确的宽高值
    private var mTitleScale: Float = 0.toFloat()
    private var mSubScribeScale: Float = 0.toFloat()
    private var mSubScribeScaleX: Float = 0.toFloat()
    private var mHeadImgScale: Float = 0.toFloat()
    override fun getLayoutId(): Int = R.layout.activity_user_detail2
//    override fun isImmersionBarImage(): Boolean = true
    private var transition: Transition? = null
    override fun initView() {
//        Glide.with(this)
//                .load("http://img.kaiyanapp.com/ff0f6d0ad5f4b6211a3f746aaaffd916.jpeg?imageMogr2/quality/60/format/jpg")
//                .into(imageView)
        Glide.with(this)
                .load("http://img.kaiyanapp.com/f9eae3e0321fa1e99a7b38641b5536a2.jpeg?imageMogr2/quality/60/format/jpg")
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_avatar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(iv_avatar, VideoDetailActivity.IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
        }
        val screenW = resources.displayMetrics.widthPixels
        val toolbarHeight = resources.getDimension(R.dimen.toolbar_height)
        val initHeight = resources.getDimension(R.dimen.subscription_head)
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (mSelfHeight === 0f) {
                mSelfHeight = ll_bt_follow.height.toFloat()
                val distanceTitle = ll_bt_follow.top + (mSelfHeight - toolbarHeight) / 2.0f
                val distanceSubscribe = tv_user_name.y + (tv_user_name.height - toolbarHeight) / 2.0f
                val distanceHeadImg = iv_avatar.y + (iv_avatar.height - toolbarHeight) / 2.0f
                val distanceSubscribeX = screenW / 2.0f - (tv_user_name.width / 2.0f + resources.getDimension(R.dimen.margin_left_home))
                mTitleScale = distanceTitle / (initHeight - toolbarHeight)
                mSubScribeScale = distanceSubscribe / (initHeight - toolbarHeight)
                mHeadImgScale = distanceHeadImg / (initHeight - toolbarHeight)
                mSubScribeScaleX = distanceSubscribeX / (initHeight - toolbarHeight)
            }
            val scale = 1.0f - -verticalOffset / (initHeight - toolbarHeight)
            iv_avatar.scaleX = scale
            iv_avatar.scaleY = scale
            iv_avatar.translationY = mHeadImgScale * verticalOffset
            ll_bt_follow.translationY = mTitleScale * verticalOffset
            Log.d("iv_avatar","Y ===== "+iv_avatar.scaleY+" X======"+ iv_avatar.scaleX+"translationY =====")
            tv_user_name.translationY = mSubScribeScale * verticalOffset
            tv_user_name.translationX = -mSubScribeScaleX * verticalOffset
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

}