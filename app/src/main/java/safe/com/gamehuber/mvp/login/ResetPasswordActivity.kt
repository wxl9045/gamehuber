package safe.com.gamehuber.mvp.login

import android.view.View
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_account.close
import kotlinx.android.synthetic.main.activity_create_account.sendCode
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.userEmail
import kotlinx.android.synthetic.main.activity_reset_password.*
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity

class ResetPasswordActivity : BaseMvpActivity<LoginPresenter>(){
    override fun getLayoutId(): Int = R.layout.activity_reset_password

    override fun getImmersionBarColor(): Int  = R.color.white

    override fun initView() {
        setMyClickListener(btConfirm,sendCode,close)
    }

    override fun onMyClick(v: View?) {
        when(v?.id){
            R.id.btConfirm -> startConfirm()
            R.id.sendCode -> startConfirm()
            R.id.close -> finish()
        }
    }

    fun startConfirm() {
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