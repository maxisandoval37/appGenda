package com.mxdigitalacademy.appgenda.gestionDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mxdigitalacademy.appgenda.modelo.ContactosSchema

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, ContactosSchema.Companion.Entrada.nombreTabla,null,1) {

    companion object{
        private const val datosTablas = " ("+ContactosSchema.Companion.Entrada.columnaTelPrincipal+" TEXT PRIMARY KEY, "+
                ContactosSchema.Companion.Entrada.columnaImgAvatar + " TEXT, " +
                ContactosSchema.Companion.Entrada.columnaNombre + " TEXT, " +
                ContactosSchema.Companion.Entrada.columnaApellido + " TEXT, " +
                ContactosSchema.Companion.Entrada.columnaTelefonoS + " TEXT, " +
                ContactosSchema.Companion.Entrada.columnaEmail + " TEXT)"

        const val createContactosTablas = "CREATE TABLE "+ContactosSchema.Companion.Entrada.nombreTabla + datosTablas
        const val removeContactosTablas = "DROP TABLE IF EXISTS"+ContactosSchema.Companion.Entrada.nombreTabla
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createContactosTablas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //Se ejecuta cuando necesitamos actualizar la version de la db
        db?.execSQL(removeContactosTablas)
        onCreate(db)
    }
}