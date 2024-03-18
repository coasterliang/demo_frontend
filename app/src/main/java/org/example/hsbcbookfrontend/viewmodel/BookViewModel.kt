package org.example.hsbcbookfrontend.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allen.library.RxHttpUtils
import com.allen.library.interceptor.Transformer
import com.allen.library.observer.CommonObserver
import com.allen.library.utils.ToastUtils
import org.example.hsbcbookfrontend.network.api.BookApi
import org.example.hsbcbookfrontend.network.model.Book
import org.example.hsbcbookfrontend.network.model.BookListModel
import org.example.hsbcbookfrontend.network.model.OperationModel

class BookViewModel : ViewModel() {
    val bookListLiveData: MutableLiveData<List<BookListModel.DataDTO>> by lazy {
        MutableLiveData()
    }

    fun getBookList() {
        RxHttpUtils.createApi(BookApi::class.java)
            .getBookList()
            ?.compose(Transformer.switchSchedulers<BookListModel>())
            ?.subscribe(object : CommonObserver<BookListModel>() {
                override fun onSuccess(book: BookListModel?) {
                    bookListLiveData.value = book?.data
                }

                override fun onError(errorMsg: String?) {
                    ToastUtils.showToast(errorMsg)
                }

            })
    }

    fun deleteBook(id: Int) {
        RxHttpUtils.createApi(BookApi::class.java)
            .deleteBook(id)
            ?.compose(Transformer.switchSchedulers<OperationModel>())
            ?.subscribe(object : CommonObserver<OperationModel>() {
                override fun onError(errorMsg: String?) {
                    ToastUtils.showToast("delete failed")
                }

                override fun onSuccess(t: OperationModel?) {
                    getBookList()
                }

            })
    }

    fun addUpdate(book: Book) {
        RxHttpUtils.createApi(BookApi::class.java)
            .addOrUpdate(book)
            ?.compose(Transformer.switchSchedulers<OperationModel>())
            ?.subscribe(object : CommonObserver<OperationModel>() {
                override fun onError(errorMsg: String?) {
                    ToastUtils.showToast("add update failed")
                }

                override fun onSuccess(t: OperationModel?) {
                    getBookList()
                }
            })
    }

    fun queryBooks(keyword: String) {
        RxHttpUtils.createApi(BookApi::class.java)
            .queryBooks(keyword)
            ?.compose(Transformer.switchSchedulers<BookListModel>())
            ?.subscribe(object : CommonObserver<BookListModel>() {
                override fun onError(errorMsg: String?) {
                    ToastUtils.showToast("query $keyword failed")
                }

                override fun onSuccess(t: BookListModel?) {
                    bookListLiveData.value = t?.data
                }
            })
    }
}