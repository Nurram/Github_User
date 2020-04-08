package com.nurram.moviecatalogue.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.Tv
import com.nurram.moviecatalogue.view.adapter.OnItemTvClick
import com.nurram.moviecatalogue.view.adapter.TvAdapter
import com.nurram.moviecatalogue.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var adapter: TvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.setHasFixedSize(true)

        adapter = context?.let {
            TvAdapter(
                it
            )
        }
        adapter?.notifyDataSetChanged()
        recyclerview.adapter = adapter

        adapter?.setOnClick(object : OnItemTvClick {
            override fun onTvClick(tv: Tv) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("tv", tv)
                startActivity(intent)
            }
        })

        viewModel.getTvs().observe(viewLifecycleOwner, Observer {
            adapter?.addTvData(it.results)
            progress_bar.visibility = View.GONE
        })
    }
}
