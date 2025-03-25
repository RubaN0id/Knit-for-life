package ru.knitforlife.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.knitforlife.core.model.Color
import ru.knitforlife.database.dao.ColorDao
import javax.inject.Inject

class ColorRepository @Inject constructor(
    val colorDao: ColorDao
) {

//    suspend fun getAll(): List<Color>{
//        return colorDao.getAll().map { it -> it.toColor() }
//    }

    fun getAll(): Flow<List<Color>> = colorDao.getAll().map { it -> it.map { color-> color.toColor() } }


    suspend fun save (vararg colors: Color){
        colorDao.insertAll(*colors.map { ru.knitforlife.database.dto.Color.getInstance(it) }.toTypedArray())
    }
}