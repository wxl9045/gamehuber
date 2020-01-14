package safe.com.gamehuber.common.ui

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.common_card_info.view.*
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.MulitImageAdapter
import safe.com.gamehuber.mvp.model.bean.HomeGameBean

/**
 * 公共卡片详情样式
 */
class SDCardInfoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : CardView(context, attrs, defStyle){
    var cardModel : CardModel = CardModel.SINGLE_IMAGE//卡片模式 4种 单图 多图 视频 文字
    var coverImgUrl : String? = null //视频界面 图片
    var desc : String? = null//描述
    var avatarUrl : String? = null//用户头像
    val defAvatar = R.mipmap.default_avatar
    var myAdapter : MulitImageAdapter? = null
    private val homeGameBeans: ArrayList<HomeGameBean>  = ArrayList()
    enum class CardModel{
        SINGLE_IMAGE,MULTI_IMAGE,VIDEO,TEXT
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.common_card_info, this, true)
    }

    fun setCoverImgClickListener(onImageClick: (view: View) -> Unit = {}) : SDCardInfoView{
        iv_cover_feed.setOnClickListener { onImageClick.invoke(it) }
        return this
    }

    fun setAvatarClickListener(onImageClick: (view: View) -> Unit = {}) : SDCardInfoView{
        iv_avatar.setOnClickListener { onImageClick.invoke(it) }
        return this
    }
    fun cardCreate(){
        var imageView : ImageView = iv_cover_feed
        when(cardModel){
            CardModel.SINGLE_IMAGE -> {
                iv_cover_feed.visibility = View.GONE
                iv_single_image.visibility = View.VISIBLE
                re_image.visibility = View.GONE
                imageView = iv_single_image
            }
            CardModel.MULTI_IMAGE -> {
                iv_cover_feed.visibility = View.GONE
                iv_single_image.visibility = View.GONE
                re_image.visibility = View.VISIBLE
            }
            CardModel.VIDEO -> {
                iv_cover_feed.visibility = View.VISIBLE
                iv_single_image.visibility = View.GONE
                re_image.visibility = View.GONE
                imageView = iv_cover_feed
            }
            CardModel.TEXT -> {
                iv_cover_feed.visibility = View.GONE
                iv_single_image.visibility = View.GONE
                re_image.visibility = View.GONE
            }
        }
        Glide.with(context)
                .load(coverImgUrl)
                .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(imageView)
        tv_describe.text = desc
        Glide.with(context)
                .load(avatarUrl)
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_avatar)

        re_image.apply {
            var linearLayoutManager = GridLayoutManager(context, 3)
            layoutManager = linearLayoutManager
            myAdapter = MulitImageAdapter(homeGameBeans)
            adapter = myAdapter
        }
    }

}
