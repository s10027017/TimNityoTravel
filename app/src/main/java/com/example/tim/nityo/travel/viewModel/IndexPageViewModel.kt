package com.example.tim.nityo.travel.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tim.nityo.travel.base.BaseViewModel
import com.example.tim.nityo.travel.data.model.AttractionsDataItem

class IndexPageViewModel: BaseViewModel() {

    private val TAG = "IndexPageViewModel"

    //LiveData declaration
    private val _attraction = MutableLiveData<AttractionsDataItem>()
    val attraction: LiveData<AttractionsDataItem> = _attraction

    fun getAttractions(data: AttractionsDataItem){
        Log.d(TAG,"getAttractions > $data")
        _attraction.value = data
    }

}