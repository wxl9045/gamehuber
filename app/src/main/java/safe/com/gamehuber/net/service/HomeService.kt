package safe.com.gamehuber.net.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import safe.com.gamehuber.mvp.model.bean.*
import safe.com.gamehuber.net.mRetrofit

interface HomeApi{

    /**
     * 首页轮播图
     */
    @GET("/v1/Advertising/selectBySpace/SHUFFLING_FIGURE")
    suspend fun getBanners() : BaseBean<List<HomeBannerBean>>

    /**
     * 强推游戏
     */
    @GET("/v1/Home/premiereGame")
    suspend fun getPremiereGame() : BaseBean<GameBean>

    /**
     * 游戏 广告
     */
    @GET("/v1/Home/advertising")
    suspend fun getAdvertising() : BaseBean<GameBean>

    /**
     * 首页列表
     */
    @POST("/v1/Home/list")
    suspend fun getHomeList(@Body body: RequestBody) : BaseBean<BaseRecordsBean<List<HomeGameBean>>>

    /**
     * 首页视频
     */
    @POST("/v1/Home/videoList")
    suspend fun getVideoList(@Body body: RequestBody) : BaseBean<BaseRecordsBean<List<HomeVideoBean>>>

    /**
     * 排行榜tab
     */
    @GET("/v1/TopicType/selectAll")
    suspend fun topicType() : BaseBean<List<TopicTypeBean>>
//
//    /**
//     * 排行榜 列表
//     */
    @POST("/v1/Topic/rank")
    suspend fun rankList(@Body body: RequestBody) : BaseBean<BaseRecordsBean<List<RankListBean>>>


}

object HomeService: HomeApi by mRetrofit.create(HomeApi::class.java)