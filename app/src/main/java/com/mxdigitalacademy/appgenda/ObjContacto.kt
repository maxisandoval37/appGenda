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
        if (nombre.length <= 10)
            this._nombre = nombre
        else
            throw IllegalArgumentException("El Nombre no puede tener más de 10 caracteres")
    }

    fun setApellido(apellido: String){
        if (apellido.length <= 10)
            this._apellido = apellido
        else
            throw IllegalArgumentException("El Apellido no puede tener más de 10 caracteres")
    }

    fun setTelPrincipal(telPrin: String){
        if (telPrin.length <= 10)
            this._telefonoPrincipal = telPrin
        else
            throw IllegalArgumentException("El 1° Teléfono no puede tener más de 10 digitos")
    }

    fun setTelSecundario(telSecun: String?){
        if (!telSecun.isNullOrEmpty()){
            if (telSecun.toString().length <= 10)
                this._telefonoSecundario = telSecun
            else
                throw IllegalArgumentException("El 2° Teléfono no puede tener más de 10 digitos")
        }
        else
            this._telefonoSecundario = telSecun
    }

    fun setEmail(email: String){
        if (email.length <= 15)
            this._email = email
        else
            throw IllegalArgumentException("El Email no puede tener más de 15 caracteres")
    }
}