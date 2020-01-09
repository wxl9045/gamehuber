package safe.com.gamehuber.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import safe.com.gamehuber.common.utils.getMobileModel
import java.util.concurrent.TimeUnit


//通过一个 QueryParameter 让 CacheInterceptor 添加 no-cache
const val FORCE_NETWORK = "forceNetwork"


val retrofit by lazy {
    val logInterceptor = HttpLoggingInterceptor { Log.d("interceptor",it) }
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(OkHttpClient.Builder()
                    .addInterceptor(addQueryParameterInterceptor())  //参数添加
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(BaseInterceptor())
                    .addInterceptor(logInterceptor)
                    .build()
            )
            .baseUrl(UrlConstant.BASE_URL2)
            .build()
}

val myRetrofit by lazy {
    val logInterceptor = HttpLoggingInterceptor { Log.d("interceptor",it) }
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(OkHttpClient.Builder()
                    .addInterceptor(addQueryParameterInterceptor())  //参数添加
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(BaseInterceptor())
                    .addInterceptor(logInterceptor)
                    .build()
            )
            .baseUrl(UrlConstant.BASE_URL)
            .build()
}
//
//private val mHttpClient by lazy {
//    val logInterceptor = HttpLoggingInterceptor { Log.d("interceptor",it) }
//    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
//    OkHttpClient.Builder()
//            .sslSocketFactory(createSSLSocketFactory())
//            .hostnameVerifier { _, _ -> true }
//            .addInterceptor(logInterceptor)
//            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
//            .build()
//}

/**
 * 设置公共参数
 */
private fun addQueryParameterInterceptor(): Interceptor {
    return Interceptor { chain ->
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", getMobileModel())
                .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        chain.proceed(request)
    }
}

class BaseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
                .apply {
                    header("token", "")
                }
                .build())
    }


}

