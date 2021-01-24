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

    }
}