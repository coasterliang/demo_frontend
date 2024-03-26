package org.example.hsbcbookfrontend

import android.app.Application
import android.content.res.AssetManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.allen.library.RxHttpUtils
import com.allen.library.config.OkHttpConfig
import junit.framework.TestCase.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.example.hsbcbookfrontend.network.model.BookListModel
import org.example.hsbcbookfrontend.viewmodel.BookViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch


@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {
    private val bookViewModel: BookViewModel = BookViewModel()
    private val server = MockWebServer()

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var context: Application

    @Mock
    private lateinit var assetManager: AssetManager

    @Before
    fun init() {
        server.start(8848)
        var BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpConfig.Builder(context) //全局的请求头信息
            //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
            //2、在没有网络的时候，去读缓存中的数据。
            .setCache(true)
            .setDebug(true)
            .setCacheMaxSize(200 * 1024 * 1024)
            .setHasNetCacheTime(10) //默认有网络时候缓存60秒
            .setNoNetCacheTime(3600) //默认有网络时候缓存3600秒
            .setReadTimeout(10) //全局超时配置
            .setWriteTimeout(10) //全局超时配置
            .setConnectTimeout(10) //全局是否打开请求log日志
            .build()
        RxHttpUtils
            .getInstance()
            .init(context as Application?)
            .config()
            //使用自定义factory的用法
            //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
            //配置全局baseUrl
            .setBaseUrl(BASE_URL)
            //开启全局配置
            .setOkClient(okHttpClient)

    }

    @Test
    fun testGetBookList() {
        val content = "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": null,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"test123\",\n" +
                "      \"author\": \"\",\n" +
                "      \"intro\": \"\",\n" +
                "      \"pubYear\": 0,\n" +
                "      \"isbn\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"name\": \"略略略\",\n" +
                "      \"author\": \"空气\",\n" +
                "      \"intro\": \"弄哭\",\n" +
                "      \"pubYear\": 2012,\n" +
                "      \"isbn\": \"空桶\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )
        val bookListLiveData: MutableLiveData<List<BookListModel.DataDTO>> = MutableLiveData()
        Mockito.`when`(bookViewModel.bookListLiveData).thenReturn(bookListLiveData)
        bookViewModel.getBookList()
        bookListLiveData.observeForever {
            assertEquals(bookListLiveData.value?.size, 2)
        }
    }

    @After
    fun end() {
        server.close()
    }
}