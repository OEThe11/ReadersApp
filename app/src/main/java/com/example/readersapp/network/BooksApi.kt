package com.example.readersapp.network

import com.example.readersapp.model.Book
import com.example.readersapp.model.Item
import com.example.readersapp.network.Endpoints.BOOK_EP
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET(BOOK_EP)
    suspend fun getAllBooks(
        //don't initialize the query, so that the whole api is available to the user
        @Query("q") query: String
    ): Book

    @GET("$BOOK_EP/{bookId}")
    suspend fun getBookInfo(
        @Path("bookId") bookId: String
    ): Item
}