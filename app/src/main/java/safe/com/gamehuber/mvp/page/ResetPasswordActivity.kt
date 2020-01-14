package safe.com.gamehuber.mvp.page

import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_reset_password.*
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.ResetPwdPresenter
import java.util.concurrent.TimeUnit

class ResetPasswordActivity : BaseMvpActivity<ResetPwdPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_reset_password

    override fun getImmersionBarColor(): Int = R.color.white

    override fun initView() {
        setMyClickListener(btConfirm, sendCode, close)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btConfirm -> (presenter.checkEmail(userEmail.text.toString()) && presenter.checkPwd(userNewPwd.text.toString()) &&
                    presenter.checkConfirmPwd(userNewPwd.text.toString(), confirm_password.text.toString())).yes {
                presenter.doReset(userEmail.text.toString(), userNewPwd.text.toString(), verCode.text.toString())
            }
            R.id.sendCode -> presenter.checkEmail(userEmail.text.toString()).yes {
                changeBtnStatue()
                presenter.sendEmail(userEmail.text.toString())
            }
            R.id.close -> finish()
        }
    }

    /**
     * 改变按钮状态   60秒后可用
     */
    private fun changeBtnStatue() {
        val countTime = 60 //总时间
        Observable.interval(0, 1, TimeUnit.SECONDS)//0延迟  每隔1秒触发
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take((countTime + 1).toLong()) //设置循环次数
                .map({ aLong -> countTime - aLong }) //从60-1
                .doOnSubscribe({ disposable -> sendCode.setEnabled(false) })
                .subscribe(object : Observer<Long> {
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        with(sendCode) {
                            text = resources.getString(R.string.send_code)
                            isEnabled = true
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(aLong: Long?) {//每隔一秒执行
                        sendCode.text = "重获(" + aLong + "s)"
                    }
                })
    }
}