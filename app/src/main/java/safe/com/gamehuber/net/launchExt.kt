package safe.com.gamehuber.net

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias onStringUnit =  (s: String,e : Exception) -> Unit
inline fun launchHttpExt(crossinline onNext: () -> Unit, crossinline onError: onStringUnit) =
    GlobalScope.launch(Dispatchers.Main) {
        try { onNext.invoke() }
        catch (e:Exception){
            var msg = when(e){
                is UnknownHostException -> "网络异常"
                is SocketTimeoutException -> "网络异常"
                is HttpException -> "服务器异常"
                else -> "未知异常"
            }
            onError.invoke(msg,e)
            Log.e("a",e.toString())
        }
    }

