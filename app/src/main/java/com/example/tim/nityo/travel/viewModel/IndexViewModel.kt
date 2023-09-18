package com.example.tim.nityo.travel.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tim.nityo.travel.base.BaseViewModel
import com.example.tim.nityo.travel.data.model.ApiResult
import com.example.tim.nityo.travel.data.model.AttractionsDataItem
import com.example.tim.nityo.travel.domain.GetAttractionsUseCase

class IndexViewModel: BaseViewModel() {

    private val TAG = "IndexViewModel"

    //LiveData declaration
    private val _attractions = MutableLiveData<ArrayList<AttractionsDataItem>>()
    val attractions: LiveData<ArrayList<AttractionsDataItem>> = _attractions
    val page = MutableLiveData<Int>(1)
    val isAddList = MutableLiveData<Boolean>(false)

    fun getAttractions(){
        Log.d("Call_GetAttractionsUseCase", "Call_GetAttractionsUseCase")
        loadingStart()
        page.value?.let {
            GetAttractionsUseCase().invoke(it) { response ->
                Log.d(TAG,"GetVersionUseCase > $response")
                if (response is ApiResult.Success) {
                    if (response.data.data.isNotEmpty()) {
                        Log.d("TAG", response.data.data.toString())
                        _attractions.value = response.data.data
                    }
                } else {
                    judgeErrorTypeAndDisplay((response as ApiResult.Error).status)
                }
                loadingDone()
            }
        }
    }

}