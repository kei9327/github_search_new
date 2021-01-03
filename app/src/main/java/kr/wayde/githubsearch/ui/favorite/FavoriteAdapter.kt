package kr.wayde.githubsearch.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.wayde.githubsearch.R
import kr.wayde.githubsearch.databinding.ItemFavoriteUserBinding
import kr.wayde.githubserach.domain.entity.GithubUser


class FavoriteAdapter(private val favoriteViewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHodler>() {
    private var favoriteItemList = mutableListOf<GithubUser>()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHodler {
        context = parent.context
        return FavoriteViewHodler(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favorite_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = favoriteItemList.size

    override fun onBindViewHolder(holder: FavoriteViewHodler, position: Int) {
        val item = favoriteItemList[position]
        holder.apply {
            onBind(item, favoriteViewModel, position)
        }
    }

    inner class FavoriteViewHodler(val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: GithubUser, favoriteViewModel: FavoriteViewModel, position: Int) {
            binding.apply {
                item?.let {item->
                    user = item
                    viewModel = favoriteViewModel

                    binding.cbFavorite.isChecked = true

                    binding.cbFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (!isChecked) {
                            favoriteViewModel.deleteFavorite(item) {
                                favoriteViewModel.loadFavorites()
                            }
                        }
                    }
                }
            }
        }
    }

    fun addAllItem(list: List<GithubUser>) {
        val calculateDiff =
            DiffUtil.calculateDiff(EmployeeDiffCallback(this.favoriteItemList, list))
        this.favoriteItemList.clear()
        favoriteItemList.addAll(list)
        calculateDiff.dispatchUpdatesTo(this)
    }

    inner class EmployeeDiffCallback(
        private val mOldList: List<GithubUser>,
        private val mNewList: List<GithubUser>
    ) :
        DiffUtil.Callback() {
         override fun getOldListSize(): Int {
            return mOldList.size
        }

        override fun getNewListSize(): Int {
            return mNewList.size
        }

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return mOldList[oldItemPosition].login == mNewList[newItemPosition].login
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            val oldItem: GithubUser = mOldList[oldItemPosition]
            val newItem: GithubUser = mNewList[newItemPosition]
            return oldItem.login == newItem.login
        }

        @Nullable
        override fun getChangePayload(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Any? {
            // Implement method if you're going to use ItemAnimator
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}