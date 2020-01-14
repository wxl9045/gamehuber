package safe.com.gamehuber.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.HomeGameBean
import safe.com.gamehuber.net.UrlConstant


class HomeGameAdapter(beans: List<HomeGameBean>) :
        BaseQuickAdapter<HomeGameBean, BaseViewHolder>(R.layout.item_game_content, beans) {

    override fun convert(helper: BaseViewHolder, bean: HomeGameBean) {
        val imageCoverOrigin = helper.getView<ImageView>(R.id.img_game)
        val imgIcon = helper.getView<ImageView>(R.id.img_icon)
        when (bean.type) {
            4 -> {//单游戏推荐
                helper.setText(R.id.tv_game_tltle, bean.title)
                        .setGone(R.id.tv_game_big_tltle, false)
                        .setGone(R.id.card_icon, true)
                        .setText(R.id.tv_game_content_title, bean.name)
            }
            5 -> {//多个游戏推荐/专辑/合集
                helper.setGone(R.id.tv_game_tltle, true)
                        .setGone(R.id.card_icon, false)
                        .setGone(R.id.tv_game_content_title, false)
                        .setText(R.id.tv_game_big_tltle, bean.name)
            }
            6 -> {//内部推荐
                helper.setText(R.id.tv_game_content_title, bean.name)
                        .setGone(R.id.tv_game_big_tltle, false)
                        .setGone(R.id.card_icon, false)
                        .setGone(R.id.tv_game_tltle, false)
            }
        }
        Glide.with(mContext)
                .load(UrlConstant.BASE_URL_FILE + bean.icon)
                .into(imgIcon)
        Glide.with(mContext)
                .load(UrlConstant.BASE_URL_FILE + bean.coverOrigin)
                .into(imageCoverOrigin)
        helper.setText(R.id.tv_game_content, bean.desc)
    }
}