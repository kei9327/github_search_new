package kr.wayde.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import kr.wayde.githubsearch.ui.base.BaseViewModel
import kr.wayde.githubsearch.ui.search.UserDataSource.Companion.USERS_PAGINATION_SIZE
import kr.wayde.githubsearch.util.InvokerT
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.entity.RequestState
import kr.wayde.githubserach.domain.interactor.usercases.CheckExistedUserUsecase
import kr.wayde.githubserach.domain.interactor.usercases.DeleteUserUsecase
import kr.wayde.githubserach.domain.interactor.usercases.GithubUserSearchUseCase
import kr.wayde.githubserach.domain.interactor.usercases.InsertUserUsecase

class UserSearchViewModel(
    private val searchUserUseCase: GithubUserSearchUseCase,
    private val existedUserUsecase: CheckExistedUserUsecase,
    private val insertUserUsecase: InsertUserUsecase,
    private val deleteUserUsecase: DeleteUserUsecase
) : BaseViewModel() {

    private var userDataSourceFactory: UserDataSourceFactory =
        UserDataSourceFactory("", searchUserUseCase, getDisposable())

    private val queryLiveData = MutableLiveData<String>()
    var searchText = ""
    var searchViewExpanded = true

    val userPagedList: LiveData<PagedList<GithubUser>> by lazy {
        Transformations.switchMap(queryLiveData) { fetchUsersListPage(it) }
    }

    val requestState: LiveData<RequestState> by lazy {
        Transformations.switchMap(userPagedList) { getRequestStates() }
    }

    fun searchUser(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun checkExistedUser(githubUser: GithubUser, invokerT: InvokerT<Boolean>) {
        addDisposable(
            existedUserUsecase.get(CheckExistedUserUsecase.Params(githubUser)).subscribe {
                invokerT.invoke(it)
            })
    }

    fun insertFavorite(githubUser: GithubUser) {
        addDisposable(
            insertUserUsecase.get(InsertUserUsecase.Params(githubUser)).subscribe()
        )
    }

    fun deleteFavorite(githubUser: GithubUser) {
        addDisposable(
            deleteUserUsecase.get(DeleteUserUsecase.Params(githubUser)).subscribe()
        )
    }

    fun isListEmpty(): Boolean {
        return userPagedList.value?.isEmpty() ?: true
    }

    fun fetchUsersListPage(
        searchQuery: String
    ): LiveData<PagedList<GithubUser>> {
        val userDataSourceFactory =
            UserDataSourceFactory(searchQuery, searchUserUseCase, getDisposable())

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(USERS_PAGINATION_SIZE)
            .build()

        return LivePagedListBuilder(userDataSourceFactory, config).build()
    }

    fun getRequestStates(): LiveData<RequestState> {
        return Transformations.switchMap<UserDataSource, RequestState>(
            userDataSourceFactory.userLiveDataSource, UserDataSource::requestState
        )
    }

}