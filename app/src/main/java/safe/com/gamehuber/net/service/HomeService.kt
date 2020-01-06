package safe.com.gamehuber.net.service

import retrofit2.http.GET
import retrofit2.http.Query
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.net.retrofit

interface HomeApi{
    /**
     * 首页精选
     */
    @GET("v2/feed?")
    suspend fun getHomeData(@Query("num") num:Int) : HomeBean
}

object HomeService: HomeApi by retrofit.create(HomeApi::class.java)