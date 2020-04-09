package safe.com.gamehuber.mvp.presenter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.base.impl.MyResult
import safe.com.gamehuber.mvp.model.PostModel
import safe.com.gamehuber.mvp.page.PostDetailActivity

class PostDetailPresenter : BasePresenter<PostDetailActivity>() {
    private var topicId: String = ""
    private var pid: String = ""
    private var typeId: String = ""

    fun searchPost(page: Int) {
        (topicId.isNullOrEmpty() || pid.isNullOrEmpty()).yes {
            view.toast("未获取详情")
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().searchPost(page, topicId, pid)
            if (result is MyResult.Success) view.showPostDetailListData(result.data)
            view.missRefresh()
        }
    }

    /**
     * 点击更多查询子回复
     */
    fun searchChildPost(page: Int, topicId: String, pid: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().searchMorePost(page, topicId, pid)
            if (result is MyResult.Success) view.showMorePostList(result.data)
            view.missRefresh()
        }
    }

    fun postDetail(id: String) {
        var map = HashMap<String, Any>()
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().postDetail(id)
            if (result is MyResult.Success) {
                topicId = result.data.topicId
                pid = result.data.id//父贴id 也就是该帖子的id
                typeId = result.data.typeId
                view.getPostDetail(result.data)
                searchPost(1)
            }
        }
        view.missRefresh()
    }

    fun replyPost(id: String, planTextContent: String) {
        view.showDialog("发送中")
        GlobalScope.launch(Dispatchers.Main) {
            val result = PostModel().replyPost(id, planTextContent)
            if (result is MyResult.Success) {
                view.sendPostOk(result.data)
            } else {
                view.toast(result.getString())
            }
            view.missDialog()
        }
    }

}