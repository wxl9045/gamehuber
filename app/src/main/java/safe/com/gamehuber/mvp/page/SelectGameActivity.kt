package safe.com.gamehuber.mvp.page

import kotlinx.android.synthetic.main.activity_select_game.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.SelectGameAdapter
import safe.com.gamehuber.common.ext.addTextChangedListenerEx
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.model.bean.SelectGameBean
import safe.com.gamehuber.mvp.presenter.SelectGamePresenter
/**
 * 选择游戏页
 */
class SelectGameActivity : BaseListActivity<SelectGamePresenter>() {

    private var selectGameAdapter: SelectGameAdapter? = null
    private var selectGameBeans: ArrayList<SelectGameBean> = ArrayList()

    override fun getLayoutId(): Int = R.layout.activity_select_game

    override fun onInit() {
        EventBus.getDefault().register(this)
        presenter.getPostList(1, 1, edSearch.text.toString())
        //搜索监听
        edSearch.addTextChangedListenerEx {
            isRefresh = true
            page = 1
            presenter.getPostList(1, 1, edSearch.text.toString())
        }
        initAdapter()
    }

    private fun initAdapter() {
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            selectGameAdapter = SelectGameAdapter(selectGameBeans)
            adapter = selectGameAdapter
            selectGameAdapter?.setOnItemClickListener{ _, _, position ->
                startActivity<PostTypeActivity>("id" to selectGameBeans[position].id,
                        "name" to selectGameBeans[position].title)
            }
        }

    }

    override fun onRefresh() {
        presenter.getPostList(1, 1, edSearch.text.toString())
    }

    override fun onLoadmore(page: Int) {
        presenter.getPostList(page, 1, edSearch.text.toString())
    }

    fun showPostListData(selectGameBeans: List<SelectGameBean>) {
        if (isRefresh) {
            this.selectGameBeans.clear()
        }
        this.selectGameBeans.addAll(selectGameBeans)
        selectGameAdapter?.notifyDataSetChanged()
    }

    @Subscribe
    fun getPostTypeEventBean(eventBean: PostTypeEventBean) {
        if (eventBean != null) {
                finish()
        }
    }
}