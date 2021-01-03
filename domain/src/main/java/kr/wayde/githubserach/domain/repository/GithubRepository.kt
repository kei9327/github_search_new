package kr.wayde.githubserach.domain.repository

import io.reactivex.Observable
import io.reactivex.Single
import kr.wayde.githubserach.domain.entity.GithubSearchUsers
import kr.wayde.githubserach.domain.entity.GithubUser

interface GithubRepository {
    fun searchUser(userKeyword: String, page: Int, perPage: Int): Single<GithubSearchUsers>

    fun checkExistedUser(githubUser: GithubUser): Observable<Boolean>

    fun insertUser(githubUser: GithubUser): Observable<Boolean>

    fun deleteUser(login: String): Observable<Boolean>

    fun getAllUsers(): Observable<List<GithubUser>>
}