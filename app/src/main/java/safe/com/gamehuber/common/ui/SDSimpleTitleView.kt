package safe.com.gamehuber.common.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.view_common_title.view.*
import org.jetbrains.anko.imageResource
import safe.com.gamehuber.R

/**
 * 公共界面title
 */
class SDSimpleTitleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle){

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_common_title, this, true)
    }

    /**
     * 设置左边图片
     */
    fun setLeftIcon(iconInt: Int) : SDSimpleTitleView{
        view_title_img_left.imageResource = iconInt
        return this
    }

    /**
     * 设置右边图片
     */
    fun setLeftText(text: String) : SDSimpleTitleView{
        view_title_txt_left.visibility = View.VISIBLE
        view_title_txt_left.text = text
        return this
    }

    /**
     * 设置右边文字
     * 此文字位置与右边图片位置一样 文字与图片不能同时设置
     */
    fun setRightText(txt: String) : SDSimpleTitleView{
        view_title_txt_right.text = txt
        return this
    }

    /**
     * 设置右边图片
     */
    fun setRightIcon(iconInt: Int) : SDSimpleTitleView{
        view_title_img_right.imageResource = iconInt
        return this
    }

    /**
     * 设置左边点击
     */
    fun onLeftBtnClick(onBtnClick: (view: View) -> Unit = {}) : SDSimpleTitleView{
        title_bt_left.setOnClickListener {onBtnClick.invoke(it)}
        return this
    }

    /**
     * 设置右边边点击
     */
    fun onRightBtnClick(onBtnClick: (view: View) -> Unit = {}) : SDSimpleTitleView{
        title_bt_right.setOnClickListener {onBtnClick.invoke(it)}
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(text: String) : SDSimpleTitleView {
        tv_title_text.text = text
        return this
    }

    /**
     * 通过string.xml设置标题
     */
    fun setTitle(stringID: Int) : SDSimpleTitleView{
        tv_title_text.setText(stringID)
        return this
    }

}
