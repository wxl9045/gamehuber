package safe.com.gamehuber.mvp.page.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_game.*
import org.jetbrains.anko.startActivity
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.GameLabelAdapter
import safe.com.gamehuber.adapter.HomeGameAdapter
import safe.com.gamehuber.common.ext.otherwise
import safe.com.gamehuber.common.ext.yes
import safe.com.gamehuber.common.utils.GlideImageLoader
import safe.com.gamehuber.mvp.base.impl.BaseMvpFragment
import safe.com.gamehuber.mvp.model.bean.GameBean
import safe.com.gamehuber.mvp.model.bean.HomeBannerBean
import safe.com.gamehuber.mvp.model.bean.HomeGameBean
import safe.com.gamehuber.mvp.page.PostDetailActivity
import safe.com.gamehuber.mvp.presenter.GamePresenter
import safe.com.gamehuber.net.UrlConstant
import safe.com.gamehuber.net.UrlConstant.BASE_URL_FILE


class GameFragment : BaseMvpFragment<GamePresenter>() {
    private var page = 1
    private var images: ArrayList<String> = ArrayList()
    private var isRefresh = false
    private val labels by lazy {
        listOf(
                "aaa", "bbb", "cccc", "dddd", "eeee", "ffff"
        )
    }
    private var homeGameBeans: ArrayList<HomeGameBean> = ArrayList()
    private var labelAdapter: GameLabelAdapter? = null
    private var homeGameAdapter: HomeGameAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_game

    override fun initView() {
        //刷新 加载
        initRefresh()
        //首页标签
        initLabelAdapter()
        //首页游戏列表
        initGameAdapter()
        setMyClickListener(card_premiere_game)
    }

    override fun onMyClick(v: View?) {
        when (v?.id) {
            R.id.card_premiere_game ->  activity?.startActivity<PostDetailActivity>()
        }
    }

    private fun initRefresh() {
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            page = 1
            presenter.getHomeList(page)
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_translucent, R.color.color_title_bg)
        mRefreshLayout.setOnLoadmoreListener {
            isRefresh = false
            page++
            presenter.getHomeList(page)
        }
    }

    private fun initLabelAdapter() {
        re_label.apply {
            layoutManager = GridLayoutManager(activity, 2)
            labelAdapter = GameLabelAdapter(labels)
            adapter = labelAdapter
        }
    }

    private fun initGameAdapter() {
        re_content.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            homeGameAdapter = HomeGameAdapter(homeGameBeans)
            adapter = homeGameAdapter
        }
    }

    override fun initData() {
        presenter.getBanners()
        presenter.getPremiereGame()
        presenter.getAdvertising()
        presenter.getHomeList(page)
    }



    fun setBanners(homeBannerBeans: List<HomeBannerBean>) {
        for (bean in homeBannerBeans) {
            images?.add(BASE_URL_FILE + bean.imageUrl)
        }
        banner.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setImageLoader(GlideImageLoader())
            setImages(images)
            setBannerAnimation(Transformer.DepthPage)
            setDelayTime(2500)//设置轮播时间
            isAutoPlay(true) //设置自动轮播，默认为true
            start()
        }
    }

    fun setHomeList(homeGameBeans: List<HomeGameBean>) {
        isRefresh.yes { this.homeGameBeans.clear() }
        this.homeGameBeans.addAll(homeGameBeans)
        homeGameAdapter?.notifyDataSetChanged()
    }

    fun setPremiereGame(gameBean: GameBean){
        Glide.with(this)
                .load(UrlConstant.BASE_URL_FILE + gameBean.coverOrigin)
                .into(img_premiere_game)
        Glide.with(this)
                .load(UrlConstant.BASE_URL_FILE + gameBean.icon)
                .into(premiere_game_icon)
        premiere_game_content.text = gameBean.desc
        premiere_game_title.text = gameBean.name
    }

    fun setAdvertising(gameBean: GameBean){
        Glide.with(this)
                .load(UrlConstant.BASE_URL_FILE + gameBean.coverOrigin)
                .into(img_game)
        Glide.with(this)
                .load(UrlConstant.BASE_URL_FILE + gameBean.icon)
                .into(img_icon)
        tv_game_content_title.text= gameBean.name
        tv_game_content.text =  gameBean.desc
//        premiere_game_content.text = gameBean.desc
//        premiere_game_title.text = gameBean.name
    }


    fun missRefresh() {
        isRefresh.yes {
            mRefreshLayout.finishRefresh()
        }.otherwise {
            mRefreshLayout.finishLoadmore()
        }
    }
}