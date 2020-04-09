package safe.com.gamehuber.mvp.model.bean

import java.io.Serializable

data class LoginBean(
        var avatar: Any,
        var backImg: Any,
        var banned: Int,
        var birthday: String,
        var comefrom: String,
        var email: String,
        var facebook: Any,
        var fan: Int,
        var follow: Int,
        var gender: Int,
        var gold: Int,
        var google: Any,
        var groupId: String,
        var groupName: Any,
        var id: String,
        var intro: String,
        var like: Int,
        var lv: Int,
        var mobile: Any,
        var msn: Any,
        var nickname: String,
        var phone: Any,
        var primeCount: Int,
        var qq: Any,
        var registerIp: String,
        var registerTime: String,
        var replyCount: Int,
        var score: Int,
        var signature: Any,
        var token: String,
        var topicCount: Int,
        var twitter: Any,
        var youtube: Any
): Serializable