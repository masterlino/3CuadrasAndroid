package com.mimo.sampleadvanced.network

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import java.io.UnsupportedEncodingException



class GsonRequest<T>(
    url: String,
    private val clazz: Class<T>,
    private val headers: Map<String, String>?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {
    private val gson = Gson()

    override @Throws(AuthFailureError::class)
    fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer _0jHsCFDOjRE6NUHuOdNCFz86qJMftwiXTzObE5jx_0b612GesLuyGSBhWQvRtBntGL8eCTBMiWhEPCf6YH6c5abkh0rg-0y5RHCEximfdyQfA7FpOIEiJvyN76UXHYx"
        return headers
    }

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
        try {
            val json = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
            return Response.success(
                gson.fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            return Response.error(ParseError(e))
        }

    }
}
