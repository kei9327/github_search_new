package kr.wayde.githubserach.domain.interactor.usercases

import io.reactivex.Single
import kr.wayde.githubserach.domain.entity.GithubSearchUsers
import kr.wayde.githubserach.domain.interactor.SingleUseCase
import kr.wayde.githubserach.domain.repository.GithubRepository
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider

class GithubUserSearchUseCase(
    private val repository: GithubRepository,
    schedulersProvider: SchedulersProvider
) : SingleUseCase<GithubSearchUsers, GithubUserSearchUseCase.Params>(schedulersProvider) {

    data class Params(val keyword: String, val page: Int, val perPage: Int)

    override fun buildUseCaseSingle(params: Params?): Single<GithubSearchUsers> =
        repository.searchUser(params!!.keyword, params.page, params.perPage)
}