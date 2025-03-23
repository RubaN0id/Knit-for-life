package ru.knitforlife.database.repository

import ru.knitforlife.core.model.Color
import ru.knitforlife.database.dao.ColorDao
import javax.inject.Inject

class ColorRepository @Inject constructor(
    val colorDao: ColorDao
) {

    suspend fun getAll(): List<Color>{
        return colorDao.getAll().map { it -> it.toColor() }
    }

    suspend fun save (vararg colors: Color){
        colorDao.insertAll(*colors.map { ru.knitforlife.database.dto.Color.getInstance(it) }.toTypedArray())
    }
}