package viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knitforlife.database.repository.ColorRepository
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    val colorRepository: ColorRepository
):ViewModel() {

    val color = MutableStateFlow<String?>(null)
    fun takeColor(color:String){
        this.color.value=color
    }
    fun save(){
//        this._color.value=this.color.value
//        this._color.update { it->
//            this.color.value }
    }



    class Factory @Inject constructor(
        private val colorRepository: ColorRepository
    ) : ViewModelProvider.Factory {


        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CameraViewModel( colorRepository) as T
    }
}