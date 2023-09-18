package com.example.tim.nityo.travel.domain

import com.example.tim.nityo.travel.data.NityoRepository
import com.example.tim.nityo.travel.data.model.ApiResult
import com.example.tim.nityo.travel.data.model.AttractionsData
import com.example.tim.nityo.travel.data.model.NityoResponse

class GetAttractionsUseCase() {
    operator fun invoke(page: Int, listener: (response: ApiResult<NityoResponse<AttractionsData>>) -> Unit) {
        return NityoRepository.getAttractions(page, listener)
    }
}