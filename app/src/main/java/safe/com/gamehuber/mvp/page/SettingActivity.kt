package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast
import safe.com.gamehuber.R
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.presenter.PostDetailPresenter


/**
 * 设置页
 */
class SettingActivity : BaseMvpActivity<PostDetailPresenter>() {


    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        sdTitle.setTitle("Setting")
                .setRightIcon(R.mipmap.icon_back)
                .setLeftText("Done")
                .onLeftBtnClick { commitData() }
                .onRightBtnClick { finish() }
    }

    private fun commitData(){
        toast("提交成功")
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btSend -> {
//                var map = mapOf<String,String>(
//                        ""
//                )
//                presenter.sendPost()
            }
            R.id.btBack -> finish()
        }
    }

}