package safe.com.gamehuber.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.PostReply2Bean
import safe.com.gamehuber.mvp.model.bean.PostReplyBean
import safe.com.gamehuber.net.UrlConstant


class MultiItemPostReplyAdapter(beans: List<MultiItemEntity>) :
        BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(beans) {
    private val typeLevel0 = 0
    private val typeLevel1 = 1

    init {
        addItemType(typeLevel0, R.layout.item_post_reply)
        addItemType(typeLevel1, R.layout.item_post_sub_reply)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (helper.itemViewType) {
            typeLevel0 -> {
                val postReplyBean = item as PostReplyBean
                var mAvatar = helper.getView<ImageView>(R.id.item_imAvatar)
                Glide.with(mContext)
                        .load(UrlConstant.BASE_URL_FILE + postReplyBean.avatar)
                        .into(mAvatar)
                helper.setText(R.id.item_name, postReplyBean.nickname)
                        .setText(R.id.item_content, postReplyBean.postContentPlaintext)
                        .setText(R.id.item_time, postReplyBean.postingTime)
                        .setText(R.id.item_tv_like, "${postReplyBean.likeCount}")
            }
            typeLevel1 -> {
                val postReply2Bean = item as PostReply2Bean
                var mAvatar = helper.getView<ImageView>(R.id.item_imAvatar)
                Glide.with(mContext)
                        .load(UrlConstant.BASE_URL_FILE + postReply2Bean.avatar)
                        .into(mAvatar)
                helper.setText(R.id.item_name, postReply2Bean.nickname)
                        .setText(R.id.item_content, postReply2Bean.postContentPlaintext)
                        .setText(R.id.item_time, postReply2Bean.postingTime)
                        .setText(R.id.item_tv_like, "${postReply2Bean.likeCount}")
            }
        }
    }
}
