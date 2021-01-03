package kr.wayde.githubserach.remote.rest

import io.reactivex.Single
import kr.wayde.githubserach.remote.rest.model.GithubResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService  {
    @GET("/search/users?")
    fun searchUser(
        @Query(value = "q", encoded = true) userKeyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): Single<GithubResponse>
}