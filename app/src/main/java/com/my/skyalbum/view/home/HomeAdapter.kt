package com.my.skyalbum.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.skyalbum.R
import com.my.skyalbum.model.api.vo.AlbumItem
import com.my.skyalbum.model.api.vo.UserItem
import kotlinx.android.synthetic.main.item_home_albums.view.*
import kotlinx.android.synthetic.main.item_home_users.view.*


class HomeAdapter(
    private val homeFuncListener: HomeFuncItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val homeUserAdapter = HomeUserAdapter(homeFuncListener)
    val homeAlbumAdapter = HomeAlbumAdapter(homeFuncListener)

    companion object {
        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_ALBUM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_USER
            else -> VIEW_TYPE_ALBUM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_users, parent, false)
                HomeUsersViewHolder(mView)
            }
            VIEW_TYPE_ALBUM -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_albums, parent, false)
                HomeAlbumsViewHolder(mView)
            }
            else -> {
                val mView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_albums, parent, false)
                HomeAlbumsViewHolder(mView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        when (holder) {
            is HomeUsersViewHolder -> {
                holder.rvUsers.also {
                    it.setHasFixedSize(true)
                    val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.layoutManager = linearLayoutManager
                    it.adapter = homeUserAdapter
                }
            }
            is HomeAlbumsViewHolder -> {
                holder.rvAlbums.also {
                    it.setHasFixedSize(true)
                    val gridLayoutManager = GridLayoutManager(context, 3)
                    it.layoutManager = gridLayoutManager
                    it.adapter = homeAlbumAdapter
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun setUsersData(data: List<UserItem>?){
        homeUserAdapter.setData(data)
    }

    fun setCurrentUser(id: Long){
        homeUserAdapter.setCurrentUser(id)
    }

    fun setAlbumsData(data: List<AlbumItem>?){
        homeAlbumAdapter.setData(data)
    }

    class HomeUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvUsers: RecyclerView = itemView.rv_home_users_list
    }

    class HomeAlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvAlbums: RecyclerView = itemView.rv_home_albums_list
    }
}