package safe.com.gamehuber.mvp.home

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_game.*
import safe.com.gamehuber.R
import safe.com.gamehuber.adapter.GameLabelAdapter
import safe.com.gamehuber.adapter.HomeGameAdapter
import safe.com.gamehuber.common.utils.GlideImageLoader
import safe.com.gamehuber.mvp.base.impl.BaseFragment
import safe.com.gamehuber.mvp.model.bean.HomeGameBean


class GameFragment : BaseFragment(){
    private val images by  lazy {
        listOf(
                R.mipmap.b1,R.mipmap.b2,R.mipmap.b3
        )
    }
    private val labels by  lazy {
        listOf(
               "aaa","bbb","cccc","dddd","eeee","ffff"
        )
    }
    private val homeGameBeans by  lazy {
        listOf(
                HomeGameBean(1,"绝地求生好好好玩，玩完大家啊基调为奇偶"),
                HomeGameBean(2,"绝地求生好好好玩，玩完大家啊基调为奇偶")
        )
    }
    private var labelAdapter : GameLabelAdapter? = null
    private var homeGameAdapter : HomeGameAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_game

    override fun initView() {
        //轮播图
        initBanner()
        //首页标签
        initLabelAdapter()
        //首页游戏列表
        initGameAdapter()
    }

    private fun initLabelAdapter(){
        re_label.apply {
            layoutManager = GridLayoutManager(activity, 2)
            labelAdapter = GameLabelAdapter(labels)
            adapter = labelAdapter
        }
    }

    private fun initGameAdapter() {
        re_content.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            homeGameAdapter = HomeGameAdapter(homeGameBeans)
            adapter = homeGameAdapter
        }
    }

    private fun initBanner(){
        banner.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setImageLoader(GlideImageLoader())
            setImages(images)
            setBannerAnimation(Transformer.DepthPage)
            setDelayTime(1500)//设置轮播时间
            isAutoPlay(true) //设置自动轮播，默认为true
            start()
        }
    }
}