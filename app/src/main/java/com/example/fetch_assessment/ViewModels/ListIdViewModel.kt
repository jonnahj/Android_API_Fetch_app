package com.example.fetch_assessment.ViewModels

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch_assessment.Entities.Item
import com.example.fetch_assessment.Utilities.CronetHelper
import com.example.fetch_assessment.Utilities.ProcessStrings
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("StaticFieldLeak")

class ListIdViewModel(private val ctx:Context): ViewModel() {
    val data = MutableLiveData<MutableMap<String, MutableList<Item>>>()

    fun makeApiCall() {
        viewModelScope.launch {
            try {
                val result = CronetHelper(ctx).fetchData()
//                println("The return value is: $result")
                data.value = ProcessStrings.processItem(ctx, result)
            } catch (e: Exception) {
                println("The exception is $e")
            }
        }
    }
}