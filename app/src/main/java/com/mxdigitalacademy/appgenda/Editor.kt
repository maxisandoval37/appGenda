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
        val boton = findViewById<Button>(R.id.btnCancelarEditor)

        boton.setOnClickListener {
            Toast.makeText(this,"Acción cancelada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        iniciarToolbar()
        setearInfoInputsTexts()
        guardarCambios()
        accionBotonCancelar()
    }
}