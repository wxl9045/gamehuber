package safe.com.gamehuber.mvp.model.bean

data class BaseRecordsBean<T>(val records: T, val total: Int, val pageNo: Int,val hasNext: Boolean)