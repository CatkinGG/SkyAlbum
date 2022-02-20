package com.my.skyalbum.view.detail

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.my.skyalbum.R
import com.my.skyalbum.model.api.vo.PhotoItem
import kotlinx.android.synthetic.main.item_detail_photo.view.*
import kotlinx.android.synthetic.main.item_home_album.view.*


class DetailPhotoAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<PhotoItem> = arrayListOf()

    companion object {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_photo, parent, false)
        return DetailPhotoViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.get(position)
        val context = holder.itemView.context
        holder as DetailPhotoViewHolder
        holder.tvTitle.text = item.title

        val theImage = GlideUrl(
            item.thumbnailUrl, LazyHeaders.Builder()
                .addHeader("User-Agent", "5")
                .build()
        )
        Glide.with(context)
            .load(theImage)
            .into(holder.ivPhoto)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<PhotoItem>?) {
        this.data.clear()
        data?.also {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    class DetailPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.iv_photo
        val tvTitle: TextView = itemView.tv_title
    }
}