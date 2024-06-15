package dev.mcarr.a2fauthcompose.util

import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException400
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException401
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException403
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException404
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException422
import dev.mcarr.a2fauthcompose.data.exceptions.Auth2FException500
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class HttpUtil(
    val apiUrl: String,
    val token: String,
    val httpsVerification: Boolean = true,
    val debugMode: Boolean = false,
    val storeSecrets: Boolean = false
) {

    private val httpClient = HttpClient(CIO) {
        if (debugMode) {
            this.developmentMode = true
        }
        engine {
            this.requestTimeout = 60_000
            if (!httpsVerification) {
                this.https.trustManager = object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    override fun checkClientTrusted(chain: Array<X509Certificate>?, authType: String?) = Unit
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    @Throws(Exception::class)
    private suspend fun performRequest(method: HttpMethod, path: String, setBodyCallback: HttpRequestBuilder.() -> Unit): HttpResponse {
        val httpResponse = httpClient.request("$apiUrl$path"){
            this.method = method
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            this.setBodyCallback()
        }
        if (debugMode) {
            try {
                println(httpResponse.bodyAsText())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val statusCode = httpResponse.status.value
        if (statusCode in 200..299) {
            return httpResponse
        }else{
            val exc = when(statusCode){
                400 -> httpResponse.body() as Auth2FException400
                401 -> httpResponse.body() as Auth2FException401
                403 -> httpResponse.body() as Auth2FException403
                404 -> httpResponse.body() as Auth2FException404
                422 -> httpResponse.body() as Auth2FException422
                500 -> httpResponse.body() as Auth2FException500
                else -> httpResponse.body() as Auth2FException
            }
            throw exc
        }
    }

    @Throws(Exception::class)
    suspend fun get(path: String): HttpResponse =
        performRequest(HttpMethod.Get, path){}

    @Throws(Exception::class)
    suspend fun post(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit): HttpResponse =
        performRequest(HttpMethod.Post, path, setBodyCallback)

    @Throws(Exception::class)
    suspend fun put(path: String, setBodyCallback: HttpRequestBuilder.() -> Unit): HttpResponse =
        performRequest(HttpMethod.Put, path, setBodyCallback)

    suspend fun delete(path: String): Boolean =
        try{
            performRequest(HttpMethod.Delete, path){}
            true
        }catch (e: Exception){
            throw e
        }

}