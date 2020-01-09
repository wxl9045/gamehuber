package safe.com.gamehuber.mvp.presenter


import safe.com.gamehuber.common.utils.NetworkUtil
import safe.com.gamehuber.mvp.base.impl.BasePresenter
import safe.com.gamehuber.mvp.page.VideoDetailActivity
import safe.com.gamehuber.mvp.model.bean.HomeBean

class VideoDetailPresenter : BasePresenter<VideoDetailActivity>(){

    fun loadVideoInfo(itemInfo : HomeBean.Issue.Item){
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(context = view.baseContext)
        playInfo?.let {
            if (it.size > 1) {
                // 当前网络是 Wifi环境下选择高清的视频
                if (netType) {
                    for (i in it) {
                        if (i.type == "high") {
                            val playUrl = i.url
                            view.setVideo(playUrl)
                            break
                        }
                    }
                } else {
                    //否则就选标清的视频
                    for (i in it) {
                        if (i.type == "normal") {
                            val playUrl = i.url
                            view.setVideo(playUrl)
                            break
                        }
                    }
                }
            } else {
                view.setVideo(itemInfo.data.playUrl)
            }
        }
    }
}