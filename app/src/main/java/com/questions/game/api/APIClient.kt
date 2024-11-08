package com.questions.game.api

import com.questions.game.utils.Constant
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object APIClient {
    private var retrofit: Retrofit? = null

    private val client: Retrofit
        get() {
            if (retrofit == null) {

                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .sslSocketFactory(getUnsafeSSLSocketFactory(), TrustAllCerts)
                    .hostnameVerifier { _, _ -> true }
                    .build()

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }

    @Singleton
    @Provides
    fun getApiService(): ApiService {
        return client.create(ApiService::class.java)
    }
}

object TrustAllCerts : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}

fun getUnsafeSSLSocketFactory(): SSLSocketFactory {
    val trustAllCerts = arrayOf<TrustManager>(TrustAllCerts)
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
    return sslContext.socketFactory
}
