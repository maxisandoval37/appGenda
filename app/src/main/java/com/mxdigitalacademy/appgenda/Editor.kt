package com.mxdigitalacademy.appgenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class Editor : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var idContactoActual:Int = 0

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->  {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hablitraBotonVolver(){
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun iniciarToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = ""
        setSupportActionBar(toolbar)
        hablitraBotonVolver()
    }

    private fun setearInfoInputsTexts(){
        val nombreEditor = findViewById<EditText>(R.id.etNombreEditor)
        val apellidoEditor = findViewById<EditText>(R.id.etApellidoEditor)
        val tel1Editor = findViewById<EditText>(R.id.etTelPrincipalEditor)
        val tel2Editor = findViewById<EditText>(R.id.etTelSecundarioEditor)
        val emailEditor = findViewById<EditText>(R.id.etEmailEditor)

        nombreEditor.setText(MainActivity.listaObjContactos[idContactoActual].getNombre(),TextView.BufferType.EDITABLE)
        apellidoEditor.setText(MainActivity.listaObjContactos[idContactoActual].getApellido(),TextView.BufferType.EDITABLE)
        tel1Editor.setText(MainActivity.listaObjContactos[idContactoActual].getTelefonoPrincipal(),TextView.BufferType.EDITABLE)
        tel2Editor.setText(MainActivity.listaObjContactos[idContactoActual].getTelefonoSecundario(),TextView.BufferType.EDITABLE)
        emailEditor.setText(MainActivity.listaObjContactos[idContactoActual].getEmail(),TextView.BufferType.EDITABLE)
    }

    fun guardarCambios(){

    }

    private fun accionBotonCancelar(){
        val boton = findViewById<Button>(R.id.btnCancelarEditor)

        boton.setOnClickListener {
            Toast.makeText(this,"Acción cancelada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        idContactoActual= intent.getStringExtra("ID_EDITOR")?.toInt()!!
        iniciarToolbar()
        setearInfoInputsTexts()
        guardarCambios()
        accionBotonCancelar()
    }
}