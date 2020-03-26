package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.page.PostDetailActivity

class PostDetailPresenter : BasePresenter<PostDetailActivity>() {

    fun searchPost(page: Int) {
        var map = HashMap<String, Any>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().searchPost(page, map)
            if (result is MyResult.Success) view.showPostDetailListData(result.data.records)
            view.missRefresh()
        }
    }

    fun postDetail(id: String) {
        var map = HashMap<String, Any>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().postDetail(id)
            if (result is MyResult.Success) view.getPostDetail(result.data)
            view.missRefresh()
        }
    }

    fun sendPost(map: Map<String,Any>) {
        view.showDialog("发送中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().addPost(map)
            if (result is MyResult.Success) {
                view.sendPostOk()
            } else {
                view.toast(result.getString())
            }
            view.missDialog()
        }
    }
}