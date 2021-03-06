package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.model.UploadFileModel
import safe.com.gamehuber.mvp.page.RichEditorActivity

class RichEditorPresenter : BasePresenter<RichEditorActivity>() {

    fun addPost(map: Map<String,Any>) {
        view.showDialog("提交中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().addPost(map)
            if (result is MyResult.Success) {
                view.finish()
            } else {
                view.toast(result.getString())
            }
            view.missDialog()
        }
    }

    fun uploadFile(files : ArrayList<String>) {
        view.showDialog("提交中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = UploadFileModel().uploadFiles(files)
            if (result is MyResult.Success) {
                view.getFileData(result.data)
            } else {
                view.toast(result.getString())
            }
            view.missDialog()
        }
    }
}