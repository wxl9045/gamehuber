package safe.com.gamehuber.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.HomeGameBean


class CommunityAdapter(beans: List<HomeGameBean>):
        BaseQuickAdapter<HomeGameBean, BaseViewHolder>(R.layout.item_community,beans)  {

    override fun convert(helper: BaseViewHolder, bean : HomeGameBean) {

    }
}