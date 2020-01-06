package safe.com.gamehuber.mvp.login

import android.view.View
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.userEmail
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity

class CreateAccountActivity : BaseMvpActivity<LoginPresenter>(){
    override fun getLayoutId(): Int = R.layout.activity_create_account

    override fun getImmersionBarColor(): Int  = R.color.white

    override fun initView() {
        setMyClickListener(btSignUp,sendCode,close)
    }

    override fun onMyClick(v: View?) {
        when(v?.id){
            R.id.btSignUp -> signUp()
            R.id.sendCode -> signUp()
            R.id.close -> finish()
        }
    }

    fun signUp() {
        presenter.checkUserName(userEmail.text.toString()).no {
            toast("请输入用户名")
            return
        }
        presenter.checkPwd(userPwd.text.toString()).no {
            toast("密码格式不正确")
            return
        }
        presenter.doLogin()
    }
}