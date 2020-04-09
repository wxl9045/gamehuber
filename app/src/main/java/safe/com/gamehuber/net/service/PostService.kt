package safe.com.gamehuber.net.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import safe.com.gamehuber.mvp.model.bean.*
import safe.com.gamehuber.net.mRetrofit

interface PostApi {
    /**
     * 帖子 选择游戏列表
     */
    @POST("/v1/Topic/search")
    suspend fun topicSearch(@Body body: RequestBody): BaseBean<BaseRecordsBean<List<SelectGameBean>>>


    /**
     * 根据游戏id 显示所有分区
     */
    @GET("/v1/PostType/topic/{id}")
    suspend fun postType(@Path("id") id: String): BaseBean<List<PostTypeBean>>

    /**
     * 发帖子
     */
    @POST("/v1/Post/add")
    suspend fun addPost(@Body body: RequestBody): BaseBean<Any>


    /**
     * 搜索帖子 帖子列表页
     */
    @POST("/v1/Post/searchPost")
    suspend fun searchPost(@Body body: RequestBody): BaseBean<BaseRecordsBean<List<PostReplyBean>>>

    /**
     * 搜索更多帖子 帖子列表子 列表
     */
    @POST("/v1/Post/searchPost")
    suspend fun searchMorePost(@Body body: RequestBody): BaseBean<BaseRecordsBean<List<PostReply2Bean>>>

    /**
     * 帖子详情
     */
    @GET("/v1/Post/detail/{id}")
    suspend fun postDetail(@Path("id") id: String): BaseBean<PostDetailBean>

    /**
     * 帖子回复
     */
    @POST("/v1/Post/reply")
    suspend fun postReply(@Body body: RequestBody): BaseBean<PostReplyBean>

}

object PostService : PostApi by mRetrofit.create(PostApi::class.java)