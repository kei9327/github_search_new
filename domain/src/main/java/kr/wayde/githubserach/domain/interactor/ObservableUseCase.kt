package kr.wayde.githubserach.domain.interactor

import io.reactivex.Observable
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider


abstract class ObservableUseCase<T, in Params>(
    private val schedulersProvider: SchedulersProvider
) {
    protected abstract fun buildUseCaseObservable(params: Params?): Observable<T>

    fun get(params: Params) = buildUseCaseObservable(params)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun get() = buildUseCaseObservable(null)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
}
