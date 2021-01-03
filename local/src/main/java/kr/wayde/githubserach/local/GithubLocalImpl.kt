package kr.wayde.githubserach.local

import io.reactivex.Observable
import io.reactivex.Single
import kr.wayde.githubserach.data.repository.GithubLocal
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.local.room.AppDBHelper
import kr.wayde.githubserach.local.room.model.GithubUserDto

class GithubLocalImpl(
    private val dbHelper: AppDBHelper
): GithubLocal {
    override fun checkExistedUser(githubUser: GithubUser): Observable<Boolean> =
        dbHelper.checkExistedUser(githubUser.login)

    override fun deleteUser(login: String): Observable<Boolean> =
        dbHelper.deleteSearch(login)

    override fun insertUser(githubUser: GithubUser): Observable<Boolean> =
        dbHelper.insertUser(GithubUserDto().apply {
            login = githubUser.login
            id = githubUser.id
            avatarUrl = githubUser.avatarUrl
            gravatarId = githubUser.gravatarId
            type = githubUser.type
            score = githubUser.score
            siteAdmin = githubUser.siteAdmin
        })

    override fun getAllUsers(): Observable<List<GithubUser>> =
        dbHelper.getAllUsers().map {
            it.map { user ->
                GithubUser(
                    user.login,
                    user.id,
                    user.avatarUrl?:"",
                    user.gravatarId?:"",
                    user.type?:"",
                    user.siteAdmin,
                    user.score,
                    true
                )
            }
        }

}