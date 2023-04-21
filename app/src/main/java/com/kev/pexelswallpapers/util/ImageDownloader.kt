package com.kev.pexelswallpapers.util

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class ImageDownloader(
    private val context: Context
) : FileDownloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadImage(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI.and(DownloadManager.Request.NETWORK_MOBILE)
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("image")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "image.jpeg"
            )
            .setAllowedOverMetered(true)

        return downloadManager.enqueue(request)
    }
}
