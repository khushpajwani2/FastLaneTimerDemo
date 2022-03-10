package com.example.newbasicstructure.viewmodel

import com.example.newbasicstructure.R
import com.example.newbasicstructure.core.uI.BaseViewModel
import com.example.newbasicstructure.data.local.IntroScreenData
import com.example.newbasicstructure.network.ApiException
import com.example.newbasicstructure.repository.DemoRepository
import com.example.newbasicstructure.util.bindingAdapter.mutableLiveData
import com.example.newbasicstructure.util.extensionFunction.convertIntoErrorObjet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by JeeteshSurana.
 */

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: DemoRepository,
) : BaseViewModel() {

    var newList = arrayListOf<IntroScreenData>()
    val introScreenDataList = mutableLiveData<ArrayList<IntroScreenData>>()


    suspend fun getData() = withContext(Dispatchers.Main) {
        try {
            repository.getWeather()
        } catch (e: ApiException) {
            mError.postValue(convertIntoErrorObjet(e))
        }
    }


    suspend fun getSampleData() = withContext(Dispatchers.Main) {
        try {
            introScreenDataList.value?.clear()
            newList.clear()
            val desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            newList.add(
                IntroScreenData(
                    "FAST DELIVERY",
                    desc,
                    R.drawable.ic_launcher_background
                )
            )
            newList.add(
                IntroScreenData(
                    "EXCITING OFFERS",
                    desc,
                    R.drawable.ic_launcher_background
                )
            )
            newList.add(
                IntroScreenData(
                    "SECURE PAYMENT",
                    desc,
                    R.drawable.ic_launcher_background
                )
            )
            introScreenDataList.value = newList
        } catch (e: ApiException) {
            mError.postValue(convertIntoErrorObjet(e))
        }
    }
}