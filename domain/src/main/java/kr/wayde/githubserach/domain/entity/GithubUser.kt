package kr.wayde.githubserach.domain.entity

data class GithubUser(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val gravatarId: String,
    val type: String,
    val siteAdmin: Boolean,
    val score: Double,
    var isChecked: Boolean = false)