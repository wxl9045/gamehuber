package safe.com.gamehuber.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.RankListBean
import safe.com.gamehuber.net.UrlConstant


class RankAdapter(beans: ArrayList<RankListBean>):
        BaseQuickAdapter<RankListBean, BaseViewHolder>(R.layout.item_rank,beans)  {

    override fun convert(helper: BaseViewHolder, bean: RankListBean) {
        val imIcon = helper.getView<ImageView>(R.id.im_icon)
        Glide.with(mContext)
                .load(UrlConstant.BASE_URL_FILE + bean.icon)
                .into(imIcon)
        helper.setText(R.id.rank_name,bean.forumName)
    }
}