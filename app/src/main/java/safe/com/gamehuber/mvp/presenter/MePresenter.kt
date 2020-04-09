package safe.com.gamehuber.mvp.home


import android.app.Activity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.imydao.jiangbei.sp.DelegatesSP
import kotlinx.android.synthetic.main.activity_user_detail.imageView
import kotlinx.android.synthetic.main.activity_user_detail.iv_avatar
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.AppContext
import safe.com.gamehuber.R

import safe.com.gamehuber.common.ext.no
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.SettingModel
import safe.com.gamehuber.mvp.model.UploadFileModel
import safe.com.gamehuber.mvp.model.bean.LoginBean
import safe.com.gamehuber.mvp.page.fragment.MeFragment
import safe.com.gamehuber.net.UrlConstant

class MePresenter : BasePresenter<MeFragment>() {
    private var loginBean: LoginBean? by DelegatesSP.userInfoSP(AppContext)

    fun uploadFile(files: ArrayList<String>) {
        view.showDialog("保存中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = UploadFileModel().uploadFiles(files)
            if (result is MyResult.Success) {
                view.getFileData(result.data)
            }
            view.missDialog()
        }
    }

    fun editUser(map: Map<String, Any?>) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = SettingModel().editUser(map)
            if (result is MyResult.Success) {
                loginBean = result.data
                setUserData()
            }
        }
    }

    fun setUserData() {
        Glide.with(view.context as Activity)
                .load(UrlConstant.BASE_URL_FILE + this.loginBean?.avatar)
                .apply(RequestOptions().placeholder(R.mipmap.default_avatar).circleCrop())
                .into(view.iv_avatar)
        Glide.with(view.context as Activity)
                .load(UrlConstant.BASE_URL_FILE + this.loginBean?.backImg)
                .apply(RequestOptions().placeholder(R.mipmap.bg_two).centerCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .into(view.imageView)
        this.loginBean?.nickname.isNullOrEmpty().no {
            view.user_name.text = this.loginBean?.nickname
        }
        this.loginBean?.intro.isNullOrEmpty().no {
            view.tv_hello.text = this.loginBean?.intro
        }
    }

}