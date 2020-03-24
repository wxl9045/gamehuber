package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.page.PostTypeActivity

class PostTypePresenter : BasePresenter<PostTypeActivity>() {

    fun postType(id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().postType(id)
            if (result is MyResult.Success) view.showPostTypeData(result.data)
        }
    }
}