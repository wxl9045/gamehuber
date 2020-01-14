package safe.com.gamehuber.mvp.model.bean

data class BaseBean<T>(val code: Int, val msg: String, val data: T)