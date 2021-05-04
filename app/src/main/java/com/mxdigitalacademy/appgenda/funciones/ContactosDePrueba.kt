package com.mxdigitalacademy.appgenda.funciones

import android.app.AlertDialog
import android.content.Context
import com.mxdigitalacademy.appgenda.R
import com.mxdigitalacademy.appgenda.gestionDB.ContactosCRUD
import com.mxdigitalacademy.appgenda.modelo.ObjContacto
import com.mxdigitalacademy.appgenda.modelo.ObjContactos

class ContactosDePrueba {
    companion object {

        private var contactosCRUD: ContactosCRUD? = null
        private var context: Context? = null

        private fun showOpcionesAgregarContactosDePrueba(context: Context) {
            val opciones = arrayOf<CharSequence>("Si", "No")
            val alertOpciones = AlertDialog.Builder(context)
            alertOpciones.setTitle("Desea cargar contactos de Prueba?")

            alertOpciones.setItems(opciones) { dialogInterface, i ->
                when (opciones[i]) {
                    "Si" -> agregarContactosDePrueba(contactosCRUD!!)
                    "No" -> dialogInterface.dismiss()
                }
            }
            alertOpciones.show()
        }

        private fun agregarContactosDePrueba(contactosCRUD: ContactosCRUD) {
            contactosCRUD.newContacto(ObjContacto("3478598723", R.drawable.walter_white.toString(), "Walter", "White", "", "ww@bb.com"))
            contactosCRUD.newContacto(ObjContacto("112783498", R.drawable.jesse_pinkman.toString(), "Jesse", "Pinkman", "2734892893", "jesseP@gmail.com"))
            contactosCRUD.newContacto(ObjContacto("1132456745", R.drawable.gus_fring.toString(), "Gus", "Fring", "", "fring@gmail.com"))
            contactosCRUD.newContacto(ObjContacto("312398723", R.drawable.walter_white.toString(), "Walter", "Fake", "23423434", "fake@kk.com"))
            contactosCRUD.newContacto(ObjContacto("113422444", R.drawable.mike_ehrmantraut.toString(), "Mike", "Ehrmantraut", "1163246952", "mikeE2021@email.com"))
            contactosCRUD.newContacto(ObjContacto("45312344", R.drawable.saul_goodman.toString(), "Saul", "Goodman", "23423412", "better_call_saul@email.com"))
            contactosCRUD.newContacto(ObjContacto("1164245678", R.drawable.hank_schrader.toString(), "Hank", "Schrader", "115634673", "hank_schrader@email.com"))
            contactosCRUD.newContacto(ObjContacto("1143653265", R.drawable.skyler_white.toString(), "Skyler", "White", "", "skyler00@hotmail.com"))
            contactosCRUD.newContacto(ObjContacto("0112342344", R.drawable.hector_salamanca.toString(), "Hector", "Salamanca", "4561233456", "hector_salamanca@email.com"))
            contactosCRUD.newContacto(ObjContacto("56245784", R.drawable.lydia_rodarte.toString(), "Lydia", "Rodarte", "43523421", "Rodarte@box.com"))
            ObjContactos.inicializarListasContactos(context!!)
            ObjContactos.adaptador?.notifyDataSetChanged()
        }

        fun cargarContactosDBVacia(context: Context) {
            this.context = context
            this.contactosCRUD = ContactosCRUD(context)
            if (contactosCRUD?.getContactos()?.isEmpty()!!) {
                showOpcionesAgregarContactosDePrueba(context)
            }
        }
    }
}