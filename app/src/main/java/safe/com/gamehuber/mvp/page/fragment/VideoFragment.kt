package safe.com.gamehuber.mvp.page.fragment


import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_video.*
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.HomeVideoAdapter
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.model.bean.HomeVideoBean
import safe.com.gamehuber.mvp.presenter.VideoPresenter
import java.util.*

class VideoFragment : BaseMvpFragment<VideoPresenter>() {

    private var videoAdapter: HomeVideoAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_video
    private var page = 1
    private var isRefresh = false
    private var isLoadmore = false
    private var homeVideoBeans: ArrayList<HomeVideoBean> = ArrayList()
    override fun initView() {
        initAdapter()
        initRefresh()
    }

    override fun initData() {
        presenter.getVideoList(page)
    }

    private fun initAdapter(){
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            videoAdapter = HomeVideoAdapter(homeVideoBeans)
            adapter = videoAdapter
        }
    }
    private fun initRefresh() {
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            isLoadmore = false
            page = 1
            presenter.getVideoList(page)
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_translucent, R.color.color_title_bg)
        mRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            isLoadmore = true
            page++
            presenter.getVideoList(page)
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    fun showVideoListData(homeVideoBeans: List<HomeVideoBean>) {
        isRefresh.yes { this.homeVideoBeans.clear() }
        this.homeVideoBeans.addAll(homeVideoBeans)
        videoAdapter?.notifyDataSetChanged()
    }

    fun missRefresh() {
        isRefresh.yes {
            mRefreshLayout.finishRefresh()
        }
        isLoadmore.yes {
            mRefreshLayout.finishLoadmore()
        }
    }
}