package com.my.skyalbum

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.my.skyalbum.model.api.ApiResult
import com.my.skyalbum.model.db.Photo
import com.my.skyalbum.view.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commit()
        }
        viewModel.getPhotos()
    }

    override fun onResume() {
        super.onResume()
        setupObserver()
    }

    fun setupObserver() {
        viewModel.getPhotosResult.observe(this, {
            when (it) {
                is ApiResult.Success -> {
                    it.result?.run{
                        viewModel.insertPhotos(this)
                    }
                }
                is ApiResult.Error -> Timber.d("api Error ${it.throwable.message}")
            }
        })
    }
}