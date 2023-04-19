package com.kev.pexelswallpapers.domain.model

import com.kev.pexelswallpapers.data.remote.PhotoDto

data class Photo(
    val id: Int,
    val src: Src,
    val url: String
)