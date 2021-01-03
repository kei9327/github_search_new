package kr.wayde.githubserach.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class GithubUserDto {
    @PrimaryKey
    var login: String = ""

    var id: Int = 0
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String ? = null

    @ColumnInfo(name = "gravator_id")
    var gravatarId: String? = null

    var type: String? = null

    @ColumnInfo(name = "site_admin")
    var siteAdmin: Boolean = false

    var score: Double = .0
}