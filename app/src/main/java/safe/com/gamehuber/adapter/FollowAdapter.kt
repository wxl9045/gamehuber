package safe.com.gamehuber.adapter

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.Constants
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ui.SDCardInfoView
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.mvp.model.bean.PostBean
import safe.com.gamehuber.mvp.page.VideoDetailActivity


class FollowAdapter(beans: List<PostBean>) :
        BaseQuickAdapter<PostBean, BaseViewHolder>(R.layout.item_video, beans) {
    private var imAdapter: MulitImageAdapter? = null
    override fun convert(helper: BaseViewHolder, item: PostBean) {
        val cover = item?.thumbLink
//        var avatar = itemData?.author?.icon

        // 作者出处为空，就显获取提供者的信息
//        if (avatar.isNullOrEmpty()) {
//            avatar = itemData?.provider?.icon
//        }

        var myCardModel = when (item.dictTypeId) {
            "1" -> SDCardInfoView.CardModel.TEXT
            "2" -> SDCardInfoView.CardModel.SINGLE_IMAGE
            "3" -> SDCardInfoView.CardModel.VIDEO
            else -> SDCardInfoView.CardModel.TEXT
        }

        var cardInfoView = helper.getView<SDCardInfoView>(R.id.sdCard)

        cardInfoView.apply {
            cardModel = myCardModel
            coverImgUrl = cover
            desc = item?.name ?: ""
//            avatarUrl = avatar
            setCoverImgClickListener {
//                goToVideoPlayer(mContext as Activity, it, item)
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}
