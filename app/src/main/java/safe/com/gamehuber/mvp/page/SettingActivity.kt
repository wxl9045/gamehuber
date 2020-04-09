package safe.com.gamehuber.mvp.page

import android.view.View
import kotlinx.android.synthetic.main.activity_setting.*
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.ui.CustomDatePicker
import safe.com.gamehuber.common.utils.changeXYear
import safe.com.gamehuber.common.utils.getSystemDate
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.home.SettingPresenter


/**
 * 设置页
 */
class SettingActivity : BaseMvpActivity<SettingPresenter>() {

    private var customDatePicker: CustomDatePicker? = null
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        presenter.setUserData()   //设置用户本地数据
        sdTitle.setTitle("Setting")
                .setRightIcon(R.mipmap.icon_back)
                .setLeftText("Done")
                .onLeftBtnClick {  presenter.editUser() }
                .onRightBtnClick { finish() }
        initDatePicker()
        setMyClickListener(tvSelectDate)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.tvSelectDate -> tvSelectDate.text.isNullOrEmpty().yes {
                customDatePicker?.show(getSystemDate("yyyy-MM-dd HH:mm"))
            }.otherwise {
                customDatePicker?.show(tvSelectDate.text.toString())
            }
        }
    }

    private fun initDatePicker() {
        val startYear = changeXYear(-2)
        val endYear = changeXYear(5)
        customDatePicker = CustomDatePicker(this, CustomDatePicker.ResultHandler { time ->
            // 回调接口，获得选中的时间
            var times = time.split(" ")//只获取年月日 不要时分
            tvSelectDate.text = times[0]
        }, startYear, endYear) // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker?.showSpecificTime(false) // 不显示时和分
        customDatePicker?.setIsLoop(false) // 不允许循环滚动
    }

}