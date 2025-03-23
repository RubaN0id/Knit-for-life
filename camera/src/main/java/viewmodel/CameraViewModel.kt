package viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knitforlife.core.model.Color
import ru.knitforlife.database.repository.ColorRepository
import ru.knitforlife.network.api.ColorApi
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val api: ColorApi,
    val colorRepository: ColorRepository
) : ViewModel() {

    val color = MutableStateFlow<String?>(null)
    fun takeColor(color: String) {
        this.color.value = color
    }

    fun save() {
        viewModelScope.launch {
            if (color.value != null) {
                val color = Color.getInstance(color.value!!)
                val response =
                    api.getColorName(color.toColorString().substring(1))
                color.name = response.name.value

                colorRepository.save(color)
            }

        }
    }
}