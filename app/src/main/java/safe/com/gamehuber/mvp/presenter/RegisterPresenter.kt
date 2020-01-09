package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.utils.isEmail
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.LoginModel
import safe.com.gamehuber.mvp.page.CreateAccountActivity

class RegisterPresenter : BasePresenter<CreateAccountActivity>() {

    fun doRegister(userEmail: String, pwd: String, verifyCode: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = LoginModel().register(userEmail, pwd, verifyCode)
            (result is MyResult.Success).yes {
                view.finish()
                view.toast("注册成功")
            }
        }
    }

    fun sendEmail(userEmail: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = LoginModel().sendEmail(userEmail)
            (result is MyResult.Success).yes {
                view.toast("邮件已发送")
            }
        }
    }

    //    fun checkPwd(pwd: String) = pwd.isNotEmpty() && pwd.length in 6..10
    fun checkEmail(userEmail: String): Boolean {
        userEmail.isEmpty().yes {
            view.toast("请填写邮箱")
            return false
        }
        isEmail(userEmail).no {
            view.toast("请填写正确邮箱")
            return false
        }
        return true
    }

    fun checkConfirmPwd(pwd: String, confirmPwd: String): Boolean {
        (pwd == confirmPwd).no {
            view.toast("俩次密码不一致")
            return false
        }
        return true
    }

    fun checkPwd(pwd: String): Boolean {
        pwd.isEmpty().yes {
            view.toast("请填写密码")
            return false
        }
        (pwd.length in 6..10).no {
            view.toast("请填写正确密码")
            return false
        }
        return true
    }
}