package ru.knitforlife.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.knitforlife.core.model.Color

@Entity
data class Color (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name:String,
    val red:Int,
    val green:Int,
    val blue:Int){

    companion object{
        fun getInstance(color: Color):ru.knitforlife.database.dto.Color{
            return ru.knitforlife.database.dto.Color(id = color.id, name = color.name, red = color.red, green = color.green, blue = color.blue)
        }
    }

    fun toColor(): Color{
        return Color(id,name,red,green,blue)
    }

}
