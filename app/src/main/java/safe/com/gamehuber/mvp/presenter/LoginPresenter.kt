package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.MainActivity
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.utils.isEmail
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.LoginModel
import safe.com.gamehuber.mvp.page.LoginActivity

class LoginPresenter : BasePresenter<LoginActivity>(){
    
    fun doLogin(userEmail:String, pwd: String){
        userEmail.isEmpty().yes {
            view.toast("请输入邮箱")
            return
        }
        isEmail(userEmail).no {
            view.toast("邮箱格式不正确")
            return
        }
        pwd.isEmpty().yes {
            view.toast("请输入密码")
            return
        }
        view.showDialog("登录中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = LoginModel().login(userEmail, pwd)
            (result is MyResult.Success).yes {
                view.toast("登录成功")
                view.startActivity<MainActivity>()
            }
            view.missDialog()
        }
    }
}