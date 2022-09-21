package com.example.retrofittest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofittest.model.Item
import com.example.retrofittest.model.TYPE_CATEGORY
import com.example.retrofittest.model.TYPE_ITEM
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val apiResponse = MutableLiveData<List<Item>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun fetchData() {
        apiResponse.value = arrayListOf(
            Item("Category1", "", TYPE_CATEGORY),
            Item("Key1", "Value1", TYPE_ITEM),
            Item("Key2", "Value2", TYPE_ITEM),
            Item("Category2", "", TYPE_CATEGORY),
            Item("Key3", "Value3", TYPE_ITEM),
            Item("Key4", "Value4", TYPE_ITEM),
        )
        error.value = null
        loading.value = false
    }


    var job: Job? = null
    val execeptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        GlobalScope.launch(Dispatchers.Main) {
            onError("Exception ${throwable.localizedMessage}")
        }
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}