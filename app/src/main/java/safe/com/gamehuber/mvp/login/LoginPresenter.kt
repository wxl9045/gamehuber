package safe.com.gamehuber.mvp.login


import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.MainActivity
import safe.com.gamehuber.mvp.base.impl.BasePresenter

class LoginPresenter : BasePresenter<LoginActivity>(){
    
    fun doLogin(){
//        launchHttpExt ({
////
////        },{s,e->})
//        GlobalScope.launch(Dispatchers.Main) {
//            var loginBean = LoginService.login_Aesnet()
////            loginBean.result?.name
//        }
        view.toast("登录成功")
        view.startActivity<MainActivity>()
    }
    fun checkUserName(userNmae : String) = userNmae.isNotEmpty()
    fun checkPwd(pwd : String) = pwd.isNotEmpty() && pwd.length in 6..10
}