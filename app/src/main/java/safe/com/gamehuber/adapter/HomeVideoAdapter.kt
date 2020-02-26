package safe.com.gamehuber.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.Constants
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ui.SDCardInfoView
import safe.com.gamehuber.common.utils.anim2Act
import safe.com.gamehuber.mvp.model.bean.HomeVideoBean
import safe.com.gamehuber.mvp.page.UserDetailActivity
import safe.com.gamehuber.mvp.page.VideoDetailActivity


class HomeVideoAdapter(beans: List<HomeVideoBean>) :
        BaseQuickAdapter<HomeVideoBean, BaseViewHolder>(R.layout.item_video, beans) {
    private var imAdapter: MulitImageAdapter? = null
    override fun convert(helper: BaseViewHolder, bean: HomeVideoBean) {

        val cover = bean.videoCover
        var avatar = bean.avatar


        var cardInfoView = helper.getView<SDCardInfoView>(R.id.sdCard)

        cardInfoView.apply {
            cardModel = SDCardInfoView.CardModel.VIDEO
            coverImgUrl = cover
            desc = bean.postContent
            avatarUrl = avatar
            nickName = bean.nickname
            setCoverImgClickListener {
                goToVideoPlayer(mContext as Activity, it, bean)
            }
            setAvatarClickListener {
                goToUserDetail(mContext as Activity, it)
            }
            cardCreate()
        }
    }


    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeVideoBean) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        anim2Act(activity, view, intent)
    }

    /**
     * 跳转到用户详情页面
     *
     * @param activity
     * @param view
     */
    private fun goToUserDetail(activity: Activity, view: View) {
        val intent = Intent(activity, UserDetailActivity::class.java)
        anim2Act(activity, view, intent)
    }
}
