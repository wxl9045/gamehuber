package safe.com.gamehuber.adapter

import android.graphics.Color
import android.support.v7.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R


class GameLabelAdapter(labels: List<String>):
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_label,labels)  {

    override fun convert(helper: BaseViewHolder, data: String) {
        helper.setText(R.id.item_tv_label, data)
        var cv = helper.getView<CardView>(R.id.card_bg)
        var colorStr = when(helper.adapterPosition){
            0 -> "#f6d7a9"
            1 -> "#d7d5f0"
            2 -> "#ceb5dc"
            3 -> "#e5978e"
            4 -> "#b4b9bc"
            5 -> "#86c3b8"
            else -> "#00574B"
        }
        cv.setCardBackgroundColor(Color.parseColor(colorStr))
    }
}