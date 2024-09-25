package ru.knitforlife.viewmodel


import androidx.annotation.OpenForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.knitforlife.api.ColorApi
import ru.knitforlife.model.Color
import ru.knitforlife.service.ColorApiService
import ru.knitforlife.utils.Open
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
@Open
class ColorsViewModel @Inject constructor(
    val api: ColorApi
) : ViewModel() {
    var colors = MutableLiveData<List<Color>>()
    fun load(){
//        var items: MutableList<Color> = colors.value.orEmpty().toMutableList()
        var items: MutableList<Color> = emptyList<Color>().toMutableList()

        for (ii in 0..9) {
            items.add(Color.getRandom())
        }

        colors.value =items
    }

    fun add(color: Color) {
        var items: MutableList<Color> = emptyList<Color>().toMutableList()
        viewModelScope.launch {
            val response = api.getColorName(color.toColorString().substring(1))

            color.name = response.name.value

        }

        items.add(color)


        colors.value = items
    }
}