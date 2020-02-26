package safe.com.gamehuber

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sendtion.xrichtext.XRichText
import safe.com.gamehuber.common.utils.TransformationScale

private lateinit var INSTANCE: Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
//        ActivityBuilder.INSTANCE.init(this)
//        SwipeFinishable.INSTANCE.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initRichTextImg() //富文本编辑器图片加载 初始化 只能在application进行
    }

    private fun initRichTextImg() {
        XRichText.getInstance().setImageLoader { imagePath, imageView, imageHeight ->
            Log.e("---", "imageHeight: $imageHeight")
            //如果是网络图片
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                Glide.with(applicationContext).asBitmap().load(imagePath).apply(RequestOptions().dontAnimate())
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                if (imageHeight > 0) {//固定高度
                                    val lp = RelativeLayout.LayoutParams(
                                            FrameLayout.LayoutParams.MATCH_PARENT, imageHeight)//固定图片高度，记得设置裁剪剧中
                                    lp.bottomMargin = 10//图片的底边距
                                    imageView.layoutParams = lp
                                    Glide.with(applicationContext).asBitmap().load(imagePath)
                                            .apply(RequestOptions().placeholder(R.mipmap.img_load_fail).centerCrop().error(R.mipmap.img_load_fail))
                                            .into(imageView)
                                } else {//自适应高度
                                    Glide.with(applicationContext).asBitmap().load(imagePath)
                                            .apply(RequestOptions().placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail))
                                            .into(TransformationScale(imageView))
                                }
                            }
                        })
            } else { //如果是本地图片
                if (imageHeight > 0) {//固定高度
                    val lp = RelativeLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT, imageHeight)//固定图片高度，记得设置裁剪剧中
                    lp.bottomMargin = 10//图片的底边距
                    imageView.layoutParams = lp
                    Glide.with(applicationContext).asBitmap().load(imagePath)
                            .apply(RequestOptions().placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).centerCrop())
                            .into(imageView)
                } else {//自适应高度
                    Glide.with(applicationContext).asBitmap().load(imagePath)
                            .apply(RequestOptions().placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail))
                            .into(TransformationScale(imageView))
                }
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }

}

object AppContext: ContextWrapper(INSTANCE)