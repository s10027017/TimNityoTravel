package com.example.tim.nityo.travel.data

import com.example.tim.nityo.travel.data.model.ApiResult
import com.example.tim.nityo.travel.data.model.AttractionsData
import com.example.tim.nityo.travel.data.model.NityoResponse

object NityoRepository {

    private var remoteDataSource: RemoteDataSource = RemoteDataSource()

    fun getAttractions(page: Int, listener: (response: ApiResult<NityoResponse<AttractionsData>>) -> Unit) = remoteDataSource.getAttractions(page, listener)

}