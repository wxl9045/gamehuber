package safe.com.gamehuber.mvp.model.bean

data class PostBean(
        val createTime: String,
        val deleted: String,
        val dictTypeId: String,
        val id: String,
        val name: String?,
        val originLink: String,
        val postId: String,
        val priority: String,
        val thumbLink: String,
        val updateTime: String
)