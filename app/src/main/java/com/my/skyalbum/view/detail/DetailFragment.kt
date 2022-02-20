package com.my.skyalbum.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.my.skyalbum.MainViewModel
import com.my.skyalbum.R
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.view.home.HomeAlbumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.progress_bar
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


@AndroidEntryPoint
class DetailFragment(
    private val albumId: Long
) : Fragment(R.layout.fragment_detail) {
    companion object {
        fun newInstance(albumId: Long) = DetailFragment(albumId)
    }

    val detailPhotoAdapter = DetailPhotoAdapter()
    private val viewModel: DetailViewModel by viewModels()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        setupListener()
        detailPhotoAdapter.setData(null)
        progress_bar.visibility = View.VISIBLE
        mainViewModel.readPhotosByAlbum(albumId)
    }

    fun setupUI() {
        rv_detail_photos_list.also {
            it.setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(context, 3)
            it.layoutManager = gridLayoutManager
            it.adapter = detailPhotoAdapter
        }
    }

    fun setupObserver() {
        mainViewModel.readPhotosResult.observe(viewLifecycleOwner, {
            progress_bar.visibility = View.GONE
            detailPhotoAdapter.setData(it)
        })
    }

    fun setupListener() {
        tv_close.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}