package safe.com.gamehuber.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class PostReply2Bean(
        var avatar: String = "",
        val children: List<PostReply2Bean>? = null,
        var id: String = "",
        val indexCount: Int = 0,
        val isEssence: Int = 0,
        val isModerator: Any? = null,
        val isOfficial: Any? = null,
        val isSticky: Int = 0,
        val isThumbsUp: Any? = null,
        val likeCount: Int = 0,
        var nickname: String = "",
        val postContent: String = "",
        var postContentPlaintext: String = "",
        val postImgList: List<PostImg>? = null,
        val postTitle: String = "",
        var postingTime: String = "",
        val replyCount: Int = 0,
        val topicId: String = "",
        val topicName: String = "",
        val userId: String = "",
        val userLv: Int = 0,
        val videoCover: String = "",
        val videoUrl: String = "",
        val viewCount: Any? = null,
        var pPosition: Int = 0,
        var hasNext: Boolean = false
) :MultiItemEntity {
    override fun getItemType(): Int {
        return 1
    }

    data class PostImg(
            val originPath: String,
            val thumbPath: Any
    )
}

