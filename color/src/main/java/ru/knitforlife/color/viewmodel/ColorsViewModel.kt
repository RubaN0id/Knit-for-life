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
    val colorRepository:ColorRepository
) : ViewModel() {



    val colorFlow: Flow<List<Color>?> = colorRepository.getAll()

}