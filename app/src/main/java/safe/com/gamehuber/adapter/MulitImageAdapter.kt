package safe.com.gamehuber.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.model.bean.HomeGameBean


class MulitImageAdapter(beans: List<HomeGameBean>):
        BaseQuickAdapter<HomeGameBean, BaseViewHolder>(R.layout.item_image,beans)  {

    override fun convert(helper: BaseViewHolder, bean : HomeGameBean) {
        val imPhoto = helper.getView<ImageView>(R.id.im_photo)
        Glide.with(mContext)
                .load(bean.content)
                .apply(RequestOptions().placeholder(R.mipmap.placeholder_banner))
                .transition(DrawableTransitionOptions().crossFade())
                .into(imPhoto)
    }
}