package kr.wayde.githubsearch.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.wayde.githubsearch.R
import kr.wayde.githubsearch.databinding.ItemSearchUserBinding
import kr.wayde.githubserach.domain.entity.GithubUser

class RepositorySearchAdapter(private val searchViewModel: UserSearchViewModel): PagedListAdapter<GithubUser, RepositorySearchAdapter.SearchResultViewHolder>(
    ItemDiffCallBack
) {
    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        context = parent.context
        return SearchResultViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            if (item != null) {
                onBind(item, searchViewModel, position)
            }
        }

    }

    inner class SearchResultViewHolder(val binding: ItemSearchUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item : GithubUser, searchViewModel: UserSearchViewModel, position: Int) {
            binding.apply {
                item?.let { item ->
                    user = item
                    viewModel = searchViewModel

                    searchViewModel.checkExistedUser(item) {
                        binding.cbFavorite.isChecked = it
                    }

                    binding.cbFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            searchViewModel.insertFavorite(item)
                        } else {
                            searchViewModel.deleteFavorite(item)
                        }
                    }
                }
            }
        }
    }
}

object ItemDiffCallBack : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.login == newItem.login
    }
}