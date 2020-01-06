package safe.com.gamehuber.mvp.home.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.home.HomeFragment
import safe.com.gamehuber.mvp.home.model.HomeModel

class HomePresenter : BasePresenter<HomeFragment>(){

    fun getHomeData(){
        GlobalScope.launch(Dispatchers.IO) {
            val res = HomeModel().getBanners()
        }
    }
}