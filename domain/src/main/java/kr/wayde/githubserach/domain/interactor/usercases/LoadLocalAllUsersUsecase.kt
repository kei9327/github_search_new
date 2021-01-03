package kr.wayde.githubserach.domain.interactor.usercases

import io.reactivex.Observable
import kr.wayde.githubserach.domain.entity.GithubUser
import kr.wayde.githubserach.domain.interactor.ObservableUseCase
import kr.wayde.githubserach.domain.repository.GithubRepository
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider


class LoadLocalAllUsersUsecase (
    private val repository: GithubRepository,
    schedulersProvider: SchedulersProvider
): ObservableUseCase<List<GithubUser>, Void>(schedulersProvider) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<GithubUser>>
            = repository.getAllUsers()
}