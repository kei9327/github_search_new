package kr.wayde.githubserach.domain.interactor

import io.reactivex.Single
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider

abstract class SingleUseCase<T, in Params>(
    private val schedulersProvider: SchedulersProvider
) {
    protected abstract fun buildUseCaseSingle(params: Params?): Single<T>

    fun get(params: Params) = buildUseCaseSingle(params)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun get() = buildUseCaseSingle(null)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
}
