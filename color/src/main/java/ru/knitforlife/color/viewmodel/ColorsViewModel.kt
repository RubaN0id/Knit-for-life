package ru.knitforlife.color.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knitforlife.network.api.ColorApi
import ru.knitforlife.core.model.Color
import ru.knitforlife.color.utils.Open
import ru.knitforlife.database.repository.ColorRepository

import javax.inject.Inject

@Open
@HiltViewModel
class ColorsViewModel @Inject constructor(
    val api: ColorApi,
    val colorRepository:ColorRepository
) : ViewModel() {
//    init {
//        observer.onEach {
//            if (it != null) {
//                val color = Color.getInstance(it)
//                val response =
//                    api.getColorName(Color.getInstance(it).toColorString().substring(1))
//                color.name = response.name.value
//                add(color)
//            }
//        }
//
////        viewModelScope.launch {
////            observer.collect { it ->
////                if (it != null) {
////                    val color = Color.getInstance(it)
////                    val response =
////                        api.getColorName(Color.getInstance(it).toColorString().substring(1))
////                    color.name = response.name.value
////                    add(color)
////                }
////            }
////        }
//    }
//
//    init {
//        initTheme()
//    }
//
//    private fun initTheme() {
//        viewModelScope.launch {
//            colorRepository.getAll().collect { state.emit(state.value.copy(theme = it)) }
//        }
//    }


    private val _colorFlow = MutableStateFlow<List<Color>?>(ArrayList<Color>(0))
//    val colorFlow: StateFlow<List<Color>?> = _colorFlow.asStateFlow()
    val colorFlow: Flow<List<Color>?> = colorRepository.getAll()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

//    fun observeColors() {
//        observer
//            .onEach {
//                if (it != null) {
//                    val color = Color.getInstance(it)
//                    val response =
//                        api.getColorName(Color.getInstance(it).toColorString().substring(1))
//                    color.name = response.name.value
//                    add(color)
//                }
//            }
//            .launchIn(viewModelScope)
//
////            viewModelScope.launch {
////            observer.let{ it ->
////                if (it.value != null) {
////                    val color = Color.getInstance(it.value!!)
////                    val response =
////                        api.getColorName(Color.getInstance(it.value!!).toColorString().substring(1))
////                    color.name = response.name.value
////                    add(color)
////                }
////            }
////        }
//    }

    fun load() {

//        viewModelScope.launch {
//            val items: List<Color> = colorRepository.getAll()
//
////        for (ii in 0..9) {
////            items.add(Color.Companion.getRandom())
////        }
//
//            _colorFlow.update { items }
//        }
//        val items: List<Color> = colorRepository.getAll()
//
////        for (ii in 0..9) {
////            items.add(Color.Companion.getRandom())
////        }
//
//        _colorFlow.update { items }
    }

    fun add(color: Color) {
        val items: MutableList<Color> = _colorFlow.value?.toMutableList() ?: mutableListOf()

//        viewModelScope.launch {
//            val response = api.getColorName(color.toColorString().substring(1))
//            color.name = response.name.value
//        }

        if(_colorFlow.value?.any { it -> color.name.equals(it.name) } != true) {
            items.add(color)
        }

        _colorFlow.update { items }
    }

//    class Factory @Inject constructor(
//        private val colorRepository: ColorRepository,
//        private val api: ColorApi,
//    ) : ViewModelProvider.Factory {
//
//        override fun <T : ViewModel> create(modelClass: Class<T>): T =
//            ColorsViewModel(api, colorRepository) as T
//
//    }
}