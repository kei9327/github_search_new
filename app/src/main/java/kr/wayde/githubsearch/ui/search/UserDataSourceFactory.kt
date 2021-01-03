package kr.wayde.githubsearch.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.interactor.usercases.GithubUserSearchUseCase

class UserDataSourceFactory(
    private val searchQuery: String,
    private val api: GithubUserSearchUseCase,
    private val disposables: CompositeDisposable
) : DataSource.Factory<Int, GithubUser>() {

    val userLiveDataSource = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, GithubUser> {
        val userDataSource =
            UserDataSource(
                searchQuery,
                api,
                disposables
            )
        userLiveDataSource.postValue(userDataSource)
        return userDataSource
    }
}