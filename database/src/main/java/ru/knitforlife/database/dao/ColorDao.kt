package ru.knitforlife.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.knitforlife.database.dto.Color

@Dao
interface ColorDao {
    @Query("Select * from color")
   fun getAll(): Flow<List<Color>>

    @Insert
    suspend fun insertAll(vararg colors: Color)
}