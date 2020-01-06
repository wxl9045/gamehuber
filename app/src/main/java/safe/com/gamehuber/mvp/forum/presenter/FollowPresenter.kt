package safe.com.gamehuber.mvp.forum.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.home.FollowFragment
import safe.com.gamehuber.mvp.home.model.HomeModel

class FollowPresenter : BasePresenter<FollowFragment>(){

    fun getHomeData(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { HomeModel().getBanners() }
            if (result is MyResult.Success) view.showVideoListData(result.data)
        }
    }
}