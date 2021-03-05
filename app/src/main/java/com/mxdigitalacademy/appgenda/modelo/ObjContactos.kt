package com.mxdigitalacademy.appgenda.modelo

import com.mxdigitalacademy.appgenda.adaptador.CustomAdapter

class ObjContactos {

    companion object{
        var listaObjContactos: ArrayList<ObjContacto> = ArrayList()
        var adaptador: CustomAdapter? = null
        var nroTelefonoClick: String = ""

        fun agregarContacto(contacto: ObjContacto){
            listaObjContactos.add(contacto)
            adaptador?.addItem(contacto)
            adaptador?.notifyDataSetChanged()
        }

        fun getContactoTelPrincipal(tel1: String): ObjContacto?{
            for (contacto in listaObjContactos){
                if (contacto.getTelefonoPrincipal() == tel1)
                    return contacto
            }
            return null
        }

        fun eliminarContactoPorTelefono(tel1: String){
            for (x:Int in 0 .. listaObjContactos.size){
                if (listaObjContactos[x].getTelefonoPrincipal() == tel1){
                    val itemBorrar = listaObjContactos[x]
                    listaObjContactos.remove(itemBorrar)
                    adaptador?.removeItem(itemBorrar)
                    adaptador?.notifyDataSetChanged()
                    break
                }
            }
        }

        fun existeTelefonoEnAgenda(tel1: String): Boolean{
            var existe = false
            for (contacto in listaObjContactos){
                existe = existe or (contacto.getTelefonoPrincipal() == tel1)
            }
            return existe
        }
    }

}