package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.BaseRecordsBean
import safe.com.gamehuber.mvp.model.bean.HomeBannerBean
import safe.com.gamehuber.mvp.model.bean.HomeGameBean
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
}
