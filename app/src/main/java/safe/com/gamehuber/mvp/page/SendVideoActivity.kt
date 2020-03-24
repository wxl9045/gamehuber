package safe.com.gamehuber.mvp.page

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_rich_editor.close
import kotlinx.android.synthetic.main.activity_send_video.*
import kotlinx.android.synthetic.main.activity_send_video.tvTitle
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.presenter.SendVideoPresenter


class SendVideoActivity : BaseMvpActivity<SendVideoPresenter>() {
    private var imagePath: String? = null
    private var videoPath: String? = null
    private var gameId: String? = null
    var name: String? = null
    private var postTypeEventBean: PostTypeEventBean? = null
    override fun getLayoutId(): Int = R.layout.activity_send_video

    override fun initView() {
        EventBus.getDefault().register(this)
        imagePath = intent.getStringExtra("imagePath")
        videoPath = intent.getStringExtra("videoPath")
        gameId = intent.getStringExtra("gameId")
        name = intent.getStringExtra("name")
        gameId.isNullOrEmpty().no {
            tvTitle.text = name
        }
        setMyClickListener(close, tvTitle,btCommit)
        loadVideoImage()
    }

    private fun loadVideoImage() {
        Glide.with(this)
                .load(imagePath)
                .into(im_video)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.close -> finish()
            R.id.btCommit -> commitData()
            R.id.tvTitle -> {
                gameId.isNullOrEmpty().yes {
                    startActivity<SelectGameActivity>()
                }.otherwise {
                    startActivity<PostTypeActivity>("id" to gameId, "name" to name)
                }
            }
        }
    }

    private fun commitData() {
        (postTypeEventBean == null).yes {
            toast("请选择游戏和所属分区")
            return
        }
        (postTypeEventBean == null).yes {
            toast("请选择游戏和所属分区")
            return
        }
        edVideoTitle.text.isNullOrEmpty().yes {
            toast("请输入标题")
            return
        }
        edContent.text.isNullOrEmpty().yes {
            toast("请输入内容")
            return
        }
        //上传视频
        videoPath?.let { presenter.uploadVideo(it) }
    }

    @Subscribe
    fun getPostTypeEventBean(eventBean: PostTypeEventBean) {
        if (eventBean != null) {
            postTypeEventBean = eventBean
            tvTitle.text = eventBean.topicName + "-" + eventBean.name
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * 上传视频成功后  提交数据
     */
    fun uploadVideoOk(videoUrl: String) {
        val map = mapOf(
                "topicId" to postTypeEventBean?.topicId,
                "title" to postTypeEventBean?.topicName,
                "typeId" to postTypeEventBean?.id,
                "type" to 3,
                "htmlContent" to edContent.text.toString(),
                "planTextContent" to edContent.text.toString(),
                "videoUrl" to videoUrl)
        presenter.addPost(map as Map<String, Any>)
    }
}