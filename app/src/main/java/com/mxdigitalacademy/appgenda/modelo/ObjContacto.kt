package com.mxdigitalacademy.appgenda.modelo

class ObjContacto(telefonoP: String, imgAvatar: String, nombre: String, apellido: String, telefonoS: String?, email: String) {

    private var _telefonoPrincipal: String = ""
    private var _imgAvatar: String = "0"
    private var _nombre: String = ""
    private var _apellido: String = ""
    private var _telefonoSecundario: String? = ""
    private var _email: String = ""

    init {
        this._telefonoPrincipal = telefonoP
        this._imgAvatar = imgAvatar
        this._nombre = nombre
        this._apellido = apellido
        this._telefonoSecundario = telefonoS
        this._email = email
    }

    companion object{
        fun datosValidos(nombre:String, apellido: String, tel1:String, tel2:String, email:String): Boolean{
            when{
                nombre.length >15 -> throw IllegalArgumentException("El Nombre no puede tener más de 15 caracteres")
                apellido.length >15 -> throw IllegalArgumentException("El Apellido no puede tener más de 15 caracteres")
                tel1.length >15 -> throw IllegalArgumentException("El 1° Teléfono no puede tener más de 15 digitos")
                tel2.length >15 -> throw IllegalArgumentException("El 2° Teléfono no puede tener más de 15 digitos")
                email.length >30 -> throw IllegalArgumentException("El Email no puede tener más de 30 caracteres")
            }
            return true
        }
    }

    fun getTelefonoPrincipal(): String {
        return  this._telefonoPrincipal
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

    fun getTelefonoSecundario(): String? {
        return  this._telefonoSecundario
    }

    fun getImgAvatar(): String {
        return this._imgAvatar
    }

    fun getEmail(): String {
        return this._email
    }

    fun setTelPrincipal(telPrin: String){
        if (telPrin.length <= 15)
            this._telefonoPrincipal = telPrin
        else
            throw IllegalArgumentException("El 1° Teléfono no puede tener más de 15 digitos")
    }

    fun setImgAvatar(patch: String){
        this._imgAvatar = patch
    }

    fun setNombre(nombre: String){
        if (nombre.length <= 15)
            this._nombre = nombre
        else
            throw IllegalArgumentException("El Nombre no puede tener más de 15 caracteres")
    }

    fun setApellido(apellido: String){
        if (apellido.length <= 15)
            this._apellido = apellido
        else
            throw IllegalArgumentException("El Apellido no puede tener más de 15 caracteres")
    }

    fun setTelSecundario(telSecun: String?){
        if (!telSecun.isNullOrEmpty()){
            if (telSecun.toString().length <= 15)
                this._telefonoSecundario = telSecun
            else
                throw IllegalArgumentException("El 2° Teléfono no puede tener más de 15 digitos")
        }
        else
            this._telefonoSecundario = telSecun
    }

    fun setEmail(email: String){
        if (email.length <= 30)
            this._email = email
        else
            throw IllegalArgumentException("El Email no puede tener más de 30 caracteres")
    }
}