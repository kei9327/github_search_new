package kr.wayde.githubserach.local.room

import io.reactivex.Observable
import kr.wayde.githubserach.local.room.model.GithubUserDto

interface DBHelper {
    fun checkExistedUser(login: String): Observable<Boolean>
    fun insertUser(userDto: GithubUserDto): Observable<Boolean>
    fun getAllUsers(): Observable<List<GithubUserDto>>
    fun deleteSearch(login: String): Observable<Boolean>

}