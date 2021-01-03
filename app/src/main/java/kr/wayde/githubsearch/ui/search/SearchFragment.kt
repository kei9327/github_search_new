package kr.wayde.githubsearch.ui.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kr.wayde.githubsearch.R
import kr.wayde.githubsearch.databinding.FragmentSearchBinding
import kr.wayde.githubsearch.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val userSearchViewModel: UserSearchViewModel by sharedViewModel()
    private val adapter by lazy {
        RepositorySearchAdapter(userSearchViewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvUserSearch.run {
            adapter = this@SearchFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun initData() {
        userSearchViewModel.userPagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}