package com.mxdigitalacademy.appgenda.funciones

import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.modelo.ObjContacto

class Funciones {

    companion object{

        fun agregarContactosDePrueba(listaObjContactos: ArrayList<ObjContacto>){
            if (listaObjContactos.isEmpty()){
                listaObjContactos.add(ObjContacto(R.drawable.walter_white.toString(), "Walter", "White", "3478598723", "", "ww@bb.com"))
                listaObjContactos.add(ObjContacto(R.drawable.jesse_pinkman.toString(), "Jesse", "Pinkman", "112783498", "2734892893", "jesseP@gmail.com"))
                listaObjContactos.add(ObjContacto(R.drawable.gus_fring.toString(), "Gus", "Fring", "1132456745", "", "fring@gmail.com"))
                listaObjContactos.add(ObjContacto(R.drawable.walter_white.toString(), "Walter", "Fake", "312398723", "23423434", "fake@kk.com"))
                listaObjContactos.add(ObjContacto(R.drawable.mike_ehrmantraut.toString(), "Mike", "Ehrmantraut", "113422444", "1163246952", "mikeE2021@email.com"))
                listaObjContactos.add(ObjContacto(R.drawable.saul_goodman.toString(), "Saul", "Goodman", "45312344", "23423412", "better_call_saul@email.com"))
                listaObjContactos.add(ObjContacto(R.drawable.lydia_rodarte.toString(), "Lydia", "Rodarte", "56245784", "43523421", "Rodarte@box.com"))
            }
        }

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
            var titulo=""
            titulo = if (n > 1)
                "$n Seleccionados"
            else
                "$n Seleccionado"
            return  titulo
        }

    }
}