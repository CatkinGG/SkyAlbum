package com.my.skyalbum.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.my.skyalbum.MainViewModel
import com.my.skyalbum.R
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.view.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.progress_bar
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val homeFuncItem = HomeFuncItem(
        onUserSelect = { id ->
            viewModel.setSelectedUser(id)
                 },
        onAlbumSelect = { id ->
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(this::class.java.simpleName)
                .add(R.id.container, DetailFragment.newInstance(id))
                .commit()
        }
    )
    private val homeAdapter = HomeAdapter(homeFuncItem)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        setupListener()
        viewModel.getUsers()
        viewModel.getAlbums()
    }

    fun setupUI() {
        rv_home_list.also {
            it.setHasFixedSize(true)
            it.adapter = homeAdapter
            it.itemAnimator = null
        }
    }

    fun setupObserver() {
        viewModel.selectedUserId.observe(viewLifecycleOwner, {
            homeAdapter.setCurrentUser(it)
            homeAdapter.setAlbumsData(null)
            progress_bar.visibility = View.VISIBLE
            if(mainViewModel.isPhotoDownload.value != true)
                viewModel.readAlbumByUserId(it)
            else
                viewModel.readAlbumAndPhotosByAlbum()
        })

        mainViewModel.isPhotoDownload.observe(viewLifecycleOwner, {
            if(it) viewModel.readAlbumAndPhotosByAlbum()
        })

        viewModel.getUsersResult.observe(viewLifecycleOwner, {
            when (it) {
                is ApiResult.Success -> {
                    homeAdapter.setUsersData(it.result)
                }
                is ApiResult.Error -> Timber.d("api Error ${it.throwable.message}")
            }
            layout_refresh.isRefreshing = false
        })

        viewModel.getAlbumsResult.observe(viewLifecycleOwner, {
            progress_bar.visibility = GONE
            when (it) {
                is ApiResult.Success -> {
                    it.result?.run{
                        viewModel.insertAlbums(this)
                    }
                }
                is ApiResult.Error -> Timber.d("api Error ${it.throwable.message}")
            }
            layout_refresh.isRefreshing = false
        })

        viewModel.readAlbumsResult.observe(viewLifecycleOwner, {
            homeAdapter.setAlbumsData(it)
            progress_bar.visibility = GONE
            layout_refresh.isRefreshing = false
        })

        viewModel.readAlbumAndPhotosResult.observe(viewLifecycleOwner, {
            homeAdapter.setAlbumsData(it)
            progress_bar.visibility = GONE
            layout_refresh.isRefreshing = false
        })
    }

    fun setupListener() {
        layout_refresh.setOnRefreshListener {
            viewModel.getUsers()
        }
    }
}