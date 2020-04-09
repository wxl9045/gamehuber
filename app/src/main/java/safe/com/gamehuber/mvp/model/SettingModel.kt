package safe.com.gamehuber.mvp.model

import safe.com.gamehuber.common.utils.getRequestBody
import safe.com.gamehuber.mvp.base.impl.BaseModel
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.net.service.SettingService

class SettingModel : BaseModel() {
    suspend fun editUser(map: Map<String,Any?>): MyResult<LoginBean> {
        return apiCall(call = {
            executeResponse(SettingService.editUser(getRequestBody(map)))
        }, errorMessage = "网络异常")
    }
}
