package org.example.hsbcbookfrontend.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allen.library.RxHttpUtils
import com.allen.library.interceptor.Transformer
import com.allen.library.observer.CommonObserver
import com.allen.library.utils.ToastUtils
import org.example.hsbcbookfrontend.network.api.BookApi
import org.example.hsbcbookfrontend.network.model.BookListModel

class BookViewModel : ViewModel(){
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
}