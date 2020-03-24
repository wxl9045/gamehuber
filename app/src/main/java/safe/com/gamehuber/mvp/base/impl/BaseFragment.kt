package safe.com.gamehuber.mvp.base.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment(),View.OnClickListener{
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

    protected open fun setMyClickListener(vararg vs : View){
        for(it in vs){
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

    protected open fun init(savedInstanceState: Bundle?) {}
    protected abstract fun getLayoutId(): Int
    protected open fun initView() {}
    protected open fun initData() {}
    protected open fun initTitle(){}
}
