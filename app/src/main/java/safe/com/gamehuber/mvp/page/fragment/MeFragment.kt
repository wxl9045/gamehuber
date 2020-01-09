package safe.com.gamehuber.mvp.page.fragment

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_me.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.home.MePresenter


class MeFragment : BaseMvpFragment<MePresenter>(){
    override fun getLayoutId(): Int = R.layout.fragment_me

    override fun initView() {
        Glide.with(this)
                .load(R.mipmap.bg_two)
                .into(imageView)
        Glide.with(this)
                .load("http://img.kaiyanapp.com/f9eae3e0321fa1e99a7b38641b5536a2.jpeg?imageMogr2/quality/60/format/jpg")
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_avatar)

        //状态栏透明和间距处理
//        activity?.let { StatusBarUtil.darkMode(it) }
//        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }
}