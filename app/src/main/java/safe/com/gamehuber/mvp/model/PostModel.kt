package safe.com.gamehuber.mvp.model

import com.imydao.jiangbei.sp.DelegatesSP
import safe.com.gamehuber.AppContext
import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.*
import safe.com.gamehuber.net.service.PostService

class PostModel : BaseModel() {
    var loginBean: LoginBean? by DelegatesSP.userInfoSP(AppContext)
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

    suspend fun searchPost(page: Int, topicId: String, pid: String): MyResult<BaseRecordsBean<List<PostReplyBean>>> {
        val condition = mapOf(
                "topicId" to topicId,
                "pid" to pid,
                "containChild" to true,
                "containHot" to true)
        val map = mapOf("pageNo" to page, "pageSize" to 10, "condition" to condition)
        return apiCall(call = {
            executeResponse(PostService.searchPost(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    suspend fun searchMorePost(page: Int, topicId: String, pid: String): MyResult<BaseRecordsBean<List<PostReply2Bean>>> {
        val condition = mapOf(
                "topicId" to topicId,
                "pid" to pid)
        val map = mapOf("pageNo" to page, "pageSize" to 10, "condition" to condition)
        return apiCall(call = {
            executeResponse(PostService.searchMorePost(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    suspend fun postDetail(id: String): MyResult<PostDetailBean> {
        return apiCall(call = {
            executeResponse(PostService.postDetail(id))
        }, errorMessage = "网络异常")
    }

    suspend fun replyPost(postId: String, planTextContent: String): MyResult<PostReplyBean> {
        val map = mapOf(
                "postId" to postId,
                "planTextContent" to planTextContent
        )
        return apiCall(call = {
            executeResponse(PostService.postReply(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }
}
