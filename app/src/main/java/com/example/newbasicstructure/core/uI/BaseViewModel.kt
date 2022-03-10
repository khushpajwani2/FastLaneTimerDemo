package com.example.newbasicstructure.core.uI

import androidx.lifecycle.ViewModel
import com.example.newbasicstructure.data.remote.model.ErrorModel
import com.example.newbasicstructure.util.bindingAdapter.mutableLiveData

/**
 * Created by JeeteshSurana.
 */

open class BaseViewModel : ViewModel() {
    var mError = mutableLiveData(ErrorModel())
}