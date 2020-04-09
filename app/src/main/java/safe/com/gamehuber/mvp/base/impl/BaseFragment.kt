package safe.com.gamehuber.mvp.base.impl

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import safe.com.gamehuber.common.ui.DialogUtils

abstract class BaseFragment : Fragment(), View.OnClickListener {
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initTitle()
        initData()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    protected open fun setMyClickListener(vararg vs: View) {
        for (it in vs) {
            it.setOnClickListener(this)
        }
    }

    protected open fun onMyClick(v: View?) {}

    /**
     * 防止按钮重复点击
     */
    override fun onClick(v: View?) {
        onMyClick(v)
    }

    fun showDialog(message: String) {
        dialog = DialogUtils.createLoadingDialog(activity, message)
    }

    fun missDialog() {
        DialogUtils.closeDialog(dialog)
    }

    protected open fun init(savedInstanceState: Bundle?) {}
    protected abstract fun getLayoutId(): Int
    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initTitle() {}
}
