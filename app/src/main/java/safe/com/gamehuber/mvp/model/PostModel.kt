package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.BaseRecordsBean
import safe.com.gamehuber.mvp.model.bean.PostTypeBean
import safe.com.gamehuber.mvp.model.bean.SelectGameBean
import safe.com.gamehuber.net.service.PostService

class PostModel : BaseModel() {

    suspend fun topicSearch(page: Int, searchType: Int, title: String): MyResult<BaseRecordsBean<List<SelectGameBean>>> {
        return apiCall(call = {
            val condition = mapOf("searchType" to searchType, "title" to title)
            val map = mapOf("pageNo" to page, "pageSize" to 10, "condition" to condition)
            executeResponse(PostService.topicSearch(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    suspend fun postType(id: String): MyResult<List<PostTypeBean>> {
        return apiCall(call = {
            executeResponse(PostService.postType(id))
        }, errorMessage = "网络异常")
    }

    suspend fun addPost(map: Map<String,Any>): MyResult<Any> {
        return apiCall(call = {
            executeResponse(PostService.addPost(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }
}
