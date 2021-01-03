package kr.wayde.githubsearch.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kr.wayde.githubsearch.R
import kr.wayde.githubsearch.databinding.FragmentFavoriteBinding
import kr.wayde.githubsearch.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private val favoriteViewModel: FavoriteViewModel by sharedViewModel()
    private val adapter by lazy {
        FavoriteAdapter(favoriteViewModel)
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.loadFavorites()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvFavorite.run {
            adapter = this@FavoriteFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun initData() {
        favoriteViewModel.favoriteUserLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addAllItem(it)
        })
    }

}