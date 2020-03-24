package safe.com.gamehuber.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.PostTypeBean


class PostTypeAdapter(beans: List<PostTypeBean>) :
        BaseQuickAdapter<PostTypeBean, BaseViewHolder>(R.layout.item_text, beans) {
    override fun convert(helper: BaseViewHolder, item: PostTypeBean) {
        helper.setText(R.id.tv_name,item.name)
    }
}
