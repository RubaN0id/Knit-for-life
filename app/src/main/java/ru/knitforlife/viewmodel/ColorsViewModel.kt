package ru.knitforlife.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.knitforlife.api.ColorApi
import ru.knitforlife.model.Color
import ru.knitforlife.utils.Open
import javax.inject.Inject

@HiltViewModel
@Open
class ColorsViewModel @Inject constructor(
    val api: ColorApi
) : ViewModel() {

    private var _colors = MutableLiveData<List<Color>>(emptyList())
    var colors: LiveData<List<Color>> = _colors

    fun load() {
        val items: MutableList<Color> = _colors.value?.toMutableList() ?: mutableListOf()

        for (ii in 0..9) {
            items.add(Color.getRandom())
        }

        _colors.value = items
    }

    fun add(color: Color) {
        val items: MutableList<Color> = _colors.value?.toMutableList() ?: mutableListOf()

        viewModelScope.launch {
            val response = api.getColorName(color.toColorString().substring(1))
            color.name = response.name.value
        }

        items.add(color)

        _colors.value = items
    }
}