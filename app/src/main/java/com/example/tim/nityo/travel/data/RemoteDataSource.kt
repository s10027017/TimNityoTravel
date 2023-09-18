package com.example.tim.nityo.travel.data

import android.util.Log
import com.example.tim.nityo.travel.data.model.ApiResult
import com.example.tim.nityo.travel.data.model.AttractionsData
import com.example.tim.nityo.travel.data.model.NityoResponse
import com.example.tim.nityo.travel.utils.SharedPreferencesSecret
import retrofit2.Call
import retrofit2.Response


class RemoteDataSource {

    private val TAG = "RemoteDataSource"

    private val apiClientService by lazy {
        ApiClientService.create()
    }

    /**
     *
     */
    fun getAttractions(page: Int, listener: (response: ApiResult<NityoResponse<AttractionsData>>) -> Unit) {
        Log.d(TAG, "getAttractions")
        apiClientService.getAttractions(SharedPreferencesSecret.language, page).enqueue(TGCallback<NityoResponse<AttractionsData>>(listener))
    }

    class TGCallback<T>(_listener: (response: ApiResult<T>) -> Unit) : retrofit2.Callback<T> {
        var listener: (response: ApiResult<T>) -> Unit = _listener

        override fun onResponse(call: Call<T>, response: Response<T>) {

            if (response.isSuccessful) {
                if (response.body() != null) {
                    listener(ApiResult.Success(response.body()!!))
                } else {
                    listener(ApiResult.Error(EMPTY_BODY))
                }
            } else {
                listener(ApiResult.Error(response.code()))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            listener(ApiResult.Error(NETWORK_ERROR))
        }
    }

    // TODO api response status code
    companion object {
        const val RESPONSE_SUCCESS = 1000
        const val NETWORK_ERROR = -1000
        const val EMPTY_BODY = -2000
    }

}