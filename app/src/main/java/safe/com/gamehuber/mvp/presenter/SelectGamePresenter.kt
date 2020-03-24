package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.page.SelectGameActivity

class SelectGamePresenter : BasePresenter<SelectGameActivity>() {

    fun getPostList(page: Int,searchType: Int,title: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().topicSearch(page,searchType,title)
            if (result is MyResult.Success) view.showPostListData(result.data.records)
            view.missRefresh()
        }
    }
}