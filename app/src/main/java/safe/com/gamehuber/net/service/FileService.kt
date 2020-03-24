package safe.com.gamehuber.net.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.net.mRetrofit

interface FileApi {
    /**
     * 批量 上传接口
     */
    @Multipart
    @POST("v1/uploadFiles")
    suspend fun uploadFile(@Part parts: List<MultipartBody.Part>): BaseBean<List<String>>

    /**
     *  上传视频接口
     */
    @Multipart
    @POST("v1/uploadVideo")
    suspend fun uploadVideo(@Part parts: MultipartBody.Part): BaseBean<String>
}

object FileService : FileApi by mRetrofit.create(FileApi::class.java)