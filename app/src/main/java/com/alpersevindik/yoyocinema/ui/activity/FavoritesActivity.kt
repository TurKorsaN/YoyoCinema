package com.alpersevindik.yoyocinema.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.alpersevindik.yoyocinema.R
import com.alpersevindik.yoyocinema.data.model.MovieDetail
import com.alpersevindik.yoyocinema.ui.adapter.BindingRecyclerViewAdapter
import com.alpersevindik.yoyocinema.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setupUI()
    }

    fun setupUI() {
        favoritesRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        val adapter = BindingRecyclerViewAdapter<MovieDetail>(R.layout.favorite_movie_item, emptyList())
        adapter.setHasStableIds(true)
        adapter.setOnClickListener(object : BindingRecyclerViewAdapter.OnItemClickListener<MovieDetail> {
            override fun onClick(item: MovieDetail, position: Int) {
                DetailActivity.start(this@FavoritesActivity, item.id)
            }
        })
        favoritesRecyclerView.adapter = adapter
    }

    fun updateFavorites() {
        val favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.getFavorites()?.observe(this, Observer { favorites ->
            (favoritesRecyclerView.adapter as BindingRecyclerViewAdapter<MovieDetail>).submitData(favorites)
        })
    }

    override fun onResume() {
        super.onResume()
        updateFavorites()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(
                    Intent(context, FavoritesActivity::class.java)
            )
        }
    }
}
