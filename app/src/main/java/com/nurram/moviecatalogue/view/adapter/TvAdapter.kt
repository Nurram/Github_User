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
import kotlinx.android.synthetic.main.item_list.view.*

class TvAdapter(private val context: Context) : RecyclerView.Adapter<TvAdapter.TvHolder>() {

    private var tv = ArrayList<Tv>()
    private var mOnItemTvClick: OnItemTvClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return TvHolder(view)
    }

    override fun getItemCount(): Int = tv.size
    override fun onBindViewHolder(holder: TvHolder, position: Int) = holder.tvBind(tv[position])

    fun addTvData(tv: ArrayList<Tv>) {
        this.tv.clear()
        this.tv.addAll(tv)
        notifyDataSetChanged()
    }

    fun setOnClick(onItemTvClick: OnItemTvClick) {
        mOnItemTvClick = onItemTvClick
    }

    inner class TvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun tvBind(tv: Tv) {
            with(itemView) {
                item_title.text = tv.name
                item_released.append(tv.first_air_date)
                item_director.append(tv.vote_average.toString())
                item_overview.text = tv.overview
            }

            Glide.with(context).load(ConstValue.BASE_PICTURE_PATH + tv.poster_path)
                .centerCrop().into(itemView.item_poster)

            itemView.setOnClickListener {
                mOnItemTvClick?.onTvClick(tv)
            }
        }
    }
}