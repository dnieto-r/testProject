package com.telefonica.movistarhome.iot.light.model.legacy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver

class ISResultReceiver(private val callBack: DataReceiverCallBack) :
    ResultReceiver(Handler(Looper.getMainLooper())) {

    companion object {
        private const val RESULT_SUCCESS_CODE = 200
        private const val RESULT_ERROR_CODE = 500
        private const val RESULT_EXCEPTION_KEY = "result.exception"

        fun ResultReceiver.sendSuccess(data: Bundle) {
            send(RESULT_SUCCESS_CODE, data)
        }

        fun ResultReceiver.sendError(exception: Exception) {
            send(
                RESULT_ERROR_CODE,
                Bundle().apply {
                    putSerializable(RESULT_EXCEPTION_KEY, exception)
                })
        }
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        when {
            resultCode == RESULT_SUCCESS_CODE && resultData != null -> {
                callBack.onSuccess(resultData)
            }
            resultCode == RESULT_ERROR_CODE && resultData != null -> {
                val exception = resultData.getSerializable(RESULT_EXCEPTION_KEY) as java.lang.Exception
                callBack.onError(exception)
            }
            else -> {
                callBack.onError(java.lang.Exception("Unknown exception"))
            }
        }
    }
}

interface DataReceiverCallBack {
    fun onSuccess(data: Bundle)
    fun onError(exception: Exception)
}