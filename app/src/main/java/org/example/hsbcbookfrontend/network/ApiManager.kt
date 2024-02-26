package org.example.hsbcbookfrontend.network

import android.app.Application
import com.allen.library.RxHttpUtils
import com.allen.library.config.OkHttpConfig
import java.io.File

class ApiManager {
    companion object {
        lateinit var context: Application
        fun initRxHttpUtil(context: Application) {
            this.context = context
            val cacheDirectory: File = File(context.cacheDir, "httpcache")
            val okHttpClient = OkHttpConfig.Builder(context) //全局的请求头信息
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                .setDebug(true)
                .setCacheMaxSize(200 * 1024 * 1024)
                .setCachePath(cacheDirectory.toString())
                .setHasNetCacheTime(10) //默认有网络时候缓存60秒
                .setNoNetCacheTime(3600) //默认有网络时候缓存3600秒
                .setReadTimeout(10) //全局超时配置
                .setWriteTimeout(10) //全局超时配置
                .setConnectTimeout(10) //全局是否打开请求log日志
                .build()
            RxHttpUtils
                .getInstance()
                .init(context)
                .config()
                //使用自定义factory的用法
                //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl("http://192.168.108.224:8848/book/")
                //开启全局配置
                .setOkClient(okHttpClient);

        }
    }
}