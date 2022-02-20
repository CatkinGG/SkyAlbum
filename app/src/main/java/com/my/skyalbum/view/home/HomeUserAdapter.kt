package com.my.skyalbum.view.home

import android.graphics.Color
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.my.skyalbum.R
import com.my.skyalbum.model.api.vo.UserItem
import com.my.skyalbum.view.custom.ChatIconDrawable
import kotlinx.android.synthetic.main.item_home_user.view.*
import com.my.skyalbum.view.home.HomeFuncItem


class HomeUserAdapter(
    private val homeFuncListener: HomeFuncItem,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: MutableList<UserItem> = arrayListOf()
    private var currentUser: Long = 1

    companion object {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_user, parent, false)
        return HomeUserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data.get(position)
        holder as HomeUserViewHolder
        holder.ivAvatar.run { this.setImageDrawable( ChatIconDrawable(this, item.name?.first().toString())) }
        holder.tvName.text = item.username
        holder.clRoot.setOnClickListener {
            homeFuncListener.onUserSelect.invoke(item.id)
        }
        if(currentUser == item.id)
            holder.clRoot.setBackgroundColor(Color.LTGRAY)
        else
            holder.clRoot.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<UserItem>?) {
        this.data.clear()
        data?.also {
            this.data.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setCurrentUser(id: Long){
        currentUser = id
        notifyDataSetChanged()
    }


    class HomeUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.iv_avatar
        val tvName: TextView = itemView.tv_name
        val clRoot: ConstraintLayout = itemView.cl_root
    }

}