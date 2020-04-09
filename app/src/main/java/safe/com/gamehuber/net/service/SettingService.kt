package safe.com.gamehuber.net.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.net.mRetrofit

interface SettingApi {
    /**
     * 编辑个人信息
     */
    @POST("v1/User/edit")
    suspend fun editUser(@Body body: RequestBody): BaseBean<LoginBean>

}

object SettingService : SettingApi by mRetrofit.create(SettingApi::class.java)