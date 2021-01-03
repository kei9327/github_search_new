package kr.wayde.githubsearch.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.entity.RequestState
import kr.wayde.githubserach.domain.interactor.usercases.GithubUserSearchUseCase
import retrofit2.HttpException

class UserDataSource(
    private val searchQuery: String,
    private val api: GithubUserSearchUseCase,
    private val disposables: CompositeDisposable
) : PageKeyedDataSource<Int, GithubUser>() {

    companion object {
        val USERS_FIRST_PAGE = 1
        val USERS_PAGINATION_SIZE  = 30
    }


    val requestState = MutableLiveData<RequestState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GithubUser>
    ) {
        requestState.postValue(RequestState.IN_PROGRESS)
        disposables.add(
            api.get(GithubUserSearchUseCase.Params(searchQuery, USERS_FIRST_PAGE, USERS_PAGINATION_SIZE))
                .subscribe(
                    {
                        if (it.totalCount <= USERS_PAGINATION_SIZE)
                            requestState.value = RequestState.LIST_COMPLETE
                        else
                            callback.onResult(it.items, null, USERS_FIRST_PAGE + 1)
                    }, {
                        handleThrowable(it, "loadInitial")
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {
        requestState.postValue(RequestState.IN_PROGRESS)
        disposables.add(
            api.get(GithubUserSearchUseCase.Params(searchQuery, params.key, USERS_PAGINATION_SIZE))
                .subscribe({
                    if (it.totalCount / USERS_PAGINATION_SIZE + 1 >= params.key) {
                        callback.onResult(it.items, params.key + 1)
                        requestState.value = RequestState.SUCCESS
                    } else {
                        requestState.value = RequestState.LIST_COMPLETE
                    }
                }, {
                    handleThrowable(it, "loadAfter")
                })

        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GithubUser>) {
    }

    private fun handleThrowable(ex: Throwable, sourceTag: String) {
        if (ex is HttpException && ex.code() == 403) {
            requestState.value = RequestState.FORBIDDEN
            Log.e("UserDataSource", "$sourceTag ${ex.message} ")
        }
        requestState.value = RequestState.FAIL
        Log.e("UserDataSource", "$sourceTag ${ex.message} ")
    }

}