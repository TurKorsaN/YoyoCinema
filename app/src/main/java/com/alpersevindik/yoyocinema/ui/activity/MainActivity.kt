package com.alpersevindik.yoyocinema.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.alpersevindik.yoyocinema.R
import com.alpersevindik.yoyocinema.data.model.Movie
import com.alpersevindik.yoyocinema.helper.diffutil.IDiffUtilModel
import com.alpersevindik.yoyocinema.ui.adapter.PageRecyclerViewAdapter
import com.alpersevindik.yoyocinema.viewmodel.SearchMovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSearchView()
        setupMovieRecyclerView()
        setupFavorites()
    }

    private fun setupFavorites() {
        favorites.setOnClickListener {
            FavoritesActivity.start(this)
        }
    }

    fun setupSearchView() {
        val searchViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrBlank())
                    animateSearchView(true)
                query?.let {
                    val result = searchViewModel.search(query)
                    result.observe(this@MainActivity, Observer { data ->
                        movieRecyclerView.adapter?.let { adapter ->
                            adapter as PageRecyclerViewAdapter<Movie>
                            adapter.submitList(data as PagedList<IDiffUtilModel>)

                            animateSearchView(data.size == 0)
                        }
                    })
                }
                return true
            }

        })
    }

    private fun setupMovieRecyclerView() {
        movieRecyclerView.layoutManager = object: GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false){
            override fun supportsPredictiveItemAnimations(): Boolean {
                return false
            }
        }
        val adapter = PageRecyclerViewAdapter<Movie>(R.layout.search_movie_item)
        adapter.setHasStableIds(true)
        adapter.setOnClickListener(object : PageRecyclerViewAdapter.OnItemClickListener<Movie> {
            override fun onClick(item: Movie, position: Int) {
                DetailActivity.start(this@MainActivity, item.id)
            }

        })
        movieRecyclerView.adapter = adapter
    }

    private fun animateSearchView(onStartPosition: Boolean) {
        if (onStartPosition) {
            root.transitionToStart()
        } else {
            root.transitionToEnd()
        }
    }
}
