package com.kev.pexelswallpapers.domain

import com.google.gson.annotations.SerializedName
import com.kev.pexelswallpapers.data.remote.SrcDto

data class Photo(
    val alt: String,
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographerId: Int,
    val photographerUrl: String,
    val srcDto: SrcDto,
    val url: String,
    val width: Int
)
