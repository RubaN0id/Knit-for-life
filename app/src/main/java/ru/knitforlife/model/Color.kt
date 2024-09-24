package ru.knitforlife.model

data class Color (
    val id:String,
    var name:String,
    val red:Int,
    val green:Int,
    val blue:Int
){


    fun toColorString():String{

        val blueHex = To00Hex(blue);
        val greenHex = To00Hex(green);
        val redHex = To00Hex(red);


        return "#"+redHex+greenHex+blueHex
    }


    companion object{
        fun getRandom():Color{
            return Color(
                java.util.UUID.randomUUID().toString(),
                listOf("John", "Mary", "Beaver").random(),
                (0..255).random(),
                (0..255).random(),
                (0..255).random()
            )
        }

        fun getInstance(hex:String):Color{
            return Color(
                java.util.UUID.randomUUID().toString(),
                "Create by camera",
                Integer.decode("0x"+hex.substring(1,3)),
                Integer.decode("0x"+hex.substring(3,5)),
                Integer.decode("0x"+hex.substring(5,7))
            )
        }

        fun To00Hex(value:Int):String {
            val hex = "00"+(Integer.toHexString(value));
            return hex.substring(hex.length-2, hex.length);
        }
    }
}