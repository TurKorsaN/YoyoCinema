package com.alpersevindik.yoyocinema.ui.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alpersevindik.yoyocinema.R
import com.alpersevindik.yoyocinema.databinding.ActivityDetailBinding
import com.alpersevindik.yoyocinema.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setupUI()
    }

    fun setupUI() {
        val id = intent.extras?.getInt(MOVIE_ID)

        val movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        id?.let {
            movieDetailViewModel.getDetail(id).observe(this, Observer { detail ->
                binding.item = detail
                movieDetailViewModel.checkFavorites()
            })

        }

        favorite.setOnClickListener { view ->
            movieDetailViewModel.isFavorite.set(!movieDetailViewModel.isFavorite.get())
        }

        movieDetailViewModel.isFavorite.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                favorite.supportImageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@DetailActivity, if (movieDetailViewModel.isFavorite.get()) R.color.colorYellow else R.color.colorGrey))
            }
        })

    }

    companion object {

        const val MOVIE_ID = "MOVIE_ID"

        fun start(context: Context, id: Int) {
            context.startActivity(
                    Intent(context, DetailActivity::class.java).apply {
                        putExtra(MOVIE_ID, id)
                    })
        }
    }
}
