package safe.com.gamehuber.net.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.mvp.model.bean.BaseRecordsBean
import safe.com.gamehuber.mvp.model.bean.HomeBannerBean
import safe.com.gamehuber.mvp.model.bean.HomeGameBean
import safe.com.gamehuber.net.myRetrofit

interface HomeApi{

    /**
     * 首页轮播图
     */
    @GET("/v1/Advertising/selectBySpace/SHUFFLING_FIGURE")
    suspend fun getBanners() : BaseBean<List<HomeBannerBean>>

    /**
     * 首页列表
     */
    @POST("/v1/Home/list")
    suspend fun getHomeList(@Body body: RequestBody) : BaseBean<BaseRecordsBean<List<HomeGameBean>>>
}

object HomeService: HomeApi by myRetrofit.create(HomeApi::class.java)