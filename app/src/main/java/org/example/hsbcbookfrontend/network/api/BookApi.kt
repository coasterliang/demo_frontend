package org.example.hsbcbookfrontend.network.api

import io.reactivex.Observable
import org.example.hsbcbookfrontend.network.model.BookListModel
import retrofit2.http.GET

interface BookApi {
    @GET("list")
    fun getBookList(): Observable<BookListModel?>?
}