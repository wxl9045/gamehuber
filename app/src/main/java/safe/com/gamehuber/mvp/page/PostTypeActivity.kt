package safe.com.gamehuber.mvp.page

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_post_type.*
import org.greenrobot.eventbus.EventBus
import safe.com.gamehuber.R
import safe.com.gamehuber.R.id.reView
import safe.com.gamehuber.adapter.PostTypeAdapter
import safe.com.gamehuber.mvp.base.impl.BaseMvpActivity
import safe.com.gamehuber.mvp.model.bean.PostTypeBean
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.presenter.PostTypePresenter

/**
 * 游戏分区页
 */
class PostTypeActivity : BaseMvpActivity<PostTypePresenter>() {

    private var postTypeAdapter: PostTypeAdapter? = null
    private var postBeans: ArrayList<PostTypeBean> = ArrayList()
    override fun getLayoutId(): Int = R.layout.activity_post_type
    private val linearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun initView() {
        val id = intent.getStringExtra("id")
        val topicName = intent.getStringExtra("name")
        presenter.postType(id)
        reView.apply {
            layoutManager = linearLayoutManager
            postTypeAdapter = PostTypeAdapter(postBeans)
            adapter = postTypeAdapter
            postTypeAdapter?.setOnItemClickListener{ _, _, position ->
                var postTypeEventBean = PostTypeEventBean(postBeans[position].id, postBeans[position].name, id, topicName)
                EventBus.getDefault().post(postTypeEventBean)
                finish()
            }
        }
    }

    fun showPostTypeData(postBeans: List<PostTypeBean>) {
        this.postBeans.addAll(postBeans)
        postTypeAdapter?.notifyDataSetChanged()
    }
}