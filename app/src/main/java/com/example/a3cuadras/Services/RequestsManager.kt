package com.mimo.sampleadvanced.network

import android.content.Context
import android.support.annotation.MainThread

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class RequestsManager private constructor(private val mContext: Context) {
    private var mRequestQueue: RequestQueue? = null

    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mContext)
            }
            return mRequestQueue
        }

    init {
        mRequestQueue = requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue?.add(req)
    }

    companion object {
        private var mInstance: RequestsManager? = null

        @MainThread
        @Synchronized
        fun getInstance(context: Context): RequestsManager {
            if (mInstance == null) {
                mInstance = RequestsManager(context.applicationContext)
            }
            return mInstance as RequestsManager
        }
    }
}