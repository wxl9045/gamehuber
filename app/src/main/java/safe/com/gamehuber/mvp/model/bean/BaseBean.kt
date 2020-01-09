package safe.com.gamehuber.mvp.model.bean

data class BaseBean<out T>(val code: Int, val msg: String, val data: T)