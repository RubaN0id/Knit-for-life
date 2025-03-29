package ru.knitforlife.database.dto


import org.junit.Assert.*
import org.junit.Test

class ColorTest {
    @Test
    fun toColor() {
        val result =Color(0,"Color",99,100,98).toColor()
        assertEquals("Color",result.name)
        assertEquals(99,result.red)
        assertEquals(100,result.green)
        assertEquals(98,result.blue)
    }

    @Test
    fun getInstanse(){
        val  result = Color.getInstance(ru.knitforlife.core.model.Color(0,"Color",99,100,98))
        assertEquals("Color",result.name)
        assertEquals(99,result.red)
        assertEquals(100,result.green)
        assertEquals(98,result.blue)
    }

}