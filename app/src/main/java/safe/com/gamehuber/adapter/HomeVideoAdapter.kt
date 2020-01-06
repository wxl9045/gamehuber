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
import safe.com.gamehuber.mvp.home.VideoDetailActivity
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.mvp.page.UserDetailActivity


class HomeVideoAdapter(beans: ArrayList<HomeBean.Issue.Item>):
        BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(R.layout.item_video,beans) {
    private var imAdapter : MulitImageAdapter? = null
    override fun convert(helper: BaseViewHolder, item : HomeBean.Issue.Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }

        var cardInfoView = helper.getView<SDCardInfoView>(R.id.sdCard)

        cardInfoView.apply {
            cardModel = SDCardInfoView.CardModel.VIDEO
            coverImgUrl = cover
            desc = itemData?.title ?: ""
            avatarUrl = avatar
            setCoverImgClickListener {
                goToVideoPlayer(mContext as Activity, it,item)
            }
            setAvatarClickListener {
                goToUserDetail(mContext as Activity, it,item)
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
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        anim2Act(activity,view,intent)
    }

    /**
     * 跳转到用户详情页面
     *
     * @param activity
     * @param view
     */
    private fun goToUserDetail(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, UserDetailActivity::class.java)
        anim2Act(activity,view,intent)
    }
}
