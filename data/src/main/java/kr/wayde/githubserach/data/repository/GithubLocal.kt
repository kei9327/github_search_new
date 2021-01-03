package kr.wayde.githubserach.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import kr.wayde.githubserach.domain.entity.GithubUser

interface GithubLocal {
    fun checkExistedUser(githubUser: GithubUser): Observable<Boolean>
    fun deleteUser(login: String) : Observable<Boolean>
    fun insertUser(githubUser: GithubUser): Observable<Boolean>
    fun getAllUsers(): Observable<List<GithubUser>>
}