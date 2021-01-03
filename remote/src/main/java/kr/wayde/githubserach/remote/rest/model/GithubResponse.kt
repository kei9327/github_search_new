package kr.wayde.githubserach.remote.rest.model

import com.google.gson.annotations.SerializedName

data class GithubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: MutableList<GithubUser>)