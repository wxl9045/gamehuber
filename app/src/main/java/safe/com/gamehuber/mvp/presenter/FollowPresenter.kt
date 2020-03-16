package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.FollowFragment

class FollowPresenter : BasePresenter<FollowFragment>() {

    fun getPostList(page: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = HomeModel().postExtList(page)
            if (result is MyResult.Success) view.showPostListData(result.data.records)
            view.missRefresh()
        }
    }
}