package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.*
import safe.com.gamehuber.net.service.HomeService

class HomeModel : BaseModel(){
    suspend fun getBanners(): MyResult<List<HomeBannerBean>> {
        return apiCall(call = {
            executeResponse(HomeService.getBanners())
        },errorMessage = "网络异常")
    }

    suspend fun getHomeList(page : Int): MyResult<BaseRecordsBean<List<HomeGameBean>>> {
        return apiCall(call = {
            val map = mapOf("pageNo" to page, "pageSize" to 10)
            executeResponse(HomeService.getHomeList(getRequestBody(map)))
        },errorMessage = "网络异常")
    }

    suspend fun getVideoList(page : Int): MyResult<BaseRecordsBean<List<HomeVideoBean>>> {
        return apiCall(call = {
            val map = mapOf("pageNo" to page, "pageSize" to 10)
            executeResponse(HomeService.getVideoList(getRequestBody(map)))
        },errorMessage = "网络异常")
    }

    suspend fun getPremiereGame(): MyResult<GameBean> {
        return apiCall(call = {
            executeResponse(HomeService.getPremiereGame())
        },errorMessage = "网络异常")
    }

    suspend fun getAdvertising(): MyResult<GameBean> {
        return apiCall(call = {
            executeResponse(HomeService.getAdvertising())
        },errorMessage = "网络异常")
    }

    suspend fun getTopicType(): MyResult<List<TopicTypeBean>> {
        return apiCall(call = {
            executeResponse(HomeService.topicType())
        },errorMessage = "网络异常")
    }

    suspend fun rankList(map: Map<String,Any>): MyResult<BaseRecordsBean<List<RankListBean>>> {
        return apiCall(call = {
            executeResponse(HomeService.rankList(getRequestBody(map)))
        },errorMessage = "网络异常")
    }

    suspend fun postExtList(page: Int): MyResult<BaseRecordsBean<List<PostBean>>> {
        val map = mapOf("pageNo" to page, "pageSize" to 10)
        return apiCall(call = {
            executeResponse(HomeService.postExtList(getRequestBody(map)))
        },errorMessage = "网络异常")
    }
}
