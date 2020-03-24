package safe.com.gamehuber.mvp.model.bean

/**
 * 游戏 分区
 */
data class PostTypeBean(
        val id: String,
        val name: String,
        val topicId: String,
        val isDefault: Boolean,
        val allowUserPost: Boolean,
        val priority: Int,
        val postCount: Int
)