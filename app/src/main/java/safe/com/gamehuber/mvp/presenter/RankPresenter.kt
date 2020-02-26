package safe.com.gamehuber.mvp.home


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.HomeModel
import safe.com.gamehuber.mvp.page.fragment.RankListFragment

class RankPresenter : BasePresenter<RankListFragment>() {

    fun getRankList(page: Int, type: Int, typeId: String) {
        val condition = mapOf("typeCode" to type, "typeId" to typeId)
        val map = mapOf("pageNo" to page, "pageSize" to 10, "condition" to condition)
        GlobalScope.launch(Dispatchers.Main) {
            val result = HomeModel().rankList(map)
            if (result is MyResult.Success) {
                view.getRankList(result.data.records)
            }
            view.missRefresh()
        }
    }
}