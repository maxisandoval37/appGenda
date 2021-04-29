package com.mxdigitalacademy.appgenda.funciones

class Funciones {

    companion object{

        fun esFormatoNumerico(n: String): Boolean{
            var ret = false
            try {
                Integer.parseInt(n)
                ret = true
            }
            catch (e: NumberFormatException){ }
            return ret
        }

        fun setCantTituloSeleccionados(n: Int): String{
            return if (n > 1)
                "$n Seleccionados"
            else
                "$n Seleccionado"
        }

    }
}