package com.nurram.moviecatalogue.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.ConstValue
import com.nurram.moviecatalogue.model.Tv
import kotlinx.android.synthetic.main.fav_item_list.view.*

class FavTvAdapter(val context: Context) : RecyclerView.Adapter<FavTvAdapter.FavHolder>() {
    private var tvs = arrayListOf<Tv>()
    private var mOnItemTvClick: OnItemTvClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fav_item_list, parent, false)
        return FavHolder(view)
    }

    override fun getItemCount(): Int = tvs.size
    override fun onBindViewHolder(holder: FavHolder, position: Int) = holder.bind(tvs[position])

    fun setOnClick(onItemTvClick: OnItemTvClick) {
        mOnItemTvClick = onItemTvClick
    }

    fun addTv(tvs: ArrayList<Tv>) {
        this.tvs.clear()
        this.tvs.addAll(tvs)
        notifyDataSetChanged()
    }

    inner class FavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tv: Tv) {
            with(itemView) {
                fav_item_title.text = tv.name
                Glide.with(context).load(ConstValue.BASE_PICTURE_PATH + tv.poster_path)
                    .centerCrop().into(fav_item_poster)
            }

            itemView.setOnClickListener {
                mOnItemTvClick?.onTvClick(tv)
            }
        }
    }
}