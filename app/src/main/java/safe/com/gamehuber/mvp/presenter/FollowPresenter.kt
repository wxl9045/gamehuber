package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.FollowFragment

class FollowPresenter : BasePresenter<FollowFragment>(){

    fun getHomeData(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { HomeModel().getBanners() }
            if (result is MyResult.Success) view.showVideoListData(result.data)
        }
    }
}