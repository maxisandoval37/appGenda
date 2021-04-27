package com.mxdigitalacademy.appgenda.modelo

import android.provider.BaseColumns

class ContactosSchema {

    companion object{
        class Entrada: BaseColumns{
            companion object{
                const val nombreTabla = "contactos"
                const val columnaTelPrincipal = "telefonoP"
                const val columnaImgAvatar = "imgAvatar"
                const val columnaNombre = "nombre"
                const val columnaApellido = "apellido"
                const val columnaTelefonoS = "telefonoS"
                const val columnaEmail = "email"
            }
        }
    }
}