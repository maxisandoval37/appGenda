package com.mxdigitalacademy.appgenda

class ObjContacto(imgAvatar: Int, nombre: String, apellido: String, telefonoP: String, telefonoS: String?, email: String) {

    private var _imgAvatar: Int = 0
    private var _nombre: String = ""
    private var _apellido: String = ""
    private var _telefonoPrincipal: String = ""
    private var _telefonoSecundario: String? = ""
    private var _email: String = ""

    init {
        this._imgAvatar = imgAvatar
        this._nombre = nombre
        this._apellido = apellido
        this._telefonoPrincipal = telefonoP
        this._telefonoSecundario = telefonoS
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

    fun getTelefonoPrincipal(): String {
        return  this._telefonoPrincipal
    }

    fun getTelefonoSecundario(): String? {
        return  this._telefonoSecundario
    }

    fun getImgAvatar(): Int {
        return this._imgAvatar
    }

    fun getEmail(): String {
        return this._email
    }

    fun setImgAvatar(patch: Int){
        this._imgAvatar = patch
    }

    fun setNombre(nombre: String){
        this._nombre = nombre
    }

    fun setApellido(apellido: String){
        this._apellido = apellido
    }

    fun setTelPrincipal(telPrin: String){
        this._telefonoPrincipal = telPrin
    }

    fun setTelSecundario(telSecun: String?){
        this._telefonoSecundario = telSecun
    }

    fun setEmail(email: String){
        this._email = email
    }
}