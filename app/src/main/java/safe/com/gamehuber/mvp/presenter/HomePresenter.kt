package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.HomeFragment

class HomePresenter : BasePresenter<HomeFragment>(){

    fun getHomeData(){
        GlobalScope.launch(Dispatchers.IO) {
            val res = HomeModel().getBanners()
        }
    }
}