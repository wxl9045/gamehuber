package safe.com.gamehuber.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.SelectGameBean
import safe.com.gamehuber.net.UrlConstant


class SelectGameAdapter(beans: List<SelectGameBean>) :
        BaseQuickAdapter<SelectGameBean, BaseViewHolder>(R.layout.item_select_game, beans) {
    override fun convert(helper: BaseViewHolder, item: SelectGameBean) {
        var imageView = helper.getView<ImageView>(R.id.im_icon)
        Glide.with(mContext).load(UrlConstant.BASE_URL_FILE + item.icon).into(imageView)
        helper.setText(R.id.tv_game_name,item.title).setText(R.id.tv_item_desc,item.content)
    }
}
