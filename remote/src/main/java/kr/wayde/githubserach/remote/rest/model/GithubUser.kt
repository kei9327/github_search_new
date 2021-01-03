package kr.wayde.githubserach.remote.rest.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["login"])
data class GithubUser(
    @field:SerializedName("login") val login: String,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
    @field:SerializedName("gravatar_id") val gravatarId: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("site_admin") val siteAdmin: Boolean,
    @field:SerializedName("score") val score: Double)