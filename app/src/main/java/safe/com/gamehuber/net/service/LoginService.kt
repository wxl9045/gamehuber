package safe.com.gamehuber.net.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.net.mRetrofit

interface LoginApi{
    /**
     * 登录
     */
    @POST("v1/User/login")
    suspend fun login(@Body body: RequestBody): BaseBean<LoginBean>


    /**
     * 发送验证码
     */
    @POST("v1/User/sendMail")
    suspend fun sendMail(@Body body: RequestBody): BaseBean<Any>

    /**
     * 注册
     */
    @POST("v1/User/register")
    suspend fun register(@Body body: RequestBody): BaseBean<Any>

    /**
     * 重置密码
     */
    @POST("v1/User/reset")
    suspend fun reset(@Body body: RequestBody): BaseBean<Any>
}

object LoginService: LoginApi by mRetrofit.create(LoginApi::class.java)