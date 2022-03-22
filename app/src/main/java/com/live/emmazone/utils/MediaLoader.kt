package com.live.emmazone.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.live.emmazone.R
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader


class MediaLoader : AlbumLoader {
    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.placeholder)
            .into(imageView)
    }
}
