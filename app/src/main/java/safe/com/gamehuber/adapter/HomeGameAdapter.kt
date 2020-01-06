package safe.com.gamehuber.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.HomeGameBean


class HomeGameAdapter(beans: List<HomeGameBean>):
        BaseQuickAdapter<HomeGameBean, BaseViewHolder>(R.layout.item_game_content,beans)  {

    override fun convert(helper: BaseViewHolder, bean : HomeGameBean) {
        helper.setText(R.id.tv_game_content, bean.content)
    }
}