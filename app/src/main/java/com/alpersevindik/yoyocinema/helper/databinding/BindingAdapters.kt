package com.alpersevindik.yoyocinema.helper.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.alpersevindik.yoyocinema.data.repository.network.ServiceURL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrBlank()) {
        var width = view.measuredWidth
        if (width == 0)
            width = view.context.resources.displayMetrics.widthPixels

        Glide.with(view.context)
                .load(ServiceURL.IMAGE_BASE + if(width > 500) "original" else { "w500" } + imageUrl)
                .apply(RequestOptions()
                        .placeholder(android.R.drawable.stat_sys_download))
                .into(view)
    }
}