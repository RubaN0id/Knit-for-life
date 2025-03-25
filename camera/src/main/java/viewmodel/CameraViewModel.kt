package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    override fun onCleared() {
        super.onCleared()
    }
}