package com.example.tim.nityo.travel.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.tim.nityo.travel.databinding.DialogCustomTextPickerBinding


class CustomTextPickerDialog(
    context: Context,
    private val valueArray: Array<String>,
    val confirmDo: (currentIndex: Int, chooseString: String) -> Unit
) : AlertDialog(context) {

    private var mBinding: DialogCustomTextPickerBinding =
        DialogCustomTextPickerBinding.inflate(LayoutInflater.from(context))

    var currentIndex = -1

    init {
        setView(mBinding.root)
        mBinding.btnDialogPickerNormalConfirm.setOnClickListener {
            currentIndex = mBinding.npTextPickerNormal.value
            confirmDo(currentIndex, valueArray[currentIndex])
            dismiss()
        }
        mBinding.btnDialogPickerNormalCancel.setOnClickListener {
            mBinding.npTextPickerNormal.value = currentIndex
            dismiss()
        }
        mBinding.npTextPickerNormal.apply {
            if (valueArray.isNotEmpty()) {
                minValue = 0
                maxValue = if (valueArray.size - 1 >= 0) {
                    valueArray.size - 1
                } else {
                    0
                }
                displayedValues = valueArray
                wrapSelectorWheel = false // 是否循環顯示
            }
        }
    }

    fun setCurrentChoose(chooseIndex: Int): String? {
        if (chooseIndex >= 0 && chooseIndex < valueArray.size) {
            mBinding.npTextPickerNormal.value = chooseIndex
            currentIndex = chooseIndex
            return valueArray[chooseIndex]
        } else {
            mBinding.npTextPickerNormal.value = 0
            currentIndex = -1
        }
        if (chooseIndex == -1) {
            return ""
        }
        return null
    }

    fun getCurrentChooseString(): String? {
        try {
            if (currentIndex != -1) {
                valueArray[currentIndex]
            }
        } catch (e: Exception) {
            return null
        }

        return null
    }

}