package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.net.service.LoginService


class LoginModel : BaseModel() {

    /**
     * 登录
     */
    suspend fun login(userEmail: String, password: String): MyResult<Any> {
        return apiCall(call = {
            var map = mapOf("email" to userEmail,"password" to password)
            executeResponse(LoginService.login(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }
    /**
     * 发送邮箱验证码
     */
    suspend fun sendEmail(userEmail: String): MyResult<Any> {
        return apiCall(call = {
            var map = mapOf("email" to userEmail)
            executeResponse(LoginService.sendMail(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    /**
     * 注册
     */
    suspend fun register(userEmail: String, password: String, verifyCode: String): MyResult<Any> {
        return apiCall(call = {
            var map = mapOf("email" to userEmail,
                    "password" to password, "verifyCode" to verifyCode)
            executeResponse(LoginService.register(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }

    /**
     * 重置 密码
     */
    suspend fun reset(userEmail: String, password: String, verifyCode: String): MyResult<Any> {
        return apiCall(call = {
            var map = mapOf("email" to userEmail,
                    "password" to password, "verifyCode" to verifyCode)
            executeResponse(LoginService.reset(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }
}
