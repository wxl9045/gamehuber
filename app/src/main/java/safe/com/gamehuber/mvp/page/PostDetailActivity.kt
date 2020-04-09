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
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.MultiItemPostReplyAdapter
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.ui.VideoListener
import safe.com.gamehuber.mvp.model.bean.*
import safe.com.gamehuber.mvp.presenter.PostDetailPresenter
import safe.com.gamehuber.net.UrlConstant


/**
 * 帖子评论列表页
 */
class PostDetailActivity : BaseListActivity<PostDetailPresenter>(), MultiItemPostReplyAdapter.ContentClickListenerInterface {


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

    private var thisPostId = "" //当前点击item id
    private var thisPostPosition = 0 ////当前点击item的位置
    private var isMoreClick = false//是否是点击更多查看
    private var isContentClick = false//是否是点击内容 回复
    override fun getLayoutId(): Int = R.layout.activity_post_detail
    override fun isImmersionBarImage(): Boolean = true
    override fun onInit() {
        var id = intent.getStringExtra("postId")
        isTransition = intent.getBooleanExtra(VideoDetailActivity.TRANSITION, false)
        EventBus.getDefault().register(this)
        initAdapter()
        initBg()
        presenter.postDetail(id)//获取帖子详情
        setMyClickListener(btSend, btBack)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btSend -> {
                edText.text.isNullOrEmpty().yes {
                    toast("请输入回复内容")
                    return
                }
                thisPostId.isNullOrEmpty().yes {
                    this.postDetail?.id?.let { presenter.replyPost(it, edText.text.toString()) }
                }.otherwise {
                    presenter.replyPost(thisPostId, edText.text.toString())
                }
            }
            R.id.btBack -> finish()
        }
    }

    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        loadVideoInfo()
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView as GSYBaseVideoPlayer?)
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
        //隐藏返回按键功能
        mVideoView.backButton.visibility = View.GONE
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
        multiItemPostReplyAdapter?.setContentClickListenerInterface(this)
    }

    override fun onRefresh() {
        presenter.searchPost(1)
    }

    override fun onLoadmore(page: Int) {
        presenter.searchPost(page)
    }

    /**
     * 获取帖子回复列表
     * children 默认最多展示2条数据
     */
    fun showPostDetailListData(baseRecordsBean: BaseRecordsBean<List<PostReplyBean>>) {
        isRefresh.yes {
            this.items.clear()
        }
        var replyBeans = baseRecordsBean.records
        for (i in replyBeans.indices) {
            (replyBeans[i].children != null && replyBeans[i].children?.size > 0).yes {
                for (k in replyBeans[i].children.indices) {
                    replyBeans[i].children[k].pPosition = i//保存当前子评论的父评论所在列表位置 很重要
                    if(k == 2 && replyBeans[i].replyCount > 2){
                        replyBeans[i].children[k].hasNext = true //是否存在下一个 显示更多按钮
                    }
                    replyBeans[i].addSubItem(replyBeans[i].children[k])
                }
            }
            items.add(replyBeans[i])
        }
        multiItemPostReplyAdapter?.notifyDataSetChanged()
        multiItemPostReplyAdapter?.expandAll()

    }
    /**
     * 获取更多帖子回复列表
     */
    fun showMorePostList(baseRecordsBean: BaseRecordsBean<List<PostReply2Bean>>) {
        baseRecordsBean.hasNext.yes {
            baseRecordsBean.records[baseRecordsBean.records.size -1].hasNext = true
        }
        items.addAll(thisPostPosition + 1, baseRecordsBean.records)//在该列表下方追加数据
        multiItemPostReplyAdapter?.notifyItemInserted(thisPostPosition + 1)
        thisPostPosition = 0
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
     * 获取帖子详情
     */
    fun getPostDetail(postDetail: PostDetailBean) {
        this.postDetail = postDetail
        Glide.with(applicationContext).asBitmap().load(postDetail.avatar)
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .into(imAvatar)
        tvName.text = postDetail.nickname
        tvTitle.text = postDetail.postTitle
        tvDate.text = postDetail.postingTime
        //是否是视频贴
        postDetail.thumbLink.isNullOrEmpty().no {
            initVideoViewConfig()
        }
    }


    /**
     * 发送评论成功
     */
    fun sendPostOk(replyBean: PostReplyBean) {
        edText.text = null
        var reply2Bean = PostReply2Bean()
        with(reply2Bean) {
            id = replyBean.id
            nickname = replyBean.nickname
            avatar = replyBean.avatar
            postContentPlaintext = replyBean.postContentPlaintext
            postingTime = replyBean.postingTime
        }
        isContentClick.yes {
            (items[thisPostPosition].itemType == 0).yes {//回复的是父帖子 直接在父帖子下 展示一个子贴
                items.add((thisPostPosition + 1), reply2Bean)
                multiItemPostReplyAdapter?.notifyItemInserted(thisPostPosition + 1)
            }.otherwise {//回复的是子帖子时 先获取所在父帖的位置 然后在父帖子下展示子贴
                var replySubBean = items[thisPostPosition] as PostReply2Bean
                var pPosition = replySubBean.pPosition//获取当前点击的父帖子 所在列表位置  然后再该位置下方添加一条数据
                items.add((pPosition + 1), reply2Bean)
                multiItemPostReplyAdapter?.notifyItemInserted(thisPostPosition + 1)
            }
        }.otherwise {
            //回复楼主时 最顶显示一条回复
            items.add(0, replyBean)
            multiItemPostReplyAdapter?.notifyItemInserted(0)
        }

        thisPostId = ""
        thisPostPosition = 0
    }

    override fun onContentClickListener(position: Int) {
        thisPostPosition = position
        isContentClick = true
        (items[position].itemType == 0).yes {
            var postReplyBean = items[position] as PostReplyBean
            //回复第一层
            thisPostId = postReplyBean.id
            edText.hint = "回复${postReplyBean.nickname}"
        }.otherwise {
            var postReplyBean = items[position] as PostReply2Bean
            //回复第二层层
            thisPostId = postReplyBean.id
            edText.hint = "回复${postReplyBean.nickname}"
        }
    }

    override fun onItemMoreClickListener(position: Int) {
        thisPostPosition = position
        isMoreClick = true
        var postReplyBean = items[position] as PostReplyBean
        page = if (postReplyBean.subItems == null) 1 else (postReplyBean.subItems.size - 2) % 10 + 1
        presenter.searchChildPost(page, postReplyBean.topicId, postReplyBean.id)
    }
}