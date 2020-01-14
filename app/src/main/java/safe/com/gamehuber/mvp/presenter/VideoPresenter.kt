package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.TestModel
import safe.com.gamehuber.mvp.page.fragment.VideoFragment

class VideoPresenter : BasePresenter<VideoFragment>(){

    fun getHomeData(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { TestModel().getTest() }
            if (result is MyResult.Success) view.showVideoListData(result.data)
        }
    }
}