package safe.com.gamehuber.mvp.home

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_video.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.Constants
import safe.com.gamehuber.R
import safe.com.gamehuber.R.id.mRecyclerView
import safe.com.gamehuber.adapter.FollowAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.forum.presenter.FollowPresenter
import safe.com.gamehuber.mvp.model.bean.HomeBean

class FollowFragment : BaseMvpFragment<FollowPresenter>(){
    private var followAdapter : FollowAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun initView() {
    }

    override fun initData() {
        presenter.getHomeData()
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    fun showVideoListData(homeBean: HomeBean){
        mRecyclerView.apply {
            layoutManager =linearLayoutManager
            followAdapter = FollowAdapter(homeBean.issueList[0].itemList)
            adapter = followAdapter
            followAdapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, _ ->
                activity?.startActivity<VideoDetailActivity>(
                        Constants.BUNDLE_VIDEO_DATA to homeBean.issueList[0].itemList,
                        VideoDetailActivity.TRANSITION to true
                )
                activity?.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }
            itemAnimator = DefaultItemAnimator()
        }
    }

    fun showError(e : Exception){
        activity?.toast(e.toString())
    }


}