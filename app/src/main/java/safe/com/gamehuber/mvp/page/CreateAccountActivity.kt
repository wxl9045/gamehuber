package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_create_account.*
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.RegisterPresenter

class CreateAccountActivity : BaseMvpActivity<RegisterPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_create_account

    override fun getImmersionBarColor(): Int = R.color.white

    override fun initView() {
        setMyClickListener(btSignUp, sendCode, close)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btSignUp ->
                (presenter.checkEmail(userEmail.text.toString()) && presenter.checkPwd(newPwd.text.toString()) &&
                        presenter.checkConfirmPwd(newPwd.text.toString(), confirmPwd.text.toString())).yes {
                    presenter.doRegister(userEmail.text.toString(),newPwd.text.toString(),verCode.text.toString())
                }
            R.id.sendCode ->
                presenter.checkEmail(userEmail.text.toString()).yes {
                    presenter.sendEmail(userEmail.text.toString())
                }
            R.id.close -> finish()
        }
    }

}