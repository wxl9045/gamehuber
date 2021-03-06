package safe.com.gamehuber.mvp.page.fragment

import android.content.Intent
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_follow.mRefreshLayout
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.fragment_video.mRecyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import safe.com.gamehuber.ConstantsCode.Companion.REQUEST_VIDEO_CODE
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.FollowAdapter
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.model.bean.PostBean
import safe.com.gamehuber.mvp.page.RichEditorActivity
import safe.com.gamehuber.mvp.page.SendVideoActivity
import safe.com.gamehuber.mvp.presenter.FollowPresenter


class FollowFragment : BaseMvpFragment<FollowPresenter>() {
    private var followAdapter: FollowAdapter? = null
    private var isRefresh = false
    private var isLoadmore = false
    private var page = 1
    override fun getLayoutId(): Int = R.layout.fragment_follow
    override fun initView() {
        initRefresh()
        setMyClickListener(btScore,btPost,btVideo)
    }

    override fun initData() {
        presenter.getPostList(page)
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun initRefresh() {
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            isLoadmore = false
            page = 1
            presenter.getPostList(page)
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_translucent, R.color.color_title_bg)
        mRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            isLoadmore = true
            page++
            presenter.getPostList(page)
        }
    }

    fun showPostListData(postBeans: List<PostBean>) {
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            followAdapter = FollowAdapter(postBeans)
            adapter = followAdapter
            followAdapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, _ ->
                //                activity?.startActivity<VideoDetailActivity>(
//                        Constants.BUNDLE_VIDEO_DATA to homeBean.issueList[0].itemList,
//                        VideoDetailActivity.TRANSITION to true
//                )
//                activity?.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }
//            itemAnimator = DefaultItemAnimator()
        }
    }

    fun showError(e: Exception) {
        activity?.toast(e.toString())
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.btScore -> {}
            R.id.btVideo -> {
                //视频贴
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_VIDEO_CODE)
            }
            R.id.btPost ->  activity?.startActivity<RichEditorActivity>()//图文贴
        }
    }

    fun missRefresh() {
        isRefresh.yes {
            mRefreshLayout.finishRefresh()
        }
        isLoadmore.yes {
            mRefreshLayout.finishLoadmore()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (requestCode == REQUEST_VIDEO_CODE).yes {
            (resultCode == AppCompatActivity.RESULT_OK).yes {
                val uri = data?.data
                val cr = activity?.contentResolver
                val cursor = cr?.query(uri, null, null, null, null)
                (cursor?.moveToFirst())?.yes {
                    //视频路径
                    val videoPath = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)?.let { cursor?.getString(it) }
                    //缩略图路径
                    val imagePath = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)?.let { cursor?.getString(it) }
                    activity?.startActivity<SendVideoActivity>("videoPath" to videoPath, "imagePath" to imagePath)
                }
            }
        }
    }
}