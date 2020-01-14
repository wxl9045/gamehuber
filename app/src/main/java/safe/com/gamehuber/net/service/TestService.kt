package safe.com.gamehuber.net.service

import retrofit2.http.GET
import retrofit2.http.Query
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.net.retrofit

interface TestApi{
    /**
     * 首页精选
     */
    @GET("v2/feed?")
    suspend fun getTestData(@Query("num") num:Int) : HomeBean

}

object TestService: TestApi by retrofit.create(TestApi::class.java)