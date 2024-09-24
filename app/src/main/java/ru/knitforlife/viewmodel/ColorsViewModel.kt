package ru.knitforlife.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.knitforlife.model.Color
import ru.knitforlife.service.ColorApiService
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ColorsViewModel @Inject constructor(): ViewModel() {
    var colors = MutableLiveData<List<Color>>()
    fun load(){
        var items: MutableList<Color> = colors.value.orEmpty().toMutableList()

        for (ii in 0..9){
            items.add(Color.getRandom())
        }

        colors.value = items
    }

    fun add(color: Color){
        var items: MutableList<Color> = colors.value.orEmpty().toMutableList()
        viewModelScope.launch {
           val response =ColorApiService.api.getColorName(color.toColorString().substring(1))
            if (response!=null){
                color.name= response.name.value
            }
        }

        items.add(color)



        colors.value = items
    }
}