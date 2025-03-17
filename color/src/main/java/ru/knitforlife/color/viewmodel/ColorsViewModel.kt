package ru.knitforlife.color.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.knitforlife.color.api.ColorApi
import ru.knitforlife.color.model.Color
import ru.knitforlife.color.utils.Open
import javax.inject.Inject
import javax.inject.Singleton

@Open
class ColorsViewModel @Inject constructor(
    val api: ColorApi,
    val observer: StateFlow<String?>
) : ViewModel() {
    init {
        observer.onEach { if (it != null) {
            val color = Color.getInstance(it)
            val response =
                api.getColorName(Color.getInstance(it).toColorString().substring(1))
            color.name = response.name.value
            add(color)
        } }

//        viewModelScope.launch {
//            observer.collect { it ->
//                if (it != null) {
//                    val color = Color.getInstance(it)
//                    val response =
//                        api.getColorName(Color.getInstance(it).toColorString().substring(1))
//                    color.name = response.name.value
//                    add(color)
//                }
//            }
//        }
    }


    private val _colorFlow = MutableStateFlow<List<Color>?>(null)
    val colorFlow: StateFlow<List<Color>?> = _colorFlow.asStateFlow()

    fun observeColors() {
        observer
            .onEach {
                if (it != null) {
                    val color = Color.getInstance(it)
                    val response =
                        api.getColorName(Color.getInstance(it).toColorString().substring(1))
                    color.name = response.name.value
                    add(color)
                }
            }
            .launchIn(viewModelScope)
    }

    fun load() {
        val items: MutableList<Color> = _colorFlow.value?.toMutableList() ?: mutableListOf()

//        for (ii in 0..9) {
//            items.add(Color.Companion.getRandom())
//        }

        _colorFlow.value = items
    }

    fun add(color: Color) {
        val items: MutableList<Color> = _colorFlow.value?.toMutableList() ?: mutableListOf()

        viewModelScope.launch {
            val response = api.getColorName(color.toColorString().substring(1))
            color.name = response.name.value
        }

        items.add(color)

        _colorFlow.value = items
    }

    class Factory @Inject constructor(
        private val colorEventObserver: StateFlow<String?>,
        private val api: ColorApi
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ColorsViewModel(api,colorEventObserver) as T

    }
}