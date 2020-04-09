package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_CODE_LOGIN_SUCCESS
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_CODE_MAIN
import safe.com.gamehuber.MainActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.LoginPresenter

class LoginActivity : BaseMvpActivity<LoginPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_login
    private var code = 0
    override fun initView() {
        EventBus.getDefault().register(this)
        setMyClickListener(btLogin, forgetPassword, createAccount, close)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btLogin -> presenter.doLogin(userEmail.text.toString(), userPwd.text.toString())
            R.id.forgetPassword -> startActivity<ResetPasswordActivity>()
            R.id.createAccount -> startActivity<CreateAccountActivity>()
            R.id.close -> finish()
        }
    }

    fun start2Act() {
        (code == REQUEST_CODE_MAIN).yes {
            EventBus.getDefault().post(REQUEST_CODE_LOGIN_SUCCESS)
        }.otherwise { startActivity<MainActivity>() }
        finish()
    }

    @Subscribe
    fun getEventCode(code: Int) {
        //当用户未登陆时 其他页面点击跳转到登录页
        this.code = code
        close.visibility = View.VISIBLE
    }


}