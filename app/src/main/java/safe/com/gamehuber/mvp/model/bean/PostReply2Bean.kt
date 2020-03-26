package safe.com.gamehuber.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class PostReply2Bean(
        val avatar: String,
        val children: List<PostReply2Bean>,
        val id: String,
        val indexCount: Int,
        val isEssence: Int,
        val isModerator: Any,
        val isOfficial: Any,
        val isSticky: Int,
        val isThumbsUp: Any,
        val likeCount: Int,
        val nickname: String,
        val postContent: String,
        val postContentPlaintext: String,
        val postImgList: List<PostImg>,
        val postTitle: String,
        val postingTime: String,
        val replyCount: Int,
        val topicId: String,
        val topicName: String,
        val userId: String,
        val userLv: Int,
        val videoCover: String,
        val videoUrl: String,
        val viewCount: Any
) :MultiItemEntity {

    override fun getItemType(): Int {
        return 1
    }

    data class PostImg(
            val originPath: String,
            val thumbPath: Any
    )
}

