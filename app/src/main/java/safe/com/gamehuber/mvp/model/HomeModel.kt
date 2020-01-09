package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.net.service.HomeService

class HomeModel : BaseModel(){
    suspend fun getBanners(): MyResult<HomeBean> {
        return apiCall(call = {
            executeResponse2(HomeService.getHomeData(2))
        },errorMessage = "网络异常")
    }
}
