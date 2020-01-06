package safe.com.gamehuber.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.home.VideoDetailActivity
import java.util.*

/**
 * 随机颜色
 */
fun getRandColorCode() : String{
    var r = ""
    var g = ""
    var b = ""
    var random = Random()
    r = Integer.toHexString(random.nextInt(256)).toUpperCase()
    g = Integer.toHexString(random.nextInt(256)).toUpperCase()
    b = Integer.toHexString(random.nextInt(256)).toUpperCase()
    r = if(r.length ==1) "0" else r
    g = if(g.length ==1) "0" else g
    b = if(b.length ==1) "0" else b
    return r+g+b
}

fun getMobileModel(): String {
    var model: String? = Build.MODEL
    model = model?.trim { it <= ' ' } ?: ""
    return model
}

fun anim2Act(activity : Activity,view : View,intent : Intent){
    intent.putExtra(VideoDetailActivity.TRANSITION, true)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        val pair = Pair(view, "IMG_TRANSITION")
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair)
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
    } else {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }
}