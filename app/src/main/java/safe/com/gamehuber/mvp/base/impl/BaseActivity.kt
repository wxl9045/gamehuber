package safe.com.gamehuber.mvp.base.impl

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.setFitsSystemWindows
import com.jakewharton.rxbinding2.view.RxView
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.ui.DialogUtils
import java.util.concurrent.TimeUnit

abstract class BaseActivity : AppCompatActivity(),View.OnClickListener {
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getLayoutId())
        init(savedInstanceState)
        initImmersionBar(getImmersionBarColor())
        initData()
        initView()
        initTitle()
    }

    protected open fun initTitle(){}

    private fun initImmersionBar(colorInt : Int){
        supportActionBar?.hide()
        isImmersionBarImage().yes {
            immersionBar {
                statusBarDarkFont(true)
                statusBarColor(R.color.color_translucent)
                navigationBarColor(R.color.color_translucent)
            }
        }.otherwise {
            setFitsSystemWindows()
            immersionBar {
                if(colorInt == 0){
                    statusBarDarkFont(true)
                    statusBarColor(R.color.white)
                    navigationBarColor(R.color.white)
                }else{
                    statusBarColor(colorInt)
                    navigationBarColor(colorInt)
                }
            }
        }

    }

    protected open fun isImmersionBarImage() : Boolean = false

    protected open fun getImmersionBarColor() : Int = 0


    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {}

    protected open fun initView() {}

    protected open fun initData() {}

    protected open fun onMyClick(v: View?) {}

    override fun onBackPressed() {
        onBack()
    }

    protected open fun onBack() {
        super.onBackPressed()
    }


    protected open fun setMyClickListener(vararg vs : View){
        for(it in vs){
            it.setOnClickListener(this)
        }
    }
    /**
     * 防止按钮重复点击
     */
    override fun onClick(v: View?) {
        v?.let {
            RxView.clicks(it).throttleFirst(1, TimeUnit.SECONDS).subscribe{
                onMyClick(v)
            }
        }
    }

   override fun onDestroy() {
        super.onDestroy()
    }

    fun showDialog(message: String) {
        dialog = DialogUtils.createLoadingDialog(this,message)
    }
    fun missDialog() {
        DialogUtils.closeDialog(dialog)
    }
}