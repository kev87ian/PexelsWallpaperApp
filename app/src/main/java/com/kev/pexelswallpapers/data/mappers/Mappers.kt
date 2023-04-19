package com.kev.pexelswallpapers.data.mappers

import com.kev.pexelswallpapers.data.local.PhotoEntity
import com.kev.pexelswallpapers.data.remote.PhotoDto
import com.kev.pexelswallpapers.data.remote.SrcDto
import com.kev.pexelswallpapers.domain.model.Photo
import com.kev.pexelswallpapers.domain.model.Src

fun PhotoDto.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        src = srcDto.toSrc(),
        url = url

    )
}

fun PhotoEntity.toPhoto(): Photo {
    return Photo(
        id = id,
        src = src,
        url = url
    )
}
fun PhotoDto.toPhoto(): Photo {
    return Photo(
        id = id,
        src = srcDto.toSrc(),
        url = url
    )
}
fun SrcDto.toSrc(): Src {
    return Src(
        portrait = portrait
    )
}
