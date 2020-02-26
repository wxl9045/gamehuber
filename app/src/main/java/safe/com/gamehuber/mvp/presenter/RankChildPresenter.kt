package safe.com.gamehuber.mvp.home


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.RankChildFragment

class RankChildPresenter : BasePresenter<RankChildFragment>(){
    fun getTopicType(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = HomeModel().getTopicType()
            if(result is MyResult.Success){
                view.showTopicType(result.data)
            }
        }
    }
}