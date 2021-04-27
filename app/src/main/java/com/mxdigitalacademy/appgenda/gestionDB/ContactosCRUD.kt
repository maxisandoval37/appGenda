package com.mxdigitalacademy.appgenda.gestionDB

import android.content.ContentValues
import android.content.Context
import com.mxdigitalacademy.appgenda.modelo.ContactosSchema
import com.mxdigitalacademy.appgenda.modelo.ObjContacto

class ContactosCRUD(context: Context) {
    private var _context: Context? = null
    private var _helper: DatabaseHelper? = null
    private var _nombreTabla = ContactosSchema.Companion.Entrada.nombreTabla

    init {
        this._context = context
        this._helper = DatabaseHelper(_context!!)
    }

    private fun mapeoColumnas(contacto: ObjContacto): ContentValues{
        val values = ContentValues()
        values.put(ContactosSchema.Companion.Entrada.columnaTelPrincipal,contacto.getTelefonoPrincipal())
        values.put(ContactosSchema.Companion.Entrada.columnaImgAvatar,contacto.getImgAvatar())
        values.put(ContactosSchema.Companion.Entrada.columnaNombre,contacto.getNombre())
        values.put(ContactosSchema.Companion.Entrada.columnaApellido,contacto.getApellido())
        values.put(ContactosSchema.Companion.Entrada.columnaTelefonoS,contacto.getTelefonoSecundario())
        values.put(ContactosSchema.Companion.Entrada.columnaEmail,contacto.getEmail())

        return values
    }

    fun newContacto(contacto: ObjContacto){
        val db = _helper?.writableDatabase
        val newRowId = db?.insert(this._nombreTabla,null,mapeoColumnas(contacto))

        db?.close()
    }

    fun getContactos(): ArrayList<ObjContacto>{
        val alumnos: ArrayList<ObjContacto> = ArrayList()
        val db = _helper?.readableDatabase!!
        val columnaTelPrincipal = ContactosSchema.Companion.Entrada.columnaTelPrincipal
        val columnaImgAvatar = ContactosSchema.Companion.Entrada.columnaImgAvatar
        val columnaNombre = ContactosSchema.Companion.Entrada.columnaNombre
        val columnaApellido = ContactosSchema.Companion.Entrada.columnaApellido
        val columnaTelefonoS = ContactosSchema.Companion.Entrada.columnaTelefonoS
        val columnaEmail = ContactosSchema.Companion.Entrada.columnaEmail

        val columnas = arrayOf(columnaTelPrincipal,columnaImgAvatar,columnaNombre,columnaApellido,columnaTelefonoS,columnaEmail)

        val c = db.query(this._nombreTabla, columnas, null, null, null, null, null)

        while (c?.moveToNext()!!){
            alumnos.add(
                ObjContacto(
                    c.getString(c.getColumnIndexOrThrow(columnaTelPrincipal)),
                    c.getString(c.getColumnIndexOrThrow(columnaImgAvatar)),
                    c.getString(c.getColumnIndexOrThrow(columnaNombre)),
                    c.getString(c.getColumnIndexOrThrow(columnaApellido)),
                    c.getString(c.getColumnIndexOrThrow(columnaTelefonoS)),
                    c.getString(c.getColumnIndexOrThrow(columnaEmail))))
        }

        db.close()
        return alumnos
    }

    fun getContactosID(id: String): ObjContacto?{
        var alumno: ObjContacto? = null
        val db = _helper?.readableDatabase!!
        val columnaTelPrincipal = ContactosSchema.Companion.Entrada.columnaTelPrincipal
        val columnaImgAvatar = ContactosSchema.Companion.Entrada.columnaImgAvatar
        val columnaNombre = ContactosSchema.Companion.Entrada.columnaNombre
        val columnaApellido = ContactosSchema.Companion.Entrada.columnaApellido
        val columnaTelefonoS = ContactosSchema.Companion.Entrada.columnaTelefonoS
        val columnaEmail = ContactosSchema.Companion.Entrada.columnaEmail

        val columnas = arrayOf(columnaTelPrincipal,columnaNombre)

        val c = db.query(this._nombreTabla, columnas, "telefonoP = ?", arrayOf(id), null, null, null)

        while (c.moveToNext()){
            alumno = ObjContacto(
                c.getString(c.getColumnIndexOrThrow(columnaTelPrincipal)),
                c.getString(c.getColumnIndexOrThrow(columnaImgAvatar)),
                c.getString(c.getColumnIndexOrThrow(columnaNombre)),
                c.getString(c.getColumnIndexOrThrow(columnaApellido)),
                c.getString(c.getColumnIndexOrThrow(columnaTelefonoS)),
                c.getString(c.getColumnIndexOrThrow(columnaEmail)))
        }

        db.close()
        return alumno
    }

    fun updateContacto(contacto: ObjContacto){
        val db = _helper?.writableDatabase!!

        db.update(this._nombreTabla,mapeoColumnas(contacto),"telefonoP = ?", arrayOf(contacto.getTelefonoPrincipal()))
        db.close()
    }

    fun deleteContacto(contacto: ObjContacto){
        val db = _helper?.writableDatabase!!
        db.delete(this._nombreTabla,"telefonoP = ?", arrayOf(contacto.getTelefonoPrincipal()))

        db.close()
    }
}