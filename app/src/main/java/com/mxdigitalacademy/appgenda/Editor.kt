package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Editor : AppCompatActivity() {

    private fun setearInfoInputsTexts(){
        val nombreEditor = findViewById<TextView>(R.id.etApellidoEditor)
        val apellidoEditor = findViewById<TextView>(R.id.etApellidoEditor)
        val tel1Editor = findViewById<TextView>(R.id.etTelPrincipalEditor)
        val tel2Editor = findViewById<TextView>(R.id.etTelSecundarioEditor)
        val emailEditor = findViewById<TextView>(R.id.etEmailEditor)

        val idContactoActual:Int = intent.getStringExtra("ID_EDITOR")?.toInt() ?: 0

        nombreEditor.text = MainActivity.listaObjContactos[idContactoActual].getNombre()
        apellidoEditor.text = MainActivity.listaObjContactos[idContactoActual].getApellido()
        tel1Editor.text = MainActivity.listaObjContactos[idContactoActual].getTelefonoPrincipal()
        tel2Editor.text = MainActivity.listaObjContactos[idContactoActual].getTelefonoSecundario()
        emailEditor.text = MainActivity.listaObjContactos[idContactoActual].getEmail()
    }

    fun guardarCambios(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        setearInfoInputsTexts()
        guardarCambios()
    }
}