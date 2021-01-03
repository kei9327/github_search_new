package kr.wayde.githubserach.remote

import io.reactivex.Single
import kr.wayde.githubserach.data.repository.GithubRemote
import kr.wayde.githubserach.domain.entity.GithubSearchUsers
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.remote.rest.GithubService

class GithubRemoteImpl(
    private val githubService: GithubService
) : GithubRemote {
    override fun searchUser(userKeyword: String, page: Int, perPage: Int): Single<GithubSearchUsers> =
        githubService.searchUser(userKeyword, page, perPage).map {
            GithubSearchUsers(it.totalCount, it.incompleteResults,
                it.items.map { GithubUser(it.login, it.id, it.avatarUrl, it.gravatarId, it.type, it.siteAdmin, it.score) } as MutableList<GithubUser>)
        }


}