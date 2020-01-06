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
import safe.com.gamehuber.mvp.home.VideoDetailActivity
import safe.com.gamehuber.mvp.model.bean.HomeBean


class FollowAdapter(beans: ArrayList<HomeBean.Issue.Item>):
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

      var myCardModel  =  when(helper.adapterPosition){
            1 -> SDCardInfoView.CardModel.VIDEO
            2,3 -> SDCardInfoView.CardModel.MULTI_IMAGE
            4 -> SDCardInfoView.CardModel.SINGLE_IMAGE
            5 -> SDCardInfoView.CardModel.MULTI_IMAGE
            else -> SDCardInfoView.CardModel.VIDEO
        }

        var cardInfoView = helper.getView<SDCardInfoView>(R.id.sdCard)

        cardInfoView.apply {
            cardModel = myCardModel
            coverImgUrl = cover
            desc = itemData?.title ?: ""
            avatarUrl = avatar
            setCoverImgClickListener {
                goToVideoPlayer(mContext as Activity, it,item)
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
