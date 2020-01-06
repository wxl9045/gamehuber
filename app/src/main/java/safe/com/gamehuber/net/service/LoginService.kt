package safe.com.gamehuber.net.service

import retrofit2.http.POST
import safe.com.gamehuber.mvp.model.bean.BaseBean
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.net.retrofit

interface LoginApi{
    /**
     * 登录获取(Aes加密)
     */
    @POST("business/v1/login/appAes")
    suspend fun login_Aesnet(): BaseBean<LoginBean>

}

object LoginService: LoginApi by retrofit.create(LoginApi::class.java)