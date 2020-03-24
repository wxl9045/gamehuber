package safe.com.gamehuber.mvp.model.bean

/**
 * 游戏
 */
data class SelectGameBean(
    val content: String,
    val downloadCount: Int,
    val followCount: Int,
    val icon: String,
    val id: String,
    val title: String,
    val viewCount: Int
)