package com.gcirilo.androidsample.core.networking

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class JsonRequest<T>(
    url: String,
    method: Int,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener,
    var clazz: Class<T>, // Class to be Returned after the network call
): Request<T>(method, url, errorListener) {

    // For de/serialization of JSON objects
    private var gson: Gson = GsonBuilder().create()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                gson.fromJson(json, clazz), // Decode using wrapper type
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
        catch (e: Error) {
            Response.error(ParseError(e))
        }
    }

}