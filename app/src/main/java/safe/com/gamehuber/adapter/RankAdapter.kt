package safe.com.gamehuber.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R


class RankAdapter(labels: List<String>):
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rank,labels)  {

    override fun convert(helper: BaseViewHolder, data: String) {
    }
}