package safe.com.gamehuber.mvp.login

import android.view.View
import kotlinx.android.synthetic.main.activity_login.btLogin
import kotlinx.android.synthetic.main.activity_login2.*
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity

class LoginActivity : BaseMvpActivity<LoginPresenter>(){
    override fun getLayoutId(): Int = R.layout.activity_login2

    override fun initView() {
        setMyClickListener(btLogin,forgetPassword,createAccount)
    }

    override fun onMyClick(v: View?) {
        when(v?.id){
            R.id.btLogin -> startLogin()
            R.id.forgetPassword -> startActivity<ResetPasswordActivity>()
            R.id.createAccount -> startActivity<CreateAccountActivity>()
        }
    }

    fun startLogin() {
//        presenter.checkUserName(userEmail.text.toString()).no {
//            toast("请输入用户名")
//            return
//        }
//        presenter.checkPwd(userPwd.text.toString()).no {
//            toast("密码格式不正确")
//            return
//        }
        presenter.doLogin()
    }

}