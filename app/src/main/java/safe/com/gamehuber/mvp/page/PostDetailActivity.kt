package safe.com.gamehuber.mvp.page

import android.annotation.TargetApi
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.graphics.Palette
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.MultiItemPostReplyAdapter
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.ui.VideoListener
import safe.com.gamehuber.mvp.model.bean.PostDetailBean
import safe.com.gamehuber.mvp.model.bean.PostReply2Bean
import safe.com.gamehuber.mvp.model.bean.PostReplyBean
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.presenter.PostDetailPresenter
import safe.com.gamehuber.net.UrlConstant


/**
 * 帖子评论列表页
 */
class PostDetailActivity : BaseListActivity<PostDetailPresenter>() {

    private var multiItemPostReplyAdapter: MultiItemPostReplyAdapter? = null
    private var postReplyBeans: ArrayList<PostReplyBean> = ArrayList()
    private var items: MutableList<MultiItemEntity> = ArrayList()
    private var postDetail: PostDetailBean? = null

    //视频相关
    private var mMaterialHeader: MaterialHeader? = null
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var isTransition: Boolean = false
    private var transition: Transition? = null

    override fun getLayoutId(): Int = R.layout.activity_post_detail
    override fun isImmersionBarImage(): Boolean = true
    override fun onInit() {
        var id = intent.getStringExtra("postId")
        isTransition = intent.getBooleanExtra(VideoDetailActivity.TRANSITION, false)
        EventBus.getDefault().register(this)
        initAdapter()
        initBg()
        presenter.postDetail(id)//获取帖子详情
        presenter.searchPost(1)//获取列表详情
        setMyClickListener(btSend,btBack)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btSend -> {
//                var map = mapOf<String,String>(
//                        ""
//                )
//                presenter.sendPost()
            }
            R.id.btBack -> finish()
        }
    }

    private fun initTransition() {
//        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            postponeEnterTransition()
//            ViewCompat.setTransitionName(mVideoView, VideoDetailActivity.IMG_TRANSITION)
//            addTransitionListener()
//            startPostponedEnterTransition()
//        } else {
            loadVideoInfo()
//        }
    }

    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        initTransition()
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        Glide.with(this)
//                .load(itemData.videoCover)
//                .into(imageView)
        mVideoView.thumbImageView = imageView

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                Logger.d("***** onAutoPlayComplete **** ")
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                toast("播放失败")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }
    }

    /**
     * 1.加载视频信息
     */
    private fun loadVideoInfo() {
        setVideo(UrlConstant.BASE_URL_FILE + postDetail?.thumbLink)
    }

    /**
     * 设置播放视频 URL
     */
    private fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
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
                loadVideoInfo()
                transition?.removeListener(this)
            }

        })
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    /**
     * 初始化背景色
     */
    private fun initBg() {
        var drawable = imageView.drawable as BitmapDrawable
        Palette.from(drawable.bitmap).maximumColorCount(10).generate {
            var s1 = it.vibrantSwatch as Palette.Swatch
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frameBg.setBackgroundColor(s1?.rgb)
            }
        }
    }

    private fun initAdapter() {
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            multiItemPostReplyAdapter = MultiItemPostReplyAdapter(items)
            adapter = multiItemPostReplyAdapter
        }

    }

    override fun onRefresh() {
        presenter.searchPost(1)
    }

    override fun onLoadmore(page: Int) {
        presenter.searchPost(page)
    }

    /**
     * 获取帖子回复列表
     */
    fun showPostDetailListData(postReplyBeans: List<PostReplyBean>) {
        if (isRefresh) {
            this.items.clear()
        }
        for (bean in postReplyBeans) {
            (bean.children != null && bean.children?.size > 0).yes {
                for (childBean in bean.children) {
                    var beanSub = childBean as PostReply2Bean
                    bean.addSubItem(beanSub)
                }
            }
            items.add(bean)
        }
        multiItemPostReplyAdapter?.notifyDataSetChanged()
        multiItemPostReplyAdapter?.expandAll()
    }

    @Subscribe
    fun getPostTypeEventBean(eventBean: PostTypeEventBean) {
        if (eventBean != null) {
            finish()
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        super.onDestroy()
    }

    /**
     * 发送评论
     */
    fun sendPostOk(){
        edText.text = null
    }

    /**
     * 获取帖子详情
     */
    fun getPostDetail(postDetail: PostDetailBean){
        this.postDetail = postDetail
        Glide.with(applicationContext).asBitmap().load(postDetail.avatar)
                .apply(RequestOptions().placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).centerCrop())
                .into(imAvatar)
        tvName.text = postDetail.nickname
        tvTitle.text = postDetail.postTitle
        tvDate.text = postDetail.postingTime
        //是否是视频贴
        postDetail.thumbLink.isNullOrEmpty().no {
            initVideoViewConfig()
        }
    }
}