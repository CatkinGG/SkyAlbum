package com.my.skyalbum.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.my.skyalbum.R
import com.my.skyalbum.model.api.vo.AlbumItem
import kotlinx.android.synthetic.main.item_home_album.view.*


class HomeAlbumAdapter(
    private val homeFuncListener: HomeFuncItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<AlbumItem> = arrayListOf()

    companion object {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_album, parent, false)
        return HomeAlbumViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.get(position)
        val context = holder.itemView.context
        holder as HomeAlbumViewHolder
        holder.tvName.text = item.title

        item.photoUrl?.let {
            val theImage = GlideUrl(
                it, LazyHeaders.Builder()
                    .addHeader("User-Agent", "5")
                    .build()
            )
            Glide.with(context)
                .load(theImage)
                .into(holder.ivCover)
        }
        holder.clRoot.setOnClickListener {
            homeFuncListener.onAlbumSelect.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<AlbumItem>?) {
        this.data.clear()
        data?.also {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    class HomeAlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCover: ImageView = itemView.iv_cover
        val tvName: TextView = itemView.tv_name
        val clRoot: ConstraintLayout = itemView.cl_root
    }
}