package com.mxdigitalacademy.appgenda.funciones

import android.content.Context
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.gestionDB.ContactosCRUD
import com.mxdigitalacademy.appgenda.modelo.ObjContacto

class Funciones {

    companion object{

        fun agregarContactosDePrueba(context: Context){
            val operacionesCRUD: ContactosCRUD = ContactosCRUD(context)
            if (operacionesCRUD.getContactos().isEmpty()){
                operacionesCRUD.newContacto(ObjContacto("3478598723", R.drawable.walter_white.toString(), "Walter", "White", "", "ww@bb.com"))
                operacionesCRUD.newContacto(ObjContacto("112783498", R.drawable.jesse_pinkman.toString(), "Jesse", "Pinkman", "2734892893", "jesseP@gmail.com"))
                operacionesCRUD.newContacto(ObjContacto("1132456745", R.drawable.gus_fring.toString(), "Gus", "Fring", "", "fring@gmail.com"))
                operacionesCRUD.newContacto(ObjContacto("312398723", R.drawable.walter_white.toString(), "Walter", "Fake", "23423434", "fake@kk.com"))
                operacionesCRUD.newContacto(ObjContacto("113422444", R.drawable.mike_ehrmantraut.toString(), "Mike", "Ehrmantraut", "1163246952", "mikeE2021@email.com"))
                operacionesCRUD.newContacto(ObjContacto("45312344", R.drawable.saul_goodman.toString(), "Saul", "Goodman", "23423412", "better_call_saul@email.com"))
                operacionesCRUD.newContacto(ObjContacto("56245784", R.drawable.lydia_rodarte.toString(), "Lydia", "Rodarte", "43523421", "Rodarte@box.com"))
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
            return if (n > 1)
                "$n Seleccionados"
            else
                "$n Seleccionado"
        }

    }
}