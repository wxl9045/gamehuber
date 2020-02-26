package safe.com.gamehuber.mvp.page.fragment

import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_video.mRecyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.Constants
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_VIDEO_CODE
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.FollowAdapter
import safe.com.gamehuber.common.ui.InvitationDialog
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.model.bean.HomeBean
import safe.com.gamehuber.mvp.page.RichEditorActivity
import safe.com.gamehuber.mvp.page.VideoDetailActivity
import safe.com.gamehuber.mvp.presenter.FollowPresenter


class FollowFragment : BaseMvpFragment<FollowPresenter>(){
    private var followAdapter : FollowAdapter? = null
    override fun getLayoutId(): Int = R.layout.fragment_follow
    override fun initView() {
        setMyClickListener(bt_editor)
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

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.bt_editor -> selectInvitation()
        }
    }

    private fun selectInvitation(){
        val invitationDialog = InvitationDialog(activity)
        invitationDialog.show()
        invitationDialog.setClicklistener(object : InvitationDialog.ClickListenerInterface {
            override fun doImageText() {
                //图文贴
                activity?.startActivity<RichEditorActivity>()
            }
            override fun doVideo() {
                //视频贴
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                activity?.startActivityForResult(intent, REQUEST_VIDEO_CODE)
            }
        })
    }
}