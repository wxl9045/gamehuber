package safe.com.gamehuber.mvp.base.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.mvp.model.bean.HomeBean
import java.io.IOException

open class BaseModel {
    suspend fun <T : Any> executeResponse(response: BaseBean<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): MyResult<T> {
        return coroutineScope {
            if (response.code == 200) {
                successBlock?.let { it() }
                MyResult.Success(response.data)
            } else {
                errorBlock?.let { it() }
                MyResult.Error(IOException(response.msg))
            }
        }
    }


    suspend fun executeResponse2(response: HomeBean, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                 errorBlock: (suspend CoroutineScope.() -> Unit)? = null): MyResult<HomeBean> {
        return coroutineScope {
            if (response.issueList != null && response.issueList.size > 0) {
                successBlock?.let { it() }
                MyResult.Success(response)
            } else {
                errorBlock?.let { it() }
                MyResult.Error(IOException("异常"))
            }
        }
    }

    suspend fun <T : Any> apiCall(call: suspend () -> MyResult<T>, errorMessage: String): MyResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            MyResult.Error(IOException(errorMessage, e))
        }
    }

}