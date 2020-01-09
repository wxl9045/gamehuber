package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.LoginPresenter

class LoginActivity : BaseMvpActivity<LoginPresenter>(){
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        setMyClickListener(btLogin,forgetPassword,createAccount)
    }

    override fun onMyClick(v: View?) {
        when(v?.id){
            R.id.btLogin -> presenter.doLogin(userEmail.text.toString(),userPwd.text.toString())
            R.id.forgetPassword -> startActivity<ResetPasswordActivity>()
            R.id.createAccount -> startActivity<CreateAccountActivity>()
        }
    }
}