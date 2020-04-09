package safe.com.gamehuber.mvp.page.fragment

import android.view.View
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.ui.PictureDialog
import safe.com.gamehuber.mvp.base.impl.TakePhotoFragment
import safe.com.gamehuber.mvp.home.MePresenter
import safe.com.gamehuber.mvp.page.SettingActivity


class MeFragment : TakePhotoFragment<MePresenter>(), TakePhoto.TakeResultListener, InvokeListener {
    private var selectType = 0// 0头像 1背景
    override fun getLayoutId(): Int = R.layout.fragment_me

    override fun initView() {
        setMyClickListener(llSetting, iv_avatar, imageView)
    }

    override fun onStart() {
        super.onStart()
        presenter.setUserData()
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.llSetting -> context?.startActivity<SettingActivity>()
            R.id.iv_avatar -> {
                selectType = 0
                showPictureDialog()
            }
            R.id.imageView -> {
                selectType = 1
                showPictureDialog()
            }
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = PictureDialog(activity)
        pictureDialog.show()
        pictureDialog.setClicklistener(object : PictureDialog.ClickListenerInterface {
            override fun doCamera() {
                takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions)
            }

            override fun doLocal() {
                takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions)
            }
        })
    }

    /**
     * 以下照片相关
     */
    override fun takeSuccess(result: TResult) {
        var iconPath = result.image.originalPath
        var images = ArrayList<String>()
        images.add(iconPath)
        presenter.uploadFile(images)//上传照片
    }

    fun getFileData(fileIds: List<String>) {
        val map = HashMap<String, Any>()
        (selectType == 0).yes {
            map["avatar"] = fileIds[0]
        }.otherwise {
            map["backImg"] = fileIds[0]
        }
        presenter.editUser(map)//编辑用户头像或背景信息
    }
}