package kr.wayde.githubserach.local.room

import io.reactivex.Observable
import kr.wayde.githubserach.local.room.model.GithubUserDto

class AppDBHelper(val appDatabase: AppDatabase) : DBHelper {
    override fun checkExistedUser(login: String): Observable<Boolean> =
        appDatabase.userDao().findById(login).map { true }.onErrorReturn { false }.toObservable()

    override fun insertUser(userDto: GithubUserDto): Observable<Boolean> =
        Observable.fromCallable {
            appDatabase.userDao().insert(userDto)
            return@fromCallable true
        }

    override fun getAllUsers(): Observable<List<GithubUserDto>> =
        appDatabase.userDao().loadAll().toObservable()

    override fun deleteSearch(login: String): Observable<Boolean> =
        Observable.fromCallable {
            appDatabase.userDao().findById(login).doOnSuccess {
                appDatabase.userDao().delete(it)
            }.subscribe()
            return@fromCallable true
        }


}
