package com.mxdigitalacademy.appgenda

class ObjContacto(imgAvatar: Int, nombre: String, apellido: String, telefono: String, email: String) {

    private var _imgAvatar: Int = 0
    private var _nombre: String = ""
    private var _apellido: String = ""
    private var _telefono: String = ""
    private var _email:String = ""

    init {
        this._imgAvatar = imgAvatar
        this._nombre = nombre
        this._apellido = apellido
        this._telefono = telefono
        this._email = email
    }
    fun getNombre(): String {
        return this._nombre
    }
    fun getApellido(): String {
        return this._apellido
    }

    fun getNombreCompleto(): String {
        return this._nombre + " " + this._apellido
    }

    fun getTelefono(): String {
        return  this._telefono
    }

    fun getImgAvatar(): Int {
        return this._imgAvatar
    }

    fun getEmail(): String {
        return this._email
    }
}