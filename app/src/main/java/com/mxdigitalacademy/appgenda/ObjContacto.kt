package com.mxdigitalacademy.appgenda

class ObjContacto(imgAvatar: Int, nombre: String, apellido: String, telefono: String, email: String) {

    private var _imgAvatar: Int = 0
    private var _nombre: String = ""
    private var _apellido: String = ""
    private var _telefono: String = ""
    private var _email: String = ""

    init {
        this._imgAvatar = imgAvatar
        this._nombre = nombre
        this._apellido = apellido
        this._telefono = telefono
        this._email = email
    }
}