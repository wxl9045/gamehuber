package safe.com.gamehuber.mvp.home


import com.imydao.jiangbei.sp.DelegatesSP
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.AppContext
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.SettingModel
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.mvp.page.SettingActivity

class SettingPresenter : BasePresenter<SettingActivity>() {
    private var loginBean: LoginBean? by DelegatesSP.userInfoSP(AppContext)
    fun editUser() {
        var gender = 0
        view.rbMan.isChecked.yes {
            gender = 0
        }
        view.rbWoman.isChecked.yes {
            gender = 1
        }
        view.rbSecret.isChecked.yes {
            gender = 2
        }
        var map = mapOf(
                "id" to loginBean?.id,
                "token" to loginBean?.token,
                "birthday" to view.tvSelectDate.text.toString(),
                "gender" to gender,
                "nickname" to view.edNickName.text.toString(),
                "intro" to view.edDesc.text.toString(),
                "comefrom" to view.edCountry.text.toString()
        )
        view.showDialog("保存中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = SettingModel().editUser(map)
            if (result is MyResult.Success) {
                loginBean = result.data
            }
            view.missDialog()
        }
    }

    fun setUserData() {
        loginBean?.nickname.isNullOrEmpty().no {
            view.edNickName.setText(loginBean?.nickname)
        }
        loginBean?.intro.isNullOrEmpty().no {
            view.edDesc.setText(loginBean?.intro)
        }
        loginBean?.comefrom.isNullOrEmpty().no {
            view.edCountry.setText(loginBean?.comefrom)
        }
        loginBean?.birthday.isNullOrEmpty().no {
            view.tvSelectDate.text = loginBean?.birthday
        }
        when (loginBean?.gender) {
            0 -> view.rbMan.isChecked = true
            1 -> view.rbWoman.isChecked = true
            2 -> view.rbSecret.isChecked = true
        }
        loginBean?.email.isNullOrEmpty().no {
            view.tvShowEmail.text = loginBean?.email
        }
    }
}