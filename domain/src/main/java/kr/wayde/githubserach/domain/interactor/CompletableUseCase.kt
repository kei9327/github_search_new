package kr.wayde.githubserach.domain.interactor

import io.reactivex.Completable
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider

abstract class CompletableUseCase<in Params>(
    private val schedulersProvider: SchedulersProvider
) {
    protected abstract fun buildUseCaseCompletable(params: Params): Completable

    fun get(params: Params): Completable = buildUseCaseCompletable(params)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
}
