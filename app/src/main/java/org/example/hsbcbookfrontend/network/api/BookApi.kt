package org.example.hsbcbookfrontend.network.api

import io.reactivex.Observable
import org.example.hsbcbookfrontend.network.model.Book
import org.example.hsbcbookfrontend.network.model.BookListModel
import org.example.hsbcbookfrontend.network.model.OperationModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApi {
    @GET("list")
    fun getBookList(): Observable<BookListModel?>?

    @GET("delete/{id}")
    fun deleteBook(@Path("id") id: Int): Observable<OperationModel?>?

    @POST("update")
    fun addOrUpdate(@Body book: Book): Observable<OperationModel?>?

    @GET("query/{keyword}")
    fun queryBooks(@Path("keyword") keyword: String): Observable<BookListModel?>?
}