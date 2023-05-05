package com.kev.pexelswallpapers.repository

import com.kev.pexelswallpapers.data.remote.PhotosApiService
import com.kev.pexelswallpapers.model.photo_details.PhotoDetailsResponse
import com.kev.pexelswallpapers.model.photo_details.Src
import com.kev.pexelswallpapers.model.photo_search.Photo
import com.kev.pexelswallpapers.model.photo_search.PhotoSearchResponse
import com.kev.pexelswallpapers.util.Resource
import com.kev.pexelswallpapers.util.Resource.Error
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosRepositoryTest {

    @Mock
    private lateinit var apiService: PhotosApiService

    private lateinit var repository: PhotosRepository

    @Before
    fun setUp() {
        repository = PhotosRepository(apiService)
    }

    @Test
    fun `get photo details - success`(): Unit = runBlocking {
        // GIven
        val photoId = 1
        val photoDetailsResponse =
            PhotoDetailsResponse(
                "alt,",
                "avgColor",
                25,
                1,
                true,
                "photographer",
                22,
                "photographerURL",
                src = Src(
                    "landsape",
                    "large",
                    "large2x",
                    "medium",
                    "original",
                    "potraitt",
                    "small",
                    "tiny"
                ),
                "url",
                25
            )
        `when`(apiService.getImageDetails(photoId)).thenReturn(photoDetailsResponse)

        // when
        val result = repository.getPhotoDetails(photoId)
        // then
        assert(result == Resource.Success(photoDetailsResponse))
    }

    @Test
    fun `getPhotoDetails should return error when an IOException is thrown`() {
        // Define the exception to be thrown
        val exception = IOException("No network")

        // Mock the getImageDetails method and throw the exception
        runBlocking {
            `when`(apiService.getImageDetails(anyInt())).thenAnswer {
                throw exception
            }
        }

        // Call the method being tested and verify the result
        val result = runBlocking { repository.getPhotoDetails(1) }
        assertTrue(result is Error)
        assertEquals(
            "Ensure you have an active internet connection",
            (result as Error).errorMessage
        )
    }

    @Test
    fun `search photo should return list of photod - success`() = runBlocking {
        val query = "cats"
        val photo = Photo(
            "alt,",
            "avgColor",
            25,
            1,
            true,
            "photographer",
            22,
            "photographerURL",
            src = com.kev.pexelswallpapers.model.photo_search.Src(
                "landscape",
                "large",
                "large2x",
                "medium",
                "original",
                "potraitt",
                "small",
                "tiny"
            ),
            "url",
            25
        )
        val expectedResponse = PhotoSearchResponse("nextPage", 1, perPage = 25, listOf(photo), 2333)

        `when`(apiService.searchPhotos(query)).thenReturn(expectedResponse)
        val result = repository.searchPhoto(query)

        assertTrue(result is Resource.Success)
        assertEquals(expectedResponse, (result as Resource.Success).data)
    }

    @Test
    fun `search photo should return exception - error`() = runBlocking {

        val query = "cats"
        val exception = IOException()

        runBlocking {
            `when`(apiService.searchPhotos(query)).thenAnswer {
                throw exception
            }
        }

        val result = repository.searchPhoto(query)
        assertTrue(result is Resource.Error)
        assertEquals("Ensure you have an active internet connection", (result as Error).errorMessage)

    }
}
