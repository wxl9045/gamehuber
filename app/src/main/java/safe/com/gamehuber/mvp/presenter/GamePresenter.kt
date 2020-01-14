package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.GameFragment

class GamePresenter : BasePresenter<GameFragment>() {

    fun getBanners() {
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { HomeModel().getBanners() }
            if (result is MyResult.Success) {
                view.setBanners(result.data)
            }
        }
    }

    fun getHomeList(page : Int) {
        GlobalScope.launch(Dispatchers.Main)  {
            val result = HomeModel().getHomeList(page)
            if (result is MyResult.Success) {
                view.setHomeList(result.data.records)
            }
            view.missRefresh()
        }
    }
}