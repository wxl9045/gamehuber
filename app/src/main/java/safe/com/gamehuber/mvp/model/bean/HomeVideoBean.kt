package safe.com.gamehuber.mvp.model.bean

import java.io.Serializable

//{
//    "id":"type对应id",
//    "type": "4:单游戏推荐 5:多个游戏推荐/专辑/合集 6:内部推荐",
//    "icon":"图标",
//    "name":"标题",
//    "desc":"介绍",
//    "cover":"背景缩略图",
//    "coverOrigin":"背景原图",
//    "rate":"打分",
//    "ratePersons":"打分人数"
//}
data class HomeVideoBean(
    val avatar: String,
    val comment: Int,
    val like: Int,
    val nickname: String,
    val postContent: String,
    val postTitle: String,
    val postingTime: String,
    val userId: String,
    val videoCover: String,
    val videoUrl: String,
    val zan: Int
):Serializable