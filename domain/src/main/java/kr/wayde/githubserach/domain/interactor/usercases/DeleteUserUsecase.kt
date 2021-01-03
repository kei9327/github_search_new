package kr.wayde.githubserach.domain.interactor.usercases

import io.reactivex.Observable
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.interactor.ObservableUseCase
import kr.wayde.githubserach.domain.repository.GithubRepository
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider

class DeleteUserUsecase (
    private val repository: GithubRepository,
    schedulersProvider: SchedulersProvider
): ObservableUseCase<Boolean, DeleteUserUsecase.Params>(schedulersProvider) {

    data class Params(val githubUser: GithubUser)

    override fun buildUseCaseObservable(params: Params?): Observable<Boolean>
            = repository.deleteUser(params!!.githubUser.login)
}