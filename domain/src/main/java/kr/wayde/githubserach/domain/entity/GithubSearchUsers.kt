package kr.wayde.githubserach.domain.entity

data class GithubSearchUsers(val totalCount: Int,
                             val incompleteResults: Boolean,
                             val items: MutableList<GithubUser>)