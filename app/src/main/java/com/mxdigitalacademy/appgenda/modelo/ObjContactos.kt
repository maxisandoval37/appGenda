package com.mxdigitalacademy.appgenda.modelo

import android.content.Context
import com.mxdigitalacademy.appgenda.adaptador.CustomAdapter
import com.mxdigitalacademy.appgenda.gestionDB.ContactosCRUD

class ObjContactos {

    companion object{
        var contactosCRUD:ContactosCRUD? = null
        var listaObjContactos: ArrayList<ObjContacto> = ArrayList()
        var adaptador: CustomAdapter? = null
        var nroTelefonoClick: String = ""

        fun inicializarListasContactos(context: Context){
            contactosCRUD = ContactosCRUD(context)
            listaObjContactos.addAll(contactosCRUD!!.getContactos())
        }

        fun agregarContacto(contacto: ObjContacto){
            contactosCRUD?.newContacto(contacto)
            listaObjContactos.add(contacto)
            adaptador?.addItem(contacto)
            adaptador?.notifyDataSetChanged()
        }

        fun getContactoTelPrincipal(tel1: String): ObjContacto?{
            return contactosCRUD?.getContactoByTelPrincipal(tel1)
        }

        fun actualizarObjetoContacto(telClickeado: String,fotoAvatar: String,nombre: String, apellido: String, tel1: String, tel2: String, email: String){
            getContactoTelPrincipal(telClickeado)?.setImgAvatar(fotoAvatar)
            getContactoTelPrincipal(telClickeado)?.setNombre(nombre)
            getContactoTelPrincipal(telClickeado)?.setApellido(apellido)
            getContactoTelPrincipal(telClickeado)?.setTelPrincipal(tel1)
            getContactoTelPrincipal(telClickeado)?.setTelSecundario(tel2)
            getContactoTelPrincipal(telClickeado)?.setEmail(email)

            contactosCRUD?.updateContacto(getContactoTelPrincipal(telClickeado)!!)
        }

        fun eliminarContactoPorTelefono(tel1: String){
            for (x:Int in 0 .. listaObjContactos.size){
                if (listaObjContactos[x].getTelefonoPrincipal() == tel1){
                    val itemBorrar = listaObjContactos[x]
                    contactosCRUD?.deleteContacto(itemBorrar)
                    listaObjContactos.remove(itemBorrar)
                    adaptador?.removeItem(itemBorrar)
                    adaptador?.notifyDataSetChanged()
                    break
                }
            }
        }

        fun existeTelefonoEnAgenda(tel1: String): Boolean{
            return (contactosCRUD?.getContactoByTelPrincipal(tel1)?.getTelefonoPrincipal().equals(tel1))
        }
    }

}