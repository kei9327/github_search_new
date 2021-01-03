package kr.wayde.githubserach.data.interactor

import io.reactivex.Observable
import io.reactivex.Single
import kr.wayde.githubserach.data.repository.GithubLocal
import kr.wayde.githubserach.data.repository.GithubRemote
import kr.wayde.githubserach.domain.entity.GithubSearchUsers
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.repository.GithubRepository

class GithubDataSource(
    private val local: GithubLocal,
    private val remote: GithubRemote
) : GithubRepository {
    override fun searchUser(
        userKeyword: String,
        page: Int,
        perPage: Int
    ): Single<GithubSearchUsers> = remote.searchUser(userKeyword, page, perPage)

    override fun checkExistedUser(githubUser: GithubUser): Observable<Boolean> =
        local.checkExistedUser(githubUser)

    override fun insertUser(githubUser: GithubUser): Observable<Boolean> =
        local.insertUser(githubUser)

    override fun deleteUser(login: String): Observable<Boolean> =
        local.deleteUser(login)

    override fun getAllUsers(): Observable<List<GithubUser>> =
        local.getAllUsers()

}