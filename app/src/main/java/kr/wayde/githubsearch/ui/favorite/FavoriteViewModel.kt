package kr.wayde.githubsearch.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.wayde.githubsearch.ui.base.BaseViewModel
import kr.wayde.githubsearch.util.Invoker
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.interactor.usercases.DeleteUserUsecase
import kr.wayde.githubserach.domain.interactor.usercases.InsertUserUsecase
import kr.wayde.githubserach.domain.interactor.usercases.LoadLocalAllUsersUsecase

class FavoriteViewModel(
    private val loadLocalAllUsersUsecase: LoadLocalAllUsersUsecase,
    private val insertUserUsecase: InsertUserUsecase,
    private val deleteUserUsecase: DeleteUserUsecase
) : BaseViewModel() {

    private val _favoriteUserLiveData = MutableLiveData<List<GithubUser>>()
    val favoriteUserLiveData: LiveData<List<GithubUser>>
        get() = _favoriteUserLiveData

    fun insertFavorite(githubUser: GithubUser) {
        addDisposable(
            insertUserUsecase.get(InsertUserUsecase.Params(githubUser)).subscribe()
        )
    }

    fun deleteFavorite(githubUser: GithubUser, invoker: Invoker) {
        addDisposable(
            deleteUserUsecase.get(DeleteUserUsecase.Params(githubUser)).subscribe {
                invoker.invoke()
            }
        )
    }

    fun loadFavorites() {
        addDisposable(
            loadLocalAllUsersUsecase.get().subscribe {
                _favoriteUserLiveData.postValue(it)
            }
        )
    }
}