package safe.com.gamehuber.mvp.base.impl

sealed class MyResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : MyResult<T>()

    data class Error(val exception: Exception) : MyResult<Nothing>()

    fun getString(): String {
        return when (this) {
            is Success<*> -> "[data=$data]"
            is Error -> "${exception.message}"
        }
    }
}