package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.*
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

    suspend fun addPost(map: Map<String, Any>): MyResult<Any> {
        return apiCall(call = {
            executeResponse(PostService.addPost(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    suspend fun searchPost(page: Int, condition: Map<String, Any>): MyResult<BaseRecordsBean<List<PostReplyBean>>> {
        val condition = mapOf(
                "topicId" to "1214575529597833218",
                "userId" to "1215287383127203841",
                "typeId" to "1236177600725864449",
                "pid" to "1")
        val map = mapOf("pageNo" to page, "pageSize" to 10, "condition" to condition)
        return apiCall(call = {
            executeResponse(PostService.searchPost(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    suspend fun postDetail(id: String): MyResult<PostDetailBean> {
        return apiCall(call = {
            executeResponse(PostService.postDetail(id))
        }, errorMessage = "网络异常")
    }

}
