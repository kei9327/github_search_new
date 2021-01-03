package kr.wayde.githubserach.data.repository

import io.reactivex.Single
import kr.wayde.githubserach.domain.entity.GithubSearchUsers

interface GithubRemote {
    fun searchUser(userKeyword: String, page: Int, perPage: Int): Single<GithubSearchUsers>
}