package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_create_account.close
import kotlinx.android.synthetic.main.activity_create_account.sendCode
import kotlinx.android.synthetic.main.activity_reset_password.*
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.LoginPresenter

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

    private fun startConfirm() {
//        presenter.doLogin()
    }
}