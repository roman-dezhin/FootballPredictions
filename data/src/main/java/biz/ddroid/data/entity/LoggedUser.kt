package biz.ddroid.data.entity

data class LoggedUser(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String?,
    val token: String
)
