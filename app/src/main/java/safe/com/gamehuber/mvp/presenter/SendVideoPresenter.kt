package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.model.UploadFileModel
import safe.com.gamehuber.mvp.page.SendVideoActivity

class SendVideoPresenter : BasePresenter<SendVideoActivity>() {

    fun uploadVideo(url: String) {
        view.showDialog("提交中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = UploadFileModel().uploadFileVideo(url)
            if (result is MyResult.Success) {
                view.uploadVideoOk(result.data)
            } else {
                view.toast(result.getString())
            }
            view.missDialog()
        }
    }

    fun uploadFiles(files: List<String>) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = UploadFileModel().uploadFiles(files)
            if (result is MyResult.Success) {
                view.uploadFileOk(result.data)
            } else {
                view.toast(result.getString())
            }
        }
    }

    fun addPost(map: Map<String,Any>) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().addPost(map)
            if (result is MyResult.Success) {
                view.finish()
            } else {
                view.toast(result.getString())
            }
        }
    }

}