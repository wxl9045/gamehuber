package safe.com.gamehuber.mvp.home


import org.jetbrains.anko.toast
import safe.com.gamehuber.mvp.base.impl.BasePresenter

class ForumPresenter : BasePresenter<RankFragment>(){

    fun doLogin(){
//        launchHttpExt ({
////
////        },{s,e->})
//        GlobalScope.launch(Dispatchers.Main) {
//            var loginBean = LoginService.login_Aesnet()
////            loginBean.result?.name
//        }
        view.activity?.toast("aa")
    }
    fun checkUserName(userNmae : String) = userNmae.isNotEmpty()
    fun checkPwd(pwd : String) = pwd.isNotEmpty() && pwd.length in 6..10
}