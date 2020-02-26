package safe.com.gamehuber.mvp.model.bean

/**
 * 游戏
 */
data class GameBean(
        val cover: String,
        val coverOrigin: String,
        val desc: String,
        val icon: String,
        val id: String,
        val name: String,
        val rate: Int,
        val ratePersons: Int,
        val title: String,
        val type: Int
)