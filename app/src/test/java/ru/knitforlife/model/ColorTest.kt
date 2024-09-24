package ru.knitforlife.model

import org.junit.Assert
import org.junit.Test


class ColorTest {

    @Test
    fun to00Hex255() {
        Assert.assertEquals("ff",Color.To00Hex(255))
    }

    @Test
    fun to00Hex0() {
        Assert.assertEquals("00",Color.To00Hex(0))
    }

    @Test
    fun toColorString255255255(){
        val color = Color("12","test",255,255,255)

        Assert.assertEquals("#ffffff",color.toColorString())
    }

    @Test
    fun toColorStringr0g126b84(){
        val color = Color("12","test",0,126,84)

        Assert.assertEquals("#007e54",color.toColorString())
    }

    @Test
    fun getInstanceByHex(){
        val color = Color.getInstance("#007e54")
        Assert.assertEquals(0,color.red)
        Assert.assertEquals(126,color.green)
        Assert.assertEquals(84,color.blue)
    }
}