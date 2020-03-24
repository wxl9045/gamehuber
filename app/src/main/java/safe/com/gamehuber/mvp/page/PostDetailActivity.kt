package safe.com.gamehuber.mvp.page

import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.graphics.Palette
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.SelectGameAdapter
import safe.com.gamehuber.mvp.model.bean.PostTypeEventBean
import safe.com.gamehuber.mvp.model.bean.SelectGameBean
import safe.com.gamehuber.mvp.presenter.SelectGamePresenter


/**
 * 帖子列表页
 */
class PostDetailActivity : BaseListActivity<SelectGamePresenter>() {

    private var selectGameAdapter: SelectGameAdapter? = null
    private var selectGameBeans: ArrayList<SelectGameBean> = ArrayList()

    override fun getLayoutId(): Int = R.layout.activity_post_detail
    override fun isImmersionBarImage(): Boolean = true
    override fun onInit() {
        EventBus.getDefault().register(this)
        initAdapter()
        initBg()
    }

    private fun initBg() {
        var drawable = imageView.drawable as BitmapDrawable
        Palette.from(drawable.bitmap).maximumColorCount(10).generate {
            var s1 = it.vibrantSwatch as Palette.Swatch
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frameBg.setBackgroundColor(s1?.rgb)
            }
        }
    }

    private fun initAdapter() {
        mRecyclerView.apply {
            layoutManager = linearLayoutManager
            selectGameAdapter = SelectGameAdapter(selectGameBeans)
            adapter = selectGameAdapter
        }

    }

    override fun onRefresh() {
    }

    override fun onLoadmore(page: Int) {
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

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}