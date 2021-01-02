package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Editor : AppCompatActivity() {

    private fun setearInfoInputsTexts(){
        val nombreEditor = findViewById<TextView>(R.id.etNombreEditor)
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

    private fun accionBotonCancelar(){
        val boton = findViewById<Button>(R.id.btnCancelar)

        boton.setOnClickListener {
            Toast.makeText(this,"Acción cancelada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        setearInfoInputsTexts()
        guardarCambios()
        accionBotonCancelar()
    }
}