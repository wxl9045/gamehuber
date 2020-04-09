package safe.com.gamehuber.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.page.VideoDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

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


/**
 * 判断邮箱是否合法
 * @param email
 * @return
 */
fun isEmail(email : String) : Boolean {
    (null== email || "" === email).yes { return false }
    var p  = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
    var m = p.matcher(email)
    return m.matches()
}

/**
 * map 转 RequestBody
 *
 * @param mp
 * @return
 */
fun getRequestBody(mp: Map<String, Any?>): RequestBody {
    val gson = Gson()
    //        JSONObject jsonObject = new JSONObject(mp);
    val bodyContent = gson.toJson(mp)
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyContent)
}

/**
 * 获取系统当前时间戳
 *"yyyy-MM-dd HH:mm"
 * @return
 */
fun dateGetTime(formatterStr : String): Long {
    val formatter = SimpleDateFormat(formatterStr)
    val curDate = Date(System.currentTimeMillis())
    return curDate.time
}

/**
 * 获取系统当前时间"yyyy-MM-dd HH:mm"
 *
 * @return
 */
fun getSystemDate(formatterStr : String): String {
    val formatter = SimpleDateFormat(formatterStr)
    val curDate = Date(System.currentTimeMillis())
    return formatter.format(curDate)
}


//当前时间 改变x年
fun changeXYear(x: Int): String {
    val nowYear = Integer.parseInt(getSystemDate("yyyy-MM-dd HH:mm").substring(0, 4))
    return (nowYear + x).toString() + "-" + "01-01 00:00"
}