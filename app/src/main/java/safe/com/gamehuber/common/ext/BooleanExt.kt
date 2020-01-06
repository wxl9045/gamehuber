package safe.com.gamehuber.common.ext

sealed class BooleanExt<out T>{
    object Otherwise : BooleanExt<Nothing>()
    class WithData<T>(val data: T) : BooleanExt<T>()
}


inline fun <T> Boolean.yes(block: ()->T)=
    when{
        this -> BooleanExt.WithData(block())
        else -> BooleanExt.Otherwise
    }


inline fun <T> Boolean.no(block: ()->T)=
    when{
        this -> BooleanExt.Otherwise
        else -> BooleanExt.WithData(block())
    }

inline fun <T> BooleanExt<T>.otherwise(block: ()->T)= when(this){
        is BooleanExt.Otherwise -> block()
        is BooleanExt.WithData -> this.data
}

